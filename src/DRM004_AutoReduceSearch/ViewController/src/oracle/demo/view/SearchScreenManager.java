/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.demo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.Map;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import oracle.adf.share.logging.ADFLogger;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelStretchLayout;
import oracle.adf.view.rich.model.AutoSuggestUIHints;

import oracle.adf.view.rich.render.ClientEvent;

import oracle.binding.OperationBinding;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.demo.view.util.BindingUtils;

/**
 * Copyright (c) 2012 Oracle Corporation
 * Author: Duncan Mills
 * Class managing the behaviors of the sample search screen and results table
 * @see UIManager which holds any persistant state in relation to this
 * This is defined as a request scoped bean in JSF
 */
public class SearchScreenManager {
    private static ADFLogger _logger =
        ADFLogger.createADFLogger(SearchScreenManager.class);
    private static final String SEARCH_QUERY_ITERATOR_NAME =
        "AllObjectsResultsQueryForTableIterator";
    private UIManager _uiManager;
    private List<SelectItem> _pageSizes;

    //OK to cache for the duration of the request as we'll us this several times
    private long _hits = -1;
    private RichPanelGroupLayout rowViewGroup;
    private RichPanelGroupLayout tileViewGroup;
    private RichPanelStretchLayout navigationBar;

    public SearchScreenManager() {
        super();
    }

    /**
     * JSF injection point for the UIManager
     * @param _uiManager
     */
    public void setUiManager(UIManager uiManager) {
        this._uiManager = uiManager;
    }

    public UIManager getUiManager() {
        return _uiManager;
    }
    
    //------ Getters / Setters for components ---------
    public void setRowViewGroup(RichPanelGroupLayout rowViewGroup) {
        this.rowViewGroup = rowViewGroup;
    }

    public RichPanelGroupLayout getRowViewGroup() {
        return rowViewGroup;
    }

    public void setTileViewGroup(RichPanelGroupLayout tileViewGroup) {
        this.tileViewGroup = tileViewGroup;
    }

    public RichPanelGroupLayout getTileViewGroup() {
        return tileViewGroup;
    }

    public void setNavigationBar(RichPanelStretchLayout navigationBar) {
        this.navigationBar = navigationBar;
    }

    public RichPanelStretchLayout getNavigationBar() {
        return navigationBar;
    }
    
    //------ Public methods called from EL ------------
    public List autoSuggestHandler(FacesContext facesContext,
                                   AutoSuggestUIHints autoSuggestUIHints) {
        if (_logger.isLoggable(Level.CONFIG)) {
            StringBuilder configMsg =
                new StringBuilder("autoSuggestHandler params: ");
            configMsg.append("submittedValue=");
            configMsg.append(autoSuggestUIHints.getSubmittedValue());
            configMsg.append(",maxHits=");
            configMsg.append(autoSuggestUIHints.getMaxSuggestedItems());
            _logger.config(configMsg.toString());
        }
        ArrayList<SelectItem> items = new ArrayList<SelectItem>();
        String enteredValue = autoSuggestUIHints.getSubmittedValue();
        if ((enteredValue != null) && (enteredValue.length() > 0)) {
            OperationBinding autoSuggestCall =
                BindingUtils.findOperationBinding("autoSuggestCandidates");
            Map params = autoSuggestCall.getParamsMap();
            params.put("searchTerm", enteredValue);
            params.put("returnCount",
                       autoSuggestUIHints.getMaxSuggestedItems());
            params.put("returnCount",
                       autoSuggestUIHints.getMaxSuggestedItems());
            List<String> results = (List<String>)autoSuggestCall.execute();
            for (String hit : results) {
                items.add(new SelectItem(hit, hit));
            }
        }
        return items;
    }

    /**
     * Gets the list of page size options you want to expose to users in a selectOneChoice.
     * @return a list of SelectItem objects that will be exposed to users in a selectOneChoice
     */
    public List<SelectItem> getPageSizes() {
        if (_pageSizes == null) {
            ArrayList<SelectItem> pageSizes = new ArrayList<SelectItem>();
            long maxPageSize = getTotalQueryHits();
            int previousSizeLimit = 0;
            int increment = UIManager.SEARCH_DISPLAY_SIZE_INCREMENT;
            int optionCount = 5;
            for (int i = 1; i <= optionCount; i++) {
                previousSizeLimit =
                        addPageSizeOption((i * increment), previousSizeLimit,
                                          maxPageSize, pageSizes);
            }
            _pageSizes = pageSizes;
            
            // now just check that the pageSize stored in the UI Manger is 
            // actually valid in light of this list, reset it if required
            int maxSizeAvailable = (pageSizes.size() * increment);
            if (_uiManager.getSearchDisplaySetSize() > maxSizeAvailable){
                _uiManager.setSearchDisplaySetSize(maxSizeAvailable);
            }
        }
        return _pageSizes;
    }

    /**
     * This routine generates the pagination numbers and is based on the total number of hits and the selected
     * page size.
     * As a twist we won't display every single page as this could grow too long so the list is generated with gaps
     * Zero is used to indicate a gap in the list and should be represented as "..." instead of a link.
     * @return a list of non-negative page integers to display to the user (zero indicates a gap)
     */
    public List<Integer> getPageNumbers() {
        // Always show:
        // - first
        // - possible "..." gap
        // - previous
        // - current
        // - next
        // - possible "..." gap
        // - last

        // Fill in about equally around the gaps until the maxPageCount is reached.

        int pageSize = _uiManager.getSearchDisplaySetSize();
        int pageCount =
            (int)Math.ceil((double)this.getTotalQueryHits() / (double)pageSize);

        ArrayList<Integer> pageNumbers = new ArrayList<Integer>();
        if (pageCount <= 5) // no gaps needed
        {
            for (int i = 1; i <= pageCount; i++)
                pageNumbers.add(i);
        } else // gaps needed
        {
            int maxPageCount = Math.min(7, pageCount);
            int firstPage = 1;
            int lastPage = pageCount;
            int currentPage = getRecalculatedSearchResultsCurrentPage();
            int previousPage = currentPage - 1;
            int nextPage = currentPage + 1;
            int gapAtFirst = Math.max(0, currentPage - firstPage - 2);
            int gapAtLast = Math.max(0, lastPage - 2 - currentPage);
            if (gapAtFirst < 1 && gapAtLast < 1)
                throw new IllegalStateException("Illegal state, gaps were expected but not realized: " +
                                                gapAtFirst + " and " +
                                                gapAtLast);

            // count the used pages:
            int pagesUsed = 1; // start with 1 for the current page
            if (firstPage != currentPage) // first and current are not shared
                pagesUsed++;
            if (previousPage >
                firstPage) // previous exists and is not shared with the first page
                pagesUsed++;
            if (nextPage <
                lastPage) // next exists and is not shared with the last page
                pagesUsed++;
            if (lastPage != currentPage) // last and current are not shared
                pagesUsed++;

            int pagesToFill = maxPageCount - pagesUsed;
            int fillAtFirst = 0;
            int fillAtLast = 0;
            if (gapAtFirst > 0 && gapAtLast > 0) {
                // fill batch evenly as long as there is space, then distribute the remaining where possible
                fillAtFirst = (int)Math.ceil((double)pagesToFill / 2.0d);
                fillAtLast = (int)Math.floor((double)pagesToFill / 2.0d);
                if (fillAtFirst > gapAtFirst) {
                    gapAtLast +=
                            fillAtFirst - gapAtFirst; // add excess on the first gap to the last gap
                } else if (fillAtLast > gapAtLast) {
                    gapAtFirst +=
                            fillAtLast - gapAtLast; // add excess on the last gap to the first gap
                }
            } else if (gapAtFirst > 0) {
                // fill entire batch on the "first" side
                fillAtFirst = pagesToFill;
            } else if (gapAtLast > 0) {
                // fill entire batch on the "last" side
                fillAtLast = pagesToFill;
            }

            if (firstPage != currentPage) // first and current are not shared
                pageNumbers.add(firstPage);
            if (previousPage - fillAtFirst >
                1 + firstPage) // really had a gap at first
                pageNumbers.add(0);
            for (int i = fillAtFirst; i > 0;
                 i--) // add the fillers for the gap at first
                pageNumbers.add(previousPage - i);
            if (previousPage >
                firstPage) // previous exists and is not shared with the first page
                pageNumbers.add(previousPage);
            pageNumbers.add(currentPage); // add the current page
            if (nextPage <
                lastPage) // next exists and is not shared with the last page
                pageNumbers.add(nextPage);
            for (int i = 1; i <= fillAtLast;
                 i++) // add the fillers for the gap at last
                pageNumbers.add(nextPage + i);
            if (nextPage + fillAtLast <
                lastPage - 1) // really had a leftover gap at last
                pageNumbers.add(0);
            if (lastPage != currentPage) // last and current are not shared
                pageNumbers.add(lastPage);
        }
        return pageNumbers;
    }

