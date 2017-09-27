/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package org.hudsonci.mobile.model;

import com.sun.util.logging.Level;

import java.util.Arrays;
import java.util.List;

import oracle.adfmf.dc.ws.rest.RestServiceAdapter;
import oracle.adfmf.framework.api.JSONBeanSerializationHelper;
import oracle.adfmf.framework.api.Model;
import oracle.adfmf.java.beans.PropertyChangeListener;
import oracle.adfmf.java.beans.PropertyChangeSupport;
import oracle.adfmf.util.Utility;

public class Job {
    
    private String _name;
    private String _url;
    private String _color;
    private String _description;
    private String _healthReport;
    private String _healthIcon;
    private int _healthScore;
    private List _builds;
    
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public Job() {
        super();
    }

    public void setName(String _name) {
        String oldName = this._name;
        this._name = _name;
        propertyChangeSupport.firePropertyChange("Name", oldName, _name);
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public String getName() {
        return _name;
    }

    public void setUrl(String _url) {
        String oldUrl = this._url;
        this._url = _url;
        propertyChangeSupport.firePropertyChange("Url", oldUrl, _url);
    }

    public String getUrl() {
        return _url;
    }

    public void setColor(String _color) {
        String oldColor = this._color;
        this._color = _color;
        propertyChangeSupport.firePropertyChange("Color", oldColor, _color);
    }

    public String getColor() {
        return _color;
    }

    public void setDescription(String _description) {
        String oldDescription = this._description;
        this._description = _description;
        propertyChangeSupport.firePropertyChange("Description", oldDescription, _description);
    }

    public String getDescription() {
        return _description;
    }


    public void setBuilds(Build[] builds) {
        Build[] oldBuilds = getBuilds();;
        this._builds = Arrays.asList(builds);
        propertyChangeSupport.firePropertyChange("Builds", oldBuilds, _builds);
    }
    
    protected void setBuildsAsList(List builds) {
        this._builds = builds;
    }
    

    public Build[] getBuilds() {
        if (this._builds != null) {
        return  (Build[])_builds.toArray(new Build[_builds.size()]);
        }
        else {
            return new Build[]{};
        }
    }

    public void setHealthReport(String _healthReport) {
        String oldHealthReport = this._healthReport;
        this._healthReport = _healthReport;
        propertyChangeSupport.firePropertyChange("HealthReport", oldHealthReport, _healthReport);
    }

    public String getHealthReport() {
        return _healthReport;
    }

    public void setHealthIcon(String _healthIcon) {
        String oldHealthIcon = this._healthIcon;
        this._healthIcon = _healthIcon;
        propertyChangeSupport.firePropertyChange("HealthIcon", oldHealthIcon, _healthIcon);
    }

    public String getHealthIcon() {
        return _healthIcon;
    }

    public void setHealthScore(int _healthScore) {
        int oldHealthScore = this._healthScore;
        this._healthScore = _healthScore;
        propertyChangeSupport.firePropertyChange("HealthScore", oldHealthScore, _healthScore);
    }

    public int getHealthScore() {
        return _healthScore;
    }
}
