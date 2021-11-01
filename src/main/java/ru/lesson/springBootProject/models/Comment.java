package ru.lesson.springBootProject.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(schema = "public", name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "filename")
    private String filename;

    public String starsByShape(){
        if(stars!=null){
            char[] chars = new char[this.stars];
            Arrays.fill(chars,'\u2605');
            return new String(chars);
        }
        else {
            return "";
        }
    }
}
