/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.Document;
import oracle.adf.model.BindingContext;
import oracle.adf.model.DataControlFactory;
import oracle.jbo.common.DefLocaleContext;
import oracle.jbo.uicli.binding.JUUtil;
import oracle.jbo.uicli.controls.JUErrorHandlerDlg;
import oracle.jbo.uicli.controls.JUNavigationBar;
import oracle.jbo.uicli.jui.JUEnvInfoProvider;
import oracle.jbo.uicli.jui.JUPanelBinding;
import oracle.jbo.uicli.mom.JUMetaObjectManager;


public class FormDeclarative extends JFrame {
    // form layout
    private GridLayout gridLayout = new GridLayout();
    private BorderLayout borderLayout = new BorderLayout();

    // panel definition used in design time
    private JUPanelBinding panelBinding = new JUPanelBinding(
            "FormDeclarativeUIModel");

    // Navigation bar
    // The status bar
    // The form's top panel
    private JPanel topPanel = new JPanel();
    private JPanel dataPanel = new JPanel();
    private JUNavigationBar hiddenNavBar = new JUNavigationBar();
    private JTextField jTextField1 = new JTextField();
    private JButton jButton1 = new JButton();

    /**
     *
     *  The default constructor for form
     */
    public FormDeclarative() {
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    int action = _popupTransactionDialog();

                    if (action != JOptionPane.CLOSED_OPTION) {
                        panelBinding.releaseDataControl();
                        System.exit(0);
                    }
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
        jTextField1.setText("jTextField1");
        jTextField1.setBounds(new Rectangle(110, 20, 180, 25));
        jButton1.setText("jButton1");
        jButton1.setBounds(new Rectangle(30, 20, 73, 23));
        this.getContentPane().setLayout(gridLayout);
        this.setTitle("Declarative Example");
        topPanel.setLayout(borderLayout);

        this.getContentPane().add(topPanel);

        this.setSize(new Dimension(326, 99));

        dataPanel.add(jButton1, null);
        dataPanel.add(jTextField1, null);
        topPanel.add(dataPanel, BorderLayout.CENTER);

        hiddenNavBar.setModel(JUNavigationBar.createPanelBinding(panelBinding,
                hiddenNavBar));
        jTextField1.setDocument((Document) panelBinding.bindUIControl("name",
                jTextField1));
        jButton1.setModel((ButtonModel) panelBinding.bindUIControl("sayHello",
                jButton1));
        jButton1.setText("sayHello");
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

            FormDeclarative frame = new FormDeclarative();
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

    private int _popupTransactionDialog() {
        if ((panelBinding == null) || (panelBinding.getPanel() == null)) {
            return JOptionPane.NO_OPTION;
        }

        if (panelBinding.isTransactionDirty()) {
            Object[] options = { "Commit", "Rollback" };

            int action = JOptionPane.showOptionDialog(FormDeclarative.this,
                    "How do you want to close the transaction?",
                    "Transaction open", JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, options, options[(0)]);

            switch (action) {
            case JOptionPane.NO_OPTION:
                hiddenNavBar.doAction(JUNavigationBar.BUTTON_ROLLBACK);

                break;

            case JOptionPane.CLOSED_OPTION:
                break;

            case JOptionPane.YES_OPTION:default:
                hiddenNavBar.doAction(JUNavigationBar.BUTTON_COMMIT);

                break;
            }

            return action;
        }

        return JOptionPane.NO_OPTION;
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
