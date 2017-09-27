CREATE TYPE num_table AS TABLE OF NUMBER;
/
CREATE TYPE varchar2_table AS TABLE OF varchar2(2000);
/
CREATE OR REPLACE FUNCTION in_number_list (p_in_list  IN  VARCHAR2)
  RETURN num_table
AS
  l_tab   num_table := num_table();
  l_text  VARCHAR2(32767) := p_in_list || ',';
  l_idx   NUMBER;
BEGIN
  LOOP
    l_idx := INSTR(l_text, ',');
    EXIT WHEN NVL(l_idx, 0) = 0;
    l_tab.extend;
    l_tab(l_tab.last) := to_number(TRIM(SUBSTR(l_text, 1, l_idx - 1)));
    l_text := SUBSTR(l_text, l_idx + 1);
  END LOOP;

  RETURN l_tab;
END;
/
show errors
CREATE OR REPLACE FUNCTION in_varchar2_list (p_in_list  IN  VARCHAR2)
  RETURN varchar2_table
AS
  l_tab   varchar2_table := varchar2_table();
  l_text  VARCHAR2(32767) := p_in_list || ',';
  l_idx   number;
BEGIN
  LOOP
    l_idx := INSTR(l_text, ',');
    EXIT WHEN NVL(l_idx, 0) = 0;
    l_tab.extend;
    l_tab(l_tab.last) := TRIM(SUBSTR(l_text, 1, l_idx - 1));
    l_text := SUBSTR(l_text, l_idx + 1);
  END LOOP;

  RETURN l_tab;
END;
/
show errors
