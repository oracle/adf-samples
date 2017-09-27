/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

import oracle.jbo.RowSet;
import oracle.jbo.ValidationException;
import oracle.jbo.rules.JboAbstractValidator;
import oracle.jbo.rules.JboValidatorContext;
import oracle.jbo.rules.JboValidatorInterface;
import oracle.jbo.server.Entity;

public class AttrLevelViewAccessorValidator extends JboAbstractValidator  {
    private String description = "Attribute Level view-accessor validator.";
    private String viewAccessorName;
    private Integer moduloToTest = 1;
    private String attrNameToBindNewValueTo;
    public AttrLevelViewAccessorValidator() {
    }

  

    /**
     * Invoked by framework for validation.
     */
    public void validate(JboValidatorContext ctx) {
        Entity e = (Entity)ctx.getSource();
        RowSet rs = (RowSet)e.getAttribute(getViewAccessorName());
        rs.setNamedWhereClauseParam(getAttrNameToBindNewValueTo(), ctx.getNewValue());
        rs.executeQuery();
        if (rs.first() == null) {
            raiseException(null, ctx);
        }
    }

    /**
     * Description of what this class validates.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Description of what this class validates.
     */
    public void setDescription(String str) {
        description = str;
    }

    public void setViewAccessorName(String viewAccessorName) {
        this.viewAccessorName = viewAccessorName;
    }

    public String getViewAccessorName() {
        return viewAccessorName;
    }

    public void setAttrNameToBindNewValueTo(String attrNameToBindNewValueTo) {
        this.attrNameToBindNewValueTo = attrNameToBindNewValueTo;
    }

    public String getAttrNameToBindNewValueTo() {
        return attrNameToBindNewValueTo;
    }

    public boolean validateValue(Object value) {
        return false;
    }

    public String getDefXMLElementTag() {
        return null;
    }
}
