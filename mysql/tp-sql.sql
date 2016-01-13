DROP DATABASE IF EXISTS tme_ntw_dirty;
CREATE DATABASE IF NOT EXISTS tme_ntw_dirty;

USE tme_ntw_dirty;

CREATE TABLE history (
	id bigint primary key auto_increment,
	log TEXT,
	published datetime 
)ENGINE=MyISAM;

CREATE TABLE users (
	id int primary key auto_increment,
	mail varchar(70),
	password varchar(50)
)ENGINE=MyISAM;

CREATE TABLE broker (
	id int primary key auto_increment,
	name varchar(50),
	bank varchar(30)
)ENGINE=MyISAM;


CREATE TABLE depository (
	id tinyint primary key auto_increment,
	name varchar(30),
	bic varchar(11)
)ENGINE=MyISAM;


CREATE TABLE countries (
	id int primary key auto_increment,
	name varchar(255)
)ENGINE=MyISAM;

CREATE TABLE transactions (
	id bigint primary key auto_increment,
	id_user int,
	id_broker int,
	price double,
	realised datetime
)ENGINE=MyISAM;
