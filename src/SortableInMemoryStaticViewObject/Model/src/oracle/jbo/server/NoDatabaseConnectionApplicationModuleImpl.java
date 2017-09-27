/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.jbo.server;

import oracle.jbo.Transaction;
/**
 * NOTE: This class is required only if you want to have the view object
 * ----  create() method fire when no database connection is present.
 * 
 *       Also note that due to classloading issues, you need to update the
 *       bc4jmt.jar to include this file in order to use it in a web application
 *       otherwise an InvalidAccessError will be raised.
 */
public class NoDatabaseConnectionApplicationModuleImpl extends ApplicationModuleImpl {
    protected void addChild(ComponentObjectImpl object) {
        super.addChild(object);
        Transaction trans;
        if ((trans = getTransaction()) == null || ((DBTransaction) trans).isConnected(false) == false)
        {
           object.callCreate();
        }        
    }
}
