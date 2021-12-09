DROP TABLE IF EXISTS comment;
DROP SEQUENCE IF EXISTS comment_comment_id_seq;
CREATE SEQUENCE IF NOT EXISTS comment_id_pkey_seq
    AS int8
    START WITH 10
    INCREMENT BY 1
    CACHE 5;
create table  IF NOT EXISTS  comment_book (
                         comment_id int8 NOT NULL DEFAULT NEXTVAL('comment_id_pkey_seq'),
                         filename varchar(255),
                         stars int2 not null,
                         text varchar(2048),
                         user_id int8 not null references userdata(user_id),
                         book_id int8 not null references book(book_id),
                         primary key (comment_id)
);
create table  IF NOT EXISTS  comment_author (
                              comment_id int8 NOT NULL DEFAULT NEXTVAL('comment_id_pkey_seq'),
                              filename varchar(255),
                              stars int2 not null,
                              text varchar(2048),
                              user_id int8 not null references userdata(user_id),
                              author_id int8 not null references author(author_id),
                              primary key (comment_id)
);
create table  IF NOT EXISTS  comment (
                         comment_id int8 NOT NULL DEFAULT NEXTVAL('comment_id_pkey_seq'),
                         filename varchar(255),
                         stars int2 not null,
                         text varchar(2048),
                         user_id int8 not null references userdata(user_id),
                         primary key (comment_id)
);