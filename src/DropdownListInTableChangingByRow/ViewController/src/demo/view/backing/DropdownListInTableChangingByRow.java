package demo.view.backing;
import demo.model.common.HRModule;
import demo.view.util.EL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import oracle.adf.model.binding.DCIteratorBinding;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.Number;
public class DropdownListInTableChangingByRow {
  private static final int ENTITYBASED = 1;
  private static final int READONLY    = 2;
  
  private int lovStyleToUse = ENTITYBASED;
  
   /**
    * This is a Map-valued property whose class is an anonymous subtype
    * of HashMap. Using this syntax we're effectively defining a new class
    * that is a subtype of HashMap right inline and then creating a new
    * instance of it. This anonymous subclass overrides the Map's get() method.
    * Rather than returning a value from the Map based on the key passed in,
    * we use the key passed in as an argument to pass to custom method on
    * the client interface of our application module. See section 8.4,
    * "Publishing Custom Service Methods to Clients" in the ADF Dev Guide
    * for Forms/4GL Developers for more information on publishing methods
    * to the client interface.
    * 
    * This trick allows our JSPX page to use an EL expression like:
    * 
    *   #{DropdownListInTableChangingByRow.lovList[row.DepartmentId]}
    *   
    * and the EL resolver translates this into a call to the get() method
    * on the Map-valued "lovList" property in this backing bean. Our overridden
    * get() method then passes the departmentId to either the 
    * lovEmpsForDepartment() or the lovEmpsForDepartmentReadOnly() method of
    * our application module's HRModule interface depending on whether the
    * user picked to test the entity-based or the read-only view object for
    * use in the dropdown list.
    * 
    * The application module returns a RowIterator which our
    * selectItemsForIterator() method then converts to a java.util.List of
    * ADF Faces SelectItem objects which is what the ADF Faces [af:selectItems]
    * tag is expecting.
    * 
    */
   public Map lovList = new HashMap(){
      @Override
      public Object get(Object key) {
        Number deptno = (Number)key;
        /* TIP: Of course, in a real application you would typically not provide
         * ---  the end-user with this choice, being able to switch between an
         *      entity-based view object or a read-only view object here is
         *      purely for educational purposes for this sample.
         */
        if (lovStyleToUse == ENTITYBASED) {
          return selectItemsForIterator(
                 getHRModule().lovEmpsForDepartment(deptno),
                 "Value","Label");
        }
        else if (lovStyleToUse == READONLY) {
          return selectItemsForIterator(
                 getHRModule().lovEmpsForDepartmentReadOnly(deptno),
                 "Value","Label");          
        }
        else return null;
      }
   };
  public void setLovList(Map lovList) {
    this.lovList = lovList;
  }
  public Map getLovList() {
    return lovList;
  }
  
  private List<SelectItem> selectItemsForIterator(RowIterator iter, 
                                                        String valueAttrName, 
                                                        String displayAttrName) {
    List<SelectItem> selectItems = new ArrayList<SelectItem>();
    /*
     * Add a "<None>" item to the top of the list.
     */
    selectItems.add(new SelectItem("","<None>"));
    if (iter != null) {
      for (Row r: iter.getAllRowsInRange()) {
        selectItems.add(new SelectItem(r.getAttribute(valueAttrName), 
                                       (String)r.getAttribute(displayAttrName)));
      }
    }
    return selectItems;
  }
  public void setLovStyleToUse(int lovStyleToUse) {
    this.lovStyleToUse = lovStyleToUse;
  }
  public int getLovStyleToUse() {
    return lovStyleToUse;
  }
  /*
   * Return the HRModule custom interface of the application module
   */
  private HRModule getHRModule() {
    return (HRModule)EL.get("#{data.HRModuleDataControl.dataProvider}");
  }
}
