/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/ToyStoreModel/src/toystore/model/businessobjects/common/CustomBC4JExceptionBundle_it.java,v 1.1.1.1 2006/01/26 21:47:21 steve Exp $
package toystore.model.businessobjects.common;
import java.util.ListResourceBundle;
import oracle.jbo.CSMessageBundle;
/**
 * Italian translations of base BC4J framework error message overrides
 * Traduzioni italiane dei messaggi di errore overridden del framework di base
 */
public class CustomBC4JExceptionBundle_it extends CustomBC4JExceptionBundle {
  private static final Object[][] sMessageStrings = new String[][] {
      { CSMessageBundle.EXC_VAL_ATTR_MANDATORY, "Campo obbligatorio" },
    };

  protected Object[][] getContents() {
    return sMessageStrings;
  }
}
