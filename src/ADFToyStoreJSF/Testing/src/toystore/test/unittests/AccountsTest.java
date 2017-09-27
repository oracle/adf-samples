/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/Testing/src/toystore/test/unittests/AccountsTest.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.test.unittests;
import junit.framework.TestCase;
import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import toystore.fwk.exceptions.ExceptionHelper;
import toystore.test.fixtures.ToyStoreConnectFixture;
/**
 * User Account Tests
 * 
 * @author Steve Muench
 */
public class AccountsTest extends TestCase {
  ToyStoreConnectFixture fixture1 = new ToyStoreConnectFixture();

  public AccountsTest(String sTestName) {
    super(sTestName);
  }

  /**
   * Test registering new user accounts.
   */
  public void testInsertingRowIntoAccountsView() {
    ViewObject view = fixture1.getApplicationModule().findViewObject("Accounts");
    assertNotNull(view);
    Row newRow = view.createRow();
    String valueBefore = (String) newRow.getAttribute("Username");
    try {
      /*
       * NOTE: The "j2ee" user already exists and will cause an error
       * ----  Due to our using bundled exception mode, the exception
       *       should not be thrown until later validation time.
       */
      newRow.setAttribute("Username", "j2ee");
      newRow.setAttribute("Password", "mypass");
      newRow.setAttribute("Email", "john_roberts@somesite.com");
      newRow.setAttribute("Firstname", "Steve");
      newRow.setAttribute("Lastname", "Muench");
      newRow.setAttribute("City", "Lafayette");
      newRow.setAttribute("State", "CA");
      newRow.setAttribute("Zip", "94549");
      /*
       * NOTE: The "USA" country code is wrong. It should be "US". This
       * ----  should cause a validation error.
       *       Due to our using bundled exception mode, the exception
       *       should not be thrown until later validation time.
       */
      newRow.setAttribute("Country", "USA");
      newRow.setAttribute("Address", "123 Acalanes Blvd.");
      newRow.setAttribute("Phone", "(925) 123-4567");
      view.insertRow(newRow);
    } catch (JboException jbo) {
      /*
       * Due to bundled exception mode, all of the above statements should
       * initially succeed and we should not land in this catch block.
       * If we do, the test fails.
       */
      fail("Should not have thrown this " + jbo.getClass().getName() +
        " exception yet due to bundled exception mode");
    }
    try {
      /*
       * This explicit call to do transaction-level validation of all
       * entity object instances in the cache that are in need of validation
       * is when a bundled validation exception will be thrown, containing
       * any problems caused above.
       */

      // In bundled exception mode, too many rows should be caught here.
      fixture1.getApplicationModule().getTransaction().validate();
    } catch (JboException userAlreadyExists) {
      /*
       * We use a helper routine here to print out the "tree" of bundled
       * exception messages for human inspection in the test log.
       */
      ExceptionHelper.showExceptionIncludingDetails(userAlreadyExists,
        fixture1.getApplicationModule());
      /*
       * Retry setting the username to a name that's not used yet
       */
      newRow.setAttribute("Username", "jroberts");
      /*
       * Retry setting the country to one of the valid country codes
       * from the toystore.model.dataaccess.CountryList.
       */
      newRow.setAttribute("Country", "US");
    }
    /*
     * This will test that we caught the above exception as expected and
     * retried with a new username.
     */
    assertEquals("Did not catch the duplicate username", "jroberts",
      newRow.getAttribute("Username"));
    fixture1.getApplicationModule().getTransaction().commit();
    /*
     * This removes the testing account from the database so we can run this
     * test again and again.
     */
    newRow.remove();
    fixture1.getApplicationModule().getTransaction().commit();
  }
  public void setUp() throws Exception {
    fixture1.setUp();
  }
  public void tearDown() throws Exception {
    fixture1.tearDown();
  }
}
