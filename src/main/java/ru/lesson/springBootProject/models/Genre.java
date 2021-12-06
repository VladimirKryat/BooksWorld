package ru.lesson.springBootProject.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "genre_book")
@Builder
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_book_id")
    private Long genreBookId;
    @Enumerated(EnumType.STRING)
    private GenreName genre;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre1 = (Genre) o;
        return genre == genre1.genre && book.equals(genre1.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genre, book);
    }

    @Override
    public String toString() {
        return book.getTitle()+" "+genre.toString();
    }
}
