/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view;

import java.util.Locale;

import javax.faces.context.FacesContext;

public class App {
    Locale preferredLocale;
    
    public void setPreferredLocaleAsString(String localeString) {
            preferredLocale = new Locale(localeString);
    }
    public String getPreferredLocaleAsString() {
            return preferredLocale.toString();
    }

    public void setPreferredLocale(Locale preferredLocale) {
            this.preferredLocale = preferredLocale;
    }

    public Locale getPreferredLocale() {
        return preferredLocale;
    }
}
