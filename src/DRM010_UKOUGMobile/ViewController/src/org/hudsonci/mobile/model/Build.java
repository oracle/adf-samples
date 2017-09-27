/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package org.hudsonci.mobile.model;

import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;

public class Build {
    private int _number;
    private boolean _building;
    private int _duration;
    private String _fullDisplayName;
    private String _id;
    private String _result;
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public Build() {
        super();
    }

    public void setNumber(int _number) {
        int oldNumber = this._number;
        this._number = _number;
        propertyChangeSupport.firePropertyChange("Number", oldNumber, _number);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public int getNumber() {
        return _number;
    }

    public void setBuilding(boolean _building) {
        boolean oldBuilding = this._building;
        this._building = _building;
        propertyChangeSupport.firePropertyChange("Building", oldBuilding, _building);
    }

    public boolean isBuilding() {
        return _building;
    }

    public void setDuration(int _duration) {
        int oldDuration = this._duration;
        this._duration = _duration;
        propertyChangeSupport.firePropertyChange("Duration", oldDuration, _duration);
    }

    public int getDuration() {
        return _duration;
    }

    public void setFullDisplayName(String _fullDisplayName) {
        String oldFullDisplayName = this._fullDisplayName;
        this._fullDisplayName = _fullDisplayName;
        propertyChangeSupport.firePropertyChange("FullDisplayName", oldFullDisplayName, _fullDisplayName);
    }

    public String getFullDisplayName() {
        return _fullDisplayName;
    }

    public void setId(String _id) {
        String oldId = this._id;
        this._id = _id;
        propertyChangeSupport.firePropertyChange("Id", oldId, _id);
    }

    public String getId() {
        return _id;
    }

    public void setResult(String _result) {
        String oldResult = this._result;
        this._result = _result;
        propertyChangeSupport.firePropertyChange("Result", oldResult, _result);
    }

    public String getResult() {
        return _result;
    }
    
}
