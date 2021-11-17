CREATE TABLE user_like_book(
    book_id int8 references book(book_id),
    user_id int8 references userdata(user_id),
    primary key (book_id,user_id)
);