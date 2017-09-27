--------------------------------------------------------
--  File created - Monday-July-13-2009   
--------------------------------------------------------
  DROP TABLE "DEPT_WITH_FLAG" cascade constraints;
--------------------------------------------------------
--  DDL for Table DEPT_WITH_FLAG
--------------------------------------------------------

  CREATE TABLE "DEPT_WITH_FLAG" 
   (	"DEPTNO" NUMBER(2,0) PRIMARY KEY, 
	"DNAME" VARCHAR2(14), 
	"LOC" VARCHAR2(13), 
	"FLAG" CHAR(1)
   ) ;

---------------------------------------------------
--   DATA FOR TABLE DEPT_WITH_FLAG
--   FILTER = none used
---------------------------------------------------
REM INSERTING into DEPT_WITH_FLAG
Insert into DEPT_WITH_FLAG (DEPTNO,DNAME,LOC,FLAG) values (10,'ACCOUNTING','NEW YORK','Y');
Insert into DEPT_WITH_FLAG (DEPTNO,DNAME,LOC,FLAG) values (20,'RESEARCH','DALLAS','N');
Insert into DEPT_WITH_FLAG (DEPTNO,DNAME,LOC,FLAG) values (30,'SALES','CHICAGO','Y');
Insert into DEPT_WITH_FLAG (DEPTNO,DNAME,LOC,FLAG) values (40,'OPERATIONS','BOSTON','N');

---------------------------------------------------
--   END DATA FOR TABLE DEPT_WITH_FLAG
---------------------------------------------------

commit;
purge recyclebin;

