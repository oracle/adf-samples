/* Copyright 2010, 2017, Oracle and/or its affiliates. All rights reserved. */
package oracle.adf.model.bc4j;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.io.Serializable;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import oracle.adf.model.DataControl;
import oracle.adf.model.DataControlFactory;
import oracle.adf.model.BindingContext;
import oracle.jbo.ApplicationModule;
import oracle.jbo.Session;
import oracle.jbo.JboContext;
import oracle.jbo.LocaleContext;
import oracle.jbo.common.NamedObjectImpl;
import oracle.jbo.common.PropertyMetadata;
import oracle.jbo.common.PropertyConstants;
import oracle.jbo.common.ws.WSApplicationModuleImpl;
import oracle.jbo.uicli.mom.JUTags;
import oracle.jbo.uicli.binding.JUApplication;

import oracle.jbo.common.ampool.SessionCookie;
import oracle.jbo.common.ampool.EnvInfoProvider;
import oracle.jbo.common.ampool.ApplicationPool;
import oracle.jbo.common.ampool.PoolMgr;
import oracle.jbo.common.ampool.SessionCookieFactory;
import oracle.jbo.common.ampool.AMPoolMessageBundle;
import oracle.jbo.common.ampool.ApplicationPoolException;

import oracle.jbo.http.HttpSessionCookieFactory;


public class DataControlFactoryImpl implements DataControlFactory 
{
   final String CPX = "cpx";
   final String BATCH = "Batch";
   final String SYNC  = "Sync";
   
   final String EXC_MODE_STRING = "ExceptionMode";
   final String EXC_MODE_DEFERRED  = "Deferred";
   final String EXC_MODE_IMMEDIATE = "Immediate";
   
   final String PARAMETER_NAME = "name";
   final String PARAMETER_VALUE = "value";
   
   private static int mSessionIdCounter = 0;
   private static Object mStaticSyncLock = new Object();
   
   public DataControlFactoryImpl()
   {
   }
   
   public DataControl createSession(BindingContext ctx, Node node, Map appParams)
   {
      
      String sName = null;
      
      if(node.getAttributes().getNamedItem(JUTags.NAME) != null)
        sName = node.getAttributes().getNamedItem(JUTags.NAME).getNodeValue();
      else
        sName = node.getAttributes().getNamedItem(JUTags.ID).getNodeValue();
        
      String configuration = node.getAttributes().getNamedItem(JUTags.Configuration).getNodeValue();
      String packageName = node.getAttributes().getNamedItem(JUTags.Package).getNodeValue();
      
      
      //this code to load the parameters list should be common to bc4j/bean DCs. However
      //for now keeping it separate just incase we diverge.
      NodeList list = null;
      Node contentNode = node.getFirstChild();
      if (contentNode != null)
      {
         list = contentNode.getChildNodes();
      }
      HashMap  parameters = new HashMap(10);
      
      parameters.put(JUTags.NAME, sName);
      parameters.put(JUTags.Configuration, configuration);
      parameters.put(JUTags.Package, packageName);
      
      Node childNode, paramNode;
      NamedObjectImpl ctr;
      if (list != null)
      {
         for(int i = 0; i < list.getLength(); i++)
         {
            childNode = list.item(i);
            if(childNode.getNodeName().equalsIgnoreCase(JUTags.Parameter))
            {
               String sParamName = childNode.getAttributes().getNamedItem(PARAMETER_NAME).getNodeValue();
               String sValue = childNode.getAttributes().getNamedItem(PARAMETER_VALUE).getNodeValue(); 
               parameters.put(sParamName, sValue);
            }
         }
      }
      return createSession(ctx, sName, appParams, parameters);
   }

   private SessionCookie findOrCreateSessionCookie(
      String dcName, BindingContext ctx, String poolName
      , String configPackage, String configSection, Properties poolProps
      , Properties cookieProps, boolean isWebApp, boolean isBatch)
   {
      Serializable psState = ctx.findPersistentState(dcName);

      if (psState != null && psState instanceof SessionCookie)
      {
         return (SessionCookie)psState;
      }

      SessionCookie cookie = null;

      String configName = null;
      if (configPackage != null && configSection != null)
      {
         configName = new StringBuffer(configPackage)
            .append(".")
            .append(configSection)
            .toString();
      }

      // Tell our jndi implementation that
      // we are running insde an appserver
      if(poolProps == null)
      {
         poolProps = new Properties();
      }

      if (isWebApp)
      {
         poolProps.put(JboContext.USE_DEFAULT_CONTEXT, "true");
      }

      ApplicationPool pool = PoolMgr.getInstance().findPool(
         poolName, configPackage, configSection, poolProps);

      if (isWebApp)
      {
         // Make sure that the HttpSessionCookieFactory is set on the pool if a
         // custom session cookie factory has not been specified
         SessionCookieFactory factory = pool.getSessionCookieFactory();
         if (PropertyMetadata.ENV_AMPOOL_COOKIE_FACTORY_CLASS_NAME.pDefault
            .equals(factory.getClass().getName()))
         {
            pool.setSessionCookieFactory(new HttpSessionCookieFactory());
         }
      }

      try
      {
         if (isWebApp)
         {
            cookie = pool.createSessionCookie(dcName, null, cookieProps);
         }
         else
         {
            long sessionId = System.currentTimeMillis();
            int sessionCounter = -1;
            synchronized (mStaticSyncLock) {
              sessionCounter = mSessionIdCounter;
              mSessionIdCounter++;
            }
            String sessionIdStr = new Long(sessionId).toString() + new Integer(sessionCounter).toString();
            cookie = pool.createSessionCookie(dcName, sessionIdStr, cookieProps);
         }
      }
      catch(ApplicationPoolException apex)
      {
         // Bug 2026193
         if (AMPoolMessageBundle.EXC_AMPOOL_COOKIE_ALREADY_EXISTS.
            equals(apex.getErrorCode()))
         {
            cookie = apex.getSessionCookie();
         }
         else
         {
            throw apex;
         }
      }
         
      return cookie;
   }
   
   public DataControl createSession(
      BindingContext ctx, String sName, Map appParams, Map parameters)
   {
      String sPackage = (String)parameters.get(JUTags.Package);
      String sConfiguration = (String)parameters.get(JUTags.Configuration);
      String sAmName = (String)parameters.get(JUTags.NAME);
      String appName = sPackage + "." + sAmName;
      
      String cpxDefName = (String)parameters.get(CPX);

      EnvInfoProvider envInfo = (EnvInfoProvider)ctx.get(APP_PARAM_ENV_INFO);
      
      JUApplication app = null;

      Properties poolProps   =
         (Properties)appParams.get(APP_PARAM_POOL_PROPERTIES);
      Properties cookieProps =
         (Properties)appParams.get(APP_PARAM_COOKIE_PROPERTIES);


      if (cookieProps == null)
      {
         cookieProps = new Properties();
      }

      boolean isWebApp = false;

      if (appParams != null)
      {
         Object sessionObj    = appParams.get(APP_PARAM_HTTP_SESSION);
         if (sessionObj != null)
         {
            isWebApp = true;
            cookieProps.put(
               HttpSessionCookieFactory.HTTP_SESSION, sessionObj);

         }
      }

      // don't use the HttpContainer -- it will cache the container
      // in the session.  copied from HttpContainer.findSessionCookie
      SessionCookie sessionCookie = findOrCreateSessionCookie(
         sAmName
         , ctx
         , sPackage + "." + sConfiguration
         , sPackage
         , sConfiguration
         , poolProps
         , cookieProps
         , isWebApp
         , BATCH.equals(parameters.get(SYNC)));


      sessionCookie.setEnvInfoProvider(envInfo);


      if(BATCH.equals(parameters.get(SYNC))) 
      {
         sessionCookie.setReferenceCounting(true);

         // JRS Why is this only preformed for batch?
         LocaleContext lctx = ctx.getLocaleContext();
         if (lctx == null)
         {
            lctx = new oracle.jbo.common.DefLocaleContext(null);
         }

         sessionCookie.setEnvironment(Session.JBO_SESSION_LOCALE, 
                                      lctx.getLocale());

         WSApplicationModuleImpl am =
            new WSApplicationModuleImpl(sessionCookie);
            
         app = new JUApplication(am);
      }
      else
      {
         if (!isWebApp)
         {
            ApplicationModule am = sessionCookie.useApplicationModule();
            app = new JUApplication(am);
         }
         else
         {
            app = new JUApplication(sessionCookie);
         }
      }

      String param = (String)parameters.get(EXC_MODE_STRING);
      if (isWebApp && param == null)
      {
         //by default webapps are set to deferred exception mode.
         ((DCJboDataControl)app).setBundledExceptionMode(DCJboDataControl.EXC_MODE_DEFERRED);
      }
      else if (param != null)
      {
         if (EXC_MODE_DEFERRED.equals(param))
         {
            ((DCJboDataControl)app).setBundledExceptionMode(DCJboDataControl.EXC_MODE_DEFERRED);
         }
         else if (EXC_MODE_IMMEDIATE.equals(param)) 
         {
            ((DCJboDataControl)app).setBundledExceptionMode(DCJboDataControl.EXC_MODE_IMMEDIATE);
         }
      }

      app.setSessionCookie(sessionCookie);
      
      app.setName(sAmName);
      app.setBindingContext(ctx);

      if (envInfo != null)
      {
         //remove envinfo from binding context.
         ctx.put(APP_PARAM_ENV_INFO, null);
      }

      app.prepareSession();

      return app;
   }
}
