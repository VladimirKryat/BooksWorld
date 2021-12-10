package ru.lesson.springBootProject.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment_book")
public class CommentBook extends Comment implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Book.class)
    @JoinColumn(name = "book_id")
    private Book book;

    public CommentBook(Comment c, Book book) {
        super(c.getCommentId(),c.getText(),c.getStars(),c.getUser(),c.getFilename());
        setBook(book);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
