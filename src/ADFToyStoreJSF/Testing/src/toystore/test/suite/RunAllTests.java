/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/Testing/src/toystore/test/suite/RunAllTests.java,v 1.2 2006/01/27 02:42:21 steve Exp $
package toystore.test.suite;
import junit.framework.Test;
import junit.framework.TestSuite;
import toystore.test.unittests.AccountsTest;
import toystore.test.unittests.CheckDescriptions;
import toystore.test.unittests.CreateAnOrderTest;
import toystore.test.unittests.ExerciseShoppingCart;
import toystore.test.unittests.StaticListTests;
import toystore.test.unittests.TestViewObjectExistence;
/**
 * Basic JUnit Test Suite to exercise the ADF Toy Store
 * business tier components without a user interface.
 * 
 * It's main() method launches the Swing UI for JUnit to run the test suite
 * 
 * @author Steve Muench
 */
public class RunAllTests {
  /**
   * Bootstrap the test suite.
   * 
   * @return The Test suite to run 
   */
  public static Test suite() {
    TestSuite suite;
    suite = new TestSuite("RunAllTests");
    suite.addTestSuite(TestViewObjectExistence.class);
    suite.addTestSuite(StaticListTests.class);
    suite.addTestSuite(AccountsTest.class);
    suite.addTestSuite(ExerciseShoppingCart.class);
    suite.addTestSuite(CreateAnOrderTest.class);
    suite.addTestSuite(CheckDescriptions.class);
    return suite;
  }
}
