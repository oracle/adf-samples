/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.bug5930745;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlListBinding;

import oracle.jbo.uicli.binding.JUCtrlListBinding;
import oracle.jbo.uicli.binding.JUCtrlListDef;

public class Bug5930745WorkaroundFacesCtrlListBindingDef extends JUCtrlListDef 
{
  public Bug5930745WorkaroundFacesCtrlListBindingDef()
  {
  }

   protected JUCtrlListBinding createListBindingInstance(
    Object control,
    DCIteratorBinding iterBinding,
    String[] attrNames,
    int listOperMode)
   {
     return new FacesCtrlListBinding(control, iterBinding, attrNames, listOperMode);
   }
    
   protected JUCtrlListBinding createListBindingInstance(
    Object control,
    DCIteratorBinding iterBinding,
    String[] attrNames,
    Object[] valueList)
   {
     return new FacesCtrlListBinding(control, iterBinding, attrNames, valueList);
   }
    
   protected JUCtrlListBinding createListBindingInstance(
    Object control,
    DCIteratorBinding iterBinding,
    String[] attrNames,
    DCIteratorBinding listIterBinding,
    String[] listAttrNames,
    String[] listDisplayAttrNames)
   {
     return new Bug5930745WorkaroundFacesCtrlListBinding(control, iterBinding, attrNames, 
       listIterBinding, listAttrNames, listDisplayAttrNames);
   }

}
