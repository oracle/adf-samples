/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/exceptions/EntityAlreadyExistsException.java,v 1.1.1.1 2006/01/26 21:47:18 steve Exp $
package toystore.fwk.exceptions;
import oracle.jbo.AttrValException;
import toystore.fwk.exceptions.ErrorMessages;
/**
 * Example of a custom attribute-level validation exception
 *
 * The toystore.fwk.model.businessobjects.ToyStoreEntityImpl class
 * traps the base framework exception oracle.jbo.TooManyObjectsException
 * and throws this custom attribute-level validation error instead.
 *
 * This allows the ADF bundled exception mode to report an attempt
 * to pick a primary key value that already exists as a problem with
 * that particular key attribute, instead of as a general object-level
 * validation problem. In the context of this demo, speifically it means
 * that when a user goes to register on the site, and they happen to pick
 * a username that someone else has already chosen, they can get a
 * username-attribute-specific error that says "This username is already
 * taken, please pick another one" or something to that effect.
 *
 * @author Steve Muench
 */
public class EntityAlreadyExistsException extends AttrValException {
 /**
  * Constructor.
  * @param messageBundle A resource bundle.
  * @param entityName Name of the entity throwing the exception
  * @param attributeName Name of the attribute in error
  * @param conflictingValue The value in error that already exists
  */
  public EntityAlreadyExistsException(Class messageBundle, String entityName,
    String attributeName, Object conflictingValue) {
    super(messageBundle,
      ErrorMessages.ENTITY_ALREADY_EXISTS, entityName,
      attributeName, conflictingValue, null);
  }
}
