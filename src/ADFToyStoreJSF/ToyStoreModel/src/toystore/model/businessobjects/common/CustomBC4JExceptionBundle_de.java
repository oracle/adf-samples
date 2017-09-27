/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/ToyStoreModel/src/toystore/model/businessobjects/common/CustomBC4JExceptionBundle_de.java,v 1.1.1.1 2006/01/26 21:47:21 steve Exp $
package toystore.model.businessobjects.common;
import java.util.ListResourceBundle;
import oracle.jbo.CSMessageBundle;
/**
 * German translations of base BC4J framework error message overrides
 * Deutsche Übersetzung zum Überschreiben der BC4J Framework Fehlermeldungen
 */
public class CustomBC4JExceptionBundle_de extends CustomBC4JExceptionBundle {
  private static final Object[][] sMessageStrings = new String[][] {
      { CSMessageBundle.EXC_VAL_ATTR_MANDATORY, "Dieses Feld ist erforderlich" },
    };

  protected Object[][] getContents() {
    return sMessageStrings;
  }
}
