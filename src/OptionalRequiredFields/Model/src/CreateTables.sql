create table optional_required_test( id number primary key,
row_type varchar2(1) check (row_type in ('A','B')),
value_a varchar2(10),
value_b varchar2(10)
);
insert into optional_required_test values (1,'A','AVALUE',NULL);
insert into optional_required_test values (2,'B',NULL,'BVALUE');
commit;

