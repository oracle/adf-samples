/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view;

import oracle.adf.model.BindingContext;
import oracle.adf.model.DataControlFactory;
import oracle.adf.model.bc4j.DCJboDataControl;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCDataControlManagement;
import oracle.adf.model.binding.DCDataControlReference;
import oracle.adf.model.servlet.HttpBindingContext;

import oracle.jbo.ApplicationModule;

import oracle.jbo.common.ampool.SessionCookieFactory;

import oracle.jbo.uicli.mom.JUMetaObjectManager;

import java.io.IOException;

import java.security.Principal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class InsureSystemDataControlFilter implements Filter {
    private static final String REQUEST_FILTER_ENTERED = "view.MyFilter.__request_filtered_entered__";
    private static final String SYSTEM_DC_NAME = "SystemDataControl";
    private static final String SYSTEM_DC_CONFIG_FILE = "System.cpx";
    private static final String STATELESS_AM_PROPERTY_NAME = "ReleaseStateless";
    
    private FilterConfig _filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        _filterConfig = filterConfig;
    }

    public void destroy() {
        _filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        boolean firstTimeEntryIntoFilterDuringSameRequest = false;

        /*
         * do this only once as in controller scenario we come in more than
         * once, for .do, for .jsp...
         */
        if (servletRequest.getAttribute(REQUEST_FILTER_ENTERED) == null) {
            firstTimeEntryIntoFilterDuringSameRequest = true;

            /*
             * set a request variable that indicates we've initialized the
             * bindingcontext and reset the inputstates on all bindingcontainers
             * thus far initialzied.
             */
            servletRequest.setAttribute(REQUEST_FILTER_ENTERED,
                REQUEST_FILTER_ENTERED);
        }

        try {
            /*
             * By checking this flag, we guarantee that this code only runs
             * for the outermost filter invocation in case the filter is
             * entered multiple times during the span of a single HTTP request.
             */        
            if (firstTimeEntryIntoFilterDuringSameRequest) {
                BindingContext bindingContext = HttpBindingContext.getContext(servletRequest);

                if (!bindingContext.containsKey(SYSTEM_DC_NAME)) {
                    JUMetaObjectManager.loadCpx(SYSTEM_DC_CONFIG_FILE,
                        initEnvironment(servletRequest, bindingContext));

                    /*
                     * search for the new DataControl and invoke beginRequest.
                     * can't use get here because it will de-reference the DC.
                     * a  linear search on the first request should be ok.
                     */
                    Iterator dcIterator = bindingContext.dataControlsIterator();

                    while (dcIterator.hasNext()) {
                        Object dc = dcIterator.next();

                        if (dc instanceof DCDataControlReference &&
                                SYSTEM_DC_NAME.equals(
                                    ((DCDataControlReference) dc).getName())) {
                            HashMap requestCtx = new HashMap(2);
                            requestCtx.put(BindingContext.HTTP_REQUEST, request);
                            requestCtx.put(BindingContext.HTTP_RESPONSE,
                                response);

                            ((DCDataControlManagement) dc).beginRequest(requestCtx);

                            requestCtx = null;

                            break;
                        }
                    }
                }

                /*
                 * NOTE: Do Any Other Custom beginRequest processing here
                 */
            }

            chain.doFilter(request, response);
        } finally {
            /*
             * By checking this flag, we guarantee that this code only runs
             * for the outermost filter invocation in case the filter is
             * entered multiple times during the span of a single HTTP request.
             */
            if (firstTimeEntryIntoFilterDuringSameRequest) {
               /*
                 * NOTE: Do Any Other Custom endRequest processing here
                 */
                BindingContext bindingContext = HttpBindingContext.getContext(servletRequest);
                DCDataControl dc = bindingContext.findDataControl(SYSTEM_DC_NAME);
                /*
                 * If the system data control is an ADF Application Module,
                 * then test to see if it has a custom property named
                 * "ReleaseStateless" defined, and if so, mark the data control
                 * to be released stateless.
                 */
                if (dc instanceof DCJboDataControl) {
                    ApplicationModule am = (ApplicationModule) dc.getDataProvider();
                    if (am.getProperty(STATELESS_AM_PROPERTY_NAME) != null) {
                        dc.resetState();
                    }
                }
            }
        }
    }

    /*
     * Initialize the hashmap of environment parameters, including the
     * existing binding context and session.
     */
    private HashMap initEnvironment(HttpServletRequest servletRequest,
        BindingContext bindingContext) {
        // initialize the SystemDataControl
        HashMap map = new HashMap(4);
        map.put(DataControlFactory.APP_PARAMS_BINDING_CONTEXT, bindingContext);
        map.put(DataControlFactory.APP_PARAM_HTTP_SESSION,
            servletRequest.getSession(true));

        /*
         * Put the request in cookie properties. HttpSessionCookieFactory
         * retrieves request from cookie properties. If user principal is
         * not null (i.e., authenticated) it will get set into
         * SessionCookie environment.
         */
        Properties requestCtx = new Properties();
        Principal userPrincipal = null;

        try {
            userPrincipal = servletRequest.getUserPrincipal();
        } catch (NoSuchMethodError e) {
            // ignore.  servlet 2.0 compatibility issue.
        }

        if (userPrincipal != null) {
            requestCtx.put(SessionCookieFactory.PROP_USER_PRINCIPAL_KEY,
                userPrincipal);
        }

        map.put(DataControlFactory.APP_PARAM_REQUEST_CONTEXT, requestCtx);

        return map;
    }
}
