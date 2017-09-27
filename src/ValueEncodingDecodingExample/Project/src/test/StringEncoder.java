/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package test;

public class StringEncoder {
  /**
   * Encodes the value of the string passed in based on a private algorithm
   * (which in this example simply reverses the string and wraps it with
   * square brackets)
   * 
   * For example, the string "abcdef" gets encoded to "[fedcba]".
   * 
   * @param s String to encode
   * @return the encoded string
   */
  public static String encode(String s) {
    return wrapInSquareBrackets(reverse(s));
  }
  /**
   * Decodes the value of the string passed in based on a private algorithm
   * (which in this example simply unwraps the enclosing square brackets and
   * reverses the string)
   * 
   * For example, the string "[fedcba]" gets decoded to "abcdef".
   * 
   * @param s String to decode
   * @return the decoded string
   */
  public static String decode (String s) {
    if (isLegimateEncodedValue(s)) {
      return reverse(unwrapSquareBrackets(s));
    }
    throw new RuntimeException(s+" is not a correctly encoded value.");
  }
  /*
   * Reverses the contents of a string.
   */
   private static String reverse(String s) {
     if (s != null) {
       int length = s.length();
       char[] newStr = new char[length];
       for (int z = 0; z < length; z++) {
         newStr[z] = s.charAt(length - z - 1);
       }
       s = new String(newStr);
     }
     return s;
   }
   
  /*
   * Wraps a string in square brackets
   */
  private static String wrapInSquareBrackets(String s) {
    if (s != null) {
      s = "["+s+"]";
    }
    return s;
  }
  /*
   * Unwrap square brackets from a string.
   */
  private static String unwrapSquareBrackets(String s) {
    if (s != null) {
      if (s.startsWith("[") && s.endsWith("]")) {
        int length = s.length();
        s = s.substring(1,length-1);
      }
    }
    return s;
  }
  /*
   * Tests whether an encoded value is a legitmate encoded value
   * 
   * In this example, we return true if the value is wrapped with square brackets.
   */
   private static boolean isLegimateEncodedValue(String s) {
     return (s == null || (s.startsWith("[") && s.endsWith("]")));
   }      
}
