/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package shay.glassfish;

import oracle.ide.extension.RegisteredByExtension;

import oracle.javatools.data.HashStructure;
import oracle.javatools.data.HashStructureAdapter;
import oracle.javatools.data.PropertyStorage;

/**
 * <tt>GlassfishDataStructure</tt> data object.
 */
@RegisteredByExtension("shay.glassfish")
public final class GlassfishDataStructure extends HashStructureAdapter {
    public static final String DATA_KEY = "shay.glassfish.TomcatDataStructure";

    private GlassfishDataStructure(HashStructure hash) {
        super(hash);
    }

    /**
     * Returns an instance of <tt>GlassfishDataStructure</tt>
     *
     * @param storage a storage object.
     * @throws NullPointerException if <tt>storage</tt> is <tt>null</tt>.
     */
    public static GlassfishDataStructure getInstance(PropertyStorage storage) {
        return new GlassfishDataStructure(findOrCreate(storage, DATA_KEY));
    }
    
    private static final String GLASSFISH_HOME = "C://glassfish3/";   //NOTRANS
    private static final String DEFAULT_GLASSFISH_HOME = "C://glassfish3/";  //NOTRANS
    public String getTomcatHome()
    {
      return _hash.getString(GLASSFISH_HOME, DEFAULT_GLASSFISH_HOME);
    }

    public void setTomcatHome(String ts)
    {
       _hash.putString(GLASSFISH_HOME, ts);
    }
    
//    private static final String GLASSFISH_START = "C://glassfish3/bin/startup.bat";   //NOTRANS
    private static final String GLASSFISH_START = "C://glassfish3/bin/asadmin start-domain domain1";   //NOTRANS
//    private static final String DEFAULT_GLASSFISH_START = "C://glassfish3/bin/startup.bat";  //NOTRANS
    private static final String DEFAULT_GLASSFISH_START = "C://glassfish3/bin/asadmin start-domain domain1";  //NOTRANS
    public String getTomcatStart()
    {
      return _hash.getString(GLASSFISH_START, DEFAULT_GLASSFISH_START);
    }

    public void setTomcatStart(String ts)
    {
       _hash.putString(GLASSFISH_START, ts);
    }

    private static final String GLASSFISH_STOP = "C://glassfish3/bin/asadmin stop-domain domain1";   //NOTRANS
    private static final String DEFAULT_GLASSFISH_STOP = "C://glassfish3/bin/asadmin stop-domain domain1";  //NOTRANS
    public String getTomcatStop()
    {
      return _hash.getString(GLASSFISH_STOP, DEFAULT_GLASSFISH_STOP);
    }

    public void setTomcatStop(String ts)
    {
       _hash.putString(GLASSFISH_STOP, ts);
    }

    private static final String GLASSFISH_DB = "C://glassfish3/bin/asadmin start-domain --debug=true";   //NOTRANS
    private static final String DEFAULT_GLASSFISH_DB = "C://glassfish3/bin/asadmin start-domain --debug=true";  //NOTRANS
    public String getTomcatDb()
    {
      return _hash.getString(GLASSFISH_DB, DEFAULT_GLASSFISH_DB);
    }

    public void setTomcatDb(String ts)
    {
       _hash.putString(GLASSFISH_DB, ts);
    }
    private static final String GLASSFISH_ADMIN = "http://localhost:4848";   //NOTRANS
    private static final String DEFAULT_GLASSFISH_ADMIN = "http://localhost:4848";  //NOTRANS
    public String getGlassfishAdmin()
    {
      return _hash.getString(GLASSFISH_ADMIN, DEFAULT_GLASSFISH_ADMIN);
    }

    public void setGlassfishAdmin(String ts)
    {
       _hash.putString(GLASSFISH_ADMIN, ts);
    }
}
