/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package system.model.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class AdditionalBeginOrEndRequestProcessingFilterBase implements Filter {
    private static final String REQUEST_FILTER_ENTERED = "view.MyFilter.__request_filtered_entered__";
    private FilterConfig _filterConfig = null;

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
                performAdditionalBeginRequestProcessing(request,response);
            }

            chain.doFilter(request, response);
        } finally {
            if (firstTimeEntryIntoFilterDuringSameRequest) {
                /*
                  * NOTE: Do Any Other Custom endRequest processing here
                  */
                performAdditionalEndRequestProcessing(request,response);
            }
        }
    }

    protected abstract void performAdditionalBeginRequestProcessing(ServletRequest request,ServletResponse response);

    protected abstract void performAdditionalEndRequestProcessing(ServletRequest request,ServletResponse response);

    public void init(FilterConfig filterConfig) throws ServletException {
        _filterConfig = filterConfig;
    }

    public void destroy() {
        _filterConfig = null;
    }
}
