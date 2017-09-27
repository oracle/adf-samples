/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.fwkext;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import oracle.jbo.DMLException;
import oracle.jbo.JboException;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.domain.Date;
import oracle.jbo.domain.Number;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.EntityImpl;
import oracle.jdbc.OracleCallableStatement;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
/**
 * Custom framework extension class, extending EntityImpl with some
 * helper methods used by our DeptImpl class.
 */
public class CustomEntityImpl extends EntityImpl {
  public CustomEntityImpl() {}

  //----------------[ Begin User-Written Code ]------------------------------
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
  protected BigDecimal bigDecimal(Number n) {
    return (n == null) ? null : n.bigDecimalValue();
  }
  protected BigDecimal bigDecimal(DBSequence n) {
    return (n == null) ? null : n.getSequenceNumber().bigDecimalValue();
  }
  protected Timestamp timestamp(Date d) {
    return (d == null) ? null : d.timestampValue();
  }
  /**
   * Simplifies calling a stored procedure with an in out bind variable
   * of ORAData type. The JPublisher classes created for Object Object 
   * types like DEPT_T, EMP_T, and EMP_T_LIST in this example are
   * of this type.
   *
   * NOTE: If you want to invoke a stored procedure without any bind variables
   * ====  then you can just use the basic getDBTransaction().executeCommand()
   *
   * @param stmt stored procedure statement to execute
   * @param bindVars Object array of parameters
   */  
  protected ORAData callInOutOraDataProcedure(int operation, String proc,
    ORAData d, int typeCode, String typeName) {
    OracleCallableStatement ocst = null;
    final String ST = "begin " + proc + ";end;";
    try {
      DBTransaction txn = getDBTransaction();
      ocst = (OracleCallableStatement) txn.createCallableStatement(ST, 0);
      ocst.registerOutParameter(1, typeCode, typeName);
      ocst.setORAData(1, d);
      ocst.execute();
      return (ORAData) ocst.getORAData(1, (ORADataFactory) d);
    }
    catch (SQLException s) {
      throw new DMLException(operation, this, getTransPostHandle(), ST, s);
    }
    finally {
      closeIfNotNull(ocst);
    }
  }
  protected void closeIfNotNull(Statement st) {
    if (st != null) {
      try {
        st.close();
      }
      catch (SQLException ex) {
        // Ignore
      }
    }
  }
}
