/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;
import java.util.Hashtable;
import javax.naming.NamingException;
import oracle.jbo.mom.xml.XMLContextImpl;
/**
 * The TestModuleLocal configuration for the TestModule application module
 * has the configuration property:
 *
 * MetaObjectContext
 *
 * set to the fully-qualified name of this class, test.MyMetaobjectContext
 */
public class MyMetaobjectContext extends XMLContextImpl {
  public MyMetaobjectContext(Hashtable env) {
    super(env);
  }

  /**
   * Overridden framework method. This method is responsible for returning
   * a stream of XML that represents the ADF Business Components framework
   * metadata for the component whose fully-qualified name is passed in
   * to be looked-up.
   *
   * @param name Fully-qualified name of the ADF BC component to be looked up
   * @return InputStream of ADF BC XML Metadata
   * @throws javax.naming.NamingException
   */
  public Object lookup(String name) throws NamingException {
    /*
     * This is just a simple example of how a custom implementation of this
     * method could return ADF BC XML Metadata from an alternative source
     * than reading it from the *.xml file on disk.
     * 
     * In this case, we only do the custom behavior if the component is
     * named "test.Dept", otherwise we do the default.
     */
    if ("test.Dept".equals(name)) {
      Demo d = new Demo();
      return d.getXMLStream();
    }
    else {
      return super.lookup(name);
    }
  }
}
