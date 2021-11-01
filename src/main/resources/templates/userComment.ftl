<#import "parts/header.ftl" as p>
<#import "parts/commentForm.ftl" as c>
<@p.page>
        <@c.addCommentForm "/userComment/${userId}/${commentId}"/>
</@p.page>