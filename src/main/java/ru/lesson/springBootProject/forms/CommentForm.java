package ru.lesson.springBootProject.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.lesson.springBootProject.models.Comment;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentForm {
    private Long commentId;
    private String text;
    private Byte stars;
    private UserForm author;
    public static CommentForm from(Comment comment){
        CommentForm commentForm = CommentForm.builder()
                .commentId(comment.getCommentId())
                .text(comment.getText())
                .stars(comment.getStars())
                .build();
        if (comment.getAuthor()!=null){
            commentForm.setAuthor(UserForm.from(comment.getAuthor()));
        }
        return commentForm;
    }
}
