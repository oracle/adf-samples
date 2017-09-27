/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import oracle.jbo.server.DBTransactionImpl2;

public class CustomDBTransactionImpl extends DBTransactionImpl2 {

    private static final String DELETECOUNT = "DeleteCount";
    private static final String INSERTCOUNT = "InsertCount";
    private static final String UPDATECOUNT = "UpdateCount";
    private static final String SNAPSHOTPREFIX = "Snapshot$";
    private static final String LASTCOMMITSUCCESSFUL = "LastCommitSuccessful";
    public void commit() {
        if (lastCommitSuccessful()) {
            resetCounters();
        }
        setSessionAttribute(LASTCOMMITSUCCESSFUL,Boolean.FALSE);
        super.commit();
        setSessionAttribute(LASTCOMMITSUCCESSFUL,Boolean.TRUE);
        System.out.println(getChangedRowCount()+" row(s) saved ("+getInsertCount()+" inserted, "+getUpdateCount()+" updated, "+getDeleteCount()+" deleted)");
    }
    public void rollback() {
        try {
            super.rollback();
        }
        finally {
            resetCounters();
        }
    }

    public void postChanges() {
        if (lastCommitSuccessful()) {
            resetCounters();
        }
        else {
          snapshotCounters();        
        }
        try {
            super.postChanges();
        }
        catch (Exception e) {
          restoreCountersFromSnapshot();            
        }
    }
    public int getDeleteCount() {
        return getNamedCounter(DELETECOUNT);
    }
    public int getUpdateCount() {
        return getNamedCounter(UPDATECOUNT);
    }
    public int getInsertCount() {
        return getNamedCounter(INSERTCOUNT);
    }
    public int getChangedRowCount() {
        return getDeleteCount()+getInsertCount()+getUpdateCount();
    }
    public void incrementInsertCounter() {
        incrementNamedCounter(INSERTCOUNT);
    }
    public void incrementUpdateCounter() {
        incrementNamedCounter(UPDATECOUNT);
    }
    public void incrementDeleteCounter() {
        incrementNamedCounter(DELETECOUNT);
    }
    // ---- PRIVATE METHODS ----
    private void resetCounters() {
        resetNamedCounter(DELETECOUNT);
        resetNamedCounter(UPDATECOUNT);
        resetNamedCounter(INSERTCOUNT);
    }
    private void restoreCountersFromSnapshot() {
        restoreNamedCounterFromSnapshot(DELETECOUNT);
        restoreNamedCounterFromSnapshot(UPDATECOUNT);
        restoreNamedCounterFromSnapshot(INSERTCOUNT);        
    }
    private void snapshotCounters() {
        snapshotNamedCounter(DELETECOUNT);
        snapshotNamedCounter(UPDATECOUNT);
        snapshotNamedCounter(INSERTCOUNT);
    }
    private void restoreNamedCounterFromSnapshot(String name) {
        setNamedCounter(SNAPSHOTPREFIX+name,getNamedCounter(name));
    }
    private void snapshotNamedCounter(String name) {
        setNamedCounter(SNAPSHOTPREFIX+name,getNamedCounter(name));
    }
    private void incrementNamedCounter(String name) {
        setNamedCounter(name,getNamedCounter(name)+1);
    }    
    private void resetNamedCounter(String name) {
        setNamedCounter(name,0);
    }
    private int getNamedCounter(String name) {
        Integer iVal = (Integer)getSessionAttribute(name);
        if (iVal == null) {
            resetNamedCounter(name);
            return 0;
        }
        return iVal;
    }
    private void setSessionAttribute(String name, Object val) {
        getSession().getUserData().put(name,val);
    }
    private Object getSessionAttribute(String name) {
        return getSession().getUserData().get(name);
    }
    private void setNamedCounter(String name, int i) {
        setSessionAttribute(name,i);
    }


    private boolean lastCommitSuccessful() {
      Boolean b = (Boolean)getSessionAttribute(LASTCOMMITSUCCESSFUL);
      return b == null || Boolean.TRUE.equals(b);
    }
}
