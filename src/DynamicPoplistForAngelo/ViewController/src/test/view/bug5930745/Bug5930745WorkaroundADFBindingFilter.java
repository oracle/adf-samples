/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.bug5930745;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;

import javax.servlet.ServletException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import oracle.adf.model.binding.DefinitionFactory;
import oracle.adf.model.servlet.ADFBindingFilter;

import oracle.jbo.uicli.mom.JUMetaObjectManager;

public class Bug5930745WorkaroundADFBindingFilter extends ADFBindingFilter {
  private static final String ADFMUINAMESPACE = "http://xmlns.oracle.com/adfm/uimodel";
  public void init(FilterConfig filterConfig) throws ServletException {
    super.init(filterConfig);
  }
  public void doFilter(ServletRequest request, ServletResponse response, 
                       FilterChain chain) throws IOException, 
                                                 ServletException {
    DefinitionFactory factory = JUMetaObjectManager.getJUMom().getDefinitionFactory(ADFMUINAMESPACE);
    if (!(factory instanceof Bug5930745WorkaroundBindingDefFactoryImpl) ) {
      JUMetaObjectManager.getJUMom().registerDefinitionFactory(ADFMUINAMESPACE, 
                                                               new Bug5930745WorkaroundBindingDefFactoryImpl());
      
    }
    super.doFilter(request, response, chain);
  }
}
