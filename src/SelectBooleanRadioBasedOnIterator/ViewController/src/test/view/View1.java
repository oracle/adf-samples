/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;

import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.jbo.Row;
import oracle.jbo.uicli.binding.JUCtrlValueBinding;

public class View1
{
   SelectBooleanRadioHelper selectedControl1 =
      new SelectBooleanRadioHelper("Loc", "currentSelectedValue1Binding", "DeptView1");
   SelectBooleanRadioHelper selectedControl2 =
      new SelectBooleanRadioHelper("Empno", "currentSelectedValue2Binding", "EmpView1");

   public SelectBooleanRadioHelper getSelectedControl1()
   {
      return selectedControl1;
   }

   public SelectBooleanRadioHelper getSelectedControl2()
   {
      return selectedControl2;
   }

   public void onSelectBoolean1RadioChanged(ValueChangeEvent valueChangeEvent)
   {
      // Force Faces to push the changed values through to its value expression target
      EL.setValueChangeEventComponentToNewValue(valueChangeEvent);
      // PPR the field on the page to show the update to the current value.
      if (Boolean.TRUE.equals(valueChangeEvent.getNewValue()))
      {
         pprComponentsById(new String[] { "it1", "it2", "it3" });
      }
   }

   public void onSelectBoolean2RadioChanged(ValueChangeEvent valueChangeEvent)
   {
      // Force Faces to push the changed values through to its value expression target
      EL.setValueChangeEventComponentToNewValue(valueChangeEvent);
      // PPR the field on the page to show the update to the current value.
      if (Boolean.TRUE.equals(valueChangeEvent.getNewValue()))
      {
         pprComponentsById(new String[] { "it1", "it2", "it3" });
      }
   }

   private void pprComponentsById(String[] componentIds)
   {
      for (String componentId : componentIds)
      {
         UIComponent uic =
            FacesContext.getCurrentInstance().getViewRoot().findComponent(componentId);
         AdfFacesContext.getCurrentInstance().addPartialTarget(uic);
      }
   }
}
