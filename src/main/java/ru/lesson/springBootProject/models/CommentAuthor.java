package ru.lesson.springBootProject.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment_author")
public class CommentAuthor extends Comment implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Author.class)
    @JoinColumn(name = "author_id")
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public CommentAuthor(Comment c, Author author){
        super(c.getCommentId(),c.getText(),c.getStars(),c.getUser(),c.getFilename());
        setAuthor(author);
    }

}
