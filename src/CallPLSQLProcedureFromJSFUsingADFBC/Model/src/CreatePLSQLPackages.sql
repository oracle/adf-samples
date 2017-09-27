drop table example_pkg_table;
drop sequence example_pkg_table_seq;
create sequence example_pkg_table_seq;
create table example_pkg_table(id number primary key, v varchar2(30), n number, d date);
create trigger assign_pk_example_pkg_table before insert on example_pkg_table 
for each row
begin
  select example_pkg_table_seq.nextval into :new.id from dual;
end;
.
/
show errors

create or replace package example_pkg
as
    procedure do_something( pv varchar2, pn number, pd date);
end;
.
/
show errors
create or replace package body example_pkg as
    procedure do_something( pv varchar2, pn number, pd date) as
    begin
      insert into example_pkg_table(v,n,d) values (pv,pn,pd);
    end;
end;
/
show errors
