QUESTION
=========
How to Upload/Render an Image File using OrdImage Control with a ADF/JSF Page?

ANSWER
=======
This use case uses ADF Readonly Form instead of ADF Readonly Table because
use of ORDImage in tables or trees isn't currently supported in ADF/JSF
(Bug 4403529)

Also, note that there is no design time support for Intermedia Tags with
JSF in JDeveloper 10.1.3.0.4. Runtime Support is available for supported
use cases.

(*) Start by creating a new Application based on the "JSF/ADFBC" template.

In the Model Project
--------------------
(*) Create an entity object for the table containing the ORDIMAGE column
(*) Create an entity-based view object based on this entity

In this example the entity is "Image" and the view object is "Images"

(*) Add an instance of this view object to your application module's data model

In this example the view object instance is named "Images" in the ImageModule.

In the ViewController Project
-----------------------------
(*) In Project Settings dialog, add "BC4J HTML" library to the libraries list

(*) Configure web.xml to support the ORDMedia Servlet

    <!-- =========== ADDED BELOW FOR ORDIMAGE SUPPORT ============ -->
    
      <filter-mapping>
         <filter-name>adfBindings</filter-name>
         <servlet-name>ordDeliverMedia</servlet-name>
      </filter-mapping>
    
      <servlet>
        <servlet-name>ordDeliverMedia</servlet-name>
        <servlet-class>oracle.ord.html.OrdPlayMediaServlet</servlet-class>
        <init-param>
          <param-name>releaseMode</param-name>
          <param-value>Stateful</param-value>
        </init-param>
      </servlet> 
    
      <servlet-mapping>
        <servlet-name>ordDeliverMedia</servlet-name>
        <url-pattern>ordDeliverMedia</url-pattern>
      </servlet-mapping> 
      
    <!-- =========== ADDED ABOVE FOR ORDIMAGE SUPPORT ============ -->


(*) Creating the BrowseImages.jspx page
   
    (*) Drop Images view as ADF ReadOnly Form with navigation from
        the data control palette.
        
    (*) Go to JSP source, change:

            <af:outputText value="#{bindings.Image.inputValue}"/>

        to

           <af:objectMedia source="#{bindings.Image.inputValue.source}"
                    contentType="#{bindings.Image.inputValue.media.mimeType}"/>

    (*) Enable ORDDomain custom input handler
    
        (*) Go to the pageDef file
        (*) Select the "Image" attribute binding
        (*) In the Property Inspect, set the "CustomInputHandler" property
            to the value "OrdDomainValueHandler"

(*) Creating the EditImages.jspx page

    (*) Drop Images view as ADF Form from the data control palette.
        (*) Include navigation buttons if you want
    (*) Drop addition built-in actions from the data control palette to
        create buttons for:  Create, Commit, and Rollback
        (*) Set the "disabled" property of the Commit button to "false"

    (*) Select the <af:form> tag in the structure window and set its
        "enctype" property to the value "multipart/form-data"

    (*) In the JSF source for the EditImage.jspx page, change:

        <af:inputText value="#{bindings.Image.inputValue}"
                      label="#{bindings.Image.label}">
          <af:validator binding="#{bindings.Image.validator}"/>
        </af:inputText>

        to:

        <af:panelLabelAndMessage>
          <af:inputFile value="#{bindings.Image.inputValue}" simple="true">
            <af:validator binding="#{bindings.Image.validator}"/>
          </af:inputFile>
          <af:objectMedia source="#{bindings.Image.inputValue.source}"
                    contentType="#{bindings.Image.inputValue.media.mimeType}"/>
        </af:panelLabelAndMessage>

    (*) Enable ORDDomain custom input handler
    
        (*) Go to the pageDef file
        (*) Select the "Image" attribute binding
        (*) In the Property Inspect, set the "CustomInputHandler" property
            to the value "OrdDomainValueHandler"
