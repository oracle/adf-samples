/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.fwkext.model;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import oracle.jbo.JboException;
import oracle.jbo.Session;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ApplicationModuleImpl;
import oracle.jdbc.OracleTypes;
import oracle.jbo.domain.Number;
public class CustomAppModuleImpl extends ApplicationModuleImpl {
  public void callProcWithNoArgs() {
    getDBTransaction().executeCommand("devguidepkg.proc_with_no_args;");
  }
  public void callProcWithThreeArgs(Number n, Date d, String v) {
    callStoredProcedure("devguide.proc_with_three_args",
                        new Object[]{n,d,v});
  }
  public String vallFuncWithThreeArgs(Number n, Date d, String v) {
    return (String)callStoredFunction(VARCHAR2,
                              "devguide.proc_with_three_args",
                              new Object[]{n,d,v});
  }
  //----------------[ Begin Helper Code ]------------------------------
  public static int NUMBER = Types.NUMERIC;
  public static int DATE = Types.DATE;
  public static int VARCHAR2 = Types.VARCHAR;

  /**
   * Simplifies calling a stored procedure with bind variables
   * 
   * NOTE: If you want to invoke a stored procedure without any bind variables
   * ====  then you can just use the basic getDBTransaction().executeCommand()
   * 
   * @param stmt stored procedure statement to execute
   * @param bindVars Object array of parameters
   */
  protected void callStoredProcedure(String stmt, Object[] bindVars) {
    CallableStatement st = null;
    try {
      st = getDBTransaction().createCallableStatement("begin " + stmt +
          " end;", 0);
      if (bindVars != null) {
        for (int z = 0; z < bindVars.length; z++) {
          st.setObject(z + 1, bindVars[z]);
        }
      }
      st.executeUpdate();
    }
    catch (SQLException e) {
      throw new JboException(e);
    }
    finally {
      if (st != null) {
        try {
          st.close();
        }
        catch (SQLException e) {}
      }
    }
  }
  /**
   * Simplifies calling a stored function with bind variables
   * 
   * You can use the NUMBER, DATE, and VARCHAR2 constants in this
   * class to indicate the function return type for these three common types,
   * otherwise use one of the JDBC types in the java.sql.Types class.
   * 
   * NOTE: If you want to invoke a stored procedure without any bind variables
   * ====  then you can just use the basic getDBTransaction().executeCommand()
   * 
   * @param sqlReturnType JDBC datatype constant of function return value
   * @param stmt stored function statement
   * @param bindVars Object array of parameters
   * @return function return value as an Object
   */
  protected Object callStoredFunction(int sqlReturnType, String stmt,
    Object[] bindVars) {
    CallableStatement st = null;
    try {
      st = getDBTransaction().createCallableStatement("begin :0 := " + stmt +
          " end;", 0);
      st.registerOutParameter(1, sqlReturnType);
      if (bindVars != null) {
        for (int z = 0; z < bindVars.length; z++) {
          st.setObject(z + 2, bindVars[z]);
        }
      }
      st.executeUpdate();
      return st.getObject(1);
    }
    catch (SQLException e) {
      throw new JboException(e);
    }
    finally {
      if (st != null) {
        try {
          st.close();
        }
        catch (SQLException e) {}
      }
    }
  }
  //----------------[ End Helper Code ]------------------------------  
}
