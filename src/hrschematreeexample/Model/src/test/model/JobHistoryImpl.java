/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.domain.Number;
import oracle.jbo.domain.Date;
import oracle.jbo.Key;
//  ---------------------------------------------------------------------
//  ---    File generated by Oracle ADF Business Components Design Time.
//  ---    Custom code may be added to this class.
//  ---------------------------------------------------------------------

public class JobHistoryImpl extends EntityImpl  {
  public static final int EMPLOYEEID = 0;
  public static final int STARTDATE = 1;
  public static final int ENDDATE = 2;
  public static final int JOBID = 3;
  public static final int DEPARTMENTID = 4;
  public static final int DEPARTMENTS = 5;
  public static final int EMPLOYEES = 6;
  public static final int JOBS = 7;







  private static EntityDefImpl mDefinitionObject;

  /**
   * 
   *  This is the default constructor (do not remove)
   */
  public JobHistoryImpl() {
  }

  /**
   * 
   *  Retrieves the definition object for this instance class.
   */
  public static synchronized EntityDefImpl getDefinitionObject() {
    if (mDefinitionObject == null) {
      mDefinitionObject = (EntityDefImpl)EntityDefImpl.findDefObject("test.model.JobHistory");
    }
    return mDefinitionObject;
  }








  /**
   * 
   *  Gets the attribute value for EmployeeId, using the alias name EmployeeId
   */
  public Number getEmployeeId() {
    return (Number)getAttributeInternal(EMPLOYEEID);
  }

  /**
   * 
   *  Sets <code>value</code> as the attribute value for EmployeeId
   */
  public void setEmployeeId(Number value) {
    setAttributeInternal(EMPLOYEEID, value);
  }

  /**
   * 
   *  Gets the attribute value for StartDate, using the alias name StartDate
   */
  public Date getStartDate() {
    return (Date)getAttributeInternal(STARTDATE);
  }

  /**
   * 
   *  Sets <code>value</code> as the attribute value for StartDate
   */
  public void setStartDate(Date value) {
    setAttributeInternal(STARTDATE, value);
  }

  /**
   * 
   *  Gets the attribute value for EndDate, using the alias name EndDate
   */
  public Date getEndDate() {
    return (Date)getAttributeInternal(ENDDATE);
  }

  /**
   * 
   *  Sets <code>value</code> as the attribute value for EndDate
   */
  public void setEndDate(Date value) {
    setAttributeInternal(ENDDATE, value);
  }

  /**
   * 
   *  Gets the attribute value for JobId, using the alias name JobId
   */
  public String getJobId() {
    return (String)getAttributeInternal(JOBID);
  }

  /**
   * 
   *  Sets <code>value</code> as the attribute value for JobId
   */
  public void setJobId(String value) {
    setAttributeInternal(JOBID, value);
  }

  /**
   * 
   *  Gets the attribute value for DepartmentId, using the alias name DepartmentId
   */
  public Number getDepartmentId() {
    return (Number)getAttributeInternal(DEPARTMENTID);
  }

  /**
   * 
   *  Sets <code>value</code> as the attribute value for DepartmentId
   */
  public void setDepartmentId(Number value) {
    setAttributeInternal(DEPARTMENTID, value);
  }
  //  Generated method. Do not modify.

  protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
    switch (index)
      {
      case EMPLOYEEID:
        return getEmployeeId();
      case STARTDATE:
        return getStartDate();
      case ENDDATE:
        return getEndDate();
      case JOBID:
        return getJobId();
      case DEPARTMENTID:
        return getDepartmentId();
      case DEPARTMENTS:
        return getDepartments();
      case EMPLOYEES:
        return getEmployees();
      case JOBS:
        return getJobs();
      default:
        return super.getAttrInvokeAccessor(index, attrDef);
      }
  }
  //  Generated method. Do not modify.

  protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception {
    switch (index)
      {
      case EMPLOYEEID:
        setEmployeeId((Number)value);
        return;
      case STARTDATE:
        setStartDate((Date)value);
        return;
      case ENDDATE:
        setEndDate((Date)value);
        return;
      case JOBID:
        setJobId((String)value);
        return;
      case DEPARTMENTID:
        setDepartmentId((Number)value);
        return;
      default:
        super.setAttrInvokeAccessor(index, value, attrDef);
        return;
      }
  }



  /**
   * 
   *  Gets the associated entity DepartmentsImpl
   */
  public DepartmentsImpl getDepartments() {
    return (DepartmentsImpl)getAttributeInternal(DEPARTMENTS);
  }

  /**
   * 
   *  Sets <code>value</code> as the associated entity DepartmentsImpl
   */
  public void setDepartments(DepartmentsImpl value) {
    setAttributeInternal(DEPARTMENTS, value);
  }

  /**
   * 
   *  Gets the associated entity EmployeesImpl
   */
  public EmployeesImpl getEmployees() {
    return (EmployeesImpl)getAttributeInternal(EMPLOYEES);
  }

  /**
   * 
   *  Sets <code>value</code> as the associated entity EmployeesImpl
   */
  public void setEmployees(EmployeesImpl value) {
    setAttributeInternal(EMPLOYEES, value);
  }

  /**
   * 
   *  Gets the associated entity JobsImpl
   */
  public JobsImpl getJobs() {
    return (JobsImpl)getAttributeInternal(JOBS);
  }

  /**
   * 
   *  Sets <code>value</code> as the associated entity JobsImpl
   */
  public void setJobs(JobsImpl value) {
    setAttributeInternal(JOBS, value);
  }

  /**
   * 
   *  Creates a Key object based on given key constituents
   */
  public static Key createPrimaryKey(Number employeeId, Date startDate) {
    return new Key(new Object[] {employeeId, startDate});
  }







}