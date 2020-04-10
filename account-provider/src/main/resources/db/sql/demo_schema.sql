CREATE TABLE account_department ( id INT(11) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT ,
parent_id INT(11) unsigned DEFAULT NULL ,
code VARCHAR(16) NOT NULL ,
name VARCHAR(64) NOT NULL ,
comment VARCHAR(128) DEFAULT NULL,
LEVEL tinyint(1) NOT NULL DEFAULT '0' ,
is_leaf tinyint(1) NOT NULL DEFAULT '0' ,
PATH VARCHAR(128) DEFAULT NULL ,
status tinyint(1) NOT NULL DEFAULT '0' ,
group_id INT(11) NOT NULL ,
creator CHAR(32) DEFAULT NULL ,
create_time datetime DEFAULT CURRENT_TIMESTAMP ,
modifier CHAR(32) DEFAULT NULL ,
modify_time datetime DEFAULT NULL );

CREATE INDEX account_department_code ON
account_department(code);

CREATE INDEX account_department_parent_id ON
account_department(parent_id);

CREATE TABLE account_department_role ( id INT(11) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT ,
department_id INT(11) unsigned NOT NULL ,
role_id INT(11) NOT NULL ,
creator CHAR(32) NOT NULL ,
create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ,
modifier CHAR(32) DEFAULT NULL ,
modify_time datetime DEFAULT NULL );

CREATE INDEX account_department_role_department_id ON
account_department_role(department_id);

CREATE INDEX account_department_role_role_id ON
account_department_role(role_id);

CREATE TABLE account_group ( id INT(11) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT ,
name VARCHAR(64) NOT NULL ,
comment VARCHAR(256) DEFAULT NULL,
status tinyint(3) NOT NULL DEFAULT '1' ,
creator CHAR(32) NOT NULL ,
create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ,
modifier CHAR(32) DEFAULT NULL ,
modify_time datetime DEFAULT NULL );

CREATE INDEX account_group_status ON
account_group(status);

CREATE TABLE account_group_role ( id INT(11) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT ,
group_id INT(11) unsigned NOT NULL ,
role_id INT(11) NOT NULL ,
creator CHAR(32) NOT NULL ,
create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ,
modifier CHAR(32) DEFAULT NULL ,
modify_time datetime DEFAULT NULL );

CREATE INDEX account_group_role_group_id ON
account_group_role(group_id);

CREATE INDEX account_group_role_role_id ON
account_group_role(role_id);

CREATE TABLE account_group_user ( id INT(11) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT ,
group_id INT(11) unsigned NOT NULL ,
user_code CHAR(32) NOT NULL ,
creator CHAR(32) NOT NULL ,
create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ,
modifier CHAR(32) DEFAULT NULL ,
modify_time datetime DEFAULT NULL );

CREATE INDEX account_group_user_group_id ON
account_group_user(group_id);

CREATE INDEX account_group_user_user_code ON
account_group_user(user_code);

CREATE TABLE account_login_info ( id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT ,
ip VARCHAR(128) NOT NULL ,
login_type tinyint(3) unsigned NOT NULL ,
device_type tinyint(3) unsigned DEFAULT NULL ,
USER CHAR(32) NOT NULL ,
create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP );

CREATE INDEX account_login_info_user ON
account_login_info(USER);

CREATE TABLE account_oauth ( id INT(11) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT ,
client_name CHAR(32) NOT NULL ,
client_secret CHAR(64) NOT NULL ,
scopes VARCHAR(255) NOT NULL ,
resource_ids VARCHAR(255) DEFAULT NULL ,
authorized_grant_types VARCHAR(255) NOT NULL ,
registered_redirect_uris VARCHAR(255) NOT NULL ,
auto_approve_scopes VARCHAR(255) DEFAULT NULL ,
access_token_validity_seconds mediumint(8) unsigned NOT NULL ,
refresh_token_validity_seconds mediumint(8) unsigned NOT NULL ,
additional_informations VARCHAR(255) DEFAULT NULL ,
creator CHAR(32) NOT NULL ,
create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP );

CREATE INDEX account_oauth_client_name ON
account_oauth(client_name);

