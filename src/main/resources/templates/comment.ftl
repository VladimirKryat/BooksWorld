<#import "parts/header.ftl" as p>
<#import "parts/commentForm.ftl" as c>
<@p.page>

    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add comment
    </a>
    <div class="collapse" id="collapseExample">
        <@c.comment "/comment"/>
    </div>

<#--'★'-->
    <div class="card-columns">
        <#assign col=0/>
            <#list comments as comment>
                <div class="card my-3" style="width: 18rem;">
                    <#if comment.filename??>
                        <img class="card-img-top" src="/img/${comment.filename}">
                    </#if>
                    <div class="card-body">
                        <h5 class="card-title">
                                ${(comment.author.username)!'Guest'}
                        </h5>
                        <h6 class="card-subtitle mb-2 text-muted">
                            ${comment.starsByShape()}
                        </h6>
                        <p class="card-text">${comment.text}</p>
                    </div>
                </div>
            </#list>
    </div>
</@p.page>