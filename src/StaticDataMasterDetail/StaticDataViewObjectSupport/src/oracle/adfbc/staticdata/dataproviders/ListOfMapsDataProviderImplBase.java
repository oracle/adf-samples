/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfbc.staticdata.dataproviders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.regex.Pattern;

import oracle.adfbc.staticdata.ListOfMapsDataProvider;

import oracle.jbo.domain.TypeFactory;

public abstract class ListOfMapsDataProviderImplBase implements ListOfMapsDataProvider {
    public abstract List<String> getAttributeNames();
    private boolean caseSensitiveMatch = false;

    protected abstract List<Map<String, Object>> getAllData();

    public List<Map<String, Object>> getData() {
        return getData(null);
    }

    public List<Map<String, Object>> getData(Map<String, Object> filter) {
        List<Map<String, Object>> filteredData = getAllData();
        if (filter != null && filter.size() > 0) {
            List<Map<String, Object>> results = 
                new ArrayList<Map<String, Object>>();
            for (Map<String, Object> row : filteredData) {
                if (rowQualifiesFilter(row, filter)) {
                    results.add(row);
                }
            }
            filteredData = results;
        }
        return filteredData;
    }

    private boolean rowQualifiesFilter(Map<String, Object> row, 
                                       Map<String, Object> filter) {
        boolean retval = true;
        for (String filterAttrName : filter.keySet()) {
            Object filterAttrValueToMatch = filter.get(filterAttrName);
            Object rowAttrValueToTest = row.get(filterAttrName);
            if (rowAttrValueToTest != null && filterAttrValueToMatch != null && 
                !filterValueAndAttributeValueMatch(filterAttrValueToMatch, 
                                                   rowAttrValueToTest)) {
                return false;
            }
        }
        return true;
    }

    protected boolean filterValueAndAttributeValueMatch(Object filterValue, 
                                                        Object attributeValue) {
        Object convertedFilterValue = 
            TypeFactory.getInstance(attributeValue.getClass(), filterValue);
        if (convertedFilterValue instanceof String && 
            attributeValue instanceof String) {
            String stringFilterValue = (String)convertedFilterValue;
            String stringAttributeValue = (String)attributeValue;
            if (!isCaseSensitiveMatch()) {
                stringFilterValue = stringFilterValue.toUpperCase();
                stringAttributeValue = stringAttributeValue.toUpperCase();
            }
            if (containsPattern(stringFilterValue)) {
                stringFilterValue = stringFilterPattern(stringFilterValue);
                return Pattern.matches(stringFilterValue, 
                                       stringAttributeValue);
            } else {
                return stringFilterValue.equals(stringAttributeValue);
            }
        }
        return convertedFilterValue.equals(attributeValue);
    }

    private boolean containsPattern(String stringFilterValue) {
        return stringFilterValue.indexOf("%") >= 0 || 
            stringFilterValue.indexOf("*") >= 0;
    }

    public void setCaseSensitiveMatch(boolean caseSensitiveMatch) {
        this.caseSensitiveMatch = caseSensitiveMatch;
    }

    public boolean isCaseSensitiveMatch() {
        return caseSensitiveMatch;
    }

    private String stringFilterPattern(String stringFilterValue) {
        if (stringFilterValue == null) {
            return ".*";
        }
        stringFilterValue = stringFilterValue.replace("*", "%");
        stringFilterValue = stringFilterValue.replace("%", ".*");
        return stringFilterValue;
    }
}
