<#import "parts/header.ftl" as p>
<#import "parts/authorForm.ftl" as a>
<#import "parts/bookForm.ftl" as b>
<#import "parts/commentForm.ftl" as c>
<@p.page>
    <div class="d-flex justify-content-center">
        <div class="card w-75">
            <@a.cardBody author/>
        </div>
    </div>
    <@b.listBookCard false/>

    <div class="card-footer text-center mt-2">
        <@c.addCommentForm "/commentauthor" author.authorId/>
        <@c.listCommentCard commentsPage/>
    </div>

</@p.page>