package ru.lesson.springBootProject.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Arrays;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_pkey_seq")
    @Column(name = "comment_id")
    private Long commentId;

    @Length(max=2048,  message = "Too long text of comment (more than 2kB)")
    @Column(length = 2048)
    private String text;

    @Min(value = 1, message = "Value of stars from 1 to 5")
    @Max(value = 5, message = "Value of stars from 1 to 5")
    @NotNull(message = "Stars cannot be null")
    @Column(name="stars", nullable = false)
    private Byte stars;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 10)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "filename")
    private String filename;

}
