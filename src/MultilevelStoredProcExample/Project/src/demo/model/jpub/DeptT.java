/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.model.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.jpub.runtime.MutableStruct;

public class DeptT implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "DEPT_T";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  private static int[] _sqlType =  { 2,12,12,2003 };
  private static ORADataFactory[] _factory = new ORADataFactory[4];
  static
  {
    _factory[3] = EmpTList.getORADataFactory();
  }
  protected static final DeptT _DeptTFactory = new DeptT();

  public static ORADataFactory getORADataFactory()
  { return _DeptTFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[4], _sqlType, _factory); }
  public DeptT()
  { _init_struct(true); }
  public DeptT(java.math.BigDecimal deptno, String dname, String loc, EmpTList emps) throws SQLException
  { _init_struct(true);
    setDeptno(deptno);
    setDname(dname);
    setLoc(loc);
    setEmps(emps);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(DeptT o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new DeptT();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getDeptno() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setDeptno(java.math.BigDecimal deptno) throws SQLException
  { _struct.setAttribute(0, deptno); }


  public String getDname() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setDname(String dname) throws SQLException
  { _struct.setAttribute(1, dname); }


  public String getLoc() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setLoc(String loc) throws SQLException
  { _struct.setAttribute(2, loc); }


  public EmpTList getEmps() throws SQLException
  { return (EmpTList) _struct.getAttribute(3); }

  public void setEmps(EmpTList emps) throws SQLException
  { _struct.setAttribute(3, emps); }

}
