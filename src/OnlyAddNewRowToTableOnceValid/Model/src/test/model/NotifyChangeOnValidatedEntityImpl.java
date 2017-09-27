/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.model;

import oracle.jbo.server.EntityImpl;

public class NotifyChangeOnValidatedEntityImpl extends EntityImpl {
    transient boolean aboutToMarkEntityValid = false;
    @Override
    protected void validateEntity() {
        super.validateEntity();
        if (getAllExceptions()==null) {            
            try {
                aboutToMarkEntityValid = true;
                notifyAttributesChanged(new int[]{0},
                                        new Object[]{getAttribute(0)},false);
            }
            finally {
                aboutToMarkEntityValid = false;
            }
        }
    }
    @Override
    public boolean isValid() {
      return aboutToMarkEntityValid ? true : super.isValid();
    }    
}
