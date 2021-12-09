package ru.lesson.springBootProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.lesson.springBootProject.models.Author;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorInfoDto {

    private Long authorId;
    private String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfDeath;
    private String biography;
    private boolean isSubscription=false;
    private int countBook=-1;
    private int countSubscribers=-1;


    public AuthorInfoDto(Author author, boolean isSubscription, int countBook, int countSubscribers){
        this.authorId=author.getAuthorId();
        this.name=author.getName();
        this.birthday=author.getBirthday();
        this.dateOfDeath=author.getDateOfDeath();
        this.biography=author.getBiography();
        this.isSubscription=isSubscription;
        this.countBook = countBook;
        this.countSubscribers=countSubscribers;
    }
    @Override
    public String toString() {
        return "AuthorInfoDto{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorInfoDto that = (AuthorInfoDto) o;
        return name.equals(that.name) && birthday.equals(that.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday);
    }
}
