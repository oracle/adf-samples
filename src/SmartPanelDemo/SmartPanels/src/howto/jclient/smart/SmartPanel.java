/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package howto.jclient.smart;
import howto.jclient.controls.NonDisableableJULabel;

import java.awt.*;

import java.io.*;

import java.net.*;

import java.util.*;

import javax.swing.*;

import oracle.jbo.*;
import oracle.jbo.common.*;
import oracle.jbo.uicli.controls.*;
import oracle.jbo.uicli.jui.*;
import oracle.jbo.domain.Number;
import oracle.xml.parser.v2.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
public class SmartPanel extends JPanel implements JClientPanel {

  private GridBagLayout layout = null;
  private ArrayList controls = new ArrayList(10);
  private URL formDefURL = null;
  private JUPanelBinding binding = null;
  protected LocaleContext locale = null;

  private static final String PANEL_XML_EXTENSION = ".pnl";

  public void setupFormDefinition() {
    String panelXMLFileName = getClass().getName().replace('.','/').concat(PANEL_XML_EXTENSION);
    this.formDefURL = Thread.currentThread().getContextClassLoader().getResource(panelXMLFileName);
  }

  protected void initializeDesignTimePanelBinding(JUPanelBinding pb) {
    binding = pb;
  }
  public SmartPanel() {
    super();
    setupFormDefinition();
    this.locale = new DefLocaleContext(Locale.getDefault());
  }

  /**
   * Constructor that takes a shared panelbinding
   */
  public SmartPanel(JUPanelBinding binding) {
    setPanelBinding(binding);
  }

  public JUPanelBinding getPanelBinding() {
    return binding;
  }

  public void setPanelBinding(JUPanelBinding panelBinding) {
    if (panelBinding.getPanel() == null) {
      panelBinding.setPanel(this);
    }

    if (binding == null || binding.getPanel() == null) {
      try {
        binding = panelBinding;
        jbInit();
      } catch(Exception ex) {
        binding.reportException(ex);
      }
    }
  }

  public void jbInit() {
    try {
      initializeFromXML();    
    }
    catch (SAXException sex) {
      sex.printStackTrace();
    }
    catch (IOException iox) {
      iox.printStackTrace();
    }

  }

  public void initializeFromXML() throws SAXException,IOException {
     SAXParser sp = new SAXParser();
     sp.setPreserveWhitespace(false);
     sp.setContentHandler(new SmartPanelBuilder(this, locale));
     sp.parse(formDefURL);
  }

class SmartPanelBuilder extends DefaultHandler  {
  private ViewObject vo = null;
  private LocaleContext locale = null;
  private String voName = null;
  private String itName = null;
  private Vector rowComponents  = new Vector(4);
  private Vector rowConstraints = new Vector(4);
  private Vector rowNonLabel    = new Vector(4);
  private int nonLabels = 0;
  private int tableCol  = -1;
  private int tableRow  = -1;
  private int colspan   = 1;
  private int cellpadding   = 0;
  private int tablecellpadding   = 0;
  private boolean header = false;
  private SmartPanel sp = null;
  private GridBagLayout layout = null;
  private JUPanelBinding binding = null;
  
  public SmartPanelBuilder( SmartPanel sp, LocaleContext locale) {
    this.sp = sp;
    this.locale = locale;
  }
  
  public void startDocument()	{ }

  public void characters(char[] arr, int start, int len) {  }

  protected void buildPanel(Attributes attributes) {
    layout = new GridBagLayout();
    sp.setLayout(layout);
    binding = sp.getPanelBinding();
    if (binding == null) {
      binding = new JUPanelBinding(attributes.getValue(DATAMOD),null);
      sp.setPanelBinding(binding);
    }
  }

  protected void addRow() {
    tableRow++;
    nonLabels = 0;
    tableCol = -1;
    rowComponents.clear();
    rowConstraints.clear();
    rowNonLabel.clear();
  }

  protected void addColumn(boolean headerCol, Attributes attributes) {
    header = headerCol;
    tableCol++;
    colspan = getIntValue(attributes,COLSPAN,1);
    cellpadding = getIntValue(attributes,CELLPAD,0);
  }

  private int getIntValue(Attributes attributes,String name, int defaultVal) {
    String val = attributes.getValue(name);
    return (val != null && !val.equals("")) 
        ? Integer.parseInt(val) : defaultVal;

  }
  protected void buildTable(Attributes attributes) {
    voName = attributes.getValue(VO);
    itName = voName+"Iter";
    tablecellpadding = getIntValue(attributes,CELLPAD,0);
    vo = binding.getApplicationModule().findViewObject(voName);
  }
  public void startElement(String uri, String localName, String qName, Attributes attributes)    {
    if      (localName.equals(PANEL)) buildPanel(attributes);
    else if (localName.equals(TABLE)) buildTable(attributes);
    else if (localName.equals(TR))    addRow();
    else if (localName.equals(TD))    addColumn(false,attributes);
    else if (localName.equals(TH))    addColumn(true,attributes);

    /*
     * Must be a control then
     */

    else {

      /*
       * Control can bound to an attribute
       */
      String attrName  = attributes.getValue(ATTR);

      /*
       * Control can indicate alignment
       */
      String alignment = attributes.getValue(ALIGN);
      
      // If no alignment specified explicitly...
      if (alignment == null) {
        // Header cells default alignment to right if not specified.
        if (header) {
          alignment = RIGHT;          
        }
        // default number attributes to right-align numbers if not specified
        else if (vo.findAttributeDef(attrName).getJavaType().equals(Number.class)) {
          alignment = RIGHT;
        }
      }

      boolean isNonLabel = false;
      JComponent comp = null;
      /*
       * <label attr="SomeAttr"/> 
       */
      if (localName.equals(LABEL)) {
        JLabel lab = new JLabel();
        comp = lab;
        String prompt = binding.getLabel(voName,attrName,locale)+COLON;
        lab.setText(prompt);
        if (alignment != null) {
          if (alignment.equals(RIGHT)) lab.setHorizontalAlignment(SwingConstants.RIGHT);
          else if (alignment.equals(CENTER)) lab.setHorizontalAlignment(SwingConstants.CENTER);
          else if (alignment.equals(LEFT)) lab.setHorizontalAlignment(SwingConstants.LEFT);
        }
      }
      /*
       * <display attr="SomeAttr"/> 
       */
      else if (localName.equals(DISPLAY)) {
        isNonLabel = true;
        nonLabels++;
        NonDisableableJULabel lab = new NonDisableableJULabel();
        String size = attributes.getValue(SIZE);
        if (size != null) {
          lab.setColumns(Integer.parseInt(size));
        }
        comp = lab;
        lab.setOpaque(true);
        lab.setBackground(SystemColor.info);
        if (alignment != null) {
          if (alignment.equals(RIGHT)) lab.setHorizontalAlignment(SwingConstants.RIGHT);
          else if (alignment.equals(CENTER)) lab.setHorizontalAlignment(SwingConstants.CENTER);
          else if (alignment.equals(LEFT)) lab.setHorizontalAlignment(SwingConstants.LEFT);
        }
        lab.setToolTipText(binding.getTooltip(voName,attrName,locale));
        lab.setForeground(SystemColor.textHighlight);
        lab.setModel(JULabelBinding.createAttributeBinding(
                        binding,
                        lab,
                        voName,
                        null,
                        itName,
                        attrName));
      }
      /*
       * <input attr="SomeAttr"/> 
       */
      else if (localName.equals(INPUT)) {
        isNonLabel = true;
        nonLabels++;
        JTextField text = new JTextField();
        comp = text;
        String size = attributes.getValue(SIZE);
        if (size != null) {
          text.setColumns(Integer.parseInt(size));
        }
        if (alignment != null) {
          if (alignment.equals(RIGHT)) text.setHorizontalAlignment(SwingConstants.RIGHT);
          else if (alignment.equals(CENTER)) text.setHorizontalAlignment(SwingConstants.CENTER);
          else if (alignment.equals(LEFT)) text.setHorizontalAlignment(SwingConstants.LEFT);
        }
        text.setToolTipText( binding.getTooltip(voName,attrName,locale));
        text.setDocument(
          JUTextFieldBinding.createAttributeBinding(
            binding,
            text,
            voName,
            null,
            itName,
            attrName));
      }
      else if (localName.equals(POPLIST)) {
        isNonLabel = true;
        nonLabels++;
        JComboBox combo = new JComboBox();
        String actionMethodName = attributes.getValue(ACTMETH);
        if (actionMethodName != null) {
          combo.addActionListener(new SmartPanelActionListener(sp,actionMethodName));
        }
        comp = combo;
        String size = attributes.getValue(SIZE);
        String lovVOName = attributes.getValue(LISTVO);
        String[] listValueAttrs   = tokenizeIntoArray(attributes.getValue(LISTVA));
        String[] listDisplayAttrs = tokenizeIntoArray(attributes.getValue(LISTDA));
        String[] listTargetAttrs  = tokenizeIntoArray(attributes.getValue(LISTTA));
//        combo.setColumns( size != null ? Integer.parseInt(size) : 10);
        combo.setToolTipText( binding.getTooltip(voName,attrName,locale));
        combo.setModel(
          JUComboBoxBinding.createLovBinding(
            binding,
            combo,
            voName,
            null, 
            itName, 
            listTargetAttrs, 
            lovVOName, 
            listValueAttrs, 
            listDisplayAttrs, 
            null, 
            null));
        combo.setEditable(false);
            
      }
      
      if (comp != null) {
        int padding = (cellpadding > tablecellpadding) ? cellpadding : tablecellpadding;
        if (isNonLabel) comp.setPreferredSize(new Dimension(1,27));
        sp.add(comp,
             new GridBagConstraints(tableCol,
                                    tableRow, 
                                    colspan, 
                                    1, 
                                    isNonLabel ? 0.1 : 0.0,
                                    0.0, 
                                    GridBagConstraints.CENTER,
                                    GridBagConstraints.BOTH,
                                    new Insets(padding,
                                               padding,
                                               padding,
                                               padding),
                                    0,
                                    0)
          );
      }
    }
  }

