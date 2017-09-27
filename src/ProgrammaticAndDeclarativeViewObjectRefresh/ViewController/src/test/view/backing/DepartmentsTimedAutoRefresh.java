/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import javax.faces.component.html.HtmlForm;

import oracle.adf.view.faces.component.core.CorePoll;
import oracle.adf.view.faces.component.core.data.CoreColumn;
import oracle.adf.view.faces.component.core.data.CoreTable;
import oracle.adf.view.faces.component.core.output.CoreMessages;
import oracle.adf.view.faces.component.core.output.CoreOutputText;
import oracle.adf.view.faces.component.html.HtmlBody;
import oracle.adf.view.faces.component.html.HtmlHead;
import oracle.adf.view.faces.component.html.HtmlHtml;
import oracle.adf.view.faces.event.PollEvent;

import test.view.util.OnPageLoadBackingBeanBase;

public class DepartmentsTimedAutoRefresh extends OnPageLoadBackingBeanBase {
  public void onPollTimerExpired(PollEvent pollEvent) {
    executeOperationBinding("Execute");
  }
}
