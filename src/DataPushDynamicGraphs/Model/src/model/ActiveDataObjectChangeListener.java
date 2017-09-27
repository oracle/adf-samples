package model;

/**
 * This is the custom listener interface that should define the various types of changes
 * the user interface must be able to show real time.
 * 
 * With the back end service you can register listeners 
 * of this type that will be notified.
 */
    public interface ActiveDataObjectChangeListener {


  void onNew(Integer rowKey,ActiveDataObject event);
  void onUpdate(Integer rowKey,ActiveDataObject event);
  void onRemove(Integer rowKey,ActiveDataObject event);
  
  String getDataObjectName();

}
