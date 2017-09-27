drop table email_message cascade constraints;
drop sequence email_message_seq;
drop table email_message_recipients cascade constraints;
drop sequence email_message_recipients_seq;

create sequence email_message_recipients_seq;
create sequence email_message_seq;

create table email_message(
  id number not null,
  sender_email varchar2(40),
  subject varchar2(80),
  date_created date,
  message_text clob,
  constraint email_message_pk primary key (id)
);
create table email_message_recipients(
  id number not null,
  message_id number not null,
  recipient_email_address varchar2(40),
  recipient_type varchar2(1),
  constraint email_recipient_pk primary key (id),
constraint email_message_fk foreign key (message_id) references email_message
);
purge recyclebin
/

