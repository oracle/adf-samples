/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.demo.view;

import oracle.adf.share.logging.ADFLogger;

/**
 * Copyright (c) 2012 Oracle Corporation
 * Author: Duncan Mills
 * Holds UI (non-Model) state that persists for the lifetime of the HTTP session
 * managed as a session scoped bean by JSF
 */
public class UIManager {
    private static ADFLogger _logger =
        ADFLogger.createADFLogger(UIManager.class);
    private static final int SEARCH_ROW_VIEW = 0;
    private static final int SEARCH_TILE_VIEW = 1;
    protected static final int SEARCH_DISPLAY_SIZE_INCREMENT = 10;


    private int _autoSuggestSuggestionCount = 6;
    private int _searchDisplaySetSize = SEARCH_DISPLAY_SIZE_INCREMENT;
    private int _searchIndex = 0;
    private int _searchResultsViewMode = SEARCH_ROW_VIEW;
    private int _searchResultsCurrentPage = 1;
    
    
    public UIManager() {
        super();
    }

    public void setAutoSuggestSuggestionCount(int _autoSuggestSuggestionCount) {
        this._autoSuggestSuggestionCount = _autoSuggestSuggestionCount;
    }

    public int getAutoSuggestSuggestionCount() {
        return _autoSuggestSuggestionCount;
    }

    public void setSearchDisplaySetSize(int _searchDisplaySetSize) {
        this._searchDisplaySetSize = _searchDisplaySetSize;
    }

    public int getSearchDisplaySetSize() {
        return _searchDisplaySetSize;
    }
    
    public boolean isSearchResultsTileView(){
        return (_searchResultsViewMode == SEARCH_TILE_VIEW);
    }
    
    public boolean isSearchResultsRowView(){
        return (_searchResultsViewMode == SEARCH_ROW_VIEW);
    }

    public void setSearchResultsViewMode(int searchResultsViewMode) {
        this._searchResultsViewMode = searchResultsViewMode;
    }

    public void setSearchIndex(int searchIndex) {
        this._searchIndex = searchIndex;
    }

    public int getSearchIndex() {
        return _searchIndex;
    }

    public void setSearchResultsCurrentPage(int newCurrentPage) {
        _logger.config("Setting Current page within search results to " + newCurrentPage);
        int firstRecordInNewPage = (newCurrentPage - 1) * _searchDisplaySetSize;
        
        // And reset the value that the iterators use
        this._searchIndex = firstRecordInNewPage;
        this._searchResultsCurrentPage = newCurrentPage;
    }

    public int getSearchResultsCurrentPage() {
        return _searchResultsCurrentPage;
    }
}
