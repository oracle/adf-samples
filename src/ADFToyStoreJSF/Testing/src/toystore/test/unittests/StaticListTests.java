/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/Testing/src/toystore/test/unittests/StaticListTests.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.test.unittests;
import junit.framework.TestCase;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import toystore.test.fixtures.ToyStoreConnectFixture;
/**
 * Test the correct functioning of our static Properties-file-based
 * View Objects.
 * 
 * @author Steve Muench
 */
public class StaticListTests extends TestCase {
  ToyStoreConnectFixture fixture1 = new ToyStoreConnectFixture();

  public StaticListTests(String sTestName) {
    super(sTestName);
  }

  /**
   * Test accessing the first and last rows of the CountryList
   */
  public void testAccessingCountryList() {
    ViewObject view = fixture1.getApplicationModule().findViewObject("CountryList");
    assertNotNull(view);
    long expectedRowCount = 194;
    long rowCount = view.getEstimatedRowCount();
    assertEquals("Country count is " + rowCount + " instead of " +
      expectedRowCount, expectedRowCount, rowCount);
    Row first = view.first();
    Row last = view.last();
    String expectedFirst = "AF";
    String expectedLast = "ZW";
    String firstCountry = (String) first.getAttribute("Code");
    String lastCountry = (String) last.getAttribute("Code");
    assertEquals("First country is " + firstCountry + "  not " + expectedFirst,
      expectedFirst, firstCountry);
    assertEquals("Last country is " + lastCountry + " not " + expectedLast,
      expectedLast, lastCountry);
  }
  /**
   * Test accessing the first and last rows of the ShippingOptionsList
   */
  public void testShippingOptionsList() {
    ViewObject view = fixture1.getApplicationModule().findViewObject("ShippingOptionsList");
    assertNotNull(view);
    long expectedRowCount = 4;
    long rowCount = view.getEstimatedRowCount();
    assertEquals("Option count is " + rowCount + " instead of " +
      expectedRowCount, expectedRowCount, rowCount);
    Row first = view.first();
    Row last = view.last();
    String expectedFirst = "MAIL";
    String expectedLast = "FEDEX";
    String firstCode = (String) first.getAttribute("Code");
    String lastCode = (String) last.getAttribute("Code");
    assertEquals("First code is " + firstCode + "  not " + expectedFirst,
      expectedFirst, firstCode);
    assertEquals("Last code is " + lastCode + " not " + expectedLast,
      expectedLast, lastCode);
  }
  /**
   * Test accessing the first and last rows of the CreditCardList
   */
  public void testCreditCardList() {
    ViewObject view = fixture1.getApplicationModule().findViewObject("CreditCardList");
    assertNotNull(view);
    long expectedRowCount = 4;
    long rowCount = view.getEstimatedRowCount();
    assertEquals("Option count is " + rowCount + " instead of " +
      expectedRowCount, expectedRowCount, rowCount);
    Row first = view.first();
    Row last = view.last();
    String expectedFirst = "VISA";
    String expectedLast = "AMEX";
    String firstCode = (String) first.getAttribute("Code");
    String lastCode = (String) last.getAttribute("Code");
    assertEquals("First code is " + firstCode + "  not " + expectedFirst,
      expectedFirst, firstCode);
    assertEquals("Last code is " + lastCode + " not " + expectedLast,
      expectedLast, lastCode);
  }
  /**
   * Test accessing the first and last rows of the ExpirationYearList
   */
  public void testExpirationYearList() {
    ViewObject view = fixture1.getApplicationModule().findViewObject("ExpirationYearList");
    assertNotNull(view);
    long expectedRowCount = 5;
    long rowCount = view.getEstimatedRowCount();
    assertEquals("Option count is " + rowCount + " instead of " +
      expectedRowCount, expectedRowCount, rowCount);
    Row first = view.first();
    Row last = view.last();
    String expectedFirst = "2005";
    String expectedLast = "2009";
    String firstCode = (String) first.getAttribute("Code");
    String lastCode = (String) last.getAttribute("Code");
    assertEquals("First code is " + firstCode + "  not " + expectedFirst,
      expectedFirst, firstCode);
    assertEquals("Last code is " + lastCode + " not " + expectedLast,
      expectedLast, lastCode);
  }
  public void setUp() throws Exception {
    fixture1.setUp();
  }
  public void tearDown() throws Exception {
    fixture1.tearDown();
  }
}
