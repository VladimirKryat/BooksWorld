<#import "parts/header.ftl" as p>
<@p.page>

    <#if message??>
        <h3>${message}</h3>
    </#if>
    <#if subscriptionsAuthors?size gt 0>
        <div class="card-columns">
            <#list subscriptionsAuthors as author>
                <div class="card" style="width: 18rem;">
                    <div class="card-body">
                        <h5 class="card-title">${author.name}</h5>
                        <h6 class="card-subtitle mb-2 text-muted">(${author.birthday} - ${author.dateOfDeath!"..."})</h6>
                        <p class="card-text">${author.biography?if_exists}</p>
                        <a href="/unsubscribe?author=${author.authorId}"  class="btn btn-outline-danger">Unsubscribe</a>
                    </div>
                </div>
            </#list>
        </div>
    <#else>
        You haven't got subscriptions.
    </#if>
</@p.page>