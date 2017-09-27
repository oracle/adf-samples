drop view testview;
drop table testtab;
create table testtab( v varchar2(10), other_unique_value varchar2(20));
create view testview as select * from testtab;
create or replace trigger insins_testview instead of insert on testview for each row
begin
  insert into testtab values (:new.v,:new.other_unique_value);
end;
.
/
show errors
create or replace trigger insupd_testview instead of update on testview for each row
begin
  update testtab
  set v = :new.v,
      other_unique_value = :new.other_unique_value
  where other_unique_value = :old.other_unique_value;
end;
.
/
show errors
create unique index testtabidx on testtab(other_unique_value);
insert into testtab values ('Steve','One');
insert into testtab values ('Paul','Two');
insert into testtab values ('Steve','Three');
commit;

