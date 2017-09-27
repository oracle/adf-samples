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

import oracle.adf.share.logging.ADFLogger;

import oracle.jbo.domain.Number;

/**
 * Service interface for sample data
 */
public class SampleData {
    private static ADFLogger _logger = ADFLogger.createADFLogger(SampleData.class);
    
    private List<DrillPath> _drillPaths;
    
    private List<SampleRow> _dataRows;
    
    private MoreData _additionalData;
    
    
    
    /**
     * Empty constructor to set up the base data for the demo
     */ 
    public SampleData(){
        initDrillPaths();
        initSampleTableData();
        _additionalData = new MoreData(0);
    }

    private void initDrillPaths() {
        _drillPaths = new ArrayList<DrillPath>(4);
        
        _drillPaths.add(new DrillPath(0,"Taxonomy",new String[] {"Domain","Kingdom","Phylum","Class","Order","Family","Genus","Species"}));
        _drillPaths.add(new DrillPath(1,"Time Periods",new String[] {"Year","Month","Week","Day"}));
        _drillPaths.add(new DrillPath(2,"Fiscal Periods",new String[] {"Fiscal Year","Fiscal Half","Fiscal Quarter","Fiscal Month","Fiscal Week","Day"}));     
        _drillPaths.add(new DrillPath(3,"Volume",new String[] {"Quiet","Moderate","Loud","Ziggy Startdust"}));
        _drillPaths.add(new DrillPath(4,"Roman",new String[] {"I","II","III","IV","V","VI","VII","VIII","IX","X"}));
    }

    private void initSampleTableData() {
        _dataRows = new ArrayList<SampleRow>(5);
        
        _dataRows.add(new SampleRow(0,"Plants",_drillPaths.get(0)));
        _dataRows.add(new SampleRow(1,"Animals",_drillPaths.get(0)));
        _dataRows.add(new SampleRow(2,"Productivity",_drillPaths.get(1)));
        _dataRows.add(new SampleRow(3,"Income",_drillPaths.get(2)));
        _dataRows.add(new SampleRow(4,"Fungi",_drillPaths.get(0)));
    }
    

    public List<DrillPath> getDrillPaths() {
        return _drillPaths;
    }

    public void setDataRows(List<SampleRow> _dataRows) {
        this._dataRows = _dataRows;
    }

    public List<SampleRow> getDataRows() {
        return _dataRows;
    }

    public void setDrillPaths(List<DrillPath> _drillPaths) {
        this._drillPaths = _drillPaths;
    }

    public void setAdditionalData(MoreData _additionalData) {
        this._additionalData = _additionalData;
    }

    public MoreData getAdditionalData() {
        return _additionalData;
    }
    
    public DrillPath getTaxonomyMeasures(){
        return _drillPaths.get(0);
    }
    
    public DrillPath getTimeMeasures(){
        return _drillPaths.get(1);
    }
    
    public DrillPath getFiscalTimeMeasures(){
        return _drillPaths.get(2);
    }
    
    public DrillPath getVolumeMeasures(){
        return _drillPaths.get(3);
    }
    
    public DrillPath getRomanNumeralMeasures(){
        return _drillPaths.get(4);
    }
}
