/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.view;

import oracle.adf.model.*;
import oracle.adf.model.binding.*;
import oracle.adf.model.generic.*;

import oracle.jbo.Row;
import oracle.jbo.RowIterator;

import oracle.jbo.common.DefLocaleContext;
import oracle.jbo.common.JBOClass;

import oracle.jbo.uicli.*;
import oracle.jbo.uicli.binding.*;
import oracle.jbo.uicli.controls.*;
import oracle.jbo.uicli.jui.*;
import oracle.jbo.uicli.mom.JUMetaObjectManager;

import java.awt.*;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.*;

import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.*;
import javax.swing.text.Document;


public class Form extends JFrame {
    // form layout
    private GridLayout gridLayout = new GridLayout();
    private BorderLayout borderLayout = new BorderLayout();

    // panel definition used in design time
    private JUPanelBinding panelBinding = new JUPanelBinding("FormUIModel");

    // Navigation bar
    // The status bar
    // The form's top panel
    private JPanel topPanel = new JPanel();
    private JPanel dataPanel = new JPanel();

    // menu definitions
    private String aboutMessage = "about message";
    private String aboutTitle = "about title ";
    private JButton jButton1 = new JButton();
    private JTextField jTextField1 = new JTextField();

    /**
     *
     *  The default constructor for form
     */
    public Form() {
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    panelBinding.releaseDataControl();
                    System.exit(0);
                }
            });
    }

    /**
     *
     *  the JbInit method
     */
    public void jbInit() throws Exception {
        // form layout
        dataPanel.setLayout(null);
        jButton1.setText("jButton1");
        jButton1.setBounds(new Rectangle(15, 10, 73, 23));
        jTextField1.setText("jTextField1");
        jTextField1.setBounds(new Rectangle(95, 10, 150, 25));
        this.getContentPane().setLayout(gridLayout);
        this.setTitle("Programmatic Example");
        topPanel.setLayout(borderLayout);

        this.getContentPane().add(topPanel);

        this.setSize(new Dimension(295, 91));

        dataPanel.add(jTextField1, null);
        dataPanel.add(jButton1, null);
        topPanel.add(dataPanel, BorderLayout.CENTER);

        jButton1.setModel((ButtonModel) panelBinding.bindUIControl("sayHello",
                jButton1));
        jButton1.setText("sayHello");

        ((JUActionBinding) panelBinding.findCtrlBinding("sayHello")).addActionBindingListener(new JUActionBindingAdapter() {
                public void beforeActionPerformed(JUActionBindingEvent ev) {
                    ArrayList myParams = new ArrayList();
                    myParams.add(jTextField1.getText());
                    ev.getActionBinding().setParams(myParams);
                }

                public void afterActionPerformed(JUActionBindingEvent ev) {
                    // Add code to run after invoking the action binding here
                }
            });
    }

    private void file_exit_action(ActionEvent e) {
        panelBinding.releaseDataControl();
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exemp) {
            exemp.printStackTrace();
        }

        try {
            // bootstrap application
            JUMetaObjectManager.setErrorHandler(new JUErrorHandlerDlg());

            JUMetaObjectManager mgr = JUMetaObjectManager.getJUMom();
            mgr.setJClientDefFactory(null);

            BindingContext ctx = new BindingContext();
            ctx.put(DataControlFactory.APP_PARAM_ENV_INFO,
                new JUEnvInfoProvider());
            ctx.setLocaleContext(new DefLocaleContext(null));

            HashMap map = new HashMap(4);
            map.put(DataControlFactory.APP_PARAMS_BINDING_CONTEXT, ctx);
            mgr.loadCpx("DataBindings.cpx", map);

            Form frame = new Form();
            frame.setBindingContext(ctx);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = frame.getSize();

            // run this form
            if (frameSize.height > screenSize.height) {
                frameSize.height = screenSize.height;
            }

            if (frameSize.width > screenSize.width) {
                frameSize.width = screenSize.width;
            }

            frame.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
            frame.setVisible(true);
        } catch (Exception ex) {
            JUErrorHandlerDlg dlg = new JUErrorHandlerDlg();
            dlg.reportException(null, ex, true);
            System.exit(1);
        }
    }

    public JUPanelBinding getPanelBinding() {
        return panelBinding;
    }

    public void setPanelBinding(JUPanelBinding binding) {
        if (binding.getPanel() == null) {
            binding.setPanel(topPanel);
        }

        if ((panelBinding == null) || (panelBinding.getPanel() == null)) {
            try {
                panelBinding = binding;
                jbInit();
            } catch (Exception ex) {
                panelBinding.reportException(ex);
            }
        }
    }

    public void setBindingContext(BindingContext bindCtx) {
        if (panelBinding.getPanel() == null) {
            panelBinding = panelBinding.setup(bindCtx, this);
            registerProjectGlobalVariables(bindCtx);
            panelBinding.refreshControl();

            try {
                jbInit();
                panelBinding.refreshControl();
            } catch (Exception ex) {
                panelBinding.reportException(ex);
            }
        }
    }

    private void registerProjectGlobalVariables(BindingContext bindCtx) {
        JUUtil.registerNavigationBarInterface(panelBinding, bindCtx);
    }

    private void unRegisterProjectGlobalVariables(BindingContext bindCtx) {
        JUUtil.unRegisterNavigationBarInterface(panelBinding, bindCtx);
    }
}
