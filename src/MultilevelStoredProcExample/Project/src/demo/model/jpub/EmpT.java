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

public class EmpT implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "EMP_T";
  public static final int _SQL_TYPECODE = OracleTypes.STRUCT;

  protected MutableStruct _struct;

  private static int[] _sqlType =  { 2,12,12,2,91,2,2,2,12 };
  private static ORADataFactory[] _factory = new ORADataFactory[9];
  protected static final EmpT _EmpTFactory = new EmpT();

  public static ORADataFactory getORADataFactory()
  { return _EmpTFactory; }
  /* constructors */
  protected void _init_struct(boolean init)
  { if (init) _struct = new MutableStruct(new Object[9], _sqlType, _factory); }
  public EmpT()
  { _init_struct(true); }
  public EmpT(java.math.BigDecimal empno, String ename, String job, java.math.BigDecimal mgr, java.sql.Timestamp hiredate, java.math.BigDecimal sal, java.math.BigDecimal comm, java.math.BigDecimal deptno, String terminated) throws SQLException
  { _init_struct(true);
    setEmpno(empno);
    setEname(ename);
    setJob(job);
    setMgr(mgr);
    setHiredate(hiredate);
    setSal(sal);
    setComm(comm);
    setDeptno(deptno);
    setTerminated(terminated);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _struct.toDatum(c, _SQL_NAME);
  }


  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  { return create(null, d, sqlType); }
  protected ORAData create(EmpT o, Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    if (o == null) o = new EmpT();
    o._struct = new MutableStruct((STRUCT) d, _sqlType, _factory);
    return o;
  }
  /* accessor methods */
  public java.math.BigDecimal getEmpno() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(0); }

  public void setEmpno(java.math.BigDecimal empno) throws SQLException
  { _struct.setAttribute(0, empno); }


  public String getEname() throws SQLException
  { return (String) _struct.getAttribute(1); }

  public void setEname(String ename) throws SQLException
  { _struct.setAttribute(1, ename); }


  public String getJob() throws SQLException
  { return (String) _struct.getAttribute(2); }

  public void setJob(String job) throws SQLException
  { _struct.setAttribute(2, job); }


  public java.math.BigDecimal getMgr() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(3); }

  public void setMgr(java.math.BigDecimal mgr) throws SQLException
  { _struct.setAttribute(3, mgr); }


  public java.sql.Timestamp getHiredate() throws SQLException
  { return (java.sql.Timestamp) _struct.getAttribute(4); }

  public void setHiredate(java.sql.Timestamp hiredate) throws SQLException
  { _struct.setAttribute(4, hiredate); }


  public java.math.BigDecimal getSal() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(5); }

  public void setSal(java.math.BigDecimal sal) throws SQLException
  { _struct.setAttribute(5, sal); }


  public java.math.BigDecimal getComm() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(6); }

  public void setComm(java.math.BigDecimal comm) throws SQLException
  { _struct.setAttribute(6, comm); }


  public java.math.BigDecimal getDeptno() throws SQLException
  { return (java.math.BigDecimal) _struct.getAttribute(7); }

  public void setDeptno(java.math.BigDecimal deptno) throws SQLException
  { _struct.setAttribute(7, deptno); }


  public String getTerminated() throws SQLException
  { return (String) _struct.getAttribute(8); }

  public void setTerminated(String terminated) throws SQLException
  { _struct.setAttribute(8, terminated); }

}
