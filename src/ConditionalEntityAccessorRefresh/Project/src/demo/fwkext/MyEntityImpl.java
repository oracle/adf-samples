/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.fwkext;
import oracle.jbo.AttributeDef;
import oracle.jbo.server.Entity;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.EntityRowSetImpl;
public class MyEntityImpl extends EntityImpl {
  private static final String REFR_ACC_PREF = "RefreshAccessor_";
  private static final String YES = "yes";

  /**
   * This overridden framework method checks to see if the current
   * attribute being retrieved is a related entity or entity row set
   * and, if there exists a custom entity metadata property named
   * RefreshAccessor_AttrName -- where "AttrName" is the name of the
   * current attribute -- then it refreshes the value of the entity
   * or entity row set from the database before returning it.
   * @return Attribute Value
   * @param index
   */
  protected Object getAttributeInternal(int index) {
    Object val = super.getAttributeInternal(index);
    EntityDefImpl def = getEntityDef();
    AttributeDef attrDef = def.getAttributeDef(index);
    byte attrKind = attrDef.getAttributeKind();
    if ((attrKind == AttributeDef.ATTR_ASSOCIATED_ROW) ||
          (attrKind == AttributeDef.ATTR_ASSOCIATED_ROWITERATOR)) {
      if (YES.equalsIgnoreCase(
              (String) def.getProperty(REFR_ACC_PREF + attrDef.getName()))) {
        if (attrKind == AttributeDef.ATTR_ASSOCIATED_ROW) {
          ((Entity) val).refresh(Entity.REFRESH_WITH_DB_ONLY_IF_UNCHANGED);
        }
        else {
          ((EntityRowSetImpl) val).executeQuery();
        }
      }
    }
    return val;
  }
}
