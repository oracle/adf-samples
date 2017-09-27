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
function SliderMeasureReformatter(elements)
{   
  this.measureElements = elements.split(",");
}

SliderMeasureReformatter.prototype = new TrConverter();

SliderMeasureReformatter.prototype.getFormatHint = function()
{
	return null;
}

SliderMeasureReformatter.prototype.getAsString = function(selection,label) {
    return this.measureElements[selection];
}

SliderMeasureReformatter.prototype.getAsObject = function(selectionString,label){

        var returnValue;
        for (var i = 0; i < this.measureElements.length; i++) {
            if (selectionString == this.measureElements[i]){
                returnValue = i;
            }
        }
	return returnValue;
}