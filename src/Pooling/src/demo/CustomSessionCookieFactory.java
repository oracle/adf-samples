/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import oracle.jbo.client.Configuration;
import oracle.jbo.common.ampool.ApplicationPool;
import oracle.jbo.common.ampool.EnvInfoProvider;
import oracle.jbo.common.ampool.SessionCookie;
import oracle.jbo.http.HttpSessionCookieFactory;
public class CustomSessionCookieFactory extends HttpSessionCookieFactory {
  public CustomSessionCookieFactory() {}

  public SessionCookie createSessionCookie(String applicationId,
    String sessionId, ApplicationPool pool, Properties properties) {
    // use the HttpSessionCookieFactory to create the cookie.  This will
    // return an instance of CustomSessionCookie because getSessionCookieClass
    // was overridden below.
    CustomSessionCookie cookie = (CustomSessionCookie) super.createSessionCookie(applicationId,
        sessionId, pool, properties);
    HttpServletRequest request = null;
    if (properties != null) {
      request = (HttpServletRequest) properties.get(PROP_HTTP_REQUEST);
    }
    if (request != null) {
      HttpSession session = request.getSession(false);
      if (session != null) {
        String dbUserName = (String) session.getAttribute(Configuration.DB_USERNAME_PROPERTY);
        String dbPassword = (String) session.getAttribute(Configuration.DB_PASSWORD_PROPERTY);

        // if a JDBC username and password have been cached in the session
        // context then use that JDBC username and password to establish
        // the database connection.
        if ((dbUserName != null) && (dbPassword != null)) {
          EnvInfoProvider provider = new CustomEnvInfoProvider(dbUserName,
              dbPassword);
          cookie.setEnvInfoProvider(provider);
        }
      }
    }
    return cookie;
  }
  public Class getSessionCookieClass() {
    return CustomSessionCookie.class;
  }
}
