<#import "parts/header.ftl" as p>
<#import "parts/commentForm.ftl" as c>
<@p.page>

        <@c.addCommentForm "/comment"/>

<#--'★'-->
<#--    отображаем карты комментариев-->
        <@c.listCommentCard/>
</@p.page>