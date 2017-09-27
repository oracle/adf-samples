create type scored_key as object (score number, key number);
.
/
create type scored_keys as table of scored_key;
.
/
