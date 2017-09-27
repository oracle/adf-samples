/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oramag.model.fwkext;

import oracle.jbo.AttributeDef;
import oracle.jbo.server.AssociationDefImpl;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.Entity;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
/*
 * This class implements a generic, and slightly more optimized version of the
 * code explained in sections "34.8 Controlling Entity Posting Order to Avoid
 * Constraint Violations" and "34.8.3.2 Forcing the Supplier to Post Before the
 * Product" in the ADF 11g Fusion Developer's Guide. Its behavior is configured
 * by setting a custom property named "PostAfterAccessors" on any entity that
 * uses this base class as its base row class. Remember that since the ADF
 * framework automatically handles the post-ordering for composition
 * associations, we don't need to perform any special processing for those.
 *
 * The "Emp" and "Dept" entity objects in this workspace are declaratively
 * configured to use this class as its base row class at runtime.
 *
 * See sections "33.1 Globally Extending ADF Business Components Functionality"
 * and "33.2 Creating a Layer of Framework Extensions" in the Fusion Developer's
 * Guide for Oracle Application Development Framework for more information
 * on this powerful framework extension capability.
 */
public class CustomEntityImpl extends EntityImpl {
    private static final String POST_AFTER_ACCESSORS = "PostAfterAccessors";
    private static final String REGEX_COMMA_WITH_OPT_WHITESPACE = ",\s*";
    /*
     * Overridden framework method
     *
     * This code overrides the default processing
     */
    @Override
    public void postChanges(TransactionEvent e) {
        String[] postAfterAccessors = getPostAfterAccessors();
        /* Only perform this processing if postAfterAccessors prop != null */
        if (postAfterAccessors != null) {
            byte postState = getPostState();
            /* If current entity is new or modified */
            if (postState == STATUS_NEW || postState == STATUS_MODIFIED) {
                for (String postAfterAccessor : postAfterAccessors) {
                    /*
                     * Only check the referenced entity if it is not a
                     * composition and it at least one of the association
                     * attributes has changed (to avoid retrieving
                     * referenced entity into cache unnecessarily.
                     */
                    if (!isComposition(postAfterAccessor) &&
                        isAnyAssocAttrChanged(postAfterAccessor)) {
                        /* Get the associated referenced entity */
                        Object obj = getAttribute(postAfterAccessor);
                        if (obj instanceof Entity) {
                            Entity refEntity = (Entity)obj;
                            /* And if it's post-status is NEW */
                            if (refEntity.getPostState() == STATUS_NEW) {
                                /*
                                 * Post the referenced entity first, before
                                 * posting this entity.
                                 */
                                refEntity.postChanges(e);
                            }
                        }
                    }
                }
            }
        }
        super.postChanges(e);
    }
    /*
     * Returns true if association named by the accessor passed in is
     * a composition
     */
    private boolean isComposition(String accessorName) {
      AttributeDef assocAttrDef = getEntityDef().findAttributeDef(accessorName);      
      return ((AssociationDefImpl)assocAttrDef).hasContainer();
    }
    /*
     * Returns true if any attribute in the current entity object, which is
     * involved in the association named by the accessor passed in, has
     * changed its value in the current transaction.
     */
    private boolean isAnyAssocAttrChanged(String accessorName) {
        AssociationDefImpl attr =
            (AssociationDefImpl)getEntityDef().findAttributeDef(accessorName);
        AttributeDefImpl[] attrsInAssoc = attr.getAttributeDefImpls();
        boolean anyAssocAttrChanged = false;
        for (AttributeDefImpl assocAttrDef : attrsInAssoc) {
            if (isAttributeChanged(assocAttrDef.getIndex())) {
                anyAssocAttrChanged = true;
                break;
            }
        }
        return anyAssocAttrChanged;
    }
    /*
     * Returns true if any attribute in the current entity object, which is
     * involved in the association named by the accessor passed in, has
     * changed its value in the current transaction.
     */
    private String[] getPostAfterAccessors() {
        EntityDefImpl eDef = getEntityDef();
        String postAfterAssocList =
            (String)eDef.getProperty(POST_AFTER_ACCESSORS);
        if (postAfterAssocList != null && postAfterAssocList.length() > 0) {
            return postAfterAssocList.split(REGEX_COMMA_WITH_OPT_WHITESPACE);
        }
        return null;
    }

}
