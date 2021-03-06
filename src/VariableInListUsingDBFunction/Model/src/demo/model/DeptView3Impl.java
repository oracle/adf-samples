/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package demo.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.List;

import oracle.jbo.server.QueryCollection;
import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jul 26 20:42:36 CEST 2010
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------

public class DeptView3Impl extends ViewObjectImpl
{
   /**
    * This is the default constructor (do not remove).
    */
   public DeptView3Impl()
   {
   }
   private String listToCSV(List<String> list) 
   {
      String ret = null;
      if (list != null) 
      {
         int size = list.size();
         if (size > 0) 
         {
           ret = list.get(0);
           for (int z = 1; z < size; z++) 
           {
             ret += ","+list.get(z);
           }
         }
      }
      return ret;
   }
  @Override
  protected void bindParametersForCollection(QueryCollection qc, Object[] params,
                                             PreparedStatement stmt) throws SQLException
  {
     List<String> exampleListOfStrings = ExampleService.findListOfDnamesToRetrieve();
     // Named bind variables are represented as Object[][]
     // with Object[*][0] being the String bind variable name and
     //      Object[*][1] being the Object bind variable value
     Object[] param0 = (Object[])params[0];
     param0[1] = listToCSV(exampleListOfStrings);
     super.bindParametersForCollection(qc, params, stmt);
  }

   /**
    * Returns the bind variable value for CommaSeparatedListOfDname.
    * @return bind variable value for CommaSeparatedListOfDname
    */
   public String getCommaSeparatedListOfDname()
   {
      return (String)getNamedWhereClauseParam("CommaSeparatedListOfDname");
   }

   /**
    * Sets <code>value</code> for bind variable CommaSeparatedListOfDname.
    * @param value value to bind as CommaSeparatedListOfDname
    */
   public void setCommaSeparatedListOfDname(String value)
   {
      setNamedWhereClauseParam("CommaSeparatedListOfDname", value);
   }
}
