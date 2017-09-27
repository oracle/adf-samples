You can run the test.ADFXMLUtil class to try reading the
xmlreadtest.xml XML file by the "Departments" VO instance
in the test.module.TestModule application module.

See the project properties "Program Arguments" field on
the "Runner" tab for the args that are being passed by
default to this console program.

The Emp and Dept entities both have DBSequence-valued
primary keys, and the CreateEmpDeptTables.sql script
creates SCOTT/TIGER tables with EMP_SEQ, DEPT_SEQ, and
BEFORE INSERT triggers to test this example.

NOTE: If you mark the WorksInDeptAssoc association as
====  being a composition, then you do NOT need the
      overridden postChanges() method in EmpImpl.java
      nor the overridden refreshFKInNewContainees()
      method in DeptImpl.java since the framework would
      take care of the post-ordering and foreign-key
      refreshing for you.
