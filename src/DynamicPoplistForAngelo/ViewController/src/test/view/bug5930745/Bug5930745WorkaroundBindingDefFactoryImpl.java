/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.bug5930745;

import oracle.adf.model.binding.DCDefBase;

import oracle.adfinternal.view.faces.model.binding.FacesCtrlActionDef;
import oracle.adfinternal.view.faces.model.binding.FacesCtrlAttrsDef;
import oracle.adfinternal.view.faces.model.binding.FacesCtrlHierDef;
import oracle.adfinternal.view.faces.model.binding.FacesCtrlListBinding;
import oracle.adfinternal.view.faces.model.binding.FacesCtrlListDef;
import oracle.adfinternal.view.faces.model.binding.FacesCtrlRangeDef;

import oracle.jbo.mom.xml.DefElement;
import oracle.jbo.uicli.binding.JUBindingDefFactoryImpl;
import oracle.jbo.uicli.mom.JUTags;

/**
 * Part of Workaround for Bug# 5930745
 * 
 * Had to copy code from FacesBindingDefFactoryImpl since it's final
 */
public class Bug5930745WorkaroundBindingDefFactoryImpl extends JUBindingDefFactoryImpl {
  public DCDefBase createDefinition(DefElement element)
  {
    String sElement = element.getElementName();
    if(sElement.equals(JUTags.PNAME_table))
    {
      return new FacesCtrlRangeDef();
    }
    else if(sElement.equals(JUTags.PNAME_tree))
    {
      return new FacesCtrlHierDef();
    }
    else if(sElement.equals(JUTags.PNAME_listOfValues) ||
            sElement.equals(JUTags.PNAME_list))
    {
      Bug5930745WorkaroundFacesCtrlListBindingDef def = new Bug5930745WorkaroundFacesCtrlListBindingDef();
      def.setSubType(DCDefBase.PNAME_ListSingleSel);
      /**
       * Use MyFacesCtrlListBinding instead of FacesCtrlListBinding
       */
      def.setControlBindingClassName(Bug5930745WorkaroundFacesCtrlListBinding.class.getName());
      return def;
    }
    else if(sElement.equals(JUTags.PNAME_attributeValues))
    {
      return new FacesCtrlAttrsDef();
    }
    else if(sElement.equals(JUTags.PNAME_methodAction) ||
            sElement.equals(JUTags.PNAME_action))
    {
      return new FacesCtrlActionDef();
    }
    return super.createDefinition(element);
  }
}
