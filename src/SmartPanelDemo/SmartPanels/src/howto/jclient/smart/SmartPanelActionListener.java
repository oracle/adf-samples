/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package howto.jclient.smart;
import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.*;

public class SmartPanelActionListener implements ActionListener {

   JPanel jp = null;
   Method method = null;
   
   public SmartPanelActionListener(JPanel jp, String methodName) {
     this.jp = jp;
     Class c = jp.getClass();
     try {
       method = c.getMethod(methodName,new Class[]{ActionEvent.class});
     }
     catch (NoSuchMethodException ex) {
       System.out.println("Method " + methodName + " not found.");       
     }
     catch (NoSuchMethodError nsmerr) {
       System.out.println("Method " + methodName + " not found.");
     }
   }
   public void actionPerformed(ActionEvent e) {
     if (method != null) {
       try {
         method.invoke(jp,new Object[]{e});
       }
       catch (InvocationTargetException t) {
         
       }
       catch (IllegalAccessException a) {
         
       }
     }
   }
}
