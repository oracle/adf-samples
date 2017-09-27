/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package org.hudsonci.mobile.model;

import com.sun.util.logging.Level;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import oracle.adfmf.dc.ws.rest.RestServiceAdapter;
import oracle.adfmf.framework.api.JSONBeanSerializationHelper;
import oracle.adfmf.framework.api.Model;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.json.JSONArray;
import oracle.adfmf.json.JSONObject;
import oracle.adfmf.util.Utility;

public class Instance {
    private String _mode;
    private String _nodeDescription;
    private String _nodeName;
    private int    _numExecutors;
    private String _description;
    private boolean _useSecurity;
    private List _jobs;
    
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public Instance() {
        super();
    }

    public void setMode(String _mode) {
        String oldMode = this._mode;
        this._mode = _mode;
        propertyChangeSupport.firePropertyChange("Mode", oldMode, _mode);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public String getMode() {
        return _mode;
    }

    public void setNodeDescription(String _nodeDescription) {
        String oldNodeDescription = this._nodeDescription;
        this._nodeDescription = _nodeDescription;
        propertyChangeSupport.firePropertyChange("NodeDescription", oldNodeDescription, _nodeDescription);
    }

    public String getNodeDescription() {
        return _nodeDescription;
    }

    public void setNodeName(String _nodeName) {
        String oldNodeName = this._nodeName;
        this._nodeName = _nodeName;
        propertyChangeSupport.firePropertyChange("NodeName", oldNodeName, _nodeName);
    }

    public String getNodeName() {
        return _nodeName;
    }

    public void setNumExecutors(int _numExecutors) {
        int oldNumExecutors = this._numExecutors;
        this._numExecutors = _numExecutors;
        propertyChangeSupport.firePropertyChange("NumExecutors", oldNumExecutors, _numExecutors);
    }

    public int getNumExecutors() {
        return _numExecutors;
    }

    public void setDescription(String _description) {
        String oldDescription = this._description;
        this._description = _description;
        propertyChangeSupport.firePropertyChange("Description", oldDescription, _description);
    }

    public String getDescription() {
        return _description;
    }

    public void setUseSecurity(boolean _useSecurity) {
        boolean oldUseSecurity = this._useSecurity;
        this._useSecurity = _useSecurity;
        propertyChangeSupport.firePropertyChange("UseSecurity", oldUseSecurity, _useSecurity);
    }

    public boolean isUseSecurity() {
        return _useSecurity;
    }

    public void setJobs(Job[] jobs) {
        Job[] oldJobs = this.getJobs();
        this._jobs = Arrays.asList(jobs);
        propertyChangeSupport.firePropertyChange("Jobs", oldJobs, jobs);
    }

    public Job[] getJobs() {
        if (this._jobs != null) {
        return  (Job[])_jobs.toArray(new Job[_jobs.size()]);
        }
        else {
            return new Job[]{};
        }
    }
    
    
    public void getJobDetail(int key){
        Utility.ApplicationLogger.logp(Level.INFO, 
                                        Instance.class.getName(),
                                        "getJobDetail",
                                        "Start getJobDetail");
        
        System.out.println("Start getJobDetail");
        
        
        //get the right job
        Job drilldownJob = (Job)this._jobs.get(key);
        String fullJobURL = drilldownJob.getUrl(); 
        
        String apiURLFragment = fullJobURL.substring(fullJobURL.indexOf("/job/")) + "api/json?depth=2";
        
        RestServiceAdapter restServiceAdapter = Model.createRestServiceAdapter();
        restServiceAdapter.clearRequestProperties();
        restServiceAdapter.setConnectionName(Catalog.LOCAL_HUDSON_SERVER);
        restServiceAdapter.setRequestType(RestServiceAdapter.REQUEST_TYPE_GET);
        restServiceAdapter.setRetryLimit(0);
        restServiceAdapter.setRequestURI(apiURLFragment);
        String jsonResponse = "";
        JSONObject instanceResponseHolder;
        try {
            jsonResponse = restServiceAdapter.send("");
            Utility.ApplicationLogger.logp(Level.INFO, Instance.class.getName(), "getJobDetail",
                                         jsonResponse);
            
            instanceResponseHolder = new JSONObject(jsonResponse); 
            if (instanceResponseHolder != null){
                drilldownJob.setDescription(instanceResponseHolder.optString("description"));
                //Get health
                JSONArray healthList = instanceResponseHolder.getJSONArray("healthReport");
                if (healthList.length() > 0){
                    JSONObject health = (JSONObject)healthList.get(0);
                    drilldownJob.setHealthReport(health.optString("description"));
                    drilldownJob.setHealthIcon(health.optString("iconUrl"));
                    drilldownJob.setHealthScore(health.optInt("score"));
                }
                
                
                //Get builds
                JSONArray builds = instanceResponseHolder.getJSONArray("builds");
                int buildCount = builds.length();
                if (buildCount > 0){
                    List buildList = new ArrayList(buildCount);
                    for (int i = 0; i < buildCount; i++) {
                        JSONObject jbuild = builds.getJSONObject(i);
                        Build build = new Build();
                        build.setNumber(jbuild.getInt("number"));
                        build.setBuilding(jbuild.optBoolean("building"));
                        build.setDuration(jbuild.optInt("duration"));
                        build.setFullDisplayName(jbuild.optString("fullDisplayName"));
                        build.setId(jbuild.optString("id"));
                        build.setResult(jbuild.optString("result"));
                        buildList.add(build);
                    }
                    drilldownJob.setBuildsAsList(buildList);
                }
                System.out.println("done");
            }  
        
        } catch (Exception e) {
            Utility.FrameworkLogger.logp(Level.SEVERE, Instance.class.getName(), "getJobDetail",
                                         "Error parsing JSON object " + e.getMessage());
        }
    }
    
    
}
