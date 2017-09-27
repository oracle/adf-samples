/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package sample.view;

public class SessionSettings {
    boolean useAutoSubmit;

    public void setUseAutoSubmit(boolean autoSubmit) {
        this.useAutoSubmit = autoSubmit;
    }

    public boolean isUseAutoSubmit() {
        return useAutoSubmit;
    }
}