CREATE TABLE account_oauth_client ( id INT(11) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT ,
client_name CHAR(32) NOT NULL ,
client_secret CHAR(64) NOT NULL ,
access_token_validity_seconds mediumint(8) unsigned NOT NULL ,
refresh_token_validity_seconds mediumint(9) unsigned NOT NULL ,
creator CHAR(32) NOT NULL ,
create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP );

CREATE INDEX account_oauth_client_client_name ON
account_oauth_client(client_name);

CREATE TABLE account_oauth_client_grant_type ( id INT(11) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT ,
client_id INT(11) unsigned NOT NULL ,
grant_type tinyint(11) unsigned NOT NULL ,
creator CHAR(64) NOT NULL ,
create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP );

CREATE INDEX account_oauth_client_grant_type_client_id_grant_type_id ON
account_oauth_client_grant_type(client_id,
grant_type);

CREATE TABLE account_oauth_client_redirect_url ( id INT(11) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT ,
client_id INT(11) unsigned NOT NULL ,
redirect_url VARCHAR(64) NOT NULL ,
creator CHAR(64) NOT NULL ,
create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP );

CREATE INDEX account_oauth_client_redirect_url_client_id_redirect_url ON
account_oauth_client_redirect_url(client_id,
redirect_url);

CREATE TABLE account_oauth_client_scope ( id INT(11) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT ,
client_id INT(11) unsigned NOT NULL ,
SCOPE CHAR(32) NOT NULL ,
creator CHAR(64) NOT NULL ,
create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP );

CREATE INDEX account_oauth_client_scope_client_id_scope ON
account_oauth_client_scope(client_id,
SCOPE);

CREATE TABLE account_role ( id INT(11) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT ,
code VARCHAR(16) NOT NULL ,
name VARCHAR(64) NOT NULL ,
comment VARCHAR(128) DEFAULT NULL,
creator CHAR(32) NOT NULL ,
create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ,
modifier CHAR(32) DEFAULT NULL ,
modify_time datetime DEFAULT NULL ,
status tinyint(3) unsigned NOT NULL DEFAULT '1' );

CREATE INDEX account_role_code ON
account_role(code);

CREATE TABLE account_user ( id INT(11) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT ,
code CHAR(32) NOT NULL ,
account VARCHAR(30) DEFAULT NULL ,
name VARCHAR(20) DEFAULT NULL ,
password CHAR(64) DEFAULT NULL ,
gender tinyint(3) unsigned NOT NULL DEFAULT '0' ,
phone VARCHAR(20) DEFAULT NULL ,
email VARCHAR(50) DEFAULT NULL ,
address VARCHAR(255) DEFAULT NULL ,
birthday datetime DEFAULT NULL ,
hire_date datetime DEFAULT NULL ,
profile_photo VARCHAR(255) DEFAULT NULL ,
description VARCHAR(255) DEFAULT NULL ,
verified tinyint(1) NOT NULL DEFAULT '0' ,
province mediumint(7) unsigned DEFAULT NULL ,
city mediumint(7) unsigned DEFAULT NULL ,
status tinyint(3) unsigned NOT NULL DEFAULT '0' ,
creator CHAR(32) DEFAULT NULL ,
create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ,
modifier CHAR(32) DEFAULT NULL ,
modify_time datetime DEFAULT NULL ,
deleted tinyint(3) unsigned NOT NULL DEFAULT '0' );

CREATE INDEX account_user_code ON
account_user(code);

CREATE INDEX account_user_phone ON
account_user(phone);

CREATE INDEX account_user_account ON
account_user(account);

CREATE INDEX account_user_email ON
account_user(email);

CREATE TABLE account_user_department ( id INT(11) unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT ,
user_code CHAR(32) NOT NULL ,
department_id INT(11) NOT NULL ,
creator CHAR(32) NOT NULL ,
create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ,
modifier CHAR(32) DEFAULT NULL ,
modify_time datetime DEFAULT NULL );

CREATE INDEX account_user_department_user_code ON
account_user_department(user_code);

CREATE INDEX account_user_department_department_id ON
account_user_department(department_id);