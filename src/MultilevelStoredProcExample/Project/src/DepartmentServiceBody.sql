create or replace package body dept_service is
  --- Private Procedures ---
  procedure add_emp(p_emp in out emp_t) is
  begin
    if (p_emp.empno is not null and p_emp.empno > 0) then
      raise_application_error(-20002,
       'New employee id is assigned by system, should be null or negative');
    else
      insert into emp(ename,job,mgr,hiredate,sal,comm,deptno)
      values (p_emp.ename,
              p_emp.job,
              p_emp.mgr,
              p_emp.hiredate,
              p_emp.sal,
              p_emp.comm,
              p_emp.deptno)
      returning empno into p_emp.empno;
    end if;
  end;
  procedure update_emp(p_emp in out emp_t) is
  begin
    update emp
       set ename = p_emp.ename,
           job   = p_emp.job,
           mgr   = p_emp.mgr,
           hiredate = p_emp.hiredate,
           sal      = p_emp.sal,
           comm     = p_emp.comm,
           deptno   = p_emp.deptno
     where empno = p_emp.empno;
  end;
  procedure remove_emp(p_emp in out emp_t) is
  begin
    delete from emp where empno = p_emp.empno;
  end;
  procedure private_add_dept(p_dept in out dept_t) is
  begin
    if (p_dept is null) then
      raise_application_error(-20000,'No department information to insert');
    elsif (p_dept.deptno is not null and p_dept.deptno > 0) then
      raise_application_error(-20001,
         'New department id is assigned by system, should be null or negative');
    else
      insert into dept(dname,loc) values (p_dept.dname,p_dept.loc)
      returning deptno into p_dept.deptno;
    end if;
  end;
  procedure private_update_dept(p_dept in out dept_t) is
  begin
    update dept
       set dname  = p_dept.dname,
           loc    = p_dept.loc
     where deptno = p_dept.deptno;
  end;
  procedure private_remove_dept(p_deptno number) is
  begin
    delete from dept where deptno = p_deptno;
  end;

  procedure update_emps(p_dept in out dept_t) is
  begin
    if (p_dept.emps is not null) then
      for j in 1..p_dept.emps.count loop
        if (upper(p_dept.emps(j).terminated)='Y') then
          remove_emp(p_dept.emps(j));
        elsif (p_dept.emps(j).empno is null or p_dept.emps(j).empno < 0) then
          p_dept.emps(j).deptno := p_dept.deptno;
          add_emp(p_dept.emps(j));
        else
          update_emp(p_dept.emps(j));
        end if;
      end loop;
    end if;
  end;
  --- Public Procedures ---
  procedure add_dept(p_dept in out dept_t) is
    new_dept dept_t;
  begin
    private_add_dept(p_dept);
    update_emps(p_dept);
  end;
  procedure remove_dept(p_deptno number) is
  begin
    private_remove_dept(p_deptno);
  end;
  procedure update_dept(p_dept in out dept_t) is
  begin
    private_update_dept(p_dept);
    update_emps(p_dept);
  end;
end;
.
/
show errors