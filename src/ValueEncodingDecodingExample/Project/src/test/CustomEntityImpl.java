/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import com.sun.java.util.collections.HashMap;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import oracle.jbo.AttributeDef;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SparseArray;

/**
 * Custom ADF BC framework extension class to enhance the entity object
 * to support the ability to declaratively mark a String-valued attribute
 * with the custom attribute-level property named "EncodeValue". If a
 * string-valued attribute sets the custom property EncodeValue="true"
 * then this class decodes the value once when the entity's data is read
 * in from the database, and ensures that the value is encoded again
 * when DML is performed to save any changes to the entity.
 */
public class CustomEntityImpl extends EntityImpl {
  private boolean performingBindDMLStatement = false;
  private boolean performingSetAttribute = false;
  /*
   * Name of custom entity object attribute property we use to declaratively
   * signal that an attribute should be encoded.
   */
  private static final String ENCODEVALUE = "EncodeValue";
  private static final String TRUE = "true";
  //========= OVERRIDDEN FRAMEWORK METHODS ====================================

  /**
   * Overridden framework method.
   * 
   * If the string-typed attribute is marked to be encoded, then 
   * decode the string value when the value is retrieved from the database.
   */
  protected void populateAttribute(int index, Object value) {
    if (attributeIsStringAndHasEncodeValueCustomPropertySet(index) && 
        (!performingSetAttribute) && 
        (!isAttributePopulated(index) || valuesDiffer(value, 
                                                      getAttribute(index)))) {
      value = decodeStringValue((String)value);
    }
    super.populateAttribute(index, value);
  }

  /**
   * Overridden framework method.
   * 
   * Ensures that any string attributes that are marked as encoded
   * get decoded in the row read from the database during lock-time
   * just before performing the "lost update protection" consistency
   * check on the row in memory.
   */
  protected boolean checkConsistency(SparseArray target, boolean lock) {
    decodeAnyEncodedRowValuesBeforeConsistencyCheck(target);
    return super.checkConsistency(target, lock);
  }

  /**
   * Overridden framework method.
   * 
   * Sets a flag so that our overridden getAttributeInternal() method
   * can know that its being called in the context of binding a DML
   * statement related to this entity object (e.g. INSERT, UPDATE, DELETE)
   */
  protected int bindDMLStatement(int operation, PreparedStatement stmt, 
                                 AttributeDefImpl[] allAttrs, 
                                 AttributeDefImpl[] retCols, 
                                 AttributeDefImpl[] retKeys, HashMap retrList, 
                                 boolean batchMode) throws SQLException {
    try {
      performingBindDMLStatement = true;
      return super.bindDMLStatement(operation, stmt, allAttrs, retCols, 
                                    retKeys, retrList, batchMode);
    } finally {
      performingBindDMLStatement = false;
    }
  }

  /**
   * Overridden framework method returns the encoded value of the attribute
   * if appropriate, but only if being called during the bindDMLStatement()
   * processing.
   */
  protected Object getAttributeInternal(int index) {
    Object ret = super.getAttributeInternal(index);
    if (attributeIsStringAndHasEncodeValueCustomPropertySet(index) && 
        performingBindDMLStatement) {
      ret = (String)encodeStringValue((String)ret);
    }
    return ret;
  }
  //========= PRIVATE IMPLEMENTATION METHODS ====================================

  /**
   * Returns true if the attribute definition at supplied index is of
   * String type and has the "EncodeValue" custom property set to "true"
   */
  private boolean attributeIsStringAndHasEncodeValueCustomPropertySet(int index) {
    AttributeDef attrDef = getEntityDef().getAttributeDef(index);
    if (attrDef.getJavaType() == String.class) {
      Object val = attrDef.getProperty(ENCODEVALUE);
      return val != null && ((String)val).equalsIgnoreCase(TRUE);
    }
    return false;
  }

  /**
   * Simple example of a decode function
   * 
   * Removes enclosing square brackets and reverses the string.
   * 
   */
  private String decodeStringValue(String s) {
    String ret = StringEncoder.decode(s);
    System.out.println("Decoding value: " + s + " to " + ret);
    return ret;
  }

  /**
   * Simple example of an encode function
   * 
   * Reverses the string and adds enclosing square brackets.
   */
  private String encodeStringValue(String s) {
    String ret = StringEncoder.encode(s);
    System.out.println("Encoding value: " + s + " to " + ret);
    return ret;
  }

  /**
   * Decodes any values corresponding to encoded string attributes in the
   * sparse array of values that will be used by the checkConsistency()
   * framework method for performing the consistency check to prevent against
   * the "lost update" problem.
   */
  private void decodeAnyEncodedRowValuesBeforeConsistencyCheck(SparseArray target) {
    int numAttrs = getAttributeCount();
    for (int i = 0; i < numAttrs; i++) {
      if (attributeIsStringAndHasEncodeValueCustomPropertySet(i)) {
        if (target.isPopulated(i)) {
          target.set(i, decodeStringValue((String)target.get(i)));
        }
      }
    }
  }

  /**
   * Returns true of the values are different
   */
  private boolean valuesDiffer(Object obj1, Object obj2) {
    boolean ret = 
      (obj1 == null && obj2 != null) || (obj1 != null && obj2 == null) || 
      (obj1 instanceof Comparable && 
       ((Comparable)obj1).compareTo(obj2) != 0) || !(obj1.equals(obj2));
    return ret;
  }

  /**
   * Overridden framework method that sets a flag so our overridden
   * populateAttribute() method can test whether it's being called
   * due to a setAttribute() or not.
   */
  public void setAttribute(int index, Object val) {
    performingSetAttribute = true;
    try {
      super.setAttribute(index, val);
    } finally {
      performingSetAttribute = false;
    }
  }
}
