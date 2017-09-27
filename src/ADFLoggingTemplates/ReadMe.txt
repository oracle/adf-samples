JDeveloper Code Templates for the ADF Logger
--------------------------------------------
By: Duncan Mills

Introduction and Usage
----------------------
The enclosed XML file defines a set of short-cut templates for the Java code editor in JDeveloper.
Once installed into your project you can use one of the templates using the short codes (e.g. lgi) and press the expand keystroke (usually control+enter). 
If you create a logger definition in the class first (using lgdef), subsequent templates will pick up the logger instance name that you have created automatically.

Installing the Template
-----------------------
1) Start JDeveloper
2) Open the preferences window (Tools --> Preferences)
3) Expand Code Editor --> Code Templates 
4) Select the [More Actions] pulldown men from the right hand panel and choose Import
5) Pick the loggingTemplates.xml file
6) You can now use the search box in the panel to view the new templates. They are all prefixed with lg*, e.g. lgdef, lgw etc. 
7) Press OK
Your templates are now usable.

Compatibility
-------------
These templates have been tested with and are compatible with JDeveloper 11.1.1.n, 11.1.2.n and 12 (pre-production testing only) 

The Code Templates
------------------
Shortcut	Purpose
lgdef		A basic static class logger definition
lgdefr		A basic static class logger definition with resource bundle
lgdefp		A basic static package logger definition
lgi			Log statement for an informational message
lgc			Log statement for configuration information
lgw			Log statement for a warning message
lgs			Log statement for an error message
lgig		Guarded log statement for an informational message
lgcg		Guarded log statement for configuration information
lgwg		Guarded log statement for a warning message
lgsg		Guarded log statement for an error message
