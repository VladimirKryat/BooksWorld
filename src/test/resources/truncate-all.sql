truncate table userdata, user_role, book,
    author, author_book, user_subscription_author,
    activation, comment, persistent_logins,
    spring_session, spring_session_attributes RESTART IDENTITY CASCADE;
