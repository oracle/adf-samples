/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import oracle.jbo.ApplicationModule;
import oracle.jbo.ViewObject;
import oracle.jbo.XMLInterface;
import oracle.jbo.client.Configuration;
import oracle.xml.parser.v2.XMLNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class ADFXMLUtil  {
  private static final String AMFLAG     = "-am";
  private static final String CONFIGFLAG = "-config";
  private static final String VOFLAG     = "-vo";
  private static final String FILEFLAG   = "-file";
  private static final String READFLAG   = "-readxml";
  private static final String WRITEFLAG  = "-writexml";
  private static final String USAGE = "usage: ADFXMLUtil "+
                                      READFLAG+"|"+WRITEFLAG+" "+
                                      AMFLAG  +" the.am.Name "+
                                      CONFIGFLAG + " TheConfigName "+
                                      VOFLAG  +" voName "+
                                      FILEFLAG+" filename.xml";

  private Operation operation;
  private String amDefName = null;
  private String voInstanceName = null;
  private String fileName = null;
  private String configName = null;
  public static void main(String[] args) throws Throwable {
    ADFXMLUtil util = new ADFXMLUtil();
    String errors = util.processParameters(args);
    if (errors != null) {
      System.out.println(errors);
      System.exit(1);
    }
    util.run();
  }

  private String processParameters(String[] args) {
    if (args == null || args.length == 0) {
      return USAGE;
    }
    int numArgs = args.length;
    int curArg = 0;
    while (curArg < numArgs) {
      if (READFLAG.equals(args[curArg])) {
        operation = Operation.READXML;
        curArg++;
      } else
      if (WRITEFLAG.equals(args[curArg])) {
        operation = Operation.WRITEXML;
        curArg++;
      } else
      if (AMFLAG.equals(args[curArg])) {
        if (curArg + 1 < numArgs) {
          amDefName = args[++curArg];
        }
      } else
      if (VOFLAG.equals(args[curArg])) {
        if (curArg + 1 < numArgs) {
          voInstanceName = args[++curArg];
        }
      } else
      if (FILEFLAG.equals(args[curArg])) {
        if (curArg + 1 < numArgs) {
          fileName = args[++curArg];
        }
      } else
      if (CONFIGFLAG.equals(args[curArg])) {
        if (curArg + 1 < numArgs) {
          configName = args[++curArg];
        }
      }      
      else {
        curArg++;
      }
    }
    if (operation == null      ||
        amDefName == null      ||
        configName == null     ||
        voInstanceName == null ||
        fileName == null) {
      return USAGE;
    }
    else {
      return null; /* OK */
    }
  }
  
  private void run() throws Throwable {  
    ApplicationModule am =
    Configuration.createRootApplicationModule(amDefName,configName);
    ViewObject vo = am.findViewObject(voInstanceName);
    if (operation == Operation.READXML) {
      DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document xmldoc = db.parse(new FileInputStream(fileName));
      vo.readXML(xmldoc.getDocumentElement(),-1);
      am.getTransaction().commit();
    }
    else if (operation == Operation.WRITEXML) {
      vo.executeQuery();
      Node n = vo.writeXML(-1,0);
      PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
      ((XMLNode)n).print(pw);
      pw.close();
    }
    Configuration.releaseRootApplicationModule(am,true);    
  } 
}
