/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view.backing;

import java.util.Locale;

import javax.faces.context.FacesContext;

import view.utils.ELHelper;

public class TestPage {
    public TestPage() {
    }

    public String onClickItalianButton() {
        // Add event code here...
        ELHelper.set("#{App.preferredLocale}", Locale.ITALIAN);
        return null;
    }

    public String onClickEnglishButton() {
        // Add event code here...
        ELHelper.set("#{App.preferredLocale}", Locale.ENGLISH);
        return null;
    }
}
