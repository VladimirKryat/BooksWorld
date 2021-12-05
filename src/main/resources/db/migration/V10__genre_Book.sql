
CREATE TABLE genre_book(
    book_id int8 references book,
    genre varchar(30) not null,
    UNIQUE (book_id,genre)
)