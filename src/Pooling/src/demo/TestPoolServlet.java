/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import oracle.jbo.ApplicationModule;
import oracle.jbo.AttributeDef;
import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.common.PropertyConstants;
import oracle.jbo.common.ampool.ApplicationPool;
import oracle.jbo.common.ampool.PoolMgr;
import oracle.jbo.common.ampool.SessionCookie;
import oracle.jbo.http.HttpContainer;
import oracle.jbo.http.HttpSessionCookieFactory;
public class TestPoolServlet extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html";
  public static final String APPLICATION_ID = "1";

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>TestPoolServlet</title></head>");
    out.println("<body>");
    // Configuration name
    String configName = "DemoModuleLocal";

    // Configuration package
    String configPackage = "demo";
    SessionCookie cookie = findSessionCookie(request, request.getSession(true),
        APPLICATION_ID, configPackage, configName);
    ApplicationModule am = null;
    try {
      // Use the session cookie created above to acquire an application module
      // resource.  Do not lock the application module resource for exclusive
      // use by this thread.  Please see the SessionCookie.useApplicationModule
      // javadoc for more information about SessionCookie locking.  In general, 
      // a session cookie lock is not required if the session does not require
      // multi-threaded access to a shared application module instance.
      am = cookie.useApplicationModule(false /* lock resource */);
      // Sample statement which acquires a session cookie lock.
      //    am = mCookie.useApplicationModule(true /* lock resource */);
      // Write the cookie value as a browser cookie.
      cookie.writeValue(response);
      ViewObject empsVO = am.findViewObject("Employees");
      Row empRow = empsVO.next();
      if (empRow == null) {
        // We must have hit the end of the iterator.  Start over again
        empRow = empsVO.first();
      }
      show(rowValues(empRow, empsVO), out);
      // Manage the iterator state created above between session requests.
      cookie.releaseApplicationModule(SessionCookie.SHARED_MANAGED_RELEASE_MODE);
      // Release the lock, but hold the application module resource as reserved
      // for this session.  Only recommended if database state is required between
      // requests.  Use of this release mode may result in excessive memory 
      // consumption and scalability issues. 
      //    cookie.releaseApplicationModule(SessionCookie.RESERVED_MANAGED_RELEASE_MODE);
      // Do not manage the iterator state created above between session requests.
      //    cookie.releaseApplicationModule(SessionCookie.RESERVED_UNMANAGED_RELEASE_MODE);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      // If an ApplicationModule was reserved then be sure to release it
      // before continuing.  Otherwise memory leaks may result.
      // The decision to use STATE_UNMANAGED here is arbitrary.  A "stateful"
      // request may actually want to revert the ApplicationModule state
      // and then perform a STATE_MANAGED release here.
      if (cookie.isApplicationModuleReserved()) {
        cookie.releaseApplicationModule(SessionCookie.SHARED_UNMANAGED_RELEASE_MODE);
      }
      throw new RuntimeException();
    }
    finally {
      ApplicationPool pool = (ApplicationPool) PoolMgr.getInstance()
                                                      .getResourcePool(configPackage +
          "." + configName);
      pool.dumpPoolStatistics(new PrintWriter(out) {
          public void println(String msg) {
            if (msg.indexOf("head:") >= 0) {
              msg = "<b>" + msg.substring("head:".length()) + "</b>";
            }
            super.println("<p>" + msg + "</p>");
          }
        });
      out.println("</body></html>");
      out.close();
    }
  }
  private SessionCookie findSessionCookie(HttpServletRequest request,
    HttpSession session, String applicationId, String configPackage,
    String configName) {
    // Load the pool creation properties with the name of our custom
    // application pool class.
    Properties cookieProps = new Properties();
    cookieProps.put(HttpSessionCookieFactory.PROP_HTTP_REQUEST, request);
    // Specify property values to be used by the application pool.  Please note
    // that these property values could also have been specified in the 
    // application configuration file.
    Properties poolProps = new Properties();
    poolProps.put(PropertyConstants.ENV_AMPOOL_COOKIE_FACTORY_CLASS_NAME,
      "demo.CustomSessionCookieFactory");
    // Find a session cookie in the HttpSession for the specified application
    // id.  If one does not exist this method will create one.
    SessionCookie cookie = HttpContainer.findSessionCookie(session,
        applicationId, configPackage + "." + configName // poolName
        , configPackage, configName, poolProps, cookieProps);
    return cookie;
  }
  private static String rowValues(Row row, ViewObject vo) {
    if (row != null) {
      AttributeDef[] attrDefs = vo.getAttributeDefs();
      StringBuffer sb = new StringBuffer();
      int attrCount = attrDefs.length;
      for (int j = 0; j < attrCount; j++) {
        if (j != 0) {
          sb.append(", ");
        }
        AttributeDef curAttrDef = attrDefs[j];
        boolean isPK = curAttrDef.isPrimaryKey();
        sb.append(isPK ? "(" : "").append(curAttrDef.getName())
          .append(isPK ? ")" : "").append("=").append(isCollectionAttr(
            curAttrDef) ? "((RowSet))" : row.getAttribute(j));
      }
      return sb.toString() + " (" + vo.getName() + " VO)";
    }
    else {
      return "";
    }
  }
  private static void show(String msg, PrintWriter out) {
    out.println("<p>" + msg + "</p>");
  }
  private static boolean isCollectionAttr(AttributeDef attrDef) {
    byte attrKind = attrDef.getAttributeKind();
    return (attrKind == AttributeDef.ATTR_ASSOCIATED_ROWITERATOR) ||
    (attrKind == AttributeDef.ATTR_ASSOCIATED_ROW);
  }
  private static String FormatException(Exception ex) {
    StringBuffer sb = new StringBuffer();
    FormatExceptionStack(ex, sb);
    return sb.toString();
  }
  private static void FormatExceptionStack(Exception ex, StringBuffer sb) {
    Exception curExcep = ex;
    sb.append(ex.getMessage()).append('
');
    if (ex instanceof JboException) {
      Object[] details = ((JboException) ex).getDetails();
      if (details != null) {
        int detailCount = details.length;
        for (int z = 0; z < detailCount; z++) {
          FormatExceptionStack((Exception) details[z], sb);
        }
      }
    }
  }
}
