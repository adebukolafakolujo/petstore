
drop user if exists 'petuser'@'localhost';
create user 'petuser'@'localhost' identified by 'petuser123';
grant all privileges on petstoredb.* to 'petuser'@'localhost';
flush priviledges;

drop database if exists petstoredb;
create database petstoredb;