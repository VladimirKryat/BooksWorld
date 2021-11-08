<#import "parts/header.ftl" as p>
<@p.page>
    <div class="card" style="width: 18rem;">
        <div class="card-body">
            <h5 class="card-title">
                ${author.name}
            </h5>
            <h6 class="card-subtitle mb-2 text-muted">
                (${author.birthday} - ${author.dateOfDeath!"..."})
            </h6>
            <p class="card-text">
                ${author.biography!""}
            </p>
            <#if !isSubscription>
                <a href="/subscribe/${author.authorId}" class="btn btn-outline-success">Subscribe</a>
            <#else >
                <a href="/unsubscribe?author=${author.authorId}" class="btn btn-outline-danger">Unsubscribe</a>
            </#if>
        </div>
    </div>
</@p.page>