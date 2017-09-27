/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.tests;
import java.util.Hashtable;

import oracle.jbo.JboContext;
import oracle.jbo.common.ampool.EnvInfoProvider;

/**
 * Custom EnvInfoProvider to enable JUnit testing of an application module
 * with JAAS security authentication.
 *
 * @author Steve Muench
 * $Id: JUnitFixureLoginInfoProvider.java,v 1.2 2006/04/20 14:34:04 steve Exp $
 */
public class JUnitFixtureLoginEnvInfoProvider implements EnvInfoProvider {
  String mUsername;
  String mPassword;

  /**
   * Constructor.
   *
   * @param userName JAAS username to use for the test
   * @param password JAAS password to use for the test
   */
  public JUnitFixtureLoginEnvInfoProvider(String userName, String password) {
    mUsername = userName;
    mPassword = password;
  }

  /**
   * Implementation of the EnvInfoProvider getInfo() method to force
   * the jbo.security.enforce property to be in the environment, along
   * with the test-supplied JAAS username/password.
   *
   * @param info string key
   * @param environment environment hashtable
   * @return value of string key
   */
  public Object getInfo(String info, Object environment) {
    Hashtable env = (Hashtable)environment;
    /*
        * The environment property below indicates authentication is needed.
        * For ADFBC it is set in ApplicationModule Configuration Properties
        * which goes into bc4j.xcfg file in the "./common" subdirectory
        * of the package where your application module's XML component
        * definition file lives.
        */
    env.put("jbo.security.enforce", "Must");
    env.put(JboContext.SECURITY_PRINCIPAL, mUsername);
    env.put(JboContext.SECURITY_CREDENTIALS, mPassword);
    return null;
  }

  /**
   * Implementing EnvInfoProvider interface.
   * @param environment JNDI environment
   * @deprecated
   */
  @Deprecated
  public void modifyInitialContext(Object environment) {
  }

  /**
   * Implementing EnvInfoProvider interface.
   * @return number of retries allowed
   */
  public int getNumOfRetries() {
    return 1;
  }
}
