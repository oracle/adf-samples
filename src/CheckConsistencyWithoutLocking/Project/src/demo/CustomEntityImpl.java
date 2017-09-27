/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo;
import oracle.jbo.ApplicationModule;
import oracle.jbo.AttributeDef;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowInconsistentException;
import oracle.jbo.StructureDef;
import oracle.jbo.ViewObject;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SparseArray;
public class CustomEntityImpl extends EntityImpl {
  public void testConsistency() {
    Key k = getKey();
    AttributeDef[] pkAttrs = new AttributeDef[k.getAttributeCount()];
    EntityDefImpl def = getEntityDef();
    AttributeDef[] attrs = def.getAttributeDefs();
    int y = 0;
    for (AttributeDef attrDef : attrs) {
      if (attrDef.isPrimaryKey()) {
        pkAttrs[y++] = attrDef;
      }
    }
    ApplicationModule rootAM = getDBTransaction().getRootApplicationModule();
    final String consistencyCheckVOName = getEntityDef().getName()+"_consistencyCheck";  
    ViewObject vo = rootAM.findViewObject(consistencyCheckVOName);
    if (vo == null) {
      StringBuilder SQL = new StringBuilder("SELECT ");
      SQL.append(def.getSelectClause(getSQLBuilder()))
         .append(" ")
         .append(" FROM ")
         .append(def.getSource())
         .append(" WHERE ");
      for (int z = 0; z < pkAttrs.length; z++) {
       if (z > 0) {
         SQL.append(" AND ");
       }
       SQL.append(pkAttrs[z].getColumnName()).append(" = ?");
      }
      vo = rootAM.createViewObjectFromQueryStmt(consistencyCheckVOName,SQL.toString());          
    }
    for (int z = 0; z < k.getAttributeCount(); z++) {
      vo.setWhereClauseParam(z,k.getAttribute(z));
    }
    vo.executeQuery();
    Row foundRow = vo.first();
    if (foundRow != null) {
      int persAttr = 0;
      for (int z = 0; z < attrs.length; z++ ) {
        if (attrs[z].getAttributeKind() != AttributeDef.ATTR_TRANSIENT) {
          if (!getSQLBuilder().compareFetchedValue(this,
                                                   (AttributeDefImpl)attrs[z],
                                                   getAttribute(z),
                                                   foundRow.getAttribute(persAttr++))) {
            throw new RowInconsistentException(k);
          }
        }
      }
    }
    else {
      throw new RuntimeException("Row with key "+k+" has been deleted since you queried it.");
    }
  }
    
}
