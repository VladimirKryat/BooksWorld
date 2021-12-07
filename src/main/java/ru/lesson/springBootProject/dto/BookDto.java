package ru.lesson.springBootProject.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.lesson.springBootProject.models.Author;
import ru.lesson.springBootProject.models.Book;
import ru.lesson.springBootProject.models.GenreBook;
import ru.lesson.springBootProject.models.GenreName;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class BookDto{
    private Long bookId;
    private String title;
    private String description;
    private Set<Author> authors=new HashSet<>();
    private String filename;
    private Long likes;
    private Boolean meIsLiked;
    private Set<GenreName> genres =new HashSet<>();

    public BookDto(){}
    public BookDto(Book book, Long likes, Boolean meIsLiked){
        this.bookId=book.getBookId();
        this.authors=book.getAuthors();
        this.filename=book.getFilename();
        this.description= book.getDescription();
        this.title=book.getTitle();
        this.likes=likes;
        this.meIsLiked=meIsLiked;
        this.genres =book.getGenres().stream().map(GenreBook::getGenre).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", authors=" + authors.stream().map((x)->x.getName()).collect(Collectors.joining(", ")) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(bookId, bookDto.bookId) && title.equals(bookDto.title) && Objects.equals(description, bookDto.description) && authors.equals(bookDto.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, title, description, authors);
    }
}
