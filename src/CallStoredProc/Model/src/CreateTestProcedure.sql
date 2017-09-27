create or replace procedure test_procedure(lv_message varchar2) as
begin
  dbms_output.put_line(lv_message);
end;
.
/
show errors
