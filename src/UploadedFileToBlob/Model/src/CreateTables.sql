DROP SEQUENCE uploaded_files_seq;
DROP TABLE uploaded_files;
CREATE TABLE uploaded_files(
  id NUMBER PRIMARY KEY,
  filename VARCHAR2(80),
  content BLOB,
  date_created DATE);
CREATE SEQUENCE uploaded_files_seq;
CREATE TRIGGER assign_file_id BEFORE INSERT ON uploaded_files FOR EACH ROW
BEGIN
  SELECT uploaded_files_seq.NEXTVAL INTO :NEW.id FROM dual;
END;
.
/

  