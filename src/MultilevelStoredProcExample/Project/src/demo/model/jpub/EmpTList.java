/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.model.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.jpub.runtime.MutableArray;

public class EmpTList implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "EMP_T_LIST";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final EmpTList _EmpTListFactory = new EmpTList();

  public static ORADataFactory getORADataFactory()
  { return _EmpTListFactory; }
  /* constructors */
  public EmpTList()
  {
    this((EmpT[])null);
  }

  public EmpTList(EmpT[] a)
  {
    _array = new MutableArray(2002, a, EmpT.getORADataFactory());
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _array.toDatum(c, _SQL_NAME);
  }

  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    EmpTList a = new EmpTList();
    a._array = new MutableArray(2002, (ARRAY) d, EmpT.getORADataFactory());
    return a;
  }

  public int length() throws SQLException
  {
    return _array.length();
  }

  public int getBaseType() throws SQLException
  {
    return _array.getBaseType();
  }

  public String getBaseTypeName() throws SQLException
  {
    return _array.getBaseTypeName();
  }

  public ArrayDescriptor getDescriptor() throws SQLException
  {
    return _array.getDescriptor();
  }

  /* array accessor methods */
  public EmpT[] getArray() throws SQLException
  {
    return (EmpT[]) _array.getObjectArray(
      new EmpT[_array.length()]);
  }

  public EmpT[] getArray(long index, int count) throws SQLException
  {
    return (EmpT[]) _array.getObjectArray(index,
      new EmpT[_array.sliceLength(index, count)]);
  }

  public void setArray(EmpT[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(EmpT[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public EmpT getElement(long index) throws SQLException
  {
    return (EmpT) _array.getObjectElement(index);
  }

  public void setElement(EmpT a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
