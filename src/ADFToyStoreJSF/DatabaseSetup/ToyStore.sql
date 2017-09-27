REM $Header: /cvs/ADFToyStoreJSF/DatabaseSetup/ToyStore.sql,v 1.1.1.1 2006/01/26 21:47:17 steve Exp $
spool ToyStore.log
DROP SEQUENCE ORDERNUM;
DROP TABLE SIGNON;
DROP TABLE PROFILE;
DROP TABLE ORDERSTATUS;
DROP TABLE LINEITEM;
DROP TABLE ORDERS;
DROP TABLE INVENTORY;
DROP TABLE ITEM;
DROP TABLE SUPPLIER;
DROP TABLE PRODUCT;
DROP TABLE CATEGORY;
DROP TABLE ACCOUNT;
DROP TABLE STATES_FOR_COUNTRY;
DROP TYPE TABLE_OF_ITEMID;
CREATE SEQUENCE ORDERNUM START WITH 1001;

CREATE TABLE ACCOUNT (
 USERID VARCHAR2(80) NOT NULL,
 EMAIL VARCHAR2(80) NOT NULL,
 FIRSTNAME VARCHAR2(80) NOT NULL,
 LASTNAME VARCHAR2(80) NOT NULL,
 STATUS VARCHAR2(2),
 ADDR1 VARCHAR2(80) NOT NULL,
 ADDR2 VARCHAR2(40),
 CITY VARCHAR2(80) NOT NULL,
 STATE VARCHAR2(80) NOT NULL,
 ZIP VARCHAR2(20) NOT NULL,
 COUNTRY VARCHAR2(20) NOT NULL,
 PHONE VARCHAR2(80) NOT NULL,
 CONSTRAINT PK_ACCOUNT PRIMARY KEY (USERID)
);

CREATE TABLE CATEGORY (
 CATID VARCHAR2(10) NOT NULL,
 NAME VARCHAR2(80),
 PICTURE VARCHAR2(255),
 CONSTRAINT PK_CATEGORY PRIMARY KEY (CATID)
);

CREATE TABLE PRODUCT (
 PRODUCTID VARCHAR2(10) NOT NULL,
 CATEGORY VARCHAR2(10) NOT NULL,
 NAME VARCHAR2(80),
 DESCN VARCHAR2(255),
 PICTURE VARCHAR2(255),
 CONSTRAINT PK_PRODUCT PRIMARY KEY (PRODUCTID),
 CONSTRAINT CATEGORY_FOR_PRODUCT FOREIGN KEY (CATEGORY)
            REFERENCES CATEGORY (CATID)
);

CREATE TABLE SUPPLIER (
 SUPPID NUMBER NOT NULL,
 NAME VARCHAR2(80),
 STATUS VARCHAR2(2) NOT NULL,
 ADDR1 VARCHAR2(80),
 ADDR2 VARCHAR2(80),
 CITY VARCHAR2(80),
 STATE VARCHAR2(80),
 ZIP VARCHAR2(5),
 PHONE VARCHAR2(80),
 CONSTRAINT PK_SUPPLIER PRIMARY KEY (SUPPID)
);

CREATE TABLE ITEM (
 ITEMID VARCHAR2(10) NOT NULL,
 PRODUCTID VARCHAR2(10) NOT NULL,
 LISTPRICE NUMBER(10,2),
 UNITCOST NUMBER(10,2),
 SUPPLIER NUMBER,
 STATUS VARCHAR2(2),
 ATTR1 VARCHAR2(80),
 ATTR2 VARCHAR2(80),
 ATTR3 VARCHAR2(80),
 ATTR4 VARCHAR2(80),
 ATTR5 VARCHAR2(80),
 CONSTRAINT PK_ITEM PRIMARY KEY (ITEMID),
 CONSTRAINT PRODUCT_KIND FOREIGN KEY (PRODUCTID)
            REFERENCES PRODUCT (PRODUCTID),
 CONSTRAINT SUPPLIED_BY FOREIGN KEY (SUPPLIER)
            REFERENCES SUPPLIER (SUPPID)
);

CREATE TABLE INVENTORY (
 ITEMID VARCHAR2(10) NOT NULL,
 QTY NUMBER NOT NULL,
 CONSTRAINT PK_INVENTORY PRIMARY KEY (ITEMID),
 CONSTRAINT ON_HAND_INFO_FOR FOREIGN KEY (ITEMID)
             REFERENCES ITEM (ITEMID)
);

