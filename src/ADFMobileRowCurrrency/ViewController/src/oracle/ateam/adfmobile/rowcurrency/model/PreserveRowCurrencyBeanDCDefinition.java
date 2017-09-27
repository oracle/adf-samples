/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.ateam.adfmobile.rowcurrency.model;

import java.util.HashMap;
import java.util.Map;

import oracle.adf.model.adapter.bean.BeanDCDefinition;

import oracle.adfmf.bindings.BindingContainer;
import oracle.adfmf.bindings.dbf.AmxIteratorBinding;
import oracle.adfmf.bindings.iterator.BasicIterator;
import oracle.adfmf.metadata.page.BeanBindingIteratorBaseDefinition;
import oracle.adfmf.util.XmlAnyDefinition;

public class PreserveRowCurrencyBeanDCDefinition extends BeanDCDefinition {
    private Map iteratorsCurrentRowIndex = new HashMap();

  /**
   * This method contains the logic to preserve row currency across pages.
   * For each iterator binding the binding id, concatenated with the "binding path" is stored in an 
   * 'iteratorsCurrentRowIndex' map together with the current index. 
   * The iterator binding itself is created by calling super. If the binding id-path of the newly 
   * created iterator binding is already present in the map, the row index in the map is set as current 
   * index on the newly created iterator binding.
   * @param object
   * @param xmlAnyDefinition
   * @param bindingContainer
   * @return
   */
  public BasicIterator getIteratorBinding(Object object, XmlAnyDefinition xmlAnyDefinition,
                                          BindingContainer bindingContainer) {
      BeanBindingIteratorBaseDefinition bindingDef = (BeanBindingIteratorBaseDefinition)xmlAnyDefinition;
      String binds = bindingDef.getBinds();
      // build up the iterator binding path by walking up the MasterBinding path until Root is
      //encountered.
      StringBuffer iteratorPath = new StringBuffer(binds);
      BeanBindingIteratorBaseDefinition curBindingDef = bindingDef;
      while (curBindingDef.getMasterBinding() != null) {
          iteratorPath.insert(0, curBindingDef.getMasterBinding()+".");
          AmxIteratorBinding curIter = (AmxIteratorBinding)bindingContainer.get(curBindingDef.getMasterBinding());
          curBindingDef = (BeanBindingIteratorBaseDefinition)curIter.getMetadataDefinition();
      }
     // prefix iter path with binding id, so we can support multiple iterators in to same data collection that can
     // have their own current row index.
      iteratorPath.insert(0, bindingDef.getId() + ":");
      BasicIterator iter = super.getIteratorBinding(object, xmlAnyDefinition, bindingContainer);
      BasicIterator previousIter = (BasicIterator)iteratorsCurrentRowIndex.put(iteratorPath.toString(), iter);
      if (previousIter != null && previousIter.getCurrentIndex() > -1 &&
          iter.getTotalRowCount() > previousIter.getCurrentIndex()) {
          iter.setCurrentIndex(previousIter.getCurrentIndex());
      }
      return iter;
  }

}
