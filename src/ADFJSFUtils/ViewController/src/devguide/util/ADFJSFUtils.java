/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package devguide.util;
import java.lang.StringBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import oracle.adf.model.BindingContext;
import oracle.adf.model.bc4j.DCJboDataControl;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.model.binding.DCParameter;
import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.ControlBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;
/**
 * A series of convenience functions for dealing with ADF Bindings
 * $Id: ADFJSFUtils.java,v 1.3 2006/01/19 01:42:48 steve Exp $
 */
public class ADFJSFUtils {

  /**
   * A convenience method for getting the value of a bound attribute in the
   * current page context programatically
   * @param bindings reference to the page's binding container, usually obtained using getBindings()
   * @param attributeName of the bound value in the pageDef
   * @return value of the attribute
   */
  public static Object getBoundAttributeValue(BindingContainer bindings, 
                                              String attributeName) {
    return findControlBinding(bindings, attributeName).getInputValue();
  }
  public static Object getBoundAttributeValue(String attributeName) {
    return findControlBinding(getBindingContainer(), 
                              attributeName).getInputValue();
  }

  /**
   * A convenience method for getting the value of a bound attribute in a
   * non-current page context programatically
   * @param bindings reference to the page's binding container, usually obtained using getBindings()
   * @param pageDefName name of the pageDef containing the binding
   * @param attributeName of the bound value in the pageDef
   * @return value of the attribute
   */
  public static Object getBoundAttributeValue(BindingContainer bindings, 
                                              String pageDefName, 
                                              String attributeName) {
    BindingContainer paramBC = 
      findBindingContainer(bindings, pageDefName);
    return getBoundAttributeValue(paramBC, attributeName);
  }

  /**
   * A convenience method for setting the value of a bound attribute in the
   * context of the current page.
   * @param bindings reference to the page's binding container, usually obtained using getBindings()
   * @param attributeName of the bound value in the pageDef
   * @param value to set
   */
  public static void setBoundAttributeValue(BindingContainer bindings, 
                                            String attributeName, 
                                            Object value) {
    findControlBinding(bindings, attributeName).setInputValue(value);
  }

  /**
   * A convenience method for setting the value of a bound attribute in the
   * context of non-current page.
   * @param bindings reference to the page's binding container, usually obtained using getBindings()
   * @param pageDefName name of the pageDef containing the binding
   * @param attributeName of the bound value in the pageDef
   * @param value to set
   */
  public static void setBoundAttributeValue(BindingContainer bindings, 
                                            String pageDefName, 
                                            String attributeName, 
                                            Object value) {
    BindingContainer paramBC = 
      findBindingContainer(bindings, pageDefName);
    setBoundAttributeValue(paramBC, attributeName, value);
  }

  /**
     * Returns the evaluated value of a pageDef parameter
     * @param bindings reference to the page's binding container, usually obtained using getBindings()
     * @param parameterName name of the pagedef parameter
     * @return evaluated value of the parameter as a String
     */
  public static Object getPageDefParameterValue(BindingContainer bindings, 
                                                String parameterName) {
    DCParameter param = 
      ((DCBindingContainer)bindings).findParameter(parameterName);
    return param.getValue();
  }

  /**
     * Returns the evaluated value of a pageDef parameter
     * @param bindings reference to the current BindingContainer
     * @param pageDefName reference to the page definition file of the page with the parameter
     * @param parameterName name of the pagedef parameter
     * @return evaluated value of the parameter as a String
     */
  public static Object getPageDefParameterValue(BindingContainer bindings, 
                                                String pageDefName, 
                                                String parameterName) {
    BindingContainer paramBC = 
      findBindingContainer(bindings, pageDefName);
    return getPageDefParameterValue(paramBC, parameterName);
  }
  private static String wrapInEL(String rawValue) {
    StringBuffer buff = new StringBuffer("${'");
    buff.append(rawValue);
    buff.append("'}");
    return buff.toString();
  }

  /**
     * @param bindings
     * @param pageDefName name of the page defintion XML file to use
     * @return BindingContainer ref for the named definition
     */
  public static BindingContainer findBindingContainer(BindingContainer bindings, 
                                                      String pageDefName) {
    BindingContext bctx = 
      ((DCBindingContainer)bindings).getBindingContext();
    BindingContainer foundContainer = 
      bctx.findBindingContainer(pageDefName);
    return foundContainer;
  }

