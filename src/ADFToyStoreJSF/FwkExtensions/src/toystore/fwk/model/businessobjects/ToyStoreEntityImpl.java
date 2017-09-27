/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/model/businessobjects/ToyStoreEntityImpl.java,v 1.1.1.1 2006/01/26 21:47:18 steve Exp $
package toystore.fwk.model.businessobjects;
import oracle.jbo.AttributeDef;
import oracle.jbo.JboException;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import toystore.fwk.exceptions.EntityAlreadyExistsException;
import toystore.fwk.exceptions.ErrorMessages;
import toystore.fwk.exceptions.ExceptionHelper;
/**
 * This class contains customizations to the base ADF Business Components
 * Entity Object implementation class that should be shared by all
 * entity objects in the Toy Store application.
 *
 * The JDeveloper "Tools | Preferences | Business Components | Base Classes"
 * preference page allows us to setup this class as our preferred base
 * class for ADF entity objects at the IDE level. It's also possible to setup
 * this at the project level by visiting the "Business Components" section
 * of the project settings.
 *
 * @author Steve Muench
 */
public class ToyStoreEntityImpl extends EntityImpl {
  private static final String LOWER = "lower";
  private static final String UPPER = "upper";
  private static final String CASE = "Case";

  /**
   * Returns true if any of the supplied attribute indexes have
   * values that have been changed in the current transaction.
   * 
   * @param attrIndexes array of attribute indices to check
   * @return True if any of the attributes are changed
   */
  protected boolean isAnyAttributeChanged(int[] attrIndexes) {
    boolean retval = false;
    if (attrIndexes != null) {
      for (int j = 0, numNames = attrIndexes.length; j < numNames; j++) {
        if (isAttributeChanged(attrIndexes[j])) {
          retval = true;
          break;
        }
      }
    }
    return retval;
  }
  /**
   * Overridden method.
   *
   * Fold the case of string-valued attributes if custom attribute
   * metadata property indicated they should be forced lower- or uppercase.
   *
   * Trap the TooManyObjectsException and convert it into a custom subclass
   * of AttrValException which contains the information on what entity
   * and what attribute caused the problem.
   *
   * By doing this, our generic error handling mechanism will perceive
   * the problem encountered as an attribute-level issue instead of a
   * general entity-level problem, letting our web pages report the
   * error next to the attribute that the user needs to fix.
   *
   * @param index The attribute index slot number
   * @param val The value being set for that attribute
   */
  protected void setAttributeInternal(int index, Object val) {
    try {
      val = foldCaseOfStringIfCasePropertySet(index, val);
      super.setAttributeInternal(index, val);
    }
    catch (JboException ex) {
      /*
       * If the exception thrown is caused by an attempt to set the entity
       * instance's primary key attribute to a value that already exists,
       * the TooManyObjectsException will be thrown by the framework.
       *
       * To be robust we need to test whether the exception is the
       * TooManyObjectsException, as well as whether it is an exception
       * wrapping that exception. This is the case because in ADF's
       * "Bundled Exceptions Mode" (which is used typically by web-based
       * applications to allow the framework to return a maximal set of
       * errors in a single roundtrip, instead of just the first error
       * enountered) any subclasses of ValidationException will be bundled
       * for later delivery to the client as nested exceptions of an outer,
       * wrapping exception.
       */
      if (ExceptionHelper.isOrCausedByDuplicateRow(ex)) {
        /*
         * To allow individual entity objects to provide a custom error message
         * to report the unique key violation for their entity type, we check
         * to see if the current entity has an entity-specific message bundle
         * class associated with it. If it does, we use that. Otherwise, we use
         * the toystore.fwk.util.ErrorMessages bundle as a generic fallback.
         *
         * In this demo, the toystore.model.businessobjects.SignOn entity and
         * toystore.model.businessobjects.Account entity provide an
         * entity-specific overridden message for this.
         */
        EntityDefImpl def = getEntityDef();
        Class msgBundle = def.getMessageBundleClass();
        /*
         * Throw our custom subclass of AttrValException, using the
         * appropriate message bundle.
         */
        throw new EntityAlreadyExistsException((msgBundle != null) ? msgBundle
                                                                   : ErrorMessages.class,
          def.getFullName(), def.getAttributeDef(index).getName(), val);
      }
      else {
        throw ex;
      }
    }
  }
  /**
   * Implement a generic upper/lower-case feature for String attributes
   * based on the presence of a custom attribute-level property named
   * "Case" whose value can be set to "lower" to force the value to lowercase
   * and "upper" to force the value to uppercase before setting it. The
   * foldCaseOfStringIfCasePropertySet() method hold the implementation.
   */
  private Object foldCaseOfStringIfCasePropertySet(int index, Object val) {
    /*
     * Only bother to do something if value is a non-null String
     */
    if ((val != null) && val instanceof String) {
      /*
       * Get attribute definition object by index for the attribute being set
       */
      AttributeDef attrDef = getEntityDef().getAttributeDef(index);
      /*
       * Get the value of the attribute-level custom property named "Case"
       */
      String caseProp = (String) attrDef.getProperty(CASE);
      /*
       * If the property is not null, then check for Lower/Upper values
       */
      if (caseProp != null) {
        /*
         * Only do case folding if attribute type is or extends String
         */
        if (attrDef.getJavaType().isAssignableFrom(String.class)) {
          if (caseProp.equalsIgnoreCase(LOWER)) {
            val = ((String) val).toLowerCase();
          }
          else if (caseProp.equalsIgnoreCase(UPPER)) {
            val = ((String) val).toUpperCase();
          }
        }
      }
    }
    return val;
  }
  /**
   * Workaround for Bug# 3939079 where the ADF entity object
   * locks the unmodified, composing parent entity instance
   * when a child instance is added to it and optimistic locking
   * is being used (which is our case in this demo).
   */
  public void lock() {
    if (getPostState() == STATUS_UNMODIFIED) {
      return;
    }
    super.lock();
  }
  /**
   * Provide a custom method to allow code to eagerly
   * lock an entity instance that is not currently modified.
   */
  public void lockUnmodified() {
    super.lock();
  }
}
