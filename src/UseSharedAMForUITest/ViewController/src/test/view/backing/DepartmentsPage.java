/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view.backing;

import oracle.adf.model.BindingContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.event.PollEvent;

public class DepartmentsPage {
    private RichTable table;

    public DepartmentsPage() {
    }
    public void onPollTimerExpired(PollEvent pollEvent) {
        Long lastRequery = (Long)AdfFacesContext.getCurrentInstance().getViewScope().get("lastRequery");
        Long lastRequeryFromVO = (Long)BindingContext.getCurrent().getCurrentBindingsEntry().getOperationBinding("getLastRequery").execute();
        System.out.println("Timer expired: lastRequery = "+lastRequery+", lastRequeryFromVO = "+lastRequeryFromVO);
        if (lastRequery == null || (!lastRequery.equals(lastRequeryFromVO))) {
            AdfFacesContext.getCurrentInstance().getViewScope().put("lastRequery",lastRequeryFromVO);
            AdfFacesContext.getCurrentInstance().addPartialTarget(getTable());
            System.out.println("Data requeried in VO. PPR'ing the table");
        }
    }

    public void setTable(RichTable table) {
        this.table = table;
    }

    public RichTable getTable() {
        return table;
    }
}
