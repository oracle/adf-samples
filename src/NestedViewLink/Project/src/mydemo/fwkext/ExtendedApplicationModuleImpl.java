/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package mydemo.fwkext;


import java.util.Iterator;
import java.util.Map;

import oracle.jbo.ApplicationModule;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ApplicationModuleImpl;
public class ExtendedApplicationModuleImpl extends ApplicationModuleImpl {
  /*
   * Automatically create runtime view link instances between master
   * VO instance in the current application module, and detail VO instance
   * whose name is provided in a custom property matching the name of the
   * master VO instance. The value of the custom property is expected to
   * be of the form "fully.qualified.viewlinkdefname,detailViewInstanceName"
   * By using a fully-qualified *INSTANCE* name, for example
   * "MyNestedAM1.MyViewName1" the detail VO instance can be in a nested
   * Application Module instance as well.
   */
   protected void create() {
       super.create();
       Map m = getProperties();
       if (m != null) {
           Iterator it = m.keySet().iterator();
           while (it.hasNext()) {
               String propertyName = (String)it.next();
               ViewObject master = findViewObject(propertyName);
               if (master != null) {
                   String propertyValue = (String)m.get(propertyName);
                   if (propertyValue != null) {
                       int commaPos = propertyValue.indexOf(',');
                       if (commaPos > 1) {
                           String viewLinkDefName = 
                               propertyValue.substring(0, commaPos);
                           String viewInstanceName = 
                               propertyValue.substring(commaPos + 1);
                           ViewObject detail = 
                               findViewObject(viewInstanceName);
                           if (detail != null) {
                               ApplicationModule amToCreateViewLinkIn = this;
                               if (detail.getApplicationModule() != master.getApplicationModule()) {
                                   amToCreateViewLinkIn = detail.getApplicationModule();
                               }
                               amToCreateViewLinkIn.createViewLink(null, viewLinkDefName, master, 
                                                                   detail);
                           }
                       }
                   }
               }
           }
       }
   }
}
