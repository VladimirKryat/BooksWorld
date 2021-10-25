-- устанавливаем расширение для postgres
CREATE EXTENSION IF NOT EXISTS pgcrypto;
UPDATE userdata
SET password=crypt(password,gen_salt('bf',8));