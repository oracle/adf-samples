/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/Testing/src/toystore/test/unittests/TestViewObjectExistence.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.test.unittests;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import oracle.jbo.ViewObject;
import toystore.test.fixtures.ToyStoreConnectFixture;
/**
 * Simple test that just tries to access all of the view object
 * instances in the application module.
 * 
 * @author Steve Muench
 */
public class TestViewObjectExistence extends TestCase {
  ToyStoreConnectFixture fixture1 = new ToyStoreConnectFixture();

  public TestViewObjectExistence(String sTestName) {
    super(sTestName);
  }

  public void testShoppingCartAccess() {
    ViewObject view = fixture1.getApplicationModule().findViewObject("ShoppingCart");
    assertNotNull(view);
  }
  public void testFindProductsAccess() {
    ViewObject view = fixture1.getApplicationModule().findViewObject("FindProducts");
    assertNotNull(view);
  }
  public void testItemsForSaleAccess() {
    ViewObject view = fixture1.getApplicationModule().findViewObject("ItemsForSale");
    assertNotNull(view);
  }
  public void testLineItemsAccess() {
    ViewObject view = fixture1.getApplicationModule().findViewObject("LineItems");
    assertNotNull(view);
  }
  public void testProductListAccess() {
    ViewObject view = fixture1.getApplicationModule().findViewObject("ProductList");
    assertNotNull(view);
  }
  public void testReviewLineItemsAccess() {
    ViewObject view = fixture1.getApplicationModule().findViewObject("ReviewLineItems");
    assertNotNull(view);
  }
  public void testReviewOrdersAccess() {
    ViewObject view = fixture1.getApplicationModule().findViewObject("ReviewOrder");
    assertNotNull(view);
  }
  public void testOrdersAccess() {
    ViewObject view = fixture1.getApplicationModule().findViewObject("Orders");
    assertNotNull(view);
  }
  public void setUp() throws Exception {
    fixture1.setUp();
  }
  public void tearDown() throws Exception {
    fixture1.tearDown();
  }
}
