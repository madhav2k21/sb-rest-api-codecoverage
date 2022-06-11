CREATE TABLE users (
	id INT(6) NOT NULL AUTO_INCREMENT,
	name VARCHAR(40) NOT NULL,
	location VARCHAR(30) NOT NULL,
	created_date_time datetime(6) DEFAULT NULL,
    updated_date_time datetime(6) DEFAULT NULL,
	PRIMARY KEY (id)
   );

