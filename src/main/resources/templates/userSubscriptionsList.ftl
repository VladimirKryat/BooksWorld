<#import "parts/header.ftl" as p>
<#import "parts/authorForm.ftl" as a>
<@p.page>

    <#if message??>
        <h3>${message}</h3>
    </#if>
    <#if subscriptionsAuthors?size gt 0>
        <div class="card-columns">
            <#list subscriptionsAuthors as author>
                <div class="card" style="width: 18rem;">
                    <@a.cardBody author/>
                </div>
            </#list>
        </div>
    <#else>
        You haven't got subscriptions.
    </#if>
</@p.page>