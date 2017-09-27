/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.binding;
import oracle.adf.model.servlet.ADFBindingFilter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import oracle.jbo.uicli.mom.JUMetaObjectManager;

public class MyADFBindingFilter extends ADFBindingFilter  {
  public void init(FilterConfig filterConfig) throws ServletException {
    super.init(filterConfig);
    JUMetaObjectManager.setControlDefFactory(new MyCustomBindingDefFactoryImpl());
    System.out.println("### Initialized Custom ADF Binding Def Factory Impl ###");
  }
}
