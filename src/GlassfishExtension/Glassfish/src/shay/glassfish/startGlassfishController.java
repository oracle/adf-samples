/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package shay.glassfish;

import java.io.IOException;

import oracle.ide.Context;
import oracle.ide.config.Preferences;
import oracle.ide.controller.Controller;
import oracle.ide.controller.IdeAction;
import oracle.ide.extension.RegisteredByExtension;
import oracle.ide.log.LogManager;
import oracle.ide.log.LogPage;
import oracle.ide.panels.TraversableContext;


/**
 * Controller for action shay.glassfish.startglassfish.
 */
@RegisteredByExtension("shay.glassfish")
public final class startGlassfishController implements Controller {
    public boolean update(IdeAction action, Context context) {
        // TODO Determine when shay.glassfish.startglassfish is enabled, and call action.setEnabled().
        return true;
    }

    public boolean handleEvent(IdeAction action, Context context) {
        // Command is handled by shay.glassfish.startTomcatCommand
        Preferences pr = Preferences.getPreferences();
        GlassfishDataStructure tc = GlassfishDataStructure.getInstance(pr);

   
        Process p;
        try {
            p=Runtime.getRuntime().exec("cmd /c start "+tc.getTomcatStart());
            LogPage msgPage = LogManager.getLogManager().getMsgPage();
            msgPage.log("Glassfish is Starting ");
            msgPage.log("
");
        } catch (IOException e) {
            LogPage msgPage = LogManager.getLogManager().getMsgPage();
            msgPage.log(e);
            msgPage.log("
");
            System.out.println(e);
        }
        return false;
    }

}
