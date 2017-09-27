/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/Testing/src/toystore/test/fixtures/ToyStoreConnectFixture.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.test.fixtures;
import oracle.jbo.ApplicationModule;
import oracle.jbo.client.Configuration;
/**
 * Basic JUnit Test Fixture that encapsulates the setup and tearDown
 * for working with an instance of the toystore.model.services.ToyStoreService
 * application module.
 * 
 * NOTE: We set the Application Module to use Bundled Exception Mode
 * ====  so that its exception reporting behavior is identical to
 *       the use that the ADF Struts support uses. The ADF feature
 *       of Bundled Exception Mode allows a maximal set of validation errors
 *       to be returned in a single exchange between the client tier and
 *       the business tier.
 *       
 * @author Steve Muench
 */
public class ToyStoreConnectFixture {
  private static final String AM = "toystore.model.services.ToyStoreService";
  /*
   * We're using the configuration ToyStoreServiceLocalJUnit instead
   * of the ToyStoreServiceLocal because the latter uses JDBC datasource
   * names "jdbc/toystoreDS" and "jdbc/toystore_statemgmt" to find connections.
   * These JDBC datasources require running in the context of a J2EE Web/EJB
   * container that provides the implementation of JDNDI datasources.
   *
   * For running JUnit tests in a standalone way, the ToyStoreServiceLocalJUnit
   * configuration uses a named JDeveloper connection (and it's related
   * JDBC connection info) instead of the datasource.
   */
  private static final String CF = "ToyStoreServiceLocalJUnit";
  ApplicationModule _am;

  public ToyStoreConnectFixture() {
  }

  /**
   * Acquire an instance of the toystore.model.services.ToyStoreService application module
   */
  public void setUp() throws Exception {
    _am = Configuration.createRootApplicationModule(AM, CF);
    /*
     * NOTE: Use Bundled Exception Mode, just like the ADF/Struts support does
     */
    _am.getTransaction().setBundledExceptionMode(true);
  }
  /**
   * Clean up the instance of the toystore.model.services.ToyStoreService application module
   */
  public void tearDown() throws Exception {
    Configuration.releaseRootApplicationModule(_am, true);
  }
  /**
   * Return the application module that we've allocated
   * 
   * @return Application module that we've allocated
   */
  public ApplicationModule getApplicationModule() {
    return _am;
  }
}
