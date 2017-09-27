/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.adfinsider.localdb.mobile.dc;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import oracle.adfinsider.localdb.database.ConnectionFactory;
import oracle.adfinsider.localdb.mobile.bo.CountryBO;

import oracle.adfmf.framework.api.AdfmfContainerUtilities;
import oracle.adfmf.framework.api.AdfmfJavaUtilities;
import oracle.adfmf.framework.api.GenericTypeBeanSerializationHelper;
import oracle.adfmf.framework.exception.AdfInvocationException;
import oracle.adfmf.util.GenericType;
import oracle.adfmf.util.Utility;


public class CountryDC {

    private static final String NOT_REACHABLE = "NotReachable"; // Indicates no network connectivity.

    public CountryDC() {
        super();

    }

    public CountryBO[] getCountries() {
        CountryBO[] countries = null;

        String networkStatus =
            (String)AdfmfJavaUtilities.evaluateELExpression("#{deviceScope.hardware.networkStatus}");

        if (networkStatus.equals(NOT_REACHABLE)) {
            countries = getCountriesFromDB();
        } else {
            countries = getCountriesFromWS();
        }

        return countries;
    }

    private CountryBO[] getCountriesFromDB() {
        Connection conn = null;
        List returnValue = new ArrayList();

        try {
            conn = ConnectionFactory.getConnection();

            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery("SELECT COUNTRY_ID, COUNTRY_NAME, REGION_ID FROM COUNTRIES;");
            while (result.next()) {
                CountryBO country = new CountryBO();
                Utility.ApplicationLogger.severe("Country: " + result.getString("COUNTRY_ID"));
                country.setCountryId(result.getString("COUNTRY_ID"));
                country.setCountryName(result.getString("COUNTRY_NAME"));
                country.setRegionId(new Integer(result.getInt("REGION_ID")));
                returnValue.add(country);
            }

        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        Collections.sort(returnValue);
        return (CountryBO[])returnValue.toArray(new CountryBO[returnValue.size()]);
    }

    private CountryBO[] getCountriesFromWS() {
        try {
            GenericType genericReturnValue =
                (GenericType)AdfmfJavaUtilities.invokeDataControlMethod("HR_WS", null, "findCountry", new ArrayList(),
                                                                        new ArrayList(), new ArrayList());
            CountryBO[] returnValue =
                (CountryBO[])GenericTypeBeanSerializationHelper.fromGenericType(CountryBO[].class, genericReturnValue,
                                                                                "result");

            Arrays.sort(returnValue);
            return returnValue;

        } catch (AdfInvocationException aie) {
            if (AdfInvocationException.CATEGORY_WEBSERVICE.compareTo(aie.getErrorCategory()) == 0) {
                AdfmfContainerUtilities.invokeContainerJavaScriptFunction("oracle.adfinsider.localdb.countries",
                                                                          "navigator.notification.alert",
                                                                          new Object[] { "The web service is unavailable. 

 Data has been retrieved from the local cache.",
                                                                                         "null", "Warning", "Ok" });

                return getCountriesFromDB();
            } else {
                throw new RuntimeException(aie);
            }
        } catch (Exception ex) {
            Utility.ApplicationLogger.severe(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