CREATE TABLE ORDERS (
 ORDERID NUMBER NOT NULL,
 USERID VARCHAR2(80) NOT NULL,
 ORDERDATE DATE NOT NULL,
 SHIPADDR1 VARCHAR2(80) NOT NULL,
 SHIPADDR2 VARCHAR2(80),
 SHIPCITY VARCHAR2(80) NOT NULL,
 SHIPSTATE VARCHAR2(80) NOT NULL,
 SHIPZIP VARCHAR2(20) NOT NULL,
 SHIPCOUNTRY VARCHAR2(20) NOT NULL,
 BILLADDR1 VARCHAR2(80) NOT NULL,
 BILLADDR2 VARCHAR2(80),
 BILLCITY VARCHAR2(80) NOT NULL,
 BILLSTATE VARCHAR2(80) NOT NULL,
 BILLZIP VARCHAR2(20) NOT NULL,
 BILLCOUNTRY VARCHAR2(20) NOT NULL,
 COURIER VARCHAR2(80),
 TOTALPRICE NUMBER(10,2) NOT NULL,
 BILLTOFIRSTNAME VARCHAR2(80) NOT NULL,
 BILLTOLASTNAME VARCHAR2(80) NOT NULL,
 SHIPTOFIRSTNAME VARCHAR2(80) NOT NULL,
 SHIPTOLASTNAME VARCHAR2(80) NOT NULL,
 CREDITCARD VARCHAR2(80),
 EXPRDATE VARCHAR2(7),
 CARDTYPE VARCHAR2(80),
 LOCALE VARCHAR2(20),
 CONSTRAINT PK_ORDERS PRIMARY KEY (ORDERID),
 CONSTRAINT ORDERED_BY FOREIGN KEY (USERID)
            REFERENCES ACCOUNT (USERID)
);

CREATE TRIGGER orders_before_insert
  BEFORE INSERT
  ON orders
  FOR EACH ROW
BEGIN
  IF (   :new.orderid < 0
      OR :new.orderid IS NULL) THEN
    SELECT ordernum.nextval
      INTO :new.orderid
      FROM dual;
  END IF;
END;
/

CREATE TABLE LINEITEM (
 ORDERID NUMBER NOT NULL,
 LINENUM NUMBER NOT NULL,
 ITEMID VARCHAR2(10) NOT NULL,
 QUANTITY NUMBER NOT NULL,
 UNITPRICE NUMBER(10,2) NOT NULL,
 CONSTRAINT PK_LINEITEM PRIMARY KEY (ORDERID, LINENUM),
 CONSTRAINT ITEM_ORDERD_ON FOREIGN KEY (ORDERID)
 REFERENCES ORDERS (ORDERID),
 CONSTRAINT ORDER_FOR_ITEM FOREIGN KEY (ITEMID)
            REFERENCES ITEM (ITEMID)
 );


CREATE TABLE ORDERSTATUS (
 ORDERID NUMBER NOT NULL,
 LINENUM NUMBER NOT NULL,
 TIMESTAMP DATE NOT NULL,
 STATUS VARCHAR2(2) NOT NULL,
 CONSTRAINT PK_ORDERSTATUS PRIMARY KEY (ORDERID, LINENUM),
 CONSTRAINT STATUS_OF_ORDER FOREIGN KEY (ORDERID)
            REFERENCES ORDERS (ORDERID)
);

CREATE TABLE PROFILE (
 USERID VARCHAR2(80) NOT NULL,
 LANGPREF VARCHAR2(80) NOT NULL,
 FAVCATEGORY VARCHAR2(30),
 MYLISTOPT NUMBER,
 BANNEROPT NUMBER,
 CONSTRAINT PK_PROFILE PRIMARY KEY (USERID),
 CONSTRAINT PROFILE_FOR_ACCOUNT FOREIGN KEY (USERID)
            REFERENCES ACCOUNT (USERID)
);

CREATE TABLE SIGNON (
 USERNAME VARCHAR2(25) NOT NULL,
 PASSWORD VARCHAR2(25) NOT NULL,
 CONSTRAINT PK_SIGNON PRIMARY KEY (USERNAME)
);

INSERT INTO account
            (
              userid,
              email,
              firstname,
              lastname,
              status,
              addr1,
              addr2,
              city,
              state,
              zip,
              country,
              phone
            )
     VALUES (
       'j2ee',
       'j2ee@yourdomain.com',
       'Jay',
       'Tooey',
       'OK',
       '901 San Antonio Road',
       'MS UCUP02-206',
       'Palo Alto',
       'CA',
       '94303',
       'US',
       '555-555-5555'
     );

INSERT INTO signon
            (username, password)
     VALUES ('j2ee', 'j2ee');

INSERT INTO profile
            (userid, langpref, favcategory, mylistopt, banneropt)
     VALUES ('j2ee', 'english', 'dogs', 1, 1);


INSERT INTO category(catid,name)
    VALUES('A','Accessories');

INSERT INTO category(catid,name)
    VALUES('G','Games');

INSERT INTO category(catid,name)
    VALUES('M','Models');

INSERT INTO category(catid,name)
    VALUES('P','Party Supplies');

INSERT INTO category(catid,name)
    VALUES('T','Toys');

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'A-DIC-1',
       'A',
       'Dice',
       'Smooth, shiny, and perfectly weighted. They feel good '||
       'in your hands. Twelve dice, assorted jewel colors.',
       '116.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'A-POK-2',
       'A',
       'Poker Chips',
       'Perfect as fake money for a saloon night fundraiser '||
       'or a more serious endeavor. 100 assorted poker chips.',
       '044.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'A-BIL-3',
       'A',
       'Billiard Balls',
       'These built-to-last billiard balls are used in pool halls '||
       'and withstand even the heaviest use.',
       '090.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'A-PIN-4',
       'A',
       'Ping Pong Paddle and Two Balls',
       'Made of the finest hardwood and long-lasting red rubber.',
       '050.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'G-CHE-1',
       'G',
       'Chess Set',
       'Match wits with your friends and acquaintances. Beautiful inlaid '||
       'hardwood board and carved stone pieces. ',
       '059.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'G-DAR-2',
       'G',
       'Dart Board',
       'This dart board is made with only the densest cork and resilient '||
       'dyes, for long wear. Darts not included.',
       '083.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'G-MAR-3',
       'G',
       'Marbles',
       '20 glass marbles. Use them in games, or as decoration in glass '||
       'jars, fish bowls, or surrounding plants.',
       '064.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'G-BAL-4',
       'G',
       'Ball and Jacks',
       'Improve hand-eye coordination with a good game of jacks.',
       '006.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'G-PUZ-5',
       'G',
       'Puzzle Cube',
       'Twist the cube to make each side one color.',
       '078.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'G-JIG-6',
       'G',
       'Jigsaw Puzzle',
       'Take the wooden pieces out of the frame, and try to put them back.',
       '102.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'P-BAL-1',
       'P',
       'Balloons',
       'No celebration is complete without festive balloons! Package '||
       'contains 12 uninflated balloons, in assorted colors.',
       '083b.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'P-PIN-2',
       'P',
       'Pinata',
       'Filled with candy and small toys. Blind-folded, supervised '||
       'participants try, in turn, to break open the suspended pinata '||
       'with a baseball bat.',
       '114.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'T-ROB-1',
       'T',
       'Robot',
       'Good-natured robot walks, shakes hands, and politely says '||
       '"Nice to meet you, Earthling." Blinking red lights on head. '||
       'Two AA batteries included.',
       '091.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'T-RUB-2',
       'T',
       'Rubber Duck',
       'A bathtime classic for adults, children, and even pets. When '||
       'you squeeze the duck, it quacks. Our version is made from '||
       'heavy duty materials that will last for generations.',
       '025.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'T-DIN-3',
       'T',
       'Dinosaur',
       'Equally at home in a child''s playroom or on top of your '||
       'computer screen, this carnivorous reptile from the past is '||
       'sure to become a favorite. And a reminder that even the '||
       'mightiest must someday fall.',
       '092.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'T-WIN-4',
       'T',
       'Windup Monkey with Cymbals',
       'Wind up this musical monkey and fill the room with the '||
       'cheerful patter of small cymbals.',
       '012.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'M-ROC-2',
       'M',
       'Rocket',
       'You almost expect a super hero to emerge from this detailed, '||
       'shiny, silver rocket. And it''s the perfect gift to tell '||
       'your loved one you want to take them to the moon.',
       '084.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'M-CAB-1',
       'M',
       'Cab Model',
       'Just like they drive in New York. We''ve thought of every '||
       'detail when constructing this sturdy miniature: you can open '||
       'the hood and trunk, and even honk the horn!',
       '111.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'T-STR-7',
       'T',
       'String Top',
       'This easy-to-use top includes complete instructions.',
       '071.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'T-PUM-8',
       'T',
       'Pump Top',
       'Watch the gorgeous metallic colors spin. Great for amusing '||
       'children and pets.',
       '087.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'T-YOY-9',
       'T',
       'Yo-yo',
       'A deep ruby color. Comprehensive instruction book '||
       'containing many tricks included.',
       '108.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'T-BEA-10',
       'T',
       'Bear',
       'Soft and cuddly, this bear will be your friend for years to come.',
       '089.jpg'
);

INSERT INTO product
            (productid, category, name, descn,picture)
     VALUES (
       'T-DOL-11',
       'T',
       'Doll',
       'If you thought they don''t make them like they used to, '||
       'here''s the doll for you! Quality construction is evident throughout.',
       '090b.jpg'
);


INSERT INTO supplier
            (suppid, name, status, addr1, city, state, zip, phone)
     VALUES (
       301,
       'Novelty Toy Company',
       'AC',
       '101 San Antonio Way',
       'Oakland',
       'CA',
       '94606',
       '510-323-4553'
);

INSERT INTO supplier
            (suppid, name, status, addr1, city, state, zip, phone)
     VALUES (
       302,
       'Games for the Masses',
       'AC',
       '120 Adeline St.',
       'Berkeley',
       'CA',
       '94703',
       '510-344-6689'
);

INSERT INTO supplier
            (suppid, name, status, addr1, city, state, zip, phone)
     VALUES (
       303,
       'RetroLand Merchandising',
       'AC',
       '34 Montana Ave.',
       'Santa Monica',
       'CA',
       '90403',
       'phone'
);

INSERT INTO supplier
            (suppid, name, status, addr1, city, state, zip, phone)
     VALUES (
       304,
       'Unusual Party Favors, Inc.',
       'AC',
       '166 Avenue B',
       'New York',
       'NY',
       '10009',
       '212-443-6558'
);

INSERT INTO supplier
            (suppid, name, status, addr1, city, state, zip, phone)
     VALUES (
       305,
       'Inanimate Friends Company',
       'AC',
       '16 Bogus Basin Rd',
       'Boise',
       'ID',
       '83702',
       '208-877-5433'
);

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-1','T-ROB-1',24.99,18.74,301,'P','Dark Grey' /* Robot */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-2','T-ROB-1',24.99,18.74,301,'P','Black' /* Robot */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-3','T-ROB-1',24.99,18.74,301,'P','Fluorescent Blue' /* Robot */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-4','T-RUB-2',4.99,3.74,301,'P','Small' /* Rubber Duck */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-5','T-RUB-2',5.50,4.00,301,'P','Medium' /* Rubber Duck */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-6','T-RUB-2',5.99,4.74,301,'P','Large' /* Rubber Duck */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-7','T-RUB-2',6.99,4.74,301,'P','Extra Large' /* Rubber Duck */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-8','T-DIN-3',11.99,8.99,301,'P','Brontosaurus' /* Dinosaur */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-9','T-DIN-3',11.99,8.99,301,'P','Stegasaurus' /* Dinosaur */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-10','T-DIN-3',11.99,8.99,301,'P','Tyrannosaurus Rex' /* Dinosaur */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-11','T-DIN-3',11.99,8.99,301,'P','Ornithocheirus' /* Dinosaur */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-12','T-DIN-3',11.99,8.99,301,'P','Pterodactyl' /* Dinosaur */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-13','T-DIN-3',11.99,8.99,301,'P','Brachiosaurus' /* Dinosaur */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-14','T-DIN-3',11.99,8.99,301,'P','Allosaurus' /* Dinosaur */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-15','T-DIN-3',11.99,8.99,301,'P','Velociraptor' /* Dinosaur */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-16','T-WIN-4',49.99,37.49,301,'P','Red/White-Striped' /* Windup Monkey */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-17','T-WIN-4',49.99,37.49,301,'P','Blue/White-Striped' /* Windup Monkey */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-18','M-ROC-2',15.99,11.99,301,'P','Apollo-13' /* Rocket */ );
INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-19','M-ROC-2',15.99,11.99,301,'P','Saturn V' /* Rocket */ );
INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-20','M-ROC-2',15.99,11.99,301,'P','Ariane' /* Rocket */ );
INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-21','M-ROC-2',15.99,11.99,301,'P','Atlas' /* Rocket */ );
INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-22','M-ROC-2',15.99,11.99,301,'P','Titan' /* Rocket */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-23','M-CAB-1',21.99,16.49,301,'P','New York' /* Cab Model */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-24','M-CAB-1',21.99,16.49,301,'P','New Jersey' /* Cab Model */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-25','M-CAB-1',21.99,16.49,301,'P','San Francisco' /* Cab Model */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-26','A-DIC-1',3.99,2.99,302,'P','Red' /* Dice */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-27','A-DIC-1',3.99,2.99,302,'P','White' /* Dice */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-28','A-POK-2',14.99,11.24,302,'P','Las Vegas Style' /* Poker Chips */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-29','A-POK-2',14.99,11.24,302,'P','Atlantic City Style' /* Poker Chips */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-30','G-CHE-1',89.99,67.49,302,'P','Teak Wood' /* Chess Set */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-31','G-CHE-1',105.00,80.49,302,'P','Alabaster' /* Chess Set */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-32','G-CHE-1',98.75,67.49,302,'P','Ebony Wood' /* Chess Set */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-33','A-BIL-3',59.99,44.99,302,'P','Snooker' /* Billiard Balls */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-34','A-BIL-3',59.99,44.99,302,'P','Standard' /* Billiard Balls */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-35','G-DAR-2',29.99,22.49,302,'P','Circular' /* Dart Board */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-36','G-DAR-2',29.99,22.49,302,'P','Square' /* Dart Board */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-37','A-PIN-4',19.99,14.99,302,'P','Blue' /* Ping Pong Paddle and Two Balls */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-38','A-PIN-4',19.99,14.99,302,'P','Red' /* Ping Pong Paddle and Two Balls */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-39','G-MAR-3',4.99,3.74,303,'P','Small' /* Marbles */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-40','G-MAR-3',7.99,5.74,303,'P','Large' /* Marbles */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-41','G-BAL-4',69,51.75,303,'P','Green' /* Ball and Jacks */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-42','G-BAL-4',69,51.75,303,'P','Purple' /* Ball and Jacks */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-43','G-BAL-4',69,51.75,303,'P','Orange' /* Ball and Jacks */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-44','T-STR-7',3.99,2.99,303,'P','Yellow-Tipped' /* String Top */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-45','T-STR-7',3.99,2.99,303,'P','Blue-Tipped' /* String Top */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-46','T-PUM-8',4.49,3.36,303,'P','Black Metallic' /* Pump Top */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-47','T-PUM-8',4.49,3.36,303,'P','Transparent Grey' /* Pump Top */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-48','T-YOY-9',5.99,4.49,303,'P','White' /* Yo-yo */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-49','T-YOY-9',5.99,4.49,303,'P','Green' /* Yo-yo */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-50','T-YOY-9',5.99,4.49,303,'P','Purple' /* Yo-yo */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-51','T-YOY-9',5.99,4.49,303,'P','Black' /* Yo-yo */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-52','T-YOY-9',5.99,4.49,303,'P','Polka Dotted' /* Yo-yo */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-53','T-YOY-9',7.99,5.50,303,'P','Glow-in-the-Dark' /* Yo-yo */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-54','G-PUZ-5',14.99,11.24,303,'P','Rainbow Colored' /* Puzzle Cube */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-55','G-PUZ-5',14.99,11.24,303,'P','Grey Patterned' /* Puzzle Cube */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-56','G-JIG-6',12.99,9.74,303,'P','Square' /* Jigsaw Puzzle */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-57','G-JIG-6',12.99,9.74,303,'P','Star-Shaped' /* Jigsaw Puzzle */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-58','G-JIG-6',12.99,9.74,303,'P','Circular' /* Jigsaw Puzzle */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-59','G-JIG-6',12.99,9.74,303,'P','Triangular' /* Jigsaw Puzzle */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-60','P-BAL-1',3.99,2.99,304,'P','Assorted Color' /* Balloons */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-61','P-BAL-1',3.99,2.99,304,'P','Only White' /* Balloons */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-62','P-BAL-1',8.99,3.50,304,'P','Heart-Shaped' /* Balloons */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-63','P-PIN-2',45.99,34.49,304,'P','Traditional Mexican' /* Piñata */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-64','P-PIN-2',45.99,34.49,304,'P','Traditional Columbian' /* Piñata */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-65','P-PIN-2',45.99,34.49,304,'P','Traditional Costa Rican' /* Piñata */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-66','P-PIN-2',45.99,34.49,304,'P','Traditional Venezuelan' /* Piñata */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-67','P-PIN-2',45.99,34.49,304,'P','Traditional Chilean' /* Piñata */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-68','P-PIN-2',45.99,34.49,304,'P','Traditonal Ecuadorian' /* Piñata */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-69','T-BEA-10',24.99,18.74,305,'P','Nanny' /* Bear */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-70','T-BEA-10',24.99,18.74,305,'P','Daddy' /* Bear */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-71','T-BEA-10',24.99,18.74,305,'P','Doctor' /* Bear */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-72','T-BEA-10',24.99,18.74,305,'P','Athlete' /* Bear */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-73','T-BEA-10',24.99,18.74,305,'P','Plain' /* Bear */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-74','T-BEA-10',24.99,18.74,305,'P','Nurse' /* Bear */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-75','T-BEA-10',24.99,18.74,305,'P','Computer Programmer' /* Bear */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-76','T-BEA-10',24.99,18.74,305,'P','Carpenter' /* Bear */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-77','T-DOL-11',29.99,22.49,305,'P','Girl' /* Doll */ );

INSERT INTO item
            (itemid, productid, listprice, unitcost, supplier, status, attr1)
 VALUES ('EST-78','T-DOL-11',29.99,22.49,305,'P','Boy' /* Doll */ );

INSERT INTO inventory
     VALUES('EST-1', 100);
INSERT INTO inventory
     VALUES('EST-2', 100);
INSERT INTO inventory
     VALUES('EST-3', 100);
INSERT INTO inventory
     VALUES('EST-4', 100);
INSERT INTO inventory
     VALUES('EST-5', 100);
INSERT INTO inventory
     VALUES('EST-6', 100);
INSERT INTO inventory
     VALUES('EST-7', 100);
INSERT INTO inventory
     VALUES('EST-8', 100);
INSERT INTO inventory
     VALUES('EST-9', 100);
INSERT INTO inventory
     VALUES('EST-10', 100);
INSERT INTO inventory
     VALUES('EST-11', 100);
INSERT INTO inventory
     VALUES('EST-12', 100);
INSERT INTO inventory
     VALUES('EST-13', 100);
INSERT INTO inventory
     VALUES('EST-14', 100);
INSERT INTO inventory
     VALUES('EST-15', 100);
INSERT INTO inventory
     VALUES('EST-16', 100);
INSERT INTO inventory
     VALUES('EST-17', 100);
INSERT INTO inventory
     VALUES('EST-18', 100);
INSERT INTO inventory
     VALUES('EST-19', 100);
INSERT INTO inventory
     VALUES('EST-20', 100);
INSERT INTO inventory
     VALUES('EST-21', 100);
INSERT INTO inventory
     VALUES('EST-22', 100);
INSERT INTO inventory
     VALUES('EST-23', 100);
INSERT INTO inventory
     VALUES('EST-24', 100);
INSERT INTO inventory
     VALUES('EST-25', 100);
INSERT INTO inventory
     VALUES('EST-26', 100);
INSERT INTO inventory
     VALUES('EST-27', 100);
INSERT INTO inventory
     VALUES('EST-28', 100);
INSERT INTO inventory
     VALUES('EST-29', 100);
INSERT INTO inventory
     VALUES('EST-30', 100);
INSERT INTO inventory
     VALUES('EST-31', 100);
INSERT INTO inventory
     VALUES('EST-32', 100);
INSERT INTO inventory
     VALUES('EST-33', 100);
INSERT INTO inventory
     VALUES('EST-34', 100);
INSERT INTO inventory
     VALUES('EST-35', 100);
INSERT INTO inventory
     VALUES('EST-36', 100);
INSERT INTO inventory
     VALUES('EST-37', 100);
INSERT INTO inventory
     VALUES('EST-38', 100);
INSERT INTO inventory
     VALUES('EST-39', 100);
INSERT INTO inventory
     VALUES('EST-40', 100);
INSERT INTO inventory
     VALUES('EST-41', 100);
INSERT INTO inventory
     VALUES('EST-42', 100);
INSERT INTO inventory
     VALUES('EST-43', 100);
INSERT INTO inventory
     VALUES('EST-44', 100);
INSERT INTO inventory
     VALUES('EST-45', 100);
INSERT INTO inventory
     VALUES('EST-46', 100);
INSERT INTO inventory
     VALUES('EST-47', 100);
INSERT INTO inventory
     VALUES('EST-48', 100);
INSERT INTO inventory
     VALUES('EST-49', 100);
INSERT INTO inventory
     VALUES('EST-50', 100);
INSERT INTO inventory
     VALUES('EST-51', 100);
INSERT INTO inventory
     VALUES('EST-52', 100);
INSERT INTO inventory
     VALUES('EST-53', 100);
INSERT INTO inventory
     VALUES('EST-54', 100);
INSERT INTO inventory
     VALUES('EST-55', 100);
INSERT INTO inventory
     VALUES('EST-56', 100);
INSERT INTO inventory
     VALUES('EST-57', 100);
INSERT INTO inventory
     VALUES('EST-58', 100);
INSERT INTO inventory
     VALUES('EST-59', 100);
INSERT INTO inventory
     VALUES('EST-60', 100);
INSERT INTO inventory
     VALUES('EST-61', 100);
INSERT INTO inventory
     VALUES('EST-62', 100);
INSERT INTO inventory
     VALUES('EST-63', 100);
INSERT INTO inventory
     VALUES('EST-64', 100);
INSERT INTO inventory
     VALUES('EST-65', 100);
INSERT INTO inventory
     VALUES('EST-66', 100);
INSERT INTO inventory
     VALUES('EST-67', 100);
INSERT INTO inventory
     VALUES('EST-68', 100);
INSERT INTO inventory
     VALUES('EST-69', 100);
INSERT INTO inventory
     VALUES('EST-70', 100);
INSERT INTO inventory
     VALUES('EST-71', 100);
INSERT INTO inventory
     VALUES('EST-72', 100);
INSERT INTO inventory
     VALUES('EST-73', 100);
INSERT INTO inventory
     VALUES('EST-74', 100);
INSERT INTO inventory
     VALUES('EST-75', 100);
INSERT INTO inventory
     VALUES('EST-76', 100);
INSERT INTO inventory
     VALUES('EST-77', 100);

CREATE TABLE STATES_FOR_COUNTRY(
  COUNTRY_CODE VARCHAR2(2),
  STATE_CODE   VARCHAR2(2),
  STATE_NAME   VARCHAR2(80),
  CONSTRAINT STATES_FOR_COUNTRY_PK PRIMARY KEY(COUNTRY_CODE, STATE_CODE)
);

