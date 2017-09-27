/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/xsql/ADFViewObject.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.fwk.xsql;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import oracle.adf.model.bc4j.DCJboDataControl;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.jbo.ViewObject;
import oracle.jbo.XMLInterface;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.xsql.XSQLActionHandlerImpl;
import oracle.xml.xsql.XSQLPageRequest;
import oracle.xml.xsql.XSQLServletPageRequest;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
/**
 * Custom Oracle XSQL Pages action handler that allows an XSQL page
 * to retrieve XML data from an ADF view object, sharing the same
 * ADF binding context and binding container used by the ADF DataAction
 * 
 * Currently only ADF Business Components view objects support the
 * automatic XML rendering, so this class tests to make sure that
 * the data control is an instance of DCJboDataControl before trying
 * to perform the XML rendering.
 * 
 * @author Steve Muench
 */
public class ADFViewObject extends XSQLActionHandlerImpl {
  /**
   * Overrides the default implementation of handleAction 
   * to define the behavior of this custom action handler.
   * 
   * @param result XML node to which any created content should be appended 
   *        as a DOM child node.
   * @throws java.sql.SQLException if database error is encountered.
   */
  public void handleAction(Node result) throws SQLException {
    XSQLPageRequest req = getPageRequest();
    if (req.getRequestType().equalsIgnoreCase("servlet")) {
      XSQLServletPageRequest xsqlHttpReq = (XSQLServletPageRequest) req;
      HttpServletRequest servletReq = xsqlHttpReq.getHttpServletRequest();
      DCBindingContainer bc = (DCBindingContainer) servletReq.getAttribute(
          "bindings");
      if (bc != null) {
        Element action = getActionElement();
        String iteratorBinding = getAttributeAllowingParam("iterator", action);
        DCIteratorBinding iter = bc.findIteratorBinding(iteratorBinding);
        DCDataControl dc = iter.getDataControl();
        if (dc instanceof DCJboDataControl) {
          ViewObject vo = iter.getViewObject();
          if (vo != null) {
            Node n = vo.writeXML(-1, XMLInterface.XML_OPT_ALL_ROWS);
            n = ((XMLDocument) result.getOwnerDocument()).adoptNode(n);
            result.appendChild(n);
          }
        }
      }
    }
  }
}
