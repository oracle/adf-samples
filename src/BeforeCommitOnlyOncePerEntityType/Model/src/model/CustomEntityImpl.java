/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package model;
import oracle.jbo.ViewObject;
import oracle.jbo.domain.Number;
import oracle.jbo.server.DBTransaction;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.TransactionEvent;
public class CustomEntityImpl extends EntityImpl {
  /**
   * This method will be invoked once per commit-cycle for each entity
   * object type represented in the entity's cache's list of entities
   * to process.
   * @param e The TransactionEvent.
   */
  public void validateEntityCollectionBeforeCommit(TransactionEvent e) {
    // Override in a subclass to do something just one time per entity
    // throw new ValidationException() if validation fails
  }
  /**
   * Overridden framework method cooperates with CustomTransactionImpl
   * which enables keeping track of  whether a beforeCommit() method
   * has fired for any entities of the current entity's type. If it's
   * the first time during this commit cycle that beforeCommit() is
   * getting called, then the validateEntityCollectionBeforeCommit()
   * method is invoked.
   *
   * NOTE: If your beforeCommit() logic throws validation errors, you
   * ====  must set the jbo.txn.handleafterpostexc property to "true"
   *       to have the framework automatically handle rolling back
   *       the in-memory state of your already-posted entity objects.
   *
   * NOTE: To use the CustomTransactionImpl class, you must set
   * ====  the TransactionFactory property to the fully-qualified
   *       class name of the custom TransactionFactory that will
   *       return instances of the CustomTransactionImpl class instead
   *       of the default.
   */
  public void beforeCommit(TransactionEvent e) {
    DBTransaction trans = getDBTransaction();
    if (trans instanceof CustomTransactionImpl) {
      CustomTransactionImpl customTrans = (CustomTransactionImpl) trans;
      if (!customTrans.alreadyDidBeforeCommitForEntityType(this)) {
        try {
          validateEntityCollectionBeforeCommit(e);
        }
        finally {
          customTrans.markAlreadyDidBeforeCommitForEntityType(this);
        }
      }
    }
    super.beforeCommit(e);
  }
  /**
   * Find instance of view object used for validation purposes in the
   * root application module. By convention, the instance of the view
   * object will be named Validation_your_pkg_YourViewObject.
   *
   * If not found, create it for the first time.
   *
   * @return ViewObject to use for validation purposes
   * @param viewObjectDefName
   */
  protected ViewObject getValidationVO(String viewObjectDefName) {
    String name = VALPREFIX + viewObjectDefName.replace('.', '_');
    ViewObject vo = getDBTransaction().getRootApplicationModule()
                      .findViewObject(name);
    if (vo == null) {
      vo = getDBTransaction().getRootApplicationModule().createViewObject(name,
          viewObjectDefName);
    }
    return vo;
  }
  /**
   * Helper method that returns the long result of a view object used
   * for validation purposes which performs a SELECT COUNT(*) query.
   */
  protected long getCountValidationVOResult(String defname) {
    ViewObject vo = getValidationVO(defname);
    vo.executeQuery();
    return ((Number) vo.first().getAttribute(0)).longValue();
  }

  private static String VALPREFIX = "Validation_";
}
