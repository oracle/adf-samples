/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oramag.model.fwkext;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.LocaleContext;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.Entity;
import oracle.jbo.server.ViewRowAttrHintsImpl;
import oracle.jbo.server.ViewRowImpl;
/*
 * This view row framework extension class adds the additional feature
 * of exposing two custom view row attribute hints:
 *
 *   'rowState' - returns the value 'Unmodified','Modified','New',or 'Deleted'
 *                for entity-based view objects, based on the entity state
 *                of the primary entity, or null if the view object is not
 *                entity based.
 *                
 *   'valueChanged' - returns the String "true" or "false" based on calling
 *                    is isAttributeChanged() API.
 *   
 * These hints can be referenced in JSF pages via the syntax:
 * 
 *     #{bindings.ControlBindingName.hints.rowState}
 *     #{bindings.ControlBindingName.hints.valueChanged}
 *     
 * or in a table:    
 *
 *     #{row.bindings.AttributeName.hints.rowState}
 *     #{row.bindings.AttributeName.hints.valueChanged}
 *     
 * or in a tree:
 *     
 *     #{node.bindings.AttributeName.hints.rowState}
 *     #{node.bindings.AttributeName.hints.valueChanged}
 *     
 * See sections "33.1 Globally Extending ADF Business Components Functionality"
 * and "33.2 Creating a Layer of Framework Extensions" in the Fusion Developer's
 * Guide for Oracle Application Development Framework for more information
 * on this powerful framework extension capability.
 * 
 * The "EmpView" view object in this workspace is declaratively
 * configured to use this class as its base view row class at runtime.
 *
 */
public class CustomViewRowImpl extends ViewRowImpl {
    @Override
    protected ViewRowAttrHintsImpl createViewRowAttrHints(AttributeDefImpl attrDef) {
        return new CustomViewRowAttrHints(attrDef,this);
    }
    class CustomViewRowAttrHints extends ViewRowAttrHintsImpl {
        protected CustomViewRowAttrHints(AttributeDefImpl attr, ViewRowImpl viewRow) {
           super(attr,viewRow);
        }
        @Override
        public String getHint(LocaleContext locale, String sHintName) {
            if ("rowState".equals(sHintName)) {
              ViewRowImpl vri = getViewRow();
              if (vri != null) {
                  Entity e = vri.getEntity(0);
                  if (e != null) {
                      return translateStatusToString(e.getEntityState());
                  }
                  return null;
              }
            }
            else if ("valueChanged".equals(sHintName)) {
                ViewRowImpl vri = getViewRow();
                if (vri != null) {
                    boolean changed = vri.isAttributeChanged(getViewAttributeDef().getName());
                    return changed ? "true":"false";
                }
            }
            return super.getHint(locale, sHintName);
        }
        private String translateStatusToString(byte b) {
          String ret = null;
          switch (b) {
            case Entity.STATUS_DELETED: {
              ret = "Deleted";
              break;
            }
            case Entity.STATUS_INITIALIZED: {
              ret = "Initialized";
              break;
            }
            case Entity.STATUS_MODIFIED: {
              ret = "Modified";
              break;
            }
            case Entity.STATUS_UNMODIFIED: {
              ret = "Unmodified";
              break;
            }
            case Entity.STATUS_NEW: {
              ret = "New";
              break;
            }
          }
          return ret;
        }        
    }
}
