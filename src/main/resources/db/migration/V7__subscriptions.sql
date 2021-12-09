create table IF NOT EXISTS user_subscription_author(
    user_id int8 not null references userdata,
    author_id int8 not null references author,
    PRIMARY KEY (user_id,author_id)
);

ALTER TABLE IF EXISTS author
ADD COLUMN biography varchar(3072);