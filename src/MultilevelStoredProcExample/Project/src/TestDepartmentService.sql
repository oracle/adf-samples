set serveroutput on size 1000000
create or replace procedure test_dept_service is
  new_dept dept_t;
  new_emp  emp_t;
  new_emp_list emp_t_list;
begin
  new_dept := dept_t(null,'Test','TestLoc',null);
  dept_service.add_dept(new_dept);
  dbms_output.put_line(new_dept.deptno||', '||new_dept.dname);
  dept_service.update_dept(new_dept);
  new_emp  := emp_t(null,'Steve',null,null,sysdate,1000,null,null,1);
  new_emp_list := emp_t_list();
  new_emp_list.extend;
  new_emp_list(1) := new_emp;
  new_dept := dept_t(null,'Test2','TestLoc2',new_emp_list);
  dept_service.add_dept(new_dept);
  dbms_output.put_line(new_dept.deptno||', '||new_dept.dname);
  for j in 1..new_emp_list.count loop
    dbms_output.put_line(new_dept.emps(j).empno||', '||new_dept.emps(j).ename);
  end loop;
end;
.
/
show errors

exec test_dept_service
