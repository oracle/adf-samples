/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package sample;

import oracle.jbo.AttributeDef;
import oracle.jbo.AttributeList;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SequenceImpl;

public class CustomEntityImpl extends EntityImpl {

    /*
     * This method iterates through all of the attribute definitions
     * for the current entity and for any attribute that has the
     * attribute-level custom property named "SequenceName" set, it populates
     * that attribute with the next value from the Oracle database sequence
     * whose name is provided as the value of that custom property.
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);
        for (AttributeDef def : getEntityDef().getAttributeDefs()) {
            String sequenceName = (String)def.getProperty("SequenceName");
            if (sequenceName != null) {
                SequenceImpl s = new SequenceImpl(sequenceName,getDBTransaction());
                populateAttributeAsChanged(def.getIndex(),s.getSequenceNumber());
            }
        }
    }
}
