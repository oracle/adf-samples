/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.test;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.XMLInterface;
import oracle.jbo.client.Configuration;
import oracle.jbo.domain.Number;
import oracle.jbo.server.ViewRowImpl;
import oracle.xml.parser.v2.XMLElement;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
/**
 * Illustrates reading an XML document containing the referenced
 * department name and having our EmpViewRow class automatically
 * convert that into the corresponding foreign key Deptno value.
 * 
 * See the setDname() method in EmpViewRowImpl.java for how this works.
 */
public class TestClient extends ViewRowImpl {
  private static final String XML1 =
    "<EmpView>" +
      "<EmpViewRow>"+
        "<Empno>101</Empno>"+
        "<Ename>STEVE</Ename>"+
        "<Dname>ACCOUNTING</Dname>"+
      "</EmpViewRow>" +
    "</EmpView>";
  private static final String XML2 =
    "<EmpView>" +
      "<EmpViewRow>"+
        "<Empno>101</Empno>"+
        "<Ename>STEVE</Ename>"+
        "<Dname>SALES</Dname>"+
      "</EmpViewRow>" +
    "</EmpView>";   
  public static void main(String[] args) throws Throwable {
    String amDef = "demo.model.TestModule";
    String config = "TestModuleLocal";
    ApplicationModule am = Configuration.createRootApplicationModule(amDef,
        config);
    ViewObject vo = am.findViewObject("EmpView");
    /*
     * We'll only be using this VO for creation and finding by key, let's say...
     */
    vo.setMaxFetchSize(0);
    /*
     * Get a new JAXP document builder
     */
    DocumentBuilder db = DocumentBuilderFactory.newInstance()
                                               .newDocumentBuilder();
    /*
     * ReadXML the XML document in XML1 string variable
     */
    vo.readXML(db.parse(new InputSource(new StringReader(XML1)))
                 .getDocumentElement(), -1);
    am.getTransaction().commit();
    /*
     * ReadXML the XML document in XML2 string variable
     */
    vo.readXML(db.parse(new InputSource(new StringReader(XML2)))
                 .getDocumentElement(), -1);
    am.getTransaction().commit();
    /*
     * Find the EmpView row by key (Empno = 101)
     */
    Row r = vo.findByKey(new Key(new Object[] { new Number(101) }), 1)[0];
    vo.setCurrentRow(r);
    /*
     * Write out the VO row contents as XML
     */
    Element e = (Element) vo.writeXML(1, XMLInterface.XML_OPT_ALL_ROWS);
    /*
     * Print the XML to System.out
     */
    ((XMLElement) e).print(System.out);
    Configuration.releaseRootApplicationModule(am, true);
  }
}
