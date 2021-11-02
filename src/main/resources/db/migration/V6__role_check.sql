ALTER TABLE if exists user_role
    DROP CONSTRAINT IF EXISTS role_value;
ALTER TABLE if exists user_role
    ADD CONSTRAINT role_value
        CHECK ( roles in ('ADMIN','USER','MANAGER') );
