package ru.lesson.springBootProject.models;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(uniqueConstraints =@UniqueConstraint(name = "author_unique", columnNames = {"name","birthday"}))
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long authorId;

    @NotEmpty(message = "Name can't be empty")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Birthday can't be empty")
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    @Column(name = "date_of_death")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfDeath;

    @Length(max = 3072, message = "Too long biography (more than 3kB)")
    private String biography;


    @ManyToMany(mappedBy = "authors",
            fetch = FetchType.LAZY,targetEntity = Book.class,
            cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @Fetch(FetchMode.SUBSELECT)
    private Set<Book> books = new HashSet<>();

    @ManyToMany(mappedBy = "subscriptions",targetEntity = User.class, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    private Set<User> subscriptions=new HashSet<>();

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, targetEntity = CommentAuthor.class)
    @Fetch(FetchMode.SUBSELECT)
    private Set<CommentAuthor> comments = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return name.equals(author.name) && birthday.equals(author.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday);
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
