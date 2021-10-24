<#import "parts/header.ftl" as p>
<#import "parts/loginForm.ftl" as l>
<@p.page>
    ${message?ifExists}
    <@l.login false/>
</@p.page>