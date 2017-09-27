drop table exampleproctab;
drop sequence exampleprocseq;
create sequence exampleprocseq;
create table exampleproctab (
id number primary key,
begin_date date,
end_date date,
other_value varchar2(40)
);
create or replace procedure exampleproc(begins date, ends date, other varchar2) is
begin
  insert into exampleproctab(id,begin_date,end_date,other_value)
  values (exampleprocseq.nextval,begins,ends,other);
end;
.
/
show errors
create or replace function examplefunc(begins date, ends date, other varchar2)
return number
is
  new_id number;
begin
  insert into exampleproctab(id,begin_date,end_date,other_value)
  values (exampleprocseq.nextval,begins,ends,other)
  returning id into new_id;
  return new_id;
end;
.
/
show errors
