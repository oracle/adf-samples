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


import oracle.adf.share.logging.ADFLogger;


/**
 * Dummy data to test out the principle
 */
public class SampleRow {
    private static ADFLogger _logger = ADFLogger.createADFLogger(SampleRow.class);
    
    private int _rowKey;
    private String _dimension;
    private DrillPath _drillPath;
    private int _rowValue = 0;
    
    
    public SampleRow (int key, String dimName, DrillPath drillPath){
        _rowKey = key;
        _dimension = dimName;
        _drillPath = drillPath;
    }


    public void setRowKey(int _rowKey) {
        this._rowKey = _rowKey;
    }

    public int getRowKey() {
        return _rowKey;
    }

    public void setDimension(String _dimension) {
        this._dimension = _dimension;
    }

    public String getDimension() {
        return _dimension;
    }


    public void setRowValue(int _rowValue) {
        this._rowValue = _rowValue;
        _logger.info("SampleRow " + _rowKey + " value set to " + _rowValue);
    }

    public int getRowValue() {
        return _rowValue;
    }

    public void setDrillPath(DrillPath _drillPath) {
        this._drillPath = _drillPath;
    }

    public DrillPath getDrillPath() {
        return _drillPath;
    }
    
    public int getDrillMin(){
        return _drillPath.getDrillPathMinimum();
    }
    
    public int getDrillMax(){
        return _drillPath.getDrillPathMaximum();
    }
    
    public String getMeasures(){
        return _drillPath.getPathMeasureList();
    }
            
}
