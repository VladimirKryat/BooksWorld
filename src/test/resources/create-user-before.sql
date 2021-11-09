insert into userdata(email,password,state,username)
values
    ('vladimirkryat@gmail.com','$2a$08$mRVex1dWVxDZ0QwM3/ntIuQpOD435A/hK7i8J9djEZjlVdU8nITKe','ACTIVE','VladimirKryat'),
    ('xocom83035@dukeoo.com','$2a$08$T8otfJhwI8B20qYGVHjbPOWXdN0Gq97hJYBE.Zmysvf508t7r27TS','ACTIVE','Vladimir');


insert into user_role(user_id, roles)
SELECT user_id, roles  from (
select user_id, 'USER' as "roles" from userdata where username='Vladimir'
union
select user_id, 'USER' from userdata where username='VladimirKryat'
union
select user_id, 'ADMIN' from userdata where username='VladimirKryat'
union
select user_id, 'MANAGER' from userdata where username='VladimirKryat') as ins;

