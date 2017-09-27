/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import oracle.jbo.domain.Array;
import oracle.jbo.server.ViewObjectImpl;

import oracle.sql.ArrayDescriptor;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class ViewObjectImplWithScoredKeyBindVariable extends ViewObjectImpl {
    /*
     * Expecting to find a list of Map with map keys "Score" and "Key"
     */
    protected Array newScoredKeysArray(List<Map<String,Long>> scoredKeys) {
        try {
            if (scoredKeys != null) {
              Connection c = getCurrentConnection();
              if (scoredKeyStructDescriptor == null || scoredKeysArrayOfStructDescriptor == null) {
                setupScoredKeyStructDescriptor(c);
                setupscoredKeysArrayOfStructDescriptor(c);
              }
              int numScoredKeys = scoredKeys.size();
              Datum[] structValues = new Datum[numScoredKeys];
              for (int z = 0; z < numScoredKeys; z++) {
                Map<String, Long> scoredKeyMap = scoredKeys.get(z);
                  structValues[z] = new STRUCT(scoredKeyStructDescriptor,c, new Object[]{
                                                     (Long)scoredKeyMap.get("Score"),
                                                     (Long)scoredKeyMap.get("Key")});                     
              }
              return new ArrayWithWorkaroundForBug7452095(scoredKeysArrayOfStructDescriptor,c,structValues,getDBTransaction());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private ArrayDescriptor scoredKeysArrayOfStructDescriptor = null;
    private StructDescriptor scoredKeyStructDescriptor = null;
    private synchronized void setupScoredKeyStructDescriptor(Connection c) throws SQLException {
        scoredKeyStructDescriptor = new StructDescriptor("SCORED_KEY",c);
    }
    private synchronized void setupscoredKeysArrayOfStructDescriptor(Connection c) throws SQLException {
      scoredKeysArrayOfStructDescriptor = new ArrayDescriptor("SCORED_KEYS",c);
    }
    private Connection getCurrentConnection() throws SQLException {
      PreparedStatement st = getDBTransaction().createPreparedStatement("commit",1);
      Connection conn = st.getConnection();
      st.close();
      return conn;
    }    

}
