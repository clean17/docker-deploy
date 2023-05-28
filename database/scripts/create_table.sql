CREATE TABLE todo (
id int auto_increment primary key,
userId int not null,
title varchar(30) not null,
checked bool not null
);