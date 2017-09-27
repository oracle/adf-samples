package test.rules;
import oracle.jbo.LocaleContext;
import oracle.jbo.ValidationException;
import oracle.jbo.domain.Date;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.rules.JbiValidator;
import oracle.jbo.server.util.PropertyChangeEvent;
/**
 * Generic Date Range Validation Rule
 *
 * When the rule is added to an entity object, the
 * developer configures its rule-instance properties
 * for "BeginDateAttrName" and "EndDateAttrName"
 * so the rule knows which date-valued attributes
 * to validate as beginning and ending dates.
 * 
 * We package this into the DateRangeValidationRule.jar
 * for use at runtime and/or design time. 
 */
public class DateRangeRule implements JbiValidator {
  public DateRangeRule() {}

  /* Return true if start date is less than end date */
  public boolean validateValue(Object entity) {
    EntityImpl eo = (EntityImpl) entity;
    Date beginDate = (Date) eo.getAttribute(getBeginDateAttrName());
    Date endDate = (Date) eo.getAttribute(getEndDateAttrName());
    return (beginDate == null) || (endDate == null) ||
    (beginDate.compareTo(endDate) < 0);
  }
  /* Invoked by the framework for validation */
  public void vetoableChange(PropertyChangeEvent eventObj) {
    EntityImpl eo = (EntityImpl) eventObj.getSource();
    if (!validateValue(eo)) {
      LocaleContext ctx = eo.getDBTransaction().getSession().getLocaleContext();
      String beginPrompt = eo.getStructureDef()
                             .findAttributeDef(getBeginDateAttrName())
                             .getUIHelper().getLabel(ctx);
      String endPrompt = eo.getStructureDef()
                           .findAttributeDef(getEndDateAttrName()).getUIHelper()
                           .getLabel(ctx);
      throw new ValidationException(beginPrompt + " must be before " +
        endPrompt);
    }
  }
  public String getDescription() {
    return description;
  }
  public String getBeginDateAttrName() {
    return beginDateAttrName;
  }
  public String getEndDateAttrName() {
    return endDateAttrName;
  }
  public void setDescription(String str) {
    description = str;
  }
  public void setBeginDateAttrName(String beginDateAttrName) {
    this.beginDateAttrName = beginDateAttrName;
  }
  public void setEndDateAttrName(String endDateAttrName) {
    this.endDateAttrName = endDateAttrName;
  }

  private String description;
  private String beginDateAttrName;
  private String endDateAttrName;
}
