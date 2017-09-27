/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package shay.glassfish;

import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.extension.RegisteredByExtension;


/**
 * Command handler for shay.glassfish.startglassfish.
 */
@RegisteredByExtension("shay.glassfish")
public final class startGlassfishCommand extends Command {
    public startGlassfishCommand() {
        super(actionId());
    }

    public int doit() {
        // TODO Implement the startTomcatCommand command's doit() method.

        return OK;
    }

    /**
     * Returns the id of the action this command is associated with.
     *
     * @return the id of the action this command is associated with.
     * @throws IllegalStateException if the action this command is associated
     *    with is not registered.
     */
    public static int actionId() {
        final Integer cmdId = Ide.findCmdID("shay.glassfish.startglassfish");
        if (cmdId == null)
            throw new IllegalStateException("Action shay.glassfish.startglassfish not found.");
        return cmdId;
    }
}
