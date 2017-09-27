/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test.view;

import javax.faces.event.ActionEvent;

import oracle.ui.pattern.dynamicShell.TabContext;

/**
 * Launcher is a backingBean-scope managed bean. The public methods are
 * available to EL. The methods call TabContext APIs available to the
 * Dynamic Tab Shell Template. The boolean value for _launchActivity
 * determines whether another tab instance is created or selected. Each tab
 * (i.e., task flow) is tracked by ID. The title is the tab label.
 */

public class Launcher {
    public void multipleInstanceActivity(ActionEvent actionEvent) {
        /**
    * Example method when called repeatedly, will open another instance as
    * oppose to selecting a previously opened tab instance. Note the boolean
    * to create another tab instance is set to true.
    */

        _launchActivity("A New Activity", "/WEB-INF/flows/new.xml#new", true);
    }

    public void launchFirstActivity(ActionEvent actionEvent) {
        /**
      * Example method to call a single instance task flow. Note the boolean
      * to create another tab instance is set to false. The taskflow ID is used
      * to track whether to create a new tab or select an existing one.
      */
        _launchActivity("The First Activity", "/WEB-INF/flows/first.xml#first",
                        false);
    }

    public void launchSecondActivity(ActionEvent actionEvent) {
        _launchActivity("Next Activity", "/WEB-INF/flows/second.xml#second",
                        false);
    }

    public void launchThirdActivity(ActionEvent actionEvent) {
        _launchActivity("Third Activity", "/WEB-INF/flows/third.xml#third",
                        false);
    }

    public void closeCurrentActivity(ActionEvent actionEvent) {
        TabContext tabContext = TabContext.getCurrentInstance();
        int tabIndex = tabContext.getSelectedTabIndex();
        if (tabIndex != -1) {
            tabContext.removeTab(tabIndex);
        }
    }

    public void currentTabDirty(ActionEvent e) {
        /**
        * When called, marks the current tab "dirty". Only at the View level
        * is it possible to mark a tab dirty since the model level does not
        * track to which tab data belongs.
        */
        TabContext tabContext = TabContext.getCurrentInstance();
        tabContext.markCurrentTabDirty(true);
    }

    public void currentTabClean(ActionEvent e) {
        TabContext tabContext = TabContext.getCurrentInstance();
        tabContext.markCurrentTabDirty(false);
    }

    public void checkState(boolean isDirty) {
        /**
      * Example method to call prior to page navigation to determine if
      * any tab has been marked as dirty. If true, a popup dialog in the
      * current page can be called to warn the user of unsaved data.
      */
        TabContext tabContext = TabContext.getCurrentInstance();
        boolean isAnyTabDirty = tabContext.isTagSetDirty();
        if (tabContext != null && isAnyTabDirty) {
            // return and warn user in popup dialog from page
            isDirty = isAnyTabDirty;
            return;
        } else {
            isDirty = isAnyTabDirty;
            return;
        }
    }

    private void _launchActivity(String title, String taskflowId,
                                 boolean newTab) {
        try {
            if (newTab) {
                TabContext.getCurrentInstance().addTab(title, taskflowId);
            } else {
                TabContext.getCurrentInstance().addOrSelectTab(title,
                                                               taskflowId);
            }
        } catch (TabContext.TabOverflowException toe) {
            // causes a dialog to be displayed to the user saying that there are
            // too many tabs open - the new tab will not be opened...
            toe.handleDefault();
        }
    }

    public void launchFirstReplaceInPlace(ActionEvent actionEvent) {
        TabContext tabContext = TabContext.getCurrentInstance();
        try {
            tabContext.setMainContent("/WEB-INF/flows/first.xml#first");
        } catch (TabContext.TabContentAreaDirtyException toe) {
            // TODO: warn user TabContext api needed for this use case.
        }
    }

    public void launchSecondReplaceInPlace(ActionEvent actionEvent) {
        TabContext tabContext = TabContext.getCurrentInstance();
        try {
            tabContext.setMainContent("/WEB-INF/flows/second.xml#second");
        } catch (TabContext.TabContentAreaDirtyException toe) {
            // TODO: warn user TabContext api needed for this use case.
        }
    }
}
