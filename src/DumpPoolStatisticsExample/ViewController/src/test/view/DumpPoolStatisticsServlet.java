/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Enumeration;
import oracle.jbo.common.ampool.ApplicationPool;
import oracle.jbo.common.ampool.PoolMgr;
public class DumpPoolStatisticsServlet extends HttpServlet {
  private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }
  public void doGet(HttpServletRequest request, 
                    HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<head><title>DumpPoolStatistics</title></head>");
    out.println("<body>");
    PoolMgr poolMgr = PoolMgr.getInstance(); 
    String poolname = request.getParameter("poolname");
    if (poolname == null || poolname.equals("")) {
      Enumeration keys = poolMgr.getResourcePoolKeys();
      if (keys != null) {
        out.println("<h3>List of Active Application Module Pools</h3>");
        out.println("<ul>");
        while (keys.hasMoreElements()) {
          String s = (String)keys.nextElement();
          out.println("<li><code><a href='DumpPoolStatistics?poolname="+
          s+"'>"+s+"</a></code></li>");
        }
        out.println("</ul>");
      }
      else {
        out.println("No pools.");
      }
    }
    else {
      out.println("<h3>Pool Statistics for Pool '"+poolname+"'</h3>");
      out.println("<a href='DumpPoolStatistics'>Back to Pool List</a>");
      out.println("<hr><blockquote><pre>");
      ApplicationPool pool =  (ApplicationPool)poolMgr.getResourcePool(poolname);
      pool.dumpPoolStatistics(new PrintWriter(out));
      out.println("</pre></blockquote>");
    }
    out.println("</body></html>");
    out.close();
  }
}
