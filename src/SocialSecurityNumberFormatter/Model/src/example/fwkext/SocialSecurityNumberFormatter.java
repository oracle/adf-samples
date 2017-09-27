/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package example.fwkext;

import java.sql.SQLException;

import java.text.ParseException;

import oracle.jbo.domain.DataCreationException;
import oracle.jbo.domain.Number;
import oracle.jbo.format.DefaultNumberFormatter;
import oracle.jbo.format.FormatErrorException;

public class SocialSecurityNumberFormatter extends DefaultNumberFormatter {
        private static final String NINE_ZEROS = "000000000";
        private static final String DASH = "-";

        public String format(String formatString, 
                             Object rawData) throws FormatErrorException {
            if (rawData instanceof Number) {
                String rawDataAsString = rawData.toString().trim();
                int lenRawDataAsString = rawDataAsString.length();
                if (lenRawDataAsString < 9) {
                    rawDataAsString = 
                            NINE_ZEROS.substring(0, 9 - lenRawDataAsString) + 
                            rawDataAsString;
                }
                return rawDataAsString.substring(0, 3) + DASH + 
                    rawDataAsString.substring(3, 5) + DASH + 
                    rawDataAsString.substring(5);
            }
            return super.format(formatString, rawData);
        }

        public Object parse(String formatString, String s) throws ParseException {
            if (s == null || s.length() == 0) {
                return null;
            } else if (s.length() == 11) {
                if (s.charAt(3) == '-' && s.charAt(6) == '-') {
                    try {
                        return new Number(s.substring(0, 3) + s.substring(4, 6) + 
                                          s.substring(7));
                    }catch (NumberFormatException e) {
                        // invalid
                    }
                    catch (SQLException e) {
                        // invalid
                    }
                }
            }
            throw new DataCreationException(SocialSecurityNumberMsgBundle.class, 
                                            SocialSecurityNumberMsgBundle.INVALID_SSN, null, 
                                            null);
        }
    }
