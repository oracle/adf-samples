/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/model/service/ToyStoreApplicationModuleImpl.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.fwk.model.service;
import java.util.Dictionary;
import oracle.jbo.Session;
import oracle.jbo.server.ApplicationModuleImpl;

/**
 * Extends the base ADF application module implementation class to
 * add support for retrieving configuration property values.
 * 
 * @author Steve Muench
 */
public class ToyStoreApplicationModuleImpl extends ApplicationModuleImpl  {
  public ToyStoreApplicationModuleImpl() {}
  /**
   * Retrieves the value of an ADF Business Components configuration 
   * property at runtime, allowing for a Java System property of the
   * same name to provide a "fallback" value in case the desired 
   * property is not found in the configuration's property list.
   * 
   * @param propertyName Name of the configuration property
   * @return The value of the configuration property, or Java System property
   *         if the value of the configuration property is null.
   */
  public String getConfigurationProperty(String propertyName) {
    Session session = getSession();
    String retVal = null;
    if (session != null) {
      Dictionary configProperties = session.getEnvironment();
      if (configProperties != null) {
        Object objVal = configProperties.get(propertyName);
        if (objVal != null) {
          if (objVal instanceof String) {
            retVal = (String)objVal;
          }
        }
      }
    }
    /*
     * If there is no AM Configuration parameter by that name,
     * then fallback to looking for a Java System property of the
     * same name, just as ADF Business Components does for its
     * built-in configuration properties.
     */
    if (retVal == null) {
      retVal = System.getProperty(propertyName);
    }
    return retVal;
  }
}
