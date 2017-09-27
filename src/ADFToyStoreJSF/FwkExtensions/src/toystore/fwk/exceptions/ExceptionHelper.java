/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
// $Header: /cvs/ADFToyStoreJSF/FwkExtensions/src/toystore/fwk/exceptions/ExceptionHelper.java,v 1.1.1.1 2006/01/26 21:47:18 steve Exp $
package toystore.fwk.exceptions;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Locale;
import oracle.jbo.ApplicationModule;
import oracle.jbo.JboException;
import oracle.jbo.TooManyObjectsException;
/**
 * Utility class for working with ADF Business Components Exceptions.
 * 
 * @author Steve Muench
 */
public class ExceptionHelper {
  // Some spaces used for the indent formatting
  private static final String SPACES = "                                      ";

  /**
   * Returns true if the JboException passed in is a TooManyObjectsException
   * or if it is wrapping a TooManyObjectsException.
   *
   * @param jboex The ADF Business Components exception object to test
   * @return true if the exception is or wraps a TooManyObjectsException
   */
  public static boolean isOrCausedByDuplicateRow(JboException jboex) {
    return isOrCausedByExceptionClass(jboex, TooManyObjectsException.class);
  }
  /**
   * Dumps an ADF Business Components exception included its nested exception
   * details to a string.
   * 
   * @param jboex The ADF Business Components exception object to test
   * @param locale The user's preferred locale.
   * @return String containing formatted exception tree of messages
   */
  public static String exceptionIncludingDetails(JboException jboex,
    Locale locale) {
    StringWriter sw = new StringWriter();
    showExceptionIncludingDetails(jboex, 0, new PrintWriter(sw), locale);
    return sw.toString();
  }
  /**
   * Dumps an ADF Business Components exception included its nested
   * exception details to System.out.
   *
   * @param ex The ADF Business Components exception object to test
   * @param am The root application module being used
   */
  public static void showExceptionIncludingDetails(JboException ex,
    ApplicationModule am) {
    Locale locale = am.getSession().getLocale();
    PrintWriter pw = new PrintWriter(System.out);
    showExceptionIncludingDetails(ex, 0, pw, locale);
    pw.flush();
    pw.close();
  }
  /**
   * Recursively dump an exception message to a PrintWriter
   * @param ex exception to format
   * @param lev indent level
   * @param pw PrintWriter to write message to
   * @param locale The locale to use for selecting the message string
   */
  private static void showExceptionIncludingDetails(Exception ex, int lev,
    PrintWriter pw, Locale locale) {
    if (ex instanceof JboException) {
      JboException jboex = (JboException) ex;
      printMessageForLevel(jboex, lev, pw, locale);
      Object[] details = jboex.getDetails();
      for (int z = 0, count = details.length; z < count; z++) {
        Exception cur = (Exception) details[z];
        if (cur instanceof JboException) {
          showExceptionIncludingDetails((JboException) cur, lev + 1, pw, locale);
        } else {
          printMessageForLevel(jboex, lev + 1, pw, locale);
        }
      }
    } else {
      printMessageForLevel(ex, lev, pw, locale);
    }
  }
  /**
   * Format the ADF Business Components Exception message without the
   * product code (JBO)and without the error number.
   *
   * @param ex exception whose message you want to format
   * @param locale The locale to use for selecting the message string
   * @return string containing the formatted error message
   */
  public static String message(Exception ex, Locale locale) {
    String msg = null;
    if (ex instanceof JboException) {
      JboException jboex = (JboException) ex;
      msg = MessageFormat.format(jboex.getLocalizedBaseMessage(locale),
          jboex.getErrorParameters());
    } else {
      msg = ex.getLocalizedMessage();
    }
    return msg;
  }
  /**
   * Format the ADF Business Components Exception message without the product
   * code (JBO) and without the error number.
   *
   * @param ex exception whose message you want to format
   * @param am The root application module to use to determine the user locale
   * @return string containing the formatted error message
   */
  public static String message(Exception ex, ApplicationModule am) {
    Locale locale = am.getSession().getLocale();
    String msg = null;
    if (ex instanceof JboException) {
      JboException jboex = (JboException) ex;
      msg = MessageFormat.format(jboex.getLocalizedBaseMessage(locale),
          jboex.getErrorParameters());
    } else {
      msg = ex.getLocalizedMessage();
    }
    return msg;
  }
  /**
   * Print an exception message at a given indent level to a PrintWriter
   *
   * @param ex exception to format
   * @param lev indent level
   * @param pw PrintWriter to write message to
   * @param locale The locale to use for selecting the message string
   */
  private static void printMessageForLevel(Exception ex, int lev,
    PrintWriter pw, Locale locale) {
    String msg = message(ex, locale);
    pw.println((lev == 0) ? msg : (SPACES.substring(0, lev * 2) + "\_" + msg));
  }
  /**
   * General helper method that returns true if a given ADF Business Components
   * exception is of the indicated exception class type, or if it contains that
   * exception class type as one of its nested, detail exceptions.
   */
  private static boolean isOrCausedByExceptionClass(JboException j,
    Class excepClass) {
    boolean ret = false;
    if (j.getClass().equals(excepClass)) {
      return true;
    }
    Object[] details = j.getDetails();
    for (int z = 0, count = details.length; !ret && (z < count); z++) {
      Exception cur = (Exception) details[z];
      if (cur.getClass().equals(excepClass)) {
        return true;
      }
      if (cur instanceof JboException) {
        ret = isOrCausedByExceptionClass((JboException) cur, excepClass);
      }
    }
    return ret;
  }
}
