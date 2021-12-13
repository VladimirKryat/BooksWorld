<#import "parts/header.ftl" as p>
<#import "parts/bookForm.ftl" as b>
<#import "parts/commentForm.ftl" as c>
<#import "parts/pager.ftl" as pager>

<@p.page>
    <div class="d-flex justify-content-center">
        <div class="card mb-3" style="max-width: 700px;">
            <div class="row no-gutters">
                <div class="col-md-4 mt-5">
                    <#if book.filename??>
                        <img class="card-img-top" src="/img/book/${book.filename}" alt=""/>
                    </#if>
                </div>
                <div class="col-md-8">
                    <@b.cardBookDto book true/>
                </div>
            </div>
        </div>
    </div>

    <div class="card-footer text-center mt-2">
        <@c.addCommentForm "/commentbook" book.bookId/>
        <@c.listCommentCard bookComments/>
    </div>

</@p.page>