REM $Header: /cvs/ADFToyStoreJSF/DatabaseSetup/CreateToyStoreUsers.sql,v 1.1.1.1 2006/01/26 21:47:17 steve Exp $
CONNECT SYS AS SYSDBA
CREATE USER toystore IDENTIFIED BY toystore;
CREATE USER toystore_statemgmt IDENTIFIED BY toystore;

GRANT CONNECT, RESOURCE TO toystore;

GRANT CONNECT, RESOURCE TO toystore_statemgmt;
