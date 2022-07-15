drop table users if exists;
create table users(
    username varchar(256) not null primary key,
    password varchar(256) not null
);