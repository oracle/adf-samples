/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import java.io.*;

/**
 * This class just illustrates a trivial example of returning an 
 * InputStream of XML for the BC4J metadata system to handle.
 */
public class Demo  {
  public Demo() {}
 
  public InputStream getXMLStream() {
   return new ByteArrayInputStream(DEPTXML.getBytes());
  }
  private static String DEPTXML =
"<!DOCTYPE Entity SYSTEM 'jbo_03_01.dtd'>"+
"<Entity"+
"   Name='Dept'"+
"   DBObjectType='table'"+
"   DBObjectName='DEPT'"+
"   AliasName='Dept'"+
"   BindingStyle='Oracle'"+
"   UseGlueCode='false'"+
"   CodeGenFlag='4'"+
"   RowClass='test.DeptImpl'>"+
"   <Attribute"+
"      Name='Deptno'"+
"      IsNotNull='true'"+
"      Precision='2'"+
"      Scale='0'"+
"      Type='oracle.jbo.domain.Number'"+
"      ColumnName='DEPTNO'"+
"      ColumnType='NUMBER'"+
"      SQLType='NUMERIC'"+
"      TableName='DEPT'"+
"      PrimaryKey='true'/>"+
"   <Attribute"+
"      Name='Dname'"+
"      Precision='14'"+
"      Type='java.lang.String'"+
"      ColumnName='DNAME'"+
"      ColumnType='VARCHAR2'"+
"      SQLType='VARCHAR'"+
"      TableName='DEPT'/>"+
"   <Attribute"+
"      Name='Loc'"+
"      Precision='13'"+
"      Type='java.lang.String'"+
"      ColumnName='LOC'"+
"      ColumnType='VARCHAR2'"+
"      SQLType='VARCHAR'"+
"      TableName='DEPT'/>"+
"</Entity>";
}
