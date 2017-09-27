/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.*;
import javax.servlet.http.*;
import oracle.jbo.pool.ResourcePool;
import oracle.jbo.pool.ResourcePoolManager;
import oracle.jbo.server.ConnectionPoolManagerFactory;
public class DumpConnectionPoolStatisticsServlet extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  public void doGet(HttpServletRequest request, 
                    HttpServletResponse response) throws ServletException, 
                                                         IOException {
    response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>DumpConnectionPoolStatistics</title></head>");
    out.println("<body>");
    out.println("<table border=0><tr><td><b>NOTE:</b></td><td>This servlet displays full " + 
                "JDBC connection details including passwords! Not recommended for" + 
                "permanent use in a production environment.</td></tr></table>");
    ResourcePoolManager poolMgr = 
      (ResourcePoolManager)ConnectionPoolManagerFactory.getConnectionPoolManager();
    String poolname = request.getParameter("poolname");
    if (poolname == null || poolname.equals("")) {
      Enumeration keys = poolMgr.getResourcePoolKeys();
      if (keys != null) {
        out.println("<h3>List of Active Connection Pools</h3>");
        out.println("<ul>");
        while (keys.hasMoreElements()) {
          String s = (String)keys.nextElement();
          out.println("<li><code><a href='DumpConnectionPoolStatistics?poolname=" + 
                      s + "'>" + s + "</a></code></li>");
        }
        out.println("</ul>");
      } else {
        out.println("No pools.");
      }
    } else {
      out.println("<h3>Pool Statistics for Connection Pool '" + poolname + 
                  "'</h3>");
      out.println("<a href='DumpConnectionPoolStatistics'>Back to Connection Pool List</a>");
      out.println("<hr><blockquote><pre>");
      ResourcePool pool = (ResourcePool)poolMgr.getResourcePool(poolname);
      pool.dumpPoolStatistics(new PrintWriter(out));
      out.println("</pre></blockquote>");
    }
    out.println("</body></html>");
    out.close();
  }
}
