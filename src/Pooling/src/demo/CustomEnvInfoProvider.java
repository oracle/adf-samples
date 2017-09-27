/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo;
import java.util.Hashtable;
import oracle.jbo.client.Configuration;
import oracle.jbo.common.ampool.EnvInfoProvider;
public class CustomEnvInfoProvider implements EnvInfoProvider {
  private final String mJDBCUserName;
  private final String mJDBCPassword;

  public CustomEnvInfoProvider(String jdbcUserName, String jdbcPassword) {
    mJDBCUserName = jdbcUserName;
    mJDBCPassword = jdbcPassword;
  }

  public Object getInfo(String info, Object connEnvironment) {
    ((Hashtable) connEnvironment).put(Configuration.DB_USERNAME_PROPERTY,
      mJDBCUserName);
    ((Hashtable) connEnvironment).put(Configuration.DB_PASSWORD_PROPERTY,
      mJDBCPassword);
    return null;
  }
  public void modifyInitialContext(Object p0) {
    // deprecated.
  }
  public int getNumOfRetries() {
    return 0;
  }
}
