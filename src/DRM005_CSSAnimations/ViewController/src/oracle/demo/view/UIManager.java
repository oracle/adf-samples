/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.demo.view;

/**
 * Holds persistent information about the UI session 
 */
public class UIManager {
    public static final String A_SIDE = "ASide";
    public static final String B_SIDE = "BSide";
    private String flipAnimationSelectedSide = A_SIDE;
    public UIManager() {
        super();
    }

    public void setFlipAnimationSelectedSide(String flipAnimationSelectedSide) {
        this.flipAnimationSelectedSide = flipAnimationSelectedSide;
    }

    public String getFlipAnimationSelectedSide() {
        return flipAnimationSelectedSide;
    }
}
