DROP DATABASE IF EXISTS CHATAPP;
CREATE DATABASE CHATAPP;
USE CHATAPP
SHOW TABLES;

INSERT INTO USER (`user_name`,`email`,`password`) VALUES ('abc','abc@gmail.com','abc');
INSERT INTO USER (`user_name`,`email`,`password`) VALUES ('xyz','xyz@gmail.com','xyz');

INSERT INTO MESSAGES (message_text,user_from,user_to,message_date) VALUES ("Hi",1,2,NOW());
INSERT INTO MESSAGES (message_text,user_from,user_to,message_date) VALUES ("Hello",2,1,NOW());
INSERT INTO MESSAGES (message_text,user_from,user_to,message_date) VALUES ("How r u?",1,2,NOW());
INSERT INTO MESSAGES (message_text,user_from,user_to,message_date) VALUES ("Fine. U?",2,1,NOW());
INSERT INTO MESSAGES (message_text,user_from,user_to,message_date) VALUES ("Never Better",1,2,NOW());
INSERT INTO MESSAGES (message_text,user_from,user_to,message_date) VALUES ("Gr8",2,1,NOW());

SHOW TABLES;

SELECT * FROM USER;
SELECT * FROM MESSAGES;

DESC USER;
DESC MESSAGES;

DROP TABLE MESSAGES;
DROP TABLE USER;
