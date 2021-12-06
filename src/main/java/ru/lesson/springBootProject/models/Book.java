package ru.lesson.springBootProject.models;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Title can't be empty")
    @Length(max=255, message = "Too long title")
    @Column(nullable = false)
    private String title;
    @Length(max = 2048, message = "Too long description(more than 2kB)")
    @Column(length = 2048)
    private String description;
    @NotEmpty(message = "Author can't be empty")
    @ManyToMany(fetch = FetchType.EAGER,
            targetEntity = Author.class)
    @JoinTable(name="author_book",
            joinColumns = @JoinColumn(name = "book_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="author_id", nullable = false))
    private Set<Author> authors=new HashSet<>();

    private String filename;

    @ManyToMany (fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinTable(name = "user_like_book",
            joinColumns = @JoinColumn(name="book_id"),
            inverseJoinColumns = @JoinColumn(name="user_id"))
    private Set<User> likes = new HashSet<>();


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Genre> genres = new HashSet<>();

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
