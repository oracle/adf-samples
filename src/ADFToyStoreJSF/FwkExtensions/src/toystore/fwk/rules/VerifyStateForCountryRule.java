/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/rules/VerifyStateForCountryRule.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.fwk.rules;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import oracle.jbo.ApplicationModule;
import oracle.jbo.ValidationException;
import oracle.jbo.ViewObject;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.rules.JbiValidator;
import oracle.jbo.server.util.PropertyChangeEvent;
import toystore.fwk.exceptions.ErrorMessages;
import toystore.fwk.model.businessobjects.ToyStoreEntityImpl;
/**
 * NOTE: This rule is designed to be used at Entity Object level,
 * ----  NOT at the Entity Object Attribute level, since it involves
 *       validating the values of more than one attribute. It also makes
 *       the assumption that the state and country values are mandatory so
 *       it does not bother to recheck that their values are non-null
 *
 * Custom entity-level validation rule that verifies that a State code is
 * valid for a country code if that country code appears in our
 * STATES_IN_COUNTRY table. It creates itself an instance of the
 * "StatesInCountry" view object to test *BOTH* whether the current country
 * should be validated as well as  whether the (country,state) pair is valid.
 *
 * Rules are java beans that implement the JbiValidator interface, and they
 * are metadata-driven. The ADF framework will automatically invoke the bean
 * setter methods to set the values of the "StateAttributeName" and
 * "CountryAttributeName" bean properties based on the values captured
 * in metadata at design time and recorded in the entity obj. This allows
 * our rule to be reused in any entity object where we might have a
 * (Country,State) pair to be validated, regardless of the names of the
 * attributes that store the country and state info in that entity.
 *
 * NOTE: We pass the validation VO reference as an argument to the other
 * ----  private routines in this class to avoid any multi-threaded problems
 *       at runtime. This validator instance will be held onto by an
 *       EntityDefImpl class and could be shared by entity instances in
 *       different application module instances at runtime.
 *
 * @author Steve Muench
 */
public class VerifyStateForCountryRule extends AttributeGroupRule {
  private static final boolean CACHE_PREVIOUS_LOOKUPS = true;
  private static final String COUNTRY_STATE_GROUP = "CountryState";
  private static final String DESC = "Validates if state is valid for country";
  private static final String VO_DEFNAME = "toystore.fwk.rules.dataaccess.StatesForCountry";
  private static final String VO_INSTANCENAME = "Validation_StatesForCountry";
  String _stateAttrName;
  String _countryAttrName;
  String _stateAttrValue;
  String _countryAttrValue;
  CountryStateCache cache;

  public VerifyStateForCountryRule() {
    if (CACHE_PREVIOUS_LOOKUPS) {
      cache = new CountryStateCache();
    }
  }

  /**
   * Overides abstract method in superclass to return the name
   * of the attribute group that we expect entity objects to have
   * when we associate this rule with them.
   * @return name of attribute group
   */
  protected String getAttributeGroupName() {
    return COUNTRY_STATE_GROUP;
  }
  /**
   * The framework never invokes validateValue() directly, but this
   * method is part of the JbiValidator interface to encourage rule writers
   * to isolate the core validation logic into this method so that when
   * the framework invokes the vetoableChange() method below, its clear
   * where the validation functionality lives.
   *
   * --> For attribute level rules, best practice is to implement your
   *     vetoableChange method below to call eventObj.getNewValue() and
   *     pass that value to validateValue() as the object to be validated.
   *
   * --> For entity-level rules (like this one) best practice is to have your
   *     vetoableChange() method pass the EntityImpl instance being validated
   *     to validateValue() as the Object parameter.
   *
   * @param obj Entity object instance being validated
   * @return True if the validation succeeds, false otherwise.
   */
  public boolean validateValue(Object obj) {
    EntityImpl entity = (EntityImpl) obj;
    /*
     * Use the rule's bean property values for "StateAttributeName" and
     * for "CountryAttributeName" and then get the value of the state and
     * country by using getAttribute() on the current entity object instance
     * for these specific attribute names. For example, in the Account entity
     * the are named "State" and "Country", while in the Orders entity they
     * are named "Shipstate" and "Shipcountry". These custom validation rule
     * bean parameters are saved in the entity object's XML descriptor and
     * automatically set on the instance of the validation rule bean before
     * this vetoableChange() method is called by the framework.
     */
    _stateAttrValue = (String) entity.getAttribute(getStateAttributeName());
    _countryAttrValue = (String) entity.getAttribute(getCountryAttributeName());
    if ((_stateAttrValue == null) || (_countryAttrValue == null)) {
      return false;
    }
    /*
     * Get a reference to the current transaction for use below in
     * creating an instance of the view object we need to check the
     * validity of the country/state combination.
     */
    DBTransaction trans = entity.getDBTransaction();
    /*
     * Get an instance of the "toystore.fwk.rules.dataaccess.StatesForCountry" view
     * object for use in checking the validity of the (country,state) pair.
     */
    ViewObject valVo = getValidationViewObject(trans);
    boolean valid = stateValidForCountry(_stateAttrValue, _countryAttrValue,
        valVo);
    /*
     * If we were using strategy (1) described in the comments for the
     * getValidationViewObject() method below, then here we would have
     * to call:
     *
     *   valVo.remove()
     *
     * to remove the temporary instance of the view object to avoid creating
     * a new VO instance each time this rule is evaluated.
     */
    if (!valid) {
      throw new ValidationException(ErrorMessages.class,
        ErrorMessages.NO_SUCH_STATE_FOR_COUNTRY,
        new Object[] { _stateAttrValue });
    }
    return true;
  }
  /*
   * The view object "StatesForCountry" is an expert-mode SQL query, with
   * NO related entity objects that issues following query:
   *
   *    select validate_state_for_country(:0,:1) from dual
   *
   * We've designed the query to invoke a stored function to
   * illustrate how easy this is to do using a view object.
   *
   * ALSO NOTE: Since we expect only a single row to be fetched by our
   * ---------  routines below, we have optimized the view object by setting
   *            its "Max Fetch Size" to 1. This means the framework will not
   *            bother to fetch a second record to detect whether there are
   *            more rows to fetch.
   *
   * Returns true if the uppercased value of stateCode is valid for
   * the country represented by the uppercased value of the countryCode
   */
  private boolean stateValidForCountry(String stateCode, String countryCode,
    ViewObject valVo) {
    Boolean validFromCache = null;
    if (CACHE_PREVIOUS_LOOKUPS) {
      validFromCache = cache.isValid(countryCode, stateCode);
      if (validFromCache != null) {
        return validFromCache.booleanValue();
      }
    }
    /*
     * Set the values of the ":0" and ":1" bind variables by
     * individually calling setWhereClauseParam() to set the (zero-based)
     * first and second bind variables. This is an alternative to the
     * also-supported approach of setting all bind variables in a single go as
     * an Object[].
     */
    valVo.setWhereClauseParam(0, countryCode);
    valVo.setWhereClauseParam(1, stateCode);
    /*
     * We're only reading a single row so no need to cache any row info
     */
    valVo.setForwardOnly(true);
    valVo.executeQuery();
    String result = (String) valVo.first().getAttribute("Found");
    if (CACHE_PREVIOUS_LOOKUPS && (validFromCache == null)) {
      if (result == null) {
        cache.updateNotBeingValidated(countryCode);
      }
      else if (result.equals("Y")) {
        cache.update(countryCode, stateCode);
      }
    }
    return (result == null) || result.equals("Y");
  }
  /**
   * Create an instance of the "toystore.fwk.rules.dataaccess.StatesForCountry"
   * view object for use in checking the validity of the (country,state) pair.
   *
   * There are two strategies we can use for "helper" view objects like this:
   *
   * (1) We can create a new instance of the VO each time using
   *     DBTransaction.createViewObject(), and then call ViewObject.remove()
   *     on that VO instance at the end of the routine, or
   *
   * (2) We can access the current root application module, and create a
   *     named VO instance in that context which we can then try to find
   *     each time we need it. In this case, we should choose a VO instance
   *     naming pattern that has no chance of conflicting with other
   *     VO instance names that might already existing in the application
   *     module's data model.
   *
   * Here we implement strategy (2) above.
   */
  private ViewObject getValidationViewObject(DBTransaction trans) {
    ApplicationModule am = trans.getRootApplicationModule();
    ViewObject retVO = am.findViewObject(VO_INSTANCENAME);
    if (retVO == null) {
      retVO = am.createViewObject(VO_INSTANCENAME, VO_DEFNAME);
    }
    return retVO;
  }
  /**
   * Getter for "Description" property. This property is required
   * by the JbiValidator interface.
   *
   * @return Description of this validation rule
   */
  public String getDescription() {
    return DESC;
  }
  /**
   * Setter for "Description" property. This property is required
   * by the JbiValidator interface.
   *
   * @param str Description for this validation rule
   */
  public void setDescription(String str) {
    // Ignore
  }
  /**
   * Setter for "StateAttributeName" property.
   *
   * @param value Name of the attribute that holds the state value in
   *              the current entity using an instance of this rule
   */
  public void setStateAttributeName(String value) {
    _stateAttrName = value;
  }
  /**
   * Getter for "StateAttributeName" property.
   *
   * @return Name of the attribute that holds the state value in
   *         the current entity using an instance of this rule
   */
  public String getStateAttributeName() {
    return _stateAttrName;
  }
  /**
   * Setter for "CountryAttributeName" property.
   *
   * @param value Name of the attribute that holds the country value in
   *              the current entity using an instance of this rule
   */
  public void setCountryAttributeName(String value) {
    _countryAttrName = value;
  }
  /**
   * Getter for "CountryAttributeName" property.
   *
   * @return Name of the attribute that holds the country value in
   *         the current entity using an instance of this rule
   */
  public String getCountryAttributeName() {
    return _countryAttrName;
  }

  class CountryStateCache {
    private static final String COUNTRY_NOT_BEING_VALIDATED = "_0_";
    private static final String BLANK = "";
    private Dictionary countriesValidated = new Hashtable(5);

    Boolean isValid(String countryCode, String stateCode) {
      String upperCountry = countryCode.toUpperCase();
      String upperState = stateCode.toUpperCase();
      Dictionary statesForCountry = (Dictionary) countriesValidated.get(upperCountry);
      if (statesForCountry == null) {
        return null;
      }
      else if (statesForCountry.get(COUNTRY_NOT_BEING_VALIDATED) != null) {
        return Boolean.TRUE;
      }
      return statesForCountry.get(upperState) != null ? Boolean.TRUE : null;
    }
    synchronized void updateNotBeingValidated(String countryCode) {
      update(countryCode, COUNTRY_NOT_BEING_VALIDATED);
    }
    synchronized void update(String countryCode, String stateCode) {
      String upperCountry = countryCode.toUpperCase();
      String upperState = stateCode.toUpperCase();
      Dictionary statesForCountry = (Dictionary) countriesValidated.get(upperCountry);
      if (statesForCountry == null) {
        statesForCountry = new Hashtable(5);
        countriesValidated.put(upperCountry, statesForCountry);
      }
      statesForCountry.put(upperState, BLANK);
    }
  }
}