  /**
   * Convenience method to find a DCControlBinding as a JUCtrlValueBinding
   * to get able to then call getInputValue() or setInputValue() on it.
   * @see ADFJSFUtils.getAttributeValue
   * @see ADFJSFUtils.setAttributeValue
   *
   * @param bindingContainer
   * @return the control value binding with the name passed in.
   */
  public static AttributeBinding findControlBinding(BindingContainer bindingContainer, 
                                                    String attributeName) {
    if (attributeName != null) {
      if (bindingContainer != null) {
        ControlBinding ctrlBinding = 
          bindingContainer.getControlBinding(attributeName);
        if (ctrlBinding instanceof AttributeBinding) {
          return (AttributeBinding)ctrlBinding;
        }
      }
    }
    return null;
  }
  public static ApplicationModule getDataControlApplicationModule(String name) {
    DCDataControl dc = getDataControl(name);
    return dc != null && dc instanceof DCJboDataControl ? 
           (ApplicationModule)dc.getDataProvider() : null;
  }
  public static DCDataControl getDataControl(String name) {
    BindingContext bc = getBindingContext();
    return bc != null ? bc.findDataControl(name) : null;
  }
  public static BindingContext getBindingContext() {
    return (BindingContext)JSFUtils.resolveExpression("#{data}");    
  }
  public static BindingContainer getBindingContainer() {
    return (BindingContainer)JSFUtils.resolveExpression("#{bindings}");
  }
  public static DCBindingContainer getDCBindingContainer() {
    return (DCBindingContainer)getBindingContainer();
  }
  public static List<SelectItem> selectItemsForIterator(String iteratorName, 
                                                        String valueAttrName, 
                                                        String displayAttrName) {
    return selectItemsForIterator(findIterator(iteratorName), 
                                  valueAttrName, displayAttrName);
  }
  public static List<SelectItem> selectItemsForIterator(String iteratorName, 
                                                        String valueAttrName, 
                                                        String displayAttrName, 
                                                        String descriptionAttrName) {
    return selectItemsForIterator(findIterator(iteratorName), 
                                  valueAttrName, displayAttrName, 
                                  descriptionAttrName);
  }
  public static List attributeListForIterator(String iteratorName, 
                                              String valueAttrName) {
    return attributeListForIterator(findIterator(iteratorName), 
                                    valueAttrName);
  }
  public static List<Key> keyListForIterator(String iteratorName) {
    return keyListForIterator(findIterator(iteratorName));
  }
  public static List<Key> keyListForIterator(DCIteratorBinding iter) {
    List attributeList = new ArrayList();
    for (Row r: iter.getAllRowsInRange()) {
      attributeList.add(r.getKey());
    }
    return attributeList;
  }
  public static List<Key> keyAttrListForIterator(String iteratorName, 
                                                 String keyAttrName) {
    return keyAttrListForIterator(findIterator(iteratorName), 
                                  keyAttrName);
  }
  public static List<Key> keyAttrListForIterator(DCIteratorBinding iter, 
                                                 String keyAttrName) {
    List attributeList = new ArrayList();
    for (Row r: iter.getAllRowsInRange()) {
      attributeList.add(new Key(new Object[] { r.getAttribute(keyAttrName) }));
    }
    return attributeList;
  }
  public static List attributeListForIterator(DCIteratorBinding iter, 
                                              String valueAttrName) {
    List attributeList = new ArrayList();
    for (Row r: iter.getAllRowsInRange()) {
      attributeList.add(r.getAttribute(valueAttrName));
    }
    return attributeList;
  }
  public static DCIteratorBinding findIterator(String name) {
    DCIteratorBinding iter = 
      getDCBindingContainer().findIteratorBinding(name);
    if (iter == null) {
      throw new RuntimeException("Iterator '" + name + "' not found");
    }
    return iter;
  }
  public static List<SelectItem> selectItemsForIterator(DCIteratorBinding iter, 
                                                        String valueAttrName, 
                                                        String displayAttrName, 
                                                        String descriptionAttrName) {
    List<SelectItem> selectItems = new ArrayList<SelectItem>();
    for (Row r: iter.getAllRowsInRange()) {
      selectItems.add(new SelectItem(r.getAttribute(valueAttrName), 
                                     (String)r.getAttribute(displayAttrName), 
                                     (String)r.getAttribute(descriptionAttrName)));
    }
    return selectItems;
  }
  public static List<SelectItem> selectItemsForIterator(DCIteratorBinding iter, 
                                                        String valueAttrName, 
                                                        String displayAttrName) {
    List<SelectItem> selectItems = new ArrayList<SelectItem>();
    for (Row r: iter.getAllRowsInRange()) {
      selectItems.add(new SelectItem(r.getAttribute(valueAttrName), 
                                     (String)r.getAttribute(displayAttrName)));
    }
    return selectItems;
  }
  public static List<SelectItem> selectItemsByKeyForIterator(String iteratorName, 
                                                             String displayAttrName) {
    return selectItemsByKeyForIterator(findIterator(iteratorName), 
                                       displayAttrName);
  }
  public static List<SelectItem> selectItemsByKeyForIterator(String iteratorName, 
                                                             String displayAttrName, 
                                                             String descriptionAttrName) {
    return selectItemsByKeyForIterator(findIterator(iteratorName), 
                                       displayAttrName, 
                                       descriptionAttrName);
  }
  public static List<SelectItem> selectItemsByKeyForIterator(DCIteratorBinding iter, 
                                                             String displayAttrName, 
                                                             String descriptionAttrName) {
    List<SelectItem> selectItems = new ArrayList<SelectItem>();
    for (Row r: iter.getAllRowsInRange()) {
      selectItems.add(new SelectItem(r.getKey(), 
                                     (String)r.getAttribute(displayAttrName), 
                                     (String)r.getAttribute(descriptionAttrName)));
    }
    return selectItems;
  }
  public static List<SelectItem> selectItemsByKeyForIterator(DCIteratorBinding iter, 
                                                             String displayAttrName) {
    List<SelectItem> selectItems = new ArrayList<SelectItem>();
    for (Row r: iter.getAllRowsInRange()) {
      selectItems.add(new SelectItem(r.getKey(), 
                                     (String)r.getAttribute(displayAttrName)));
    }
    return selectItems;
  }
}
