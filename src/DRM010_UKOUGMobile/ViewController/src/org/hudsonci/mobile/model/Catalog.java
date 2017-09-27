/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package org.hudsonci.mobile.model;

import com.sun.util.logging.Level;

import java.util.List;

import oracle.adfmf.dc.ws.rest.RestServiceAdapter;
import oracle.adfmf.framework.api.JSONBeanSerializationHelper;
import oracle.adfmf.framework.api.Model;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.json.JSONObject;
import oracle.adfmf.json.JSONArray;
import oracle.adfmf.json.JSONException;
import oracle.adfmf.util.Utility;

public class Catalog {
    Instance _registeredInstance;
    public static final String LOCAL_HUDSON_SERVER = "LocalHudson";
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public Catalog(){
        initFromConnection(LOCAL_HUDSON_SERVER);
    }

    private void initFromConnection(String hudsonConnection) {
        
        Utility.FrameworkLogger.logp(Level.CONFIG, Catalog.class.getName(), "initFromConnection",
                                     "Populating Instance");

        RestServiceAdapter restServiceAdapter = Model.createRestServiceAdapter();
        restServiceAdapter.clearRequestProperties();
        restServiceAdapter.setConnectionName(hudsonConnection);
        restServiceAdapter.setRequestType(RestServiceAdapter.REQUEST_TYPE_GET);
        restServiceAdapter.setRetryLimit(0);
        restServiceAdapter.setRequestURI("/api/json/");
        String jsonResponse = "";
        try {
            jsonResponse = restServiceAdapter.send("");
            Instance instance = (Instance)JSONBeanSerializationHelper.fromJSON(Instance.class, jsonResponse);
            
            if (instance != null){
                this.setRegisteredInstance(instance);
            }         
        
        } catch (Exception e) {
            Utility.FrameworkLogger.logp(Level.SEVERE, Catalog.class.getName(), "initFromConnection",
                                         "Error parsing JSON object " + e.getMessage());
        }
        
    }

    public void setRegisteredInstance(Instance _registeredInstance) {
        Instance oldRegisteredInstance = this._registeredInstance;
        this._registeredInstance = _registeredInstance;
        propertyChangeSupport.firePropertyChange("RegisteredInstance", oldRegisteredInstance, _registeredInstance);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public Instance getRegisteredInstance() {
        return _registeredInstance;
    }
    
    public void refresh(){
        initFromConnection(LOCAL_HUDSON_SERVER);
    }
}
