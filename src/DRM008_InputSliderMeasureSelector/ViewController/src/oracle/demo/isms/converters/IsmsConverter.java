/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.demo.isms.converters;
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


import java.util.Collection;

import java.util.Collections;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;

import oracle.adf.view.rich.component.rich.input.RichInputNumberSlider;

import oracle.adf.view.rich.component.rich.input.RichInputRangeSlider;

import org.apache.myfaces.trinidad.convert.ClientConverter;

public class IsmsConverter implements ClientConverter, javax.faces.convert.Converter {
    private static ADFLogger _logger = ADFLogger.createADFLogger(IsmsConverter.class);
    private static final String ISMS_COVERTER_ATTRIBUTE = "imsConverterMeasureElements";


    public IsmsConverter() {
        super();
    }

    /**
     * This call returns the location of the client side javascript part of the converter
     * @param context -  Faces context
     * @return URL to the js file
     */
    public String getClientLibrarySource(FacesContext context) {
        return context.getExternalContext().getRequestContextPath() + "/resources/js/ismsClientConverter.js";
    }

    /**
     * No need for any supporting scripts so this returns an empty set
     * @return
     */
    public Collection<String> getClientImportNames() {
        return Collections.emptySet();
    }

    /**
     * This would be used to return embedded JS, however we're linking to an external
     * JS file here so it's not needed
     * @param context - FacesContext
     * @param component - component with the converter
     * @return
     */
    public String getClientScript(FacesContext context, UIComponent component) {
        return null;
    }

    /**
     * Hooks the client side script up, passing the set of values that will be assigned for the 0..n range
     * @param context
     * @param component
     * @return
     */
    public String getClientConversion(FacesContext context, UIComponent component) {
        return ("new SliderMeasureReformatter(\"" + getConverterConfigElementsJS(component) + "\")");
    }

    /**
     *  Converts the String value of the component (the measure label) which will be sent up on the HTTP request
     *  into the Object version which will be the numerical position in the measure list
     *  In this basic version of the converter we're just converting to an 0..n integer
     * @param facesContext
     * @param uIComponent Parent slider component
     * @param valueAsString String value from the HTTP request
     * @return Integer
     */
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String valueAsString) {
        _logger.config("Looking up: " + valueAsString);
        String[] seArray = getConverterConfigElements(uIComponent);
        int result = -1;
        for (int i = 0; i < seArray.length; i++) {
            if (seArray[i].equals(valueAsString)) {
                result = i;
                break;
            }
        }
        if (result < 0) {
            _logger.warning("No match found by converter for value " + valueAsString);
            return null;
        } else {
            return result;
        }
    }


    /**
     * Converts the Object value supplied by the model into a String that can be sent down to the client
     * @param facesContext
     * @param uIComponent Parent slider component
     * @param valueAsObject
     * @return String representation based on the supplied measure list
     */
    public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object valueAsObject) {
        String[] seArray = getConverterConfigElements(uIComponent);

        int lookup = ((Double)valueAsObject).intValue();

        if (lookup >= 0 && lookup < seArray.length) {
            return seArray[lookup];
        }
        return null;
    }


    /**
     * Reads the value of the <f:attribute> that is passed as a child of the slider component
     * and parses that out into the Array of strings.
     * Note that we cannot cache the String[] but must parse this each time from the attribute
     * This is because, in the case of the table, only a single component and hence converter will
     * be created and re-used by all rows, yet we want each row to potentialy have different sets of
     * measures. So as a result we read the attribute list on each call to ensure it's matched with the
     * current usage of the component
     * It would be possible to cache a map if the arrays keyed by the attribute as well - left as an exercise 
     * for the reader.
     * Throws runtime exception if this is not present
     * @param component
     * @return comma delimited string of the measure values
     */
    private String[] getConverterConfigElements(UIComponent component) {
        
        String[] seElements;
        
        //Check this is the correct type of component
        if (!(component instanceof RichInputNumberSlider || component instanceof RichInputRangeSlider)) {
            String wrongcomponenterrm =
                "Can't create converter for components other than <af:inputNumberSlider> and <af:inputRangeSlider>";
            _logger.severe(wrongcomponenterrm);
            throw new RuntimeException(wrongcomponenterrm);
        }

        String sliderElements = (String)component.getAttributes().get(ISMS_COVERTER_ATTRIBUTE);
        if (sliderElements == null || sliderElements.isEmpty() || sliderElements.indexOf(',') == -1) {
            String badlisterrm =
                "Can't create converter, no comma delimited String list as been supplied in the <f:attribute> " +
                ISMS_COVERTER_ATTRIBUTE;
            _logger.severe(badlisterrm);
            throw new RuntimeException(badlisterrm);
        } else {
            seElements = sliderElements.split(",");
            int elementCount = seElements.length;

            //Assuming we are dealing with whole Numbers here so we grab the integer values
            int componentMin;
            int componentMax;
            if (component instanceof RichInputNumberSlider) {
                componentMin = ((RichInputNumberSlider)component).getMinimum().intValue();
                componentMax = ((RichInputNumberSlider)component).getMaximum().intValue() +1;
            } else {
                componentMin = ((RichInputRangeSlider)component).getMinimum().intValue();
                componentMax = ((RichInputRangeSlider)component).getMaximum().intValue() +1;

            }
            if (componentMin != 0) {
                String nonzeroerrm =
                    "Slider Minimum value is not zero. This converter can currently only work with zero based lists. You need to adapt the code to work with other mappings";
                _logger.severe(nonzeroerrm);
                throw new RuntimeException(nonzeroerrm);
            }
            if (componentMax > elementCount) {
                _logger.config("Warning: Not enough measure lables have been defined for slider " + component.getId());
                //If the max of the sider needs more labels than are defined in the list then add some
                String[] extendedElements = new String[componentMax];
                System.arraycopy(seElements, 0, extendedElements, 0, elementCount);
                for (int i = elementCount; i < componentMax; i++) {
                    extendedElements[i] = "Undefined " + i;
                }

                seElements = extendedElements;

            }
        }
        return seElements;
    }

    /**
     *  Re-converts the array of values into a comma delimited string.
     *  In this case though, the string may have been padded to afford extra members
     * @param component
     * @return comma delimited String passed to the JavaScript half of the converter
     */
    private String getConverterConfigElementsJS(UIComponent component) {
        String[] elements = getConverterConfigElements(component);
        StringBuilder js = new StringBuilder();
        boolean first = true;
        for (String frag : elements) {
            if (!first) {
                js.append(",");
            } else {
                first = false;
            }
            js.append(frag);
        }
        return js.toString();
    }
}
