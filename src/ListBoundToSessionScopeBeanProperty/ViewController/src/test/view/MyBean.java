/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;

import oracle.jbo.domain.Number;

public class MyBean {
    Number selectedDepartment;

    public void setSelectedDepartment(Number selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public Number getSelectedDepartment() {
        return selectedDepartment;
    }
}
