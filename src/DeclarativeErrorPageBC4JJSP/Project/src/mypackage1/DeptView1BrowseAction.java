/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package mypackage1;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracle.jbo.html.BC4JContext;
import oracle.jbo.ViewObject;
import oracle.jbo.html.struts11.BC4JUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DeptView1BrowseAction extends Action 
{
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    BC4JContext context = BC4JContext.getContext(request);

    // Retrieve the view object instance to work with by name
    ViewObject vo = context.getApplicationModule().findViewObject("DeptView1");
    vo.setRangeSize(3);
    vo.setIterMode(ViewObject.ITER_MODE_LAST_PAGE_PARTIAL);
    // Do any additional VO setup here (e.g. setting bind parameter values)

    return BC4JUtils.getForwardFromContext(context, mapping);
  }
}
