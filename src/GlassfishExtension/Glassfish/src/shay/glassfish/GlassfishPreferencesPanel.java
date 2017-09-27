/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package shay.glassfish;

import javax.swing.JTextField;
import javax.swing.JTextArea;

import oracle.ide.extension.RegisteredByExtension;
import oracle.ide.panels.DefaultTraversablePanel;
import oracle.ide.panels.TraversableContext;

import oracle.javatools.ui.layout.FieldLayoutBuilder;

/** 
 * Tomcat Preferences preference page implementation.
 */
@RegisteredByExtension("shay.glassfish")
final class GlassfishPreferencesPanel extends DefaultTraversablePanel {
    private final JTextField home = new JTextField();
    private final JTextField start = new JTextField();
    private final JTextField stop = new JTextField();
    private final JTextField dbg = new JTextField();
    private final JTextField admin = new JTextField();
    private final JTextArea intro = new JTextArea("Fill the complete path to bat files that will start/stop/start in debug mode your Tomcat instance.");

    public GlassfishPreferencesPanel() {
        // TODO lay out the Tomcat Preferences preferences page
        FieldLayoutBuilder b = new FieldLayoutBuilder(this);

        b.addHintText("Insert the complete path to bat files that will start/stop and start in debug mode your Tomcat instance.");
        b.add(b.field().label().withText("&Glassfish Home Directory:").component(home));
        b.add(b.field().label().withText("&Start Glassfish Command:").component(start));
        b.add(b.field().label().withText("&Stop Glassfish Command:").component(stop));
        b.add(b.field().label().withText("&Start Glassfish  in Dubug Mode Command:").component(dbg));
        b.add(b.field().label().withText("&Glassfish Admin URL:").component(admin));
        b.addVerticalSpring();
    }

    public void onEntry(TraversableContext context) {
        final GlassfishDataStructure data = getTomcatDataStructure(context);
        // TODO populate the controls in Tomcat Preferences from the data model
        home.setText(data.getTomcatHome());
        start.setText(data.getTomcatStart());
        stop.setText(data.getTomcatStop());
        dbg.setText(data.getTomcatDb());
        admin.setText(data.getGlassfishAdmin());
    }

    public void onExit(TraversableContext context) {
        final GlassfishDataStructure data = getTomcatDataStructure(context);
        // TODO populate the data model from the controls in Tomcat Preferences
        data.setTomcatHome(home.getText());
        data.setTomcatStart(start.getText());
        data.setTomcatStop(stop.getText());
        data.setTomcatDb(dbg.getText());
        data.setGlassfishAdmin(dbg.getText());
    }
    

    private static GlassfishDataStructure getTomcatDataStructure(TraversableContext tc) {
        return GlassfishDataStructure.getInstance(tc.getPropertyStorage());
    }
}
