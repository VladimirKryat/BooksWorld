package ru.lesson.springBootProject.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;
    @Column(nullable = false)
    private String title;
    @ManyToMany(fetch = FetchType.EAGER,
            targetEntity = Author.class)
    @JoinTable(name="author_book",
            joinColumns = @JoinColumn(name = "book_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="author_id", nullable = false))
    private Set<Author> authors=new HashSet<>();

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", authors= " + authors.stream().map(x->x.getName()).collect(Collectors.joining(", ")) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return title.equals(book.title) && authors.equals(book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, authors);
    }
}
