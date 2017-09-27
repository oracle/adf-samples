/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.demo.isms.model;
/*******************************************************************************
 *
 * Copyright (c) 2013 Oracle Corporation.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the MIT License
 *
 * Contributors:
 *   Duncan Mills
 *
 *
 *******************************************************************************/ 


import java.util.ArrayList;
import java.util.List;

public class DrillPath {
    String[] _elements;
    int _key;
    String _description;
    
    
    public DrillPath(int key, String name, String[] elements){
        _key = key;
        _description = name;
        _elements = elements;
    }
    
    public String getPathMeasureList(){
        StringBuilder measures = new StringBuilder();
        for (int i=0; i < _elements.length; i++) {
            if (i!=0){
                measures.append(",");
            }
            measures.append(_elements[i]);
        }
        return measures.toString();
    }
    
    public int getDrillPathMinimum(){
        return 0;
    }
    
    public int getDrillPathMaximum(){
        return _elements.length -1;
    }  
    
    @Override
    public String toString(){
        return "DrillPath " + _key + ":" + getPathMeasureList();
    }

    public void setKey(int _key) {
        this._key = _key;
    }

    public int getKey() {
        return _key;
    }

    public List<String> getElementList() {
        List<String> asList = new ArrayList<String>(_elements.length);
        for(String element:_elements){
            asList.add(element);
        }
        return asList;
    }

    public String getDescription() {
        return _description;
    }
}
