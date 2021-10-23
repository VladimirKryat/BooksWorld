package ru.lesson.springBootProject.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lesson.springBootProject.models.Comment;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentForm {
    private Long commentId;
    private String text;
    private Byte stars;
    private UserForm author;
    private String filename;
    public static CommentForm from(Comment comment){
        CommentForm commentForm = CommentForm.builder()
                .commentId(comment.getCommentId())
                .text(comment.getText())
                .stars(comment.getStars())
                .build();
        if (comment.getAuthor()!=null){
            commentForm.setAuthor(UserForm.from(comment.getAuthor()));
        }
        if (comment.getFilename()!=null){
            commentForm.setFilename(comment.getFilename());
        }
        return commentForm;
    }

    public String starsByShape(){
        char[] chars = new char[this.stars];
        Arrays.fill(chars,'\u2605');
        System.out.println(new String(chars));
        return new String(chars);
    }
}
