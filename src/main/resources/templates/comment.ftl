<#import "parts/header.ftl" as p>
<#import "parts/loginForm.ftl" as l>
<#import "parts/commentForm.ftl" as c>
<@p.page>

    Add new comment
    <@c.comment "/comment"/>

    <div>

        <table>
            <tr>
                <th>CommentID</th>
                <th>Text</th>
                <th>Stars</th>
                <th>Author</th>
                <th>AuthorID</th>
            </tr>

            <#list comments as comment>
                <tr>
                    <td>${comment.commentId}</td>
                    <td>${comment.text}</td>
                    <td>${comment.stars}</td>
                    <#if comment.author??>
                        <td>${comment.author.username}</td>
                        <td>${comment.author.userId}</td>
                    <#else>
                        <td> &lt;none&gt; </td>
                        <td> &lt;none&gt; </td>
                    </#if>
                </tr>
            </#list>
        </table>
    </div>
    <@l.logout/>
</@p.page>