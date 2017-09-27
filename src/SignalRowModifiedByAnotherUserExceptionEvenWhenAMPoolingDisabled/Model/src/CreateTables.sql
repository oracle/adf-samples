drop sequence dept_with_ver_table_seq;
create sequence dept_with_ver_table_Seq start with 41;

DROP TABLE DEPT_WITH_OBJ_VERSION CASCADE CONSTRAINTS;


CREATE TABLE DEPT_WITH_OBJ_VERSION (
 DEPTNO NUMBER(2),
 DNAME VARCHAR2(14),
 LOC VARCHAR2(13),
 OBJECT_VERSION NUMBER,
 CONSTRAINT DEPT_WITH_VER_PK PRIMARY KEY (DEPTNO))
 ;

INSERT INTO DEPT_WITH_OBJ_VERSION VALUES (10, 'ACCOUNTING', 'NEW YORK',1);
INSERT INTO DEPT_WITH_OBJ_VERSION VALUES (20, 'RESEARCH',   'DALLAS',1);
INSERT INTO DEPT_WITH_OBJ_VERSION VALUES (30, 'SALES',      'CHICAGO',1);
INSERT INTO DEPT_WITH_OBJ_VERSION VALUES (40, 'OPERATIONS', 'BOSTON',1);


COMMIT;

create or replace trigger dept_with_ver_table_befins
before insert on dept for each row
begin
  if (:new.deptno is null or :new.deptno < 0) then
    select dept_with_ver_table_seq.nextval into :new.deptno from dual;
  end if;
end;
.
/
