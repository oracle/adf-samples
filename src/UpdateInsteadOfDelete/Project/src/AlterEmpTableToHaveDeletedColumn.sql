alter table emp add ( deleted varchar2(1) default 'N');
update emp set deleted = 'N';
commit;
