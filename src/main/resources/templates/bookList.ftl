<#import "parts/header.ftl" as p>
<#import "parts/bookForm.ftl" as b>
<@p.page>
    <#if message??>
        <h3>${message}</h3>
    </#if>
    <@b.listBookCard true/>
</@p.page>