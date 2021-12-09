truncate table userdata, user_role, book,
    author, author_book, user_subscription_author,
    activation, comment, persistent_logins,
    spring_session,comment_author, comment_book, spring_session_attributes RESTART IDENTITY CASCADE;
