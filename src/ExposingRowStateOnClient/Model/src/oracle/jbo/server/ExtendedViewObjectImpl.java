/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.jbo.server;
/**
 * NOTE: It is not good practice to define user extension classes
 * ====  in the oracle.jbo.server.* package. However, until Bug# 4547474
 *       gets fixed (asking to make certain package-private methods
 *       required in this solution public) I needed to place this
 *       class into the oracle.jbo.server package.
 */
import oracle.jbo.AttributeDef;
import oracle.jbo.Row;

import oracle.jbo.server.EntityEvent;
import oracle.jbo.server.ViewObjectImpl;

import java.util.Enumeration;


public class ExtendedViewObjectImpl extends ViewObjectImpl {
    public static final String ROWSTATE_ATTR = "RowState";

    /*
     * Add a dynamic "RowState" attribute to this view object
     */
    protected void create() {
        super.create();
        addDynamicAttribute(ROWSTATE_ATTR);
    }

    /*
     * Augment the default behavior of the source changed event to notify
     * any view rows containing the entity whose state has been changed
     * that its "RowState" attribute has been changed.
     */
    public void sourceChanged(EntityEvent event) {
        super.sourceChanged(event);

        if (event.getEventType() == EntityEvent.STATE_CHANGE) {
            Enumeration e = getAllCachedQueryCollections();
            EntityImpl entity = event.getEntity();

            while (e.hasMoreElements()) {
                QueryCollection qc = (QueryCollection) e.nextElement();
                AttributeDef attrDef = qc.getViewObjectImpl().findAttributeDef(ROWSTATE_ATTR);

                if (attrDef != null) {
                    /*
                     * NOTE: This oracle.jbo.server.* package-privae
                     * ====  method has changed signature in 10.1.3
                     */
                    qc.getViewObjectImpl().notifyRowUpdated(qc, entity,
                        new int[] { attrDef.getIndex() });
                }
            }
        }
    }
}
