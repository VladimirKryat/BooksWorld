package ru.lesson.springBootProject.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment_book")
public class CommentBook extends Comment{

    @ManyToOne(fetch = FetchType.LAZY,targetEntity = Book.class)
    @JoinColumn(name = "book_id")
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
