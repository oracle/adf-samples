/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example;

import oracle.jbo.AttributeDef;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;

public class MyViewRowImpl extends ViewRowImpl {
    public MyViewRowImpl() {
    }

    /*
     * In class MyViewRowImpl extends ViewRowImpl
     * 
     * Force view-object-attribute-level defaults to override
     * entity-attribute-level defaults.
     */
    protected void initDefaults() {
        super.initDefaults();
        for (AttributeDef attr: getViewObject().getAttributeDefs()) {
            byte attrKind = attr.getAttributeKind();
            // If the attribute is not an association accessor
            if (attrKind != AttributeDef.ATTR_ASSOCIATED_ROW && 
                attrKind != AttributeDef.ATTR_ASSOCIATED_ROWITERATOR) {
                AttributeDefImpl attrImpl = (AttributeDefImpl)attr;
                Object defaultValue = attrImpl.getDefaultValue();
                // And if the default value at VO attr level is non null...
                if (defaultValue != null) {
                    // Default view row attribute to that VO attr level default
                    populateAttributeAsChanged(attr.getIndex(), defaultValue);
                }
            }
        }
    }
}
