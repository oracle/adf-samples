/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/Testing/src/toystore/test/unittests/ExerciseShoppingCart.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.test.unittests;
import junit.framework.TestCase;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import toystore.model.dataaccess.ShoppingCartImpl;
import toystore.model.dataaccess.common.ShoppingCart;
import toystore.model.services.common.ToyStoreService;
import toystore.test.fixtures.ToyStoreConnectFixture;
/**
 * Shopping Cart Tests
 * 
 * @author Steve Muench
 */
public class ExerciseShoppingCart extends TestCase {
  ToyStoreConnectFixture fixture1 = new ToyStoreConnectFixture();

  public ExerciseShoppingCart(String sTestName) {
    super(sTestName);
  }

  /**
   * Test using the shopping cart
   */
  public void testUsingShoppingCart() {
    /*
     * Get an instance of our "toystore.model.services.common.ToyStoreService" application module
     * and cast it to the tier-independent "toystore.model.services.common.ToyStoreService"
     * interface so we can work with the custom business methods on our
     * service interface.
     */
    ToyStoreService store = (ToyStoreService) fixture1.getApplicationModule();
    /*
     * Get an instance of our "toystore.model.dataaccess.common.ShoppingCart"
     * interface so we can work with the custom business methods on our
     * shopping cart interface.
     */
    ShoppingCart cart = (ShoppingCart) store.findViewObject("ShoppingCart");
    /*
     * Test getting the cart total where there are no items
     */
    assertEquals("empty cart had non-zero total", 0, cart.cartTotal(), 0);
    /*
     * Adjust the quantity in the cart to have one of the "EST-15" items
     * This simulates clicking on the (Add to cart...) button for this item
     */
    store.adjustQuantityInCart(new String[] { "EST-15" }, new long[] { 1 });
    assertTrue("Item not found (or found multiple times) in the cart",
      checkItemIsInCartWithQuantity(cart, "EST-15", 1));
    /*
     * Adjust the quantity in the cart a second time to make sure that the
     * existing item doesn't get added twice to the cart.
     */
    store.adjustQuantityInCart(new String[] { "EST-15" }, new long[] { 1 });
    assertTrue("Item not found (or found multiple times) in the cart",
      checkItemIsInCartWithQuantity(cart, "EST-15", 1));
    store.adjustQuantityInCart(new String[] { "EST-15" }, new long[] { 3 });
    assertTrue("Item not found (or found multiple times) in the cart",
      checkItemIsInCartWithQuantity(cart, "EST-15", 3));
    // BUG 2740780 - Problem readding item that used to be in the rowset
    //               after its been removed if a findByKey() has been called
    //               in the interim.
    //
    store.adjustQuantityInCart(new String[] { "EST-15" }, new long[] { 0 });
    assertTrue("Item not found (or found multiple times) in the cart",
      !checkItemIsInCart(cart, "EST-15"));
    /*
     * Adjust the quantity in the cart to have one of the "EST-14" items
     * This simulates clicking on the (Add to cart...) button for this item
     */
    store.adjustQuantityInCart(new String[] { "EST-4" }, new long[] { 1 });
    assertTrue("Item not found (or found multiple times) in the cart",
      checkItemIsInCartWithQuantity(cart, "EST-4", 1));
    /*
     * Adjust the quantity in the cart to have 2 of EST-15 and 3 of EST-4
     * This simulates clicking on the (Update Cart) button after adjusting
     * the quatities that you want in your cart.
     */
    store.adjustQuantityInCart(new String[] { "EST-15", "EST-4" },
      new long[] { 2, 3 });
    assertTrue("Item not found (or found multiple times) in the cart",
      checkItemIsInCartWithQuantity(cart, "EST-4", 3));
    assertTrue("Item not found (or found multiple times) in the cart",
      checkItemIsInCartWithQuantity(cart, "EST-15", 2));
    assertEquals("Cart total not equal", 38.95, checkCartTotal(cart), 0);
    /*
     * Test that the cart reflects that there are some items in it
     */
    assertTrue("Cart is supposed to be non-empty now", !store.isCartEmpty());
    ((ShoppingCartImpl) cart).emptyCart();
    /*
     * Test that the cart got emptied
     */
    assertTrue("Cart is supposed to be empty now", store.isCartEmpty());
    /*
     * Test getting the cart total again where there are no items
     */
    assertEquals("empty cart had non-zero total", 0, cart.cartTotal(), 0);
    store.adjustQuantityInCart(new String[] { "EST-15" }, new long[] { 0 });
    assertTrue("Item not found (or found multiple times) in the cart",
      !checkItemIsInCart(cart, "EST-15"));
    store.adjustQuantityInCart(new String[] { "EST-15" }, new long[] { 1 });
    assertTrue("Item not found (or found multiple times) in the cart",
      checkItemIsInCart(cart, "EST-15"));
    store.adjustQuantityInCart(new String[] { "EST-15" }, new long[] { 0 });
    assertTrue("Item not found (or found multiple times) in the cart",
      !checkItemIsInCart(cart, "EST-15"));
    // BUG 2740780 - Problem readding item that used to be in the rowset
    //               after its been removed if a findByKey() has been called
    //               in the interim.
    //
    store.adjustQuantityInCart(new String[] { "EST-15" }, new long[] { 1 });
    assertTrue("Item not found (or found multiple times) in the cart",
      checkItemIsInCartWithQuantity(cart, "EST-15", 1));
  }
  public void setUp() throws Exception {
    fixture1.setUp();
  }
  public void tearDown() throws Exception {
    fixture1.tearDown();
  }
  private void dumpShoppingCart() {
  }
  private boolean checkItemIsInCartWithQuantity(ViewObject cart, String itemid,
    long qty) {
    boolean foundIt = false;
    cart.reset();
    while (cart.hasNext()) {
      Row r = cart.next();
      String curItemId = (String) r.getAttribute("Itemid");
      Long curItemQty = (Long) r.getAttribute("Quantity");
      if (curItemId.equals(itemid) && (curItemQty.longValue() == qty)) {
        if (foundIt) {
          // We shouldn't find the same item more than once in the cart
          return false;
        } else {
          foundIt = true;
        }
      }
    }
    return foundIt;
  }
  private boolean checkItemIsInCart(ViewObject cart, String itemid) {
    boolean foundIt = false;
    cart.reset();
    while (cart.hasNext()) {
      Row r = cart.next();
      String curItemId = (String) r.getAttribute("Itemid");
      if (curItemId.equals(itemid)) {
        if (foundIt) {
          // We shouldn't find the same item more than once in the cart
          return false;
        } else {
          foundIt = true;
        }
      }
    }
    return foundIt;
  }
  private double checkCartTotal(ViewObject cart) {
    cart.reset();
    double total = 0;
    while (cart.hasNext()) {
      Row r = cart.next();
      double price = ((Number) r.getAttribute("Listprice")).doubleValue();
      long quantity = ((Long) r.getAttribute("Quantity")).longValue();
      total += (price * quantity);
    }
    return total;
  }
}
