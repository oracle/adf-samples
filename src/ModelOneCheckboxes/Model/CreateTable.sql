drop table test_table;
create table test_table(
  id number primary key,
  option_one varchar2(1) default 'N' check (option_one in ('Y','N')),
  option_two varchar2(1) default 'N' check (option_two in ('Y','N'))
);
insert into test_table values (1,'N','N');
insert into test_table values (2,'N','Y');
insert into test_table values (3,'Y','N');
insert into test_table values (4,'Y','Y');
commit;