  public void endElement(String uri, String localName, String qName)	{
//    if (localName.equals(TR)) finishRow();
  }

//  protected void finishRow() {
//    if (nonLabels != 0) {
//      double newWeight = (double)1.0 / (double)nonLabels;
//      double newWeight = 1.0;
//
//      for (int z = 0, numComps = rowComponents.size(); z < numComps; z++) {
//          JComponent curComp = (JComponent)rowComponents.elementAt(z);
//          GridBagConstraints curConstraint = 
//            (GridBagConstraints)rowConstraints.elementAt(z);
//          if (((Boolean)rowNonLabel.elementAt(z)) == Boolean.TRUE) {
//            curConstraint.weightx = newWeight;
//          }
//          sp.add(curComp, curConstraint);
//      }
//    }
//  }
  
  public void endDocument()	{ }
}  

  public static String [] tokenizeIntoArray(String s) {
    if (s == null || s.equals("")) {
      return null;
    }
    // Turn commas into whitespace.
    s = s.replace(',',' ');
    StringTokenizer st = new StringTokenizer(s);
    Vector v = new Vector(10);
    while (st.hasMoreTokens()) {
      v.addElement(st.nextToken());
    }
    int toks = v.size();
    String[] tok = new String[toks];
    for (int z=0;z<toks;z++) {
      tok[z] = (String)v.elementAt(z);
    }
    return tok;
  }

  private static final String ALIGN   = "align";
  private static final String ATTR    = "attr";
  private static final String CELLPAD = "cellpadding";
  private static final String CENTER  = "center";
  private static final String COLON   = ":";
  private static final String COLSPAN = "colspan";
  private static final String DATAMOD = "datamodel";
  private static final String DISPLAY = "display";
  private static final String INPUT   = "input";
  private static final String LABEL   = "label";
  private static final String LEFT    = "left";
  private static final String LISTDA  = "list-display-attrs";
  private static final String LISTTA  = "list-target-attrs";
  private static final String LISTVA  = "list-value-attrs";
  private static final String LISTVO  = "list-viewobject";
  private static final String PANEL   = "panel";
  private static final String POPLIST = "poplist";
  private static final String RIGHT   = "right";
  private static final String SIZE    = "size";
  private static final String TABLE   = "table";
  private static final String TD      = "td";
  private static final String TH      = "th";
  private static final String TR      = "tr";
  private static final String VO      = "viewobject";
  private static final String ACTMETH = "on-action-performed";
  
  
  
}
