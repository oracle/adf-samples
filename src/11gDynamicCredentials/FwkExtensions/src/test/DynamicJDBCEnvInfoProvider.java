/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import java.util.Hashtable;
import oracle.jbo.client.Configuration;
import oracle.jbo.common.ampool.EnvInfoProvider;
/**
 * This custom EnvInfoProvider implemenation is used by the
 * DynamicJDBCSessionCookieFactory, which constructs an instance of
 * this class, passing in the username/password/connection information
 * to use for the dynamic credentials.
 */
public class DynamicJDBCEnvInfoProvider  implements oracle.jbo.common.ampool.EnvInfoProvider  {
  private String mJDBCUserName = null; 
  private String mJDBCPassword = null;
  private String mJDBCURL = null;
  public DynamicJDBCEnvInfoProvider(String jdbcUserName, String jdbcPassword,
                                    String jdbcURL) {
     mJDBCUserName = jdbcUserName; 
     mJDBCPassword = jdbcPassword;
     mJDBCURL      = jdbcURL;
  }   
  public Object getInfo(String info, Object connEnvironment)
  {
    if(mJDBCUserName != null)                                                                
    {
      ((Hashtable)connEnvironment).put(Configuration.DB_USERNAME_PROPERTY, mJDBCUserName);
    }
    if(mJDBCPassword != null)                                                              
    {
      ((Hashtable)connEnvironment).put(Configuration.DB_PASSWORD_PROPERTY, mJDBCPassword);
    }
    if(mJDBCURL != null)
    {
      ((Hashtable) connEnvironment).put(Configuration.DB_CONNECT_STRING_PROPERTY,mJDBCURL);
    }     
     return null;                                                                         
  }

    /**
     * @deprecated {@link #getInfo(String, Object)} should be implemented
     * @since 9.0.2
     */
  public void modifyInitialContext(Object initialContext) {
  }

  public int getNumOfRetries() {
    return 0;
  }
}
