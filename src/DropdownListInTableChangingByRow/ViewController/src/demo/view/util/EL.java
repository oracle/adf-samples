package demo.view.util;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
public class EL {
   public static boolean test(String booleanExpr) {
     return Boolean.TRUE.equals(get(booleanExpr));
   }  
  public static String getAsString(String expr) {
    return (String)get(expr);
  }
  public static Object get(String expr) {
    FacesContext fc = FacesContext.getCurrentInstance();
    ValueBinding vb = fc.getApplication().createValueBinding(expr);
    return vb.getValue(fc);
  }
  public static void set(String expr, Object value) {
    FacesContext fc = FacesContext.getCurrentInstance();
    ValueBinding vb = fc.getApplication().createValueBinding(expr);
    vb.setValue(fc,value);    
  }
}
