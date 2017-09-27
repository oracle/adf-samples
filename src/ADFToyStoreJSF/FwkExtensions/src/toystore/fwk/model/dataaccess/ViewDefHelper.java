/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/model/dataaccess/ViewDefHelper.java,v 1.1.1.1 2006/01/26 21:47:19 steve Exp $
package toystore.fwk.model.dataaccess;
import oracle.jbo.AttributeDef;
import oracle.jbo.common.JboTypeMap;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewDefImpl;
/**
 * Routines that simplify creating view objects dynamically without
 * incurring the runtime-describe overhead of using the
 * createViewObjectFromQueryStmt() API on oracle.jbo.ApplicationModule.
 *
 * The typical way to use these routines is:
 *
 *    ViewDefImpl v = new ViewDefImpl("some.unique.DefNameForTheObject");
 *
 * Then, define the view object's SQL statement by either using a fully-
 * specified "expert-mode" SQL query:
 *
 *    v.setQuery("select e.empno,e.ename,e.sal,e.deptno,d.dname,d.loc,"+
 *              "d.deptno,trunc(sysdate)+1 tomorrow_date, "+
 *              "e.sal + nvl(e.comm,0) total_compensation, "+
 *              "to_char(e.hiredate,'dd-mon-yyyy') formated_hiredate"+
 *              "  from emp e, dept d "+
 *              " where e.deptno = d.deptno (+)"+
 *              " order by e.ename");
 *    v.setFullSql(true);
 *
 * or else you can construct the SQL statement in parts like this:
 *
 *    v.setSelectClause("e.empno,e.ename,e.sal,e.deptno,d.dname,d.loc,"+
 *                      "d.deptno,trunc(sysdate)+1 tomorrows_date,"+
 *                       "e.sal + nvl(e.comm,0) total_compensation, "+
 *                       "to_char(e.hiredate,'dd-mon-yyyy') formated_hiredate");
 *    v.setFromClause("emp e, dept d");
 *    v.setWhereClause("e.deptno = d.deptno (+)");
 *    v.setOrderByClause("e.ename");
 *
 * then you need to resolve and register the view definition:
 *
 *   v.resolveDefObject();
 *   v.registerDefObject();
 *
 * and finally can use the dynamically-created view definition to construct
 * instances of view objects based on it:
 *
 *   ViewObject vo = createViewObject("SomeInstanceName",v);
 *
 * @author Steve Muench
 */
public class ViewDefHelper {
  public ViewDefHelper() {}

