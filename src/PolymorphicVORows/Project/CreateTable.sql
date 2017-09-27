drop table person;
create table person(
  id number primary key,
  name varchar2(30),
  sex varchar2(1),
  man_attr varchar2(20),
  woman_attr varchar2(20)
);
insert into person(id,name,sex,man_attr)
            values(1,'Steve','M','ManValue');
insert into person(id,name,sex,woman_attr)
            values(2,'June','W','WomanValue');
commit;
            
      