package test.rules;
import java.beans.BeanDescriptor;
import java.beans.SimpleBeanInfo;
/**
 * Bean Info to associate our custom editor with the validation rule
 * 
 * We package this into the DateRangeValidationRuleDT.jar
 * for use at design time only.* 
 */
public class DateRangeRuleBeanInfo extends SimpleBeanInfo {
  public BeanDescriptor getBeanDescriptor() {
    return new BeanDescriptor(DateRangeRule.class, DateRangeRuleCustomizer.class);
  }
}