  /**
   * Adds a transient view attribute and sets it to be updateable.
   * 
   * @param v The view defintion object
   * @param viewAttrName Name of the new SQL derived view object attribute
   * @param javaType Class object representing type of view attribute
   */
  public static void addUpdateableTransientViewAttr(ViewDefImpl v,
    String viewAttrName, Class javaType) {
    AttributeDefImpl attr;
    attr = v.addViewAttribute(viewAttrName, null, javaType);
    attr.setUpdateableFlag(AttributeDef.UPDATEABLE);
  }
  /**
   * Adds a SQL calculated view attribute of type oracle.jbo.domain.Number
   * having a given precision/scale. An EO-mapped attribute would inherit
   * this datatype information from the underlying EO attribute, but for
   * a SQL-devired attribute, we need to set that information ourselves.
   * 
   * @param v The view defintion object
   * @param viewAttrName Name of the new SQL derived view object attribute
   * @param SQLExpression SQL expression to use if query is not expert-mode
   * @param precision Precision of attribute value
   * @param scale Scale of attribute value
   */
  public static void addSQLDerivedNumberViewAttr(ViewDefImpl v,
    String viewAttrName, String SQLExpression, int precision, int scale) {
    addSQLDerivedViewAttr(v, viewAttrName, SQLExpression, Number.class,
      precision, scale);
  }
  /**
   * Adds a SQL calculated view attribute of type oracle.jbo.domain.Date.
   * 
   * @param v The view defintion object
   * @param viewAttrName Name of the new SQL derived view object attribute
   * @param SQLExpression SQL expression to use if query is not expert-mode
   */
  public static void addSQLDerivedDateViewAttr(ViewDefImpl v,
    String viewAttrName, String SQLExpression) {
    addSQLDerivedViewAttr(v, viewAttrName, SQLExpression, Date.class, 0, 0);
  }
  /**
   * Adds a SQL calculated view attribute of type String (corresponding
   * to database VARCHAR2 type), and sets its maximum length.
   * 
   * @param v The view defintion object
   * @param viewAttrName Name of the new SQL derived view object attribute
   * @param SQLExpression SQL expression to use if query is not expert-mode
   * @param maxLength Maximum length of the string value for this attribute
   */
  public static void addSQLDerivedVarcharViewAttr(ViewDefImpl v,
    String viewAttrName, String SQLExpression, int maxLength) {
    addSQLDerivedViewAttr(v, viewAttrName, SQLExpression, String.class,
      maxLength, 0);
  }
  /**
   * Adds a SQL calculated view attribute of a given type with given
   * precision and scale (if applicable, otherwise pass 0).
   * 
   * @param v The view defintion object
   * @param viewAttrName Name of the new SQL derived view object attribute
   * @param SQLExpression SQL expression to use if query is not expert-mode
   * @param javaType Class object representing type of view attribute
   * @param precision Precision of attribute value
   * @param scale Scale of attribute value
   */
  public static void addSQLDerivedViewAttr(ViewDefImpl v, String viewAttrName,
    String SQLExpression, Class javaType, int precision, int scale) {
    AttributeDefImpl attr;
    attr = v.addViewAttribute(viewAttrName, SQLExpression, javaType);
    /*
     * Determine the JDBC type id that should be used from the type map
     * for this Java type, and set the SQL type Id of the new view attribute
     */
    int sqlTypeId = JboTypeMap.sqlTypeToSQLTypeId(JboTypeMap.attrTypeToSQLType(
          javaType.getName()));
    attr.setSQLType(sqlTypeId);
    /*
     * If the column type is a character type, it's good for JDBC performance
     * to set the maximum length of the string to avoid having the JDBC
     * driver assume that it's 4000 long. This saves memory at the JDBC layer.
     */
    if (JboTypeMap.isCharType(sqlTypeId)) {
      attr.setPrecisionScale(precision, 0);
    }
     /*
     * If a Number type, set its precision and scale
     */
    else if (JboTypeMap.isNumericType(sqlTypeId)) {
      attr.setPrecisionScale(precision, scale);
    }
  }
  /**
   * Adds a view attribute that's mapped to an underlying entity object
   * attribute from one of the entity usages already added to this
   * view definition.
   * 
   * @param v The view defintion object
   * @param viewAttrName Name of the new SQL derived view object attribute
   * @param entityUsageName Name of the entity usage
   * @param entityAttrName Name of the corresponding entity attribute
   */
  public static void addEntityMappedViewAttr(ViewDefImpl v,
    String viewAttrName, String entityUsageName, String entityAttrName) {
    v.addEntityAttribute(viewAttrName, entityUsageName, entityAttrName, true);
  }
  /**
   * Adds an entity usage to a view defintion, giving it an
   * "alias" or usage name to refer to it by.
   * 
   * @param v The view defintion object
   * @param entityUsageName Name of the entity usage
   * @param entityDefName Fully-qualified name of the entity definition
   * @param readOnly Flag indicating whether the entity usage is read-only
   */
  public static void addEntityUsageToViewDef(ViewDefImpl v,
    String entityUsageName, String entityDefName, boolean readOnly) {
    v.addEntityUsage(entityUsageName, entityDefName, false, readOnly);
  }
  /**
   * Adds an entity usage that will be used for reference
   * purposes to the view object. By providing the additional
   * information about the association, the framework automatically
   * will fetch data for attribute related to the reference entity
   * when the foreign key changes.
   * @param v The view defintion object
   * @param entityUsageName Name of the entity usage
   * @param entityDefName Fully-qualified name of the entity definition
   * @param assocName Fully-qualified name of the association for the reference
   * @param assocEndName Alias for the name of the association end
   * @param srcUsageName Name of the usage name on the other end of association
   * @param readOnly Flag indicating whether the entity usage is read-only
   */
  public static void addReferenceEntityUsageToViewDef(ViewDefImpl v,
    String entityUsageName, String entityDefName, String assocName,
    String assocEndName, String srcUsageName, boolean readOnly) {
    v.addEntityUsage(entityUsageName, entityDefName, true, readOnly, assocName,
      assocEndName, srcUsageName);
  }
}
