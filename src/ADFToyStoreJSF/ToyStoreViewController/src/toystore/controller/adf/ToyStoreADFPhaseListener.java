/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package toystore.controller.adf;
import oracle.adf.controller.faces.lifecycle.ADFPhaseListener;
import oracle.adf.controller.v2.lifecycle.PageLifecycle;
public class ToyStoreADFPhaseListener extends ADFPhaseListener {
  protected PageLifecycle createPageLifecycle() {
    return new ToyStorePageLifecycle();
  }
}
