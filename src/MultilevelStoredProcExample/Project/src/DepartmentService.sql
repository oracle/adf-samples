create or replace package dept_service is
  procedure add_dept(p_dept in out dept_t);
  procedure remove_dept(p_deptno number);
  procedure update_dept(p_dept in out dept_t);
end;
.
/
show errors
