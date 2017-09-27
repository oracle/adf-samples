/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import java.sql.*;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.AttributeDescriptor;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class StandaloneJDBCTest {
    
    private static final String STMT =
        "select s.score, e.empno, e.ename
" + 
        "from emp e,
" + 
        "     table( scored_keys( scored_key(98,7839),
" + 
        "                         scored_key(96,7369))) s "+
        "where e.empno = s.key
" + 
        "order by score desc";

    private static final String STMTQUERYSTRUCTS =
        "select value(s) x " + 
        "from " + 
        "     table( scored_keys( scored_key(98,7839),
" + 
        "                         scored_key(96,7369))) s ";

    private static final String STMTQUERYARRAYOFSTRUCTS =
        "select  scored_keys( scored_key(98,7839),
" + 
        "                         scored_key(96,7369)) s
" + 
        "from dual";
    
    private static final String STMTWITHBINDVAR =
            "select s.score, e.empno, e.ename
" + 
            "from emp e,
" + 
            "     table( :VarArrayOfStruct ) s "+
            "where e.empno = s.key
" + 
            "order by score desc";
    
    public static void main(String[] args) throws Throwable {
        Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(STMT);
        ResultSet rs = ps.executeQuery();     
        while (rs.next()) {
            System.out.println(rs.getLong("SCORE")+", "+
                               rs.getLong("EMPNO")+", "+
                               rs.getString("ENAME"));
        }
        rs.close();
        ps.close();
        
        ps = c.prepareStatement(STMTQUERYSTRUCTS);
        OracleResultSet ors = (OracleResultSet)ps.executeQuery();     
        while (ors.next()) {
            STRUCT s = ors.getSTRUCT(1);
            Object[] attrs = s.getAttributes();
            System.out.println(s.getDescriptor().getTypeName());
            System.out.println(attrs[0]+", "+attrs[1]);
            
            
        }
        ors.close();
        ps.close();

        ps = c.prepareStatement(STMTQUERYARRAYOFSTRUCTS);
        ors = (OracleResultSet)ps.executeQuery();     
        ARRAY arr = null;
        while (ors.next()) {
            arr = ors.getARRAY(1);
            Datum[] scoredKey = arr.getOracleArray();
            if (scoredKey != null) {
                for (Datum d : scoredKey) {
                    STRUCT s = (STRUCT)d;
                    Object[] attrs = s.getAttributes();
                    System.out.println(s.getDescriptor().getTypeName());
                    System.out.println(attrs[0]+", "+attrs[1]);
                }
            }
        }
        ors.close();
        ps.close();
        

        OraclePreparedStatement ops = (OraclePreparedStatement)c.prepareStatement(STMTWITHBINDVAR);
        ops.setARRAYAtName("VarArrayOfStruct",arr);
        rs = ops.executeQuery();     
        while (rs.next()) {
            System.out.println(rs.getLong("SCORE")+", "+
                               rs.getLong("EMPNO")+", "+
                               rs.getString("ENAME"));
        }
        rs.close();
        
        ArrayDescriptor ad = new ArrayDescriptor("SCORED_KEYS",c);
        STRUCT[] structs = new STRUCT[2];
        StructDescriptor sd = new StructDescriptor("SCORED_KEY",c);
        structs[0] = new STRUCT(sd,c,new Object[]{new Long(98),new Long(7839)});
        structs[1] = new STRUCT(sd,c,new Object[]{new Long(96),new Long(7369)});
        arr = new ARRAY(ad,c,structs);
        
        ops.setARRAYAtName("VarArrayOfStruct",arr);
        rs = ops.executeQuery();     
        while (rs.next()) {
            System.out.println(rs.getLong("SCORE")+", "+
                               rs.getLong("EMPNO")+", "+
                               rs.getString("ENAME"));
        }
        rs.close();
        ops.close();
        
        c.close();
    }


    public static Connection getConnection() throws SQLException {
        String username = "scott";
        String password = "tiger";
        String thinConn = "jdbc:oracle:thin:@localhost:1521:ORCL";
        DriverManager.registerDriver(new OracleDriver());
        Connection conn =
            DriverManager.getConnection(thinConn, username, password);
        conn.setAutoCommit(false);
        return conn;
    }

}
