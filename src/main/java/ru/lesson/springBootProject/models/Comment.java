package ru.lesson.springBootProject.models;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_generator")
    @SequenceGenerator(name = "comment_id_generator",sequenceName = "comment_id_pkey_seq", allocationSize = 1, initialValue = 10)
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

    @Override
    public int hashCode() {
        int hash = (int)stars;
        hash = hash*31 + (text==null? 0 : text.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        boolean isEqualsByTextAndStars = this.text.equals(comment.text) && this.stars.equals(comment.stars);
        //если id у обоих объектов заполнены и валидны(>0), то сравниваем и их тоже
        if ((this.commentId!=null && this.commentId>0 )&& (comment.commentId!=null && comment.commentId>0))
            return isEqualsByTextAndStars && this.commentId.equals(comment.commentId);
        else return isEqualsByTextAndStars;
    }

    @Override
    public String toString() {
        return this.getClass().getName()+"{" +
                "commentId=" + commentId +
                ", text='" + text + '\'' +
                ", stars=" + stars +
                ", filename='" + filename + '\'' +
                '}';
    }
    //используется Freemarker'ом для отображения оценки
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
