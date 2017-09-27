/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package system.model.servlet;

import oracle.adf.model.BindingContext;
import oracle.adf.model.DataControlFactory;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCDataControlDef;
import oracle.adf.model.binding.DCDataControlManagement;
import oracle.adf.model.binding.DCDataControlReference;
import oracle.adf.model.servlet.HttpBindingContext;

import oracle.jbo.common.ampool.SessionCookieFactory;

import oracle.jbo.uicli.mom.JUApplicationDefImpl;
import oracle.jbo.uicli.mom.JUMetaObjectManager;

import java.security.Principal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class InsureSystemDataControlFilter
    extends AdditionalBeginOrEndRequestProcessingFilterBase {
    private static final String SYSTEM_DC_NAME = "SystemDataControl";
    private static final String SYSTEM_DC_CONFIG_FILE = "System.cpx";
    private static final String STATELESS_DC_PROPERTY_NAME = "ReleaseMode";
    private static final String STATELESS_DC_PROPERTY_VAL = "Stateless";

    protected void performAdditionalBeginRequestProcessing(
        ServletRequest request, ServletResponse response) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
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
                    requestCtx.put(BindingContext.HTTP_RESPONSE, response);

                    ((DCDataControlManagement) dc).beginRequest(requestCtx);

                    requestCtx = null;

                    break;
                }
            }
        }
    }

    protected void performAdditionalEndRequestProcessing(
        ServletRequest request, ServletResponse response) {
        HttpServletRequest servletRequest = (HttpServletRequest) request;

        BindingContext bindingContext = HttpBindingContext.getContext(servletRequest);
        DCDataControl dc = bindingContext.findDataControl(SYSTEM_DC_NAME);
        String releaseMode = getSystemDataControlParam(STATELESS_DC_PROPERTY_NAME);

        /*
         * If the system data control has a custom data control
         * property named "ReleaseMode" defined, and its value
         * is equal to "Stateless", then mark the data control
         * to be released stateless.
         */
        if (STATELESS_DC_PROPERTY_VAL.equalsIgnoreCase(releaseMode)) {
            dc.resetState();
        }
    }

    private String getSystemDataControlParam(String param) {
        return getDataControlParameter(SYSTEM_DC_NAME, param);
    }

    private String getDataControlParameter(String dcName, String param) {
        JUApplicationDefImpl appDef = (JUApplicationDefImpl) JUMetaObjectManager.getJUMom()
                                                                                .findDefinitionObject("System.cpx",
                JUMetaObjectManager.TYP_DEF_APPLICATION,
                JUApplicationDefImpl.class, false);
        DCDataControlDef dcDef = appDef.findSession(dcName);

        if (dcDef != null) {
            Map params = dcDef.getParameters();

            if ((params != null) && params.containsKey(param)) {
                return (String) params.get(param);
            }
        }

        return null;
    }

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
