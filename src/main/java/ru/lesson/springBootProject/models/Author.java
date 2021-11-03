package ru.lesson.springBootProject.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints =@UniqueConstraint(name = "author_unique", columnNames = {"name","birthday"}))
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long authorId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;
    @Column(name = "date_of_death")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfDeath;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "authors",
            fetch = FetchType.LAZY,targetEntity = Book.class,
            cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<Book> books=new HashSet<>();
}
