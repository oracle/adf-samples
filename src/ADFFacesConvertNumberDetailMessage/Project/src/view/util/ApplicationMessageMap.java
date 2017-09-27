/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package view.util;

import com.sun.faces.util.Util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.context.FacesContext;

public class ApplicationMessageMap {
    Map messages;
    String basename;

    public Map getMessages() {
        if (messages == null) {
            final ResourceBundle bundle = 
                ResourceBundle.getBundle(basename, FacesContext.getCurrentInstance().getViewRoot().getLocale(), 
                                         Util.getCurrentLoader(this));
            messages = new Map() {
                        // this is an immutable Map

                        // Do not need to implement for immutable Map

                        public void clear() {
                            throw new UnsupportedOperationException();
                        }


                        public boolean containsKey(Object key) {
                            boolean result = false;
                            if (null != key) {
                                result = 
                                        (null != bundle.getObject(key.toString()));
                            }
                            return result;
                        }


                        public boolean containsValue(Object value) {
                            Enumeration keys = bundle.getKeys();
                            Object curObj = null;
                            boolean result = false;
                            while (keys.hasMoreElements()) {
                                curObj = 
                                        bundle.getObject((String)keys.nextElement());
                                if ((curObj == value) || 
                                    ((null != curObj) && curObj.equals(value))) {
                                    result = true;
                                    break;
                                }
                            }
                            return result;
                        }


                        public Set entrySet() {
                            HashMap mappings = new HashMap();
                            Enumeration keys = bundle.getKeys();
                            while (keys.hasMoreElements()) {
                                Object key = keys.nextElement();
                                Object value = bundle.getObject((String)key);
                                mappings.put(key, value);
                            }
                            return mappings.entrySet();
                        }


                        public boolean equals(Object obj) {
                            if ((obj == null) || !(obj instanceof Map)) {
                                return false;
                            }

                            if (entrySet().equals(((Map)obj).entrySet())) {
                                return true;
                            }

                            return false;
                        }


                        public Object get(Object key) {
                            if (null == key) {
                                return null;
                            }
                            Object result = null;
                            try {
                                result = bundle.getObject(key.toString());
                            } catch (MissingResourceException e) {
                                result = "???" + key + "???";
                            }
                            return result;
                        }


                        public int hashCode() {
                            return bundle.hashCode();
                        }


                        public boolean isEmpty() {
                            boolean result = true;
                            Enumeration keys = bundle.getKeys();
                            result = !keys.hasMoreElements();
                            return result;
                        }


                        public Set keySet() {
                            Set keySet = new HashSet();
                            Enumeration keys = bundle.getKeys();
                            while (keys.hasMoreElements()) {
                                keySet.add(keys.nextElement());
                            }
                            return keySet;
                        }


                        // Do not need to implement for immutable Map

                        public Object put(Object k, Object v) {
                            throw new UnsupportedOperationException();
                        }


                        // Do not need to implement for immutable Map

                        public void putAll(Map t) {
                            throw new UnsupportedOperationException();
                        }


                        // Do not need to implement for immutable Map

                        public Object remove(Object k) {
                            throw new UnsupportedOperationException();
                        }


                        public int size() {
                            int result = 0;
                            Enumeration keys = bundle.getKeys();
                            while (keys.hasMoreElements()) {
                                keys.nextElement();
                                result++;
                            }
                            return result;
                        }


                        public java.util.Collection values() {
                            ArrayList result = new ArrayList();
                            Enumeration keys = bundle.getKeys();
                            while (keys.hasMoreElements()) {
                                result.add(bundle.getObject((String)keys.nextElement()));
                            }
                            return result;
                        }
                    };
        }
        return messages;
    }

    public void setBasename(String basename) {
        this.basename = basename;
    }

    public String getBasename() {
        return basename;
    }
}
