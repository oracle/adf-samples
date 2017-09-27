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


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;


import oracle.jbo.domain.Number;

public class ismsJboConverter extends IsmsConverter {
    public ismsJboConverter() {
        super();
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String valueAsString) {
        int intValue = (Integer)super.getAsObject(facesContext, uIComponent, valueAsString);
        return new Number(intValue);
    }
    
    
}
