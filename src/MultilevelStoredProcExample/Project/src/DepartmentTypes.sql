drop type dept_t_list;
drop type dept_t;
drop type emp_t_list;
drop type emp_t;
create type emp_t as object(
  empno number(4),
  ename varchar2(10),
  job varchar2(9),
  mgr number(4),
  hiredate date,
  sal number(7,2),
  comm number(7,2),
  deptno number(2),
  terminated varchar(1)
);
.
/
create type emp_t_list as table of emp_t;
/
create type dept_t as object(
  deptno number(2),
  dname varchar2(14),
  loc varchar2(13),
  emps emp_t_list
);
.
/
create type dept_t_list as table of dept_t;
/
