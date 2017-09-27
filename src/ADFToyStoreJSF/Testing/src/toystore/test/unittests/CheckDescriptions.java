/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/Testing/src/toystore/test/unittests/CheckDescriptions.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.test.unittests;
import java.util.Hashtable;
import junit.framework.TestCase;
import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import toystore.test.fixtures.ToyStoreConnectFixture;
/**
 * Shopping Cart Tests
 * 
 * @author Steve Muench
 */
public class CheckDescriptions extends TestCase {
  private static Hashtable h = new Hashtable(23);

  static {
    h.put("A-BIL-3", new Integer(94));
    h.put("A-DIC-1", new Integer(104));
    h.put("A-PIN-4", new Integer(56));
    h.put("A-POK-2", new Integer(105));
    h.put("G-BAL-4", new Integer(56));
    h.put("G-CHE-1", new Integer(105));
    h.put("G-DAR-2", new Integer(105));
    h.put("G-JIG-6", new Integer(66));
    h.put("G-MAR-3", new Integer(103));
    h.put("G-PUZ-5", new Integer(43));
    h.put("M-CAB-1", new Integer(161));
    h.put("M-ROC-2", new Integer(166));
    h.put("P-BAL-1", new Integer(113));
    h.put("P-PIN-2", new Integer(141));
    h.put("T-BEA-10", new Integer(65));
    h.put("T-DIN-3", new Integer(199));
    h.put("T-DOL-11", new Integer(123));
    h.put("T-PUM-8", new Integer(77));
    h.put("T-ROB-1", new Integer(144));
    h.put("T-RUB-2", new Integer(171));
    h.put("T-STR-7", new Integer(52));
    h.put("T-WIN-4", new Integer(88));
    h.put("T-YOY-9", new Integer(82));
  }

  ToyStoreConnectFixture fixture1 = new ToyStoreConnectFixture();

  public CheckDescriptions(String sTestName) {
    super(sTestName);
  }

  /**
   * Test using the shopping cart
   */
  public void testGettingDescriptions() {
    ApplicationModule am = fixture1.getApplicationModule();
    ViewObject vo = am.findViewObject("FindProducts");
    vo.setNamedWhereClauseParam("ProductName", null);
    vo.executeQuery();
    int z = 0;
    while (vo.hasNext()) {
      Row r = vo.next();
      String id = (String) r.getAttribute("Productid");
      int expectedLength = ((Integer) h.get(id)).intValue();
      String description = (String) r.getAttribute("Description");
      int actualLength = description.length();
      assertEquals("Description for " + id + " has wrong length.",
        expectedLength, actualLength);
      System.out.println(id + "," + description);
    }
  }
  public void setUp() throws Exception {
    fixture1.setUp();
  }
  public void tearDown() throws Exception {
    fixture1.tearDown();
  }
}