    /**
     * For convenience calculate of we're on the first page or not
     * @return true if on the first page
     */
    public boolean isSearchResultsFirstPage() {
        return (_uiManager.getSearchResultsCurrentPage() <= 1);
    }

    /**
     * For convenience calculate of we're on the last page or not
     * @return true if on the last page
     */
    public boolean isSearchResultsLastPage() {
        long hits = this.getTotalQueryHits();
        return ((_uiManager.getSearchResultsCurrentPage() * _uiManager.getSearchDisplaySetSize()) >= hits);
    }
    
    /**
     * return the last record displayed on this particular page
     * Note that we need to do some special processing when this is 
     * the last page
     * @return highest record number displayed on this page
     */
    public int getSearchLastIndex() {
        int potentialMax =  _uiManager.getSearchIndex() + _uiManager.getSearchDisplaySetSize();
        long hits = this.getTotalQueryHits();
        return (int)((potentialMax > hits) ? hits : potentialMax);
    }
    
    /**
     * Invoke the search
     * @param actionEvent
     */
    public void searchHander(ActionEvent actionEvent) {
        invokeQueryRefresh();
    }
    

    //-------------- Internal Methods ---------------------------------------//

    /**
     * Routine to create a list entry to provide the user with the option for the number of rows to
     * view at once.
     * @param desiredSize number of hits represented by the list option
     * @param previousSize the high water mark from the last item
     * @param maxPageSize The upper ceiling. We can't display more on a page than there are total hits
     * @param pageSizes reference to an array of SelectListItems that this entry will be appended to
     * @return highwatermark to use when creating the next entry
     */
    private int addPageSizeOption(int desiredSize, int previousSize,
                                  long maxPageSize,
                                  List<SelectItem> pageSizes) {
        if (maxPageSize > previousSize) {
            pageSizes.add(new SelectItem(desiredSize));
        }
        return desiredSize;
    }

    /**
     * Query the main search iterator to find the number of hits
     * The result of this call is cached for the lifetime of this
     * particular request only
     * @return total hit count
     */
    private long getTotalQueryHits() {
        if (_hits == -1) {
            DCIteratorBinding iterator =
                BindingUtils.findIteratorBindingByName(SEARCH_QUERY_ITERATOR_NAME);
            _hits = iterator.getEstimatedRowCount();
        }
        return _hits;
    }

    /**
     *Internal method used to calculate the current page that the user can see
     * This needs to be recalculated because a range size change may be the thing
     * triggering the re-layout
     * @return page number as an integer
     */
    private int getRecalculatedSearchResultsCurrentPage() {
        int firstRecord = _uiManager.getSearchIndex();
        int pageSize = _uiManager.getSearchDisplaySetSize();
        int calculatedPage = (int)(Math.floor((double)firstRecord/pageSize) + 1);
        _logger.info("Recalculated Page number is " + calculatedPage);
        _uiManager.setSearchResultsCurrentPage(calculatedPage);
        return calculatedPage;
    }
    
    /**
     * Called to execute a query on the search either from an AJAX call or
     * from an actionListener
     */
    private void invokeQueryRefresh(){
        resetUIManagers();
        OperationBinding query = BindingUtils.findOperationBinding("ExecuteWithParams");
        query.execute();
        refreshInterestedUI();
    }

    private void refreshInterestedUI() {
        AdfFacesContext actx = AdfFacesContext.getCurrentInstance();
        actx.addPartialTarget(this.getRowViewGroup());
        actx.addPartialTarget(this.getTileViewGroup());
        actx.addPartialTarget(this.getNavigationBar());
    }
    /**
     * When a new query is invoked then all bets are off as far as
     * page numbers, current record and all that stuff
     * the UI would go seriously wacky of we don't reset these
     */
    private void resetUIManagers() {
        // Need to ensure the record count is updated in light of the 
        // new query results 
        _hits = -1;
        
        // Force a re-calcualtion of the pagesizes 
        _pageSizes = null;
        
        //Reset the view page for results to 1
        _uiManager.setSearchResultsCurrentPage(1);
        _uiManager.setSearchIndex(0);
    }
    
    
    /**
     * Invoked by an AJAX push from the client wired up via 
     * an af:serverListener tag
     * @param clientEvent
     */
    public void clientPushQueryHandler(ClientEvent clientEvent) {
        _logger.info("Query push from client");
        invokeQueryRefresh();
    }


}
