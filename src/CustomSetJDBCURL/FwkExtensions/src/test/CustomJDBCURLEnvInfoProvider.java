/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import java.util.Hashtable;
import oracle.jbo.client.Configuration;
import oracle.jbo.common.ampool.EnvInfoProvider;

public class CustomJDBCURLEnvInfoProvider implements EnvInfoProvider  {
  /**
   * NOTE: You could modify this example to read this connection
   * ----  information from some alternative location such as a properties
   *       file in the classpath, a custom repository, or something else.
   *       
   *       The security of your deployment obviously improves if you are
   *       working with encrypted password information when you implement
   *       your solution based on this example. The more interesting
   *       alternative source and eventual decrypting of the stored password
   *       are left as exercises for the reader.
   *       
   * NOTE: You should set the value of the jbo.server.internal_connection
   * ----  configuration property to a different, fully-qualified JDBC 
   *       URL if you do not want it to use the same account as the
   *       application user.
   */
  private String mJDBCUserName = "scott"; 
  private String mJDBCPassword = "tiger";
  private String mJDBCURL = "jdbc:oracle:thin:@localhost:1521:ORCL";
  
  public CustomJDBCURLEnvInfoProvider() {}   
  public Object getInfo(String propName, Object object) {
    if (object instanceof Hashtable) {
      Hashtable connectionEnv = (Hashtable)object;
      if(mJDBCUserName != null) {
        connectionEnv.put(Configuration.DB_USERNAME_PROPERTY,mJDBCUserName);
      }
      if(mJDBCPassword != null) {
        connectionEnv.put(Configuration.DB_PASSWORD_PROPERTY,mJDBCPassword);
      }
      if(mJDBCURL != null) {
        connectionEnv.put(Configuration.DB_CONNECT_STRING_PROPERTY,mJDBCURL);
      }
    }
    return null;                                                                         
  }
  public void modifyInitialContext(Object initialContext) {}
  public int getNumOfRetries() { return 0; }
}
