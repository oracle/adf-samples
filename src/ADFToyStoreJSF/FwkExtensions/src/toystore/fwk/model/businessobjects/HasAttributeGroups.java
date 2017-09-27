/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/model/businessobjects/HasAttributeGroups.java,v 1.1.1.1 2006/01/26 21:47:18 steve Exp $
package toystore.fwk.model.businessobjects;
/**
 * Interface supported by entity objects that want to support
 * having named groups of attributes.
 */
public interface HasAttributeGroups {
  /**
   * Sets a named attribute group to be valid.
   * @param name name of the attribute group to test
   */
  void setAttributeGroupValid(String name);
  /**
   * Test whether a named attribute group is valid.
   * @param name name of the attribute group to test
   * @return true if the attribute group is valid
   */
  boolean isAttributeGroupValid(String name);
}