INSERT INTO STATES_FOR_COUNTRY VALUES ('US','AL','Alabama');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','AK','Alaska');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','AZ','Arizona');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','AR','Arkansas');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','CA','California');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','CO','Colorado');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','CT','Connecticut');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','DC','DC');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','DE','Delaware');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','FL','Florida');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','GA','Georgia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','HI','Hawaii');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','ID','Idaho');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','IL','Illinois');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','IN','Indiana');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','IA','Iowa');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','KS','Kansas');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','KY','Kentucky');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','LA','Louisiana');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','ME','Maine');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','MD','Maryland');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','MA','Massachusetts');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','MI','Michigan');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','MN','Minnesota');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','MS','Mississippi');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','MO','Missouri');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','MT','Montana');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','NE','Nebraska');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','NV','Nevada');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','NH','New Hampshire');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','NJ','New Jersey');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','NM','New Mexico');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','NY','New York');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','NC','North Carolina');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','ND','North Dakota');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','OH','Ohio');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','OK','Oklahoma');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','OR','Oregon');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','PA','Pennsylvania');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','RI','Rhode Island');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','SC','South Carolina');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','SD','South Dakota');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','TN','Tennessee');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','TX','Texas');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','UT','Utah');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','VT','Vermont');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','VA','Virginia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','WA','Washington');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','WV','West Virginia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','WI','Wisconsin');
INSERT INTO STATES_FOR_COUNTRY VALUES ('US','WY','Wyoming');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','AG','Agrigento');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','AL','Alessandria');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','AN','Ancona');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','AO','Aosta');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','AR','Arezzo');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','AP','Ascoli Piceno');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','AT','Asti');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','AV','Avellino');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','BA','Bari');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','BL','Belluno');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','BN','Benevento');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','BG','Bergamo');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','BI','Biella');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','BO','Bologna');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','BZ','Bolzano');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','BS','Brescia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','BR','Brindisi');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','CA','Cagliari');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','CL','Caltanissetta');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','CB','Campobasso');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','CE','Caserta');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','CT','Catania');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','CZ','Catanzaro');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','CH','Chieti');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','CO','Como');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','CS','Cosenza');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','CR','Cremona');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','KR','Crotone');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','CN','Cuneo');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','EN','Enna');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','FE','Ferrara');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','FI','Firenze');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','FG','Foggia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','FC','Forli');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','FR','Frosinone');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','GE','Genova');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','GO','Gorizia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','GR','Grosseto');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','IM','Imperia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','IS','Isernia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','AQ','L''Aquila');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','SP','La Spezia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','LT','Latina');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','LE','Lecce');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','LC','Lecco');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','LI','Livorno');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','LO','Lodi');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','LU','Lucca');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','MC','Macerata');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','MN','Mantova');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','MS','Massa Carrara');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','MT','Matera');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','ME','Messina');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','MI','Milano');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','MO','Modena');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','NA','Napoli');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','NO','Novara');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','NU','Nuoro');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','OR','Oristano');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','PD','Padova');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','PA','Palermo');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','PR','Parma');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','PV','Pavia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','PG','Perugia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','PU','Pesaro-Urbino');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','PE','Pescara');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','PC','Piacenza');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','PI','Pisa');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','PT','Pistoia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','PN','Pordenone');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','PZ','Potenza');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','PO','Prato');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','RG','Ragusa');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','RA','Ravenna');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','RC','Reggio Calabria');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','RE','Reggio Emilia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','RI','Rieti');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','RN','Rimini');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','RM','Roma');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','RO','Rovigo');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','SA','Salerno');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','SS','Sassari');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','SV','Savona');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','SI','Siena');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','SR','Siracusa');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','SO','Sondrio');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','TA','Taranto');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','TE','Teramo');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','TR','Terni');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','TO','Torino');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','TP','Trapani');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','TN','Trento');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','TV','Treviso');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','TS','Trieste');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','UD','Udine');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','VA','Varese');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','VE','Venezia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','VB','Verbania');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','VC','Vercelli');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','VR','Verona');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','VV','Vibo Valentia');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','VI','Vicenza');
INSERT INTO STATES_FOR_COUNTRY VALUES ('IT','VT','Viterbo');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','BW','Baden-Württemberg');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','BY','Freistaat Bayern');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','BE','Berlin');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','BB','Brandenburg');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','HB','Freie Hansestadt Bremen');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','HH','Freie und Hansestadt Hamburg');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','HE','Hessen');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','MV','Mecklenburg-Vorpommern');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','NI','Niedersachsen');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','NW','Nordrhein-Westfalen');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','RP','Rheinland-Pfalz');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','SL','Saarland');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','SN','Freistaat Sachsen');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','ST','Sachsen-Anhalt');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','SH','Schleswig-Holstein');
INSERT INTO STATES_FOR_COUNTRY VALUES ('DE','TH','Freistaat Thüringen');
COMMIT;

CREATE INDEX IDX_ITEM_PRODID ON ITEM(PRODUCTID);

CREATE INDEX IDX_PROD_CATEGORY ON PRODUCT(CATEGORY);

CREATE OR REPLACE FUNCTION validate_state_for_country(country VARCHAR2,
                                                      state   VARCHAR2)
RETURN VARCHAR2 IS
  tmp VARCHAR2(1);
  found BOOLEAN;
  CURSOR check_country(cv_country VARCHAR2,cv_state VARCHAR2) IS
  SELECT null
    FROM states_for_country
   WHERE country_code = UPPER(cv_country)
     AND state_code LIKE NVL(UPPER(cv_state),'%');
BEGIN
  /*
   * If the country in question appears in at least one row of our table,
   * then that is a signal that we want to validate the states in that
   * country. If no rows appear in the STATES_FOR_COUNTRY with this country
   * code, then that is a signal that we don't want to validate the states
   * for that country, and we skip the subsequent state validation.
   */
  OPEN check_country(country,null);
  FETCH check_country INTO tmp;
  found := check_country%FOUND;
  CLOSE check_country;
  IF (found) THEN
    OPEN check_country(country,state);
    FETCH check_country INTO tmp;
    found := check_country%FOUND;
    CLOSE check_country;
    IF (found) THEN
      RETURN 'Y';
    ELSE
      RETURN 'N';
    END IF;
  ELSE
    RETURN NULL;
  END IF;
END;
.
/
show errors
purge recyclebin;

spool off
