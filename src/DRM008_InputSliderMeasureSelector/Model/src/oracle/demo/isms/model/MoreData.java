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

import oracle.jbo.domain.Number;

public class MoreData {
    private static ADFLogger _logger = ADFLogger.createADFLogger(MoreData.class);
    private int _rowKey;

    /*
     * More sample data used to test different numeriocal data types
     */
    private Long _dataValueAsLong = new Long(0);
    private Long _dataValueAsLong1 = new Long(0);
    private Long _dataValueAsLong2 = new Long(0);
    private Double _dataValueAsDouble = new Double(0);
    private Float _dataValueAsFloat = new Float(0);
    private oracle.jbo.domain.Number _dataValueAsJBONumber = new oracle.jbo.domain.Number(0); 
    private Long _dataRangeLow = new Long(0);
    private Long _dataRangeHigh = new Long(5);
    
    
    public MoreData(int key){
        _rowKey = key;
    }
    
    public void setDataValueAsLong(Long _dataValueAsLong) {
        this._dataValueAsLong = _dataValueAsLong;
        _logger.info("SampleData Long value set to " + _dataValueAsLong);
    }

    public Long getDataValueAsLong() {
        return _dataValueAsLong;
    }

    public void setDataValueAsDouble(Double _dataValueAsDouble) {
        this._dataValueAsDouble = _dataValueAsDouble;
        _logger.info("SampleData Double value set to " + _dataValueAsDouble);
    }

    public Double getDataValueAsDouble() {
        return _dataValueAsDouble;
    }

    public void setDataValueAsFloat(Float _dataValueAsFloat) {
        this._dataValueAsFloat = _dataValueAsFloat;
        _logger.info("SampleData Float value set to " + _dataValueAsFloat);
    }

    public Float getDataValueAsFloat() {
        return _dataValueAsFloat;
    }

    public void setDataValueAsJBONumber(Number _dataValueAsJBONumber) {
        this._dataValueAsJBONumber = _dataValueAsJBONumber;
        _logger.info("SampleData JBO Number value set to " + _dataValueAsJBONumber);
    }

    public Number getDataValueAsJBONumber() {
        return _dataValueAsJBONumber;
    }


    public void setDataRangeLow(Long _dataRangeLow) {
        this._dataRangeLow = _dataRangeLow;
        _logger.info("SampleData Range Low value set to " + _dataRangeLow);
    }

    public Long getDataRangeLow() {
        return _dataRangeLow;
    }

    public void setDataRangeHigh(Long _dataRangeHigh) {
        this._dataRangeHigh = _dataRangeHigh;
        _logger.info("SampleData Range High value set to " + _dataRangeHigh);
    }

    public Long getDataRangeHigh() {
        return _dataRangeHigh;
    }

    public void setRowKey(int rowKey) {
        this._rowKey = rowKey;
    }

    public int getRowKey() {
        return _rowKey;
    }

    public void setDataValueAsLong1(Long _dataValueAsLong1) {
        this._dataValueAsLong1 = _dataValueAsLong1;
    }

    public Long getDataValueAsLong1() {
        return _dataValueAsLong1;
    }

    public void setDataValueAsLong2(Long _dataValueAsLong2) {
        this._dataValueAsLong2 = _dataValueAsLong2;
    }

    public Long getDataValueAsLong2() {
        return _dataValueAsLong2;
    }
}
