/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.demo.isms.view;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.model.NumberRange;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;

public class SamplePageBacking {
    public SamplePageBacking() {
        super();
    }
    
    public NumberRange getRangeSliderValue(){
        //Map the bound values to an intermediate NumberRange
        
        BindingContext ctx = BindingContext.getCurrent();
        BindingContainer bc = ctx.getCurrentBindingsEntry();
        
        AttributeBinding low = (AttributeBinding)bc.getControlBinding("dataRangeLow");
        AttributeBinding high = (AttributeBinding)bc.getControlBinding("dataRangeHigh");
        
        return new NumberRange((Long)low.getInputValue(),(Long)high.getInputValue());
    }
    
    
    public void setRangeSliderValue(NumberRange range){
        
        BindingContext ctx = BindingContext.getCurrent();
        BindingContainer bc = ctx.getCurrentBindingsEntry();
        AttributeBinding low = (AttributeBinding)bc.getControlBinding("dataRangeLow");
        AttributeBinding high = (AttributeBinding)bc.getControlBinding("dataRangeHigh");
        
        low.setInputValue(range.getMinimum());
        high.setInputValue(range.getMaximum());
    }
    
}
