delete from emp where deptno not in (10,20,30,40);
delete from dept where deptno not in (10,20,30,40);
commit;
exit