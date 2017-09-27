/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/rules/AttributeGroupRule.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.fwk.rules;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.rules.JbiValidator;
import oracle.jbo.server.util.PropertyChangeEvent;
import toystore.fwk.model.businessobjects.HasAttributeGroups;
/**
 * Base class that custom validation rule classes can extend
 * to take advantage of named attribute group support. If the
 * named attribute group, identified by the overriding validator
 * in its getAttributeGroupName() method, is valid then it won't be
 * revalidated unnecessarily.
 *
 * @author Steve Muench
 */
public abstract class AttributeGroupRule implements JbiValidator {
  /**
   * Validators that subclass this base rule implementation need
   * to override this method and return the name of the attribute
   * group to which they want to relate this rule.
   * @return attribute group name
   */
  protected abstract String getAttributeGroupName();
  /**
   * Part of JbiValidator interface implementation. Override in subclasses
   * to provide the implementation of the validation logic for this 
   * attribute-group-enabled rule.
   * 
   * @param obj value being validated.
   * @return true if value is valid
   */
  public abstract boolean validateValue(Object obj);
  /**
   * Invoked by framework at the appropriate time for validation based
   * on whether the instance of the rule is attached to the entity object
   * at entity level, or entity-attribute level.
   *
   * In this particular case, this rule is an entity-level rule since it
   * validates the values of multiple attributes together.
   *
   * @param eventObj The property change event object
   */
  public final void vetoableChange(PropertyChangeEvent eventObj) {
    /*
     * The source object will be an entity object instance for validation rules
     * so we use the getSource() method on the event object to determine
     * which entity instance we are being asked to validate.
     */
    EntityImpl entity = (EntityImpl) eventObj.getSource();
    HasAttributeGroups hgEntity = null;
    if (entity instanceof HasAttributeGroups) {
      hgEntity = (HasAttributeGroups) entity;
      if (hgEntity.isAttributeGroupValid(getAttributeGroupName())) {
        return;
      }
    }
    boolean valid = validateValue(entity);
    if (valid && (hgEntity != null)) {
      hgEntity.setAttributeGroupValid(getAttributeGroupName());
    }
  }
}
