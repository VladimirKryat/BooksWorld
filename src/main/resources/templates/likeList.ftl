<#import "parts/header.ftl" as p>
<#import "parts/bookForm.ftl" as b>
<#import "parts/pager.ftl" as pager>

<@p.page>
        <#if message??>
                <h3>${message}</h3>
        </#if>
        <@b.listBookCard books />
        <@pager.pager url books "books"/>
</@p.page>