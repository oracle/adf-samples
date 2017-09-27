drop function get_name_value_list;
drop type name_value_list;
drop type name_value_type;
create TYPE NAME_VALUE_TYPE AS OBJECT
( DESCRIPTION VARCHAR2(40),
  MEANING     VARCHAR2(40)
);
/
show errors

CREATE TYPE NAME_VALUE_LIST AS TABLE OF NAME_VALUE_TYPE;
/
show errors
CREATE FUNCTION GET_NAME_VALUE_LIST( p_list_type VARCHAR2)
RETURN NAME_VALUE_LIST AS
  names NAME_VALUE_LIST := NEW NAME_VALUE_LIST();
  nv    NAME_VALUE_TYPE;
  lv_list_type VARCHAR2(1) := UPPER(p_list_type);
BEGIN
  IF (lv_list_type = 'D') THEN
    select name_value_type(dname, to_char(deptno))
      bulk collect into names
      from dept
      order by dname;    
  ELSIF (lv_list_type = 'E') THEN
    select name_value_type(ename, to_char(empno))
      bulk collect into names
      from emp
      order by ename;  
  ELSE
    NULL;
  END IF;
  RETURN names;
END GET_NAME_VALUE_LIST;
/
show errors
