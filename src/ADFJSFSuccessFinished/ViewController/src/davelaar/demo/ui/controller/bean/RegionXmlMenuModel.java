package davelaar.demo.ui.controller.bean;


import davelaar.demo.ui.util.JsfUtils;

import java.io.InputStream;
import java.io.Serializable;

import java.net.URL;

import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import oracle.adf.share.logging.ADFLogger;

import org.apache.myfaces.trinidad.model.XMLMenuModel;


/**
 * This menu model extends the standard trinidad XMLMenuModel to add support for a one-page uishell application with dynamic
 * regions. In this case the selected menu entry depends on the current dynamic region as stored in the MainRegionManager.
 *
 * @author Steven Davelaar
 */
  public class RegionXmlMenuModel extends XMLMenuModel  implements Serializable
  {
   private static final ADFLogger sLog = ADFLogger.createADFLogger(RegionXmlMenuModel.class);

   @Override
   /**
    * Return focus row key based on current dynamic region (if set), 
    * otherwise call super to return standard behavior.
    */
   public Object getFocusRowKey()
   {
     // first check whether default behavior results in focus of some menu item 
     Object focusRowKey = super.getFocusRowKey();          
     if (focusRowKey!=null)
     {
       return focusRowKey;
     }
     Map<String, List<Object>> focusPathMap = getViewIdFocusPathMap();
     String taskFlowName = getMainRegionManager().getCurrentTaskFlowName();
     if (taskFlowName!=null)
     {
       List<Object> focusPath = focusPathMap.get(taskFlowName);
       Object path = focusPath != null? focusPath.get(0): null;        
       sLog.fine("Focus row key based on current region: "+path);
       return path;
     }
     return null;
   }
   
   public DynamicRegionManager getMainRegionManager()
   {
     return (DynamicRegionManager) 
       JsfUtils.getExpressionValue("#{pageFlowScope.mainRegionManager}");
   }


   @Override
   /**
    * Standard XMLMenuModel.getStream method returns null when the menu model XML file is included as ADF library.
    * In this case, we try to retrive the stream by calling getResourceAsStream using the class loader of this class.
    * 
    */
   public InputStream getStream(String uri)
   {
     InputStream is = null;
     try
     {
       // copied from super, we do not call super method, because then stack trace is written
       // to log file when not found because of ADF lib
       FacesContext context = FacesContext.getCurrentInstance();
       URL url = context.getExternalContext().getResource(uri);
       is = url.openStream();
     }
     catch (Exception ex)
     {
       // do nothing, this can fail when using ADF library
     }
     if (is==null)
     {
       is = this.getClass().getClassLoader().getResourceAsStream(uri);
     }
     return is;
   }
  }
