create table settings( code varchar2(4) primary key, status number(1) default 0 check (status in (1,0)));
insert into settings values ('ABCD',0);
insert into settings values ('EFGH',1);
insert into settings values ('IJKL',0);
commit;
