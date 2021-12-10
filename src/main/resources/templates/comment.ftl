<#import "parts/header.ftl" as p>
<#import "parts/commentForm.ftl" as c>
<@p.page>

        <#if comment.getAuthor??>
                <@c.addCommentForm "/commentauthor" comment.getAuthor().getAuthorId()/>
        <#elseif comment.getBook??>
                <@c.addCommentForm "/commentbook" comment.getBook().getBookId()/>
        <#else>
                <@c.addCommentForm '/commentedit' (-5)/>
        </#if>

</@p.page>