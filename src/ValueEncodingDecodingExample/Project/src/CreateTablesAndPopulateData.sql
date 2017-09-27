drop table user_info;
create table user_info (
  username varchar2(10) primary key,
  password varchar2(10)
);
insert into user_info values('smuench','[fedcba]');
insert into user_info values('fnimphiu','[zyxwvu]');
commit;
