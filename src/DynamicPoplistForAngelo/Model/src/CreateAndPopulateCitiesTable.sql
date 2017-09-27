DROP TABLE CITIES;
DROP SEQUENCE CITIES_SEQ;
CREATE SEQUENCE CITIES_SEQ;
CREATE TABLE CITIES( 
  CITY_ID    NUMBER PRIMARY KEY,
  NAME       VARCHAR2(30) NOT NULL,
  REGION_ID  NUMBER       NOT NULL,
  COUNTRY_ID CHAR(2)  NOT NULL,
  NOTES      VARCHAR2(50),
  CONSTRAINT REGION_FOR_CITY FOREIGN KEY (REGION_ID) REFERENCES REGIONS,
  CONSTRAINT COUNTRY_FOR_CITY FOREIGN KEY (COUNTRY_ID) REFERENCES COUNTRIES
);
insert into cities(city_id,name,region_id,country_id,notes)
select cities_seq.nextval,l.city,c.region_id,l.country_id,
       'Some notes about '||l.city
from locations l, countries c
where c.country_id = l.country_id;
COMMIT;

  
