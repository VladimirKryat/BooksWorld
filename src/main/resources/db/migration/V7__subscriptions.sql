create table user_subscription_author(
    user_id int8 not null references userdata,
    author_id int8 not null references author,
    PRIMARY KEY (user_id,author_id)
);

ALTER TABLE author
ADD COLUMN biography varchar(3072);