ADF Mobile Insider Essentials
Using the local database
Application sample
author: Frédéric Desbiens
v 1.0.1

0. Version history
==========================
1.0   Initial version
1.0.1 Minor fixes
- Data will be retrieved from the local database if the web service call times
  out.
- Added code to the ConnectionFactory to close the DB connection and nullify the 
  attribute.
- The DB connection is closed in deactivate() instead of stop() in the 
  LifeCycleListenerImpl class.


1. Description
==============
This sample is made of two distinct applications: 
   - HRServices, which is a simple SOAP web service (SDO view object) built on 
     the top of the HR database schema. 
   - LocalDB, an ADF Mobile application that demonstrates local database 
     techniques and calls the web service when a network is available.

When a network is available, LocalDB will try to retrieve data from the web 
service. If not, it will do a query on the local database. 

2. Prerequisites
================
You will need JDeveloper 11.1.2.4 to run the web service and deploy the mobile 
application on a device and simulator. You also need access to an instance of 
Oracle Database 10g and higher.

3. How to run
=============
To run the sample, please follow these steps:

   i.   Open HRServices in JDeveloper and adapt the parameters of the database 
        connection to fit your environment.
   ii.  Deploy HRServices on a WebLogic Server instance. This can be 
        JDeveloper's integrated server.
   iii. Test the service. The url should follow this form: 
        http://<host_name_or_ip>:<port>/HRServices/HRModuleService
   iv.  Open LocalDB in JDeveloper and alter the connections.xml file to 
        reflect the ip/host name and port you used in the previous step. You 
        will have to make the change in the  <wsconnection> and <soap> tags.
   v.   Deploy LocalDB on your favorite device or emulator. Make sure the 
        mobile device and the machine where WebLogic runs are on the same 
        network. 

4. How to use the LocalDB application
=====================================
Just launch the application with an active network connection. The application
will retrieve records from the server. Then, deactivate the network (or engage
airplane mode) and press the « Refresh » button at the bottom of the screen. 
A list of imaginary countries fetched from the local database will be displayed 
instead. 
