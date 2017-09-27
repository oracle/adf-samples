/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/Testing/src/toystore/test/unittests/CreateAnOrderTest.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.test.unittests;
import junit.framework.TestCase;
import oracle.jbo.AttributeDef;
import oracle.jbo.AttributeList;
import oracle.jbo.JboException;
import oracle.jbo.NameValuePairs;
import oracle.jbo.Row;
import oracle.jbo.StructureDef;
import oracle.jbo.ViewObject;
import toystore.fwk.exceptions.ExceptionHelper;
import toystore.model.services.common.ToyStoreService;
import toystore.test.fixtures.ToyStoreConnectFixture;
/**
 * Order Tests
 * 
 * @author Steve Muench
 */
public class CreateAnOrderTest extends TestCase {
  ToyStoreConnectFixture fixture1 = new ToyStoreConnectFixture();

  public CreateAnOrderTest(String sTestName) {
    super(sTestName);
  }

  /**
   * Test creating a new order, mimicking the exact operations that
   * our Struts Actions in the "controller" layer do to create the same.
   */
  public void testCreatingAnOrder() {
    /*
     * Get an instance of our "toystore.model.services.ToyStoreService" application module
     * and cast it to the tier-independent "toystore.model.services.common.ToyStoreService"
     * interface so we can work with the custom business methods on our
     * service interface.
     */
    ToyStoreService store = (ToyStoreService) fixture1.getApplicationModule();
    /*
     * Test logging into the web site by authenticating the "j2ee" user
     */
    boolean ok = store.validSignon("j2ee", "j2ee");
    assertEquals(ok, true);
    /*
     * Adjust the quantity in the cart to have one of the "EST-15" items
     * This simulates clicking on the (Add to cart...) button for this item
     */
    store.adjustQuantityInCart(new String[] { "EST-15" }, new long[] { 1 });
    /*
     * Adjust the quantity in the cart to have one of the "EST-14" items
     * This simulates clicking on the (Add to cart...) button for this item
     */
    store.adjustQuantityInCart(new String[] { "EST-4" }, new long[] { 1 });
    /*
     * Adjust the quantity in the cart to have 2 of EST-15 and 3 of EST-4
     * This simulates clicking on the (Update Cart) button after adjusting
     * the quatities that you want in your cart.
     */
    store.adjustQuantityInCart(new String[] { "EST-15", "EST-4" },
      new long[] { 2, 3 });
    /*
     * Create a blank row to be used for rendering the "/confirmshippinginfo"
     * web form.
     */
    ViewObject orders = store.findViewObject("Orders");
    Row dummyRow = orders.createRow();
    /*
     * Simulate the toystore.web.struts.actions.PlaceOrderAction
     * that creates a blank "dummy" row for rendering the HTML
     * form in the "confirmshippinginfo.jsp" page. Just like the
     * ADF/Struts infrastructure does, we mark this
     * created row to have STATUS_INITIALIZED so that it does not
     * cause any new entities to be added to the cache. Effectively,
     * we're just using this blank row to present any default values
     * that might have been set in the business components either
     * declaratively or programmatically at the entity object level
     *
     * Since we never *SET* any attributes on this particular blank row,
     * and we never have inserted the new row into the view object, it
     * will just be garbage collected.
     */
    dummyRow.setNewRowState(Row.STATUS_INITIALIZED);
    /*
     * Simulate a Struts DynaActionForm name/value pair "bag" using
     * a helper routine to make a copy of the values in the dummy row.
     */
    AttributeList formBean = formBeanPopulatedFromRow(dummyRow);
    /*
     * Simulate the Struts form bean population in response to the
     * HTTP POST submission of the values in the HTML form
     * in "confirmshippinginfo.jsp"
     */
    formBean.setAttribute("ExprMonth", "10");
    formBean.setAttribute("ExprYear", "2008");
    formBean.setAttribute("Courier", "DHL");
    formBean.setAttribute("Creditcard", "1234567890123456");
    formBean.setAttribute("Cardtype", "VISA");
    /*
     * Simulate the toystore.web.struts.actions.FinalizeOrderAction
     * It will create a new row, populate the new rows with
     * values from the Struts "OrdersForm" DynaActionForm bean,
     * insert the row into the Orders view object, and if successful
     * will invoke the finalizeOrder() business method on our
     * ToyStoreService service interface.
     */
    {
      /*
       * Create the new row
       */
      Row newOrder = orders.createRow();
      /*
       * Simulate populating the row from the form bean
       */
      populateRowFromFormBean(formBean, newOrder);
      /*
       * Insert the row in the same manner as the FinalizeOrderAction
       * does (via calling super.execute() on its parent class
       */
      orders.insertRowAtRangeIndex(0, newOrder);
      /*
       * Run the finalizeOrder() business method on our service interface
       * This will translate the items in the shopping cart into new
       * line items for our new order, as well as decrement inventory
       * levels for the items ordered.
       */
      String confirmation = null;
      try {
        confirmation = store.finalizeOrder();
      } catch (JboException jboex) {
        ExceptionHelper.showExceptionIncludingDetails(jboex,
          fixture1.getApplicationModule());
        fail("Should not have come here!");
      }
      /*
       * If everything succeeds, we should have our (sequence-generated)
       * order confirmation number here.
       */
      assertNotNull("The confirmation number was null", confirmation);
    }
  }
  /**
   * Populate the target row using the attribute values found in
   * the AttributeList instance provided.
   */
  private void populateRowFromFormBean(AttributeList formbean, Row row) {
    String[] formBeanAttrNames = formbean.getAttributeNames();
    Object[] formBeanAttrValues = formbean.getAttributeValues();
    /*
     * This gives us the metadata about the structure of the row
     */
    StructureDef rowDef = row.getStructureDef();
    /*
     * Loop over the form bean attribute names
     */
    for (int z = 0, attrCount = formBeanAttrNames.length; z < attrCount; z++) {
      /*
       * Get the row attribute definition object for the attribute having
       * the same name as the current form bean attribute name. Not assuming
       * the attribute positions "match up" is more robust.
       */
      AttributeDef rowAttrDef = rowDef.findAttributeDef(formBeanAttrNames[z]);
      int rowAttrIndex = rowAttrDef.getIndex();
      /*
       * If target row attribute corresponding to the name of the current
       * form bean attribute is updateable, then set the target row attribute
       * to have the value of the corresponding form bean attribute.
       */
      if (row.isAttributeUpdateable(rowAttrIndex)) {
        row.setAttribute(rowAttrIndex, formBeanAttrValues[z]);
      }
    }
  }
  /**
   * Return a simple oracle.jbo.NameValuePairs object (which implements
   * the oracle.jbo.AttributeList interface) with a copy of the values in
   * the row.
   */
  private AttributeList formBeanPopulatedFromRow(Row r) {
    NameValuePairs nvp = new NameValuePairs();
    StructureDef def = r.getStructureDef();
    AttributeDef[] attrs = def.getAttributeDefs();
    for (int z = 0, attrCount = attrs.length; z < attrCount; z++) {
      String curAttrName = attrs[z].getName();
      nvp.setAttribute(curAttrName, r.getAttribute(curAttrName));
    }
    return nvp;
  }
  public void setUp() throws Exception {
    fixture1.setUp();
  }
  public void tearDown() throws Exception {
    fixture1.tearDown();
  }
}
