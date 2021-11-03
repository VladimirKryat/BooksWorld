INSERT INTO userdata(username,email, password, state)
VALUES ('VladimirKryat','vladimirkryat@gmail.com','1997','ACTIVE');

INSERT INTO user_role (user_id,roles)
VALUES ((SELECT user_id FROM userdata WHERE username='VladimirKryat'),'USER');

INSERT INTO user_role (user_id,roles)
VALUES ((SELECT user_id FROM userdata WHERE username='VladimirKryat'),'ADMIN');

INSERT INTO user_role (user_id,roles)
VALUES ((SELECT user_id FROM userdata WHERE username='VladimirKryat'),'MANAGER');
