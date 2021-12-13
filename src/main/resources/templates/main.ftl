<#import "parts/header.ftl" as head>
<@head.page>
    <#if Session.SPRING_SECURITY_CONTEXT??>
        <h5>Hello, ${Session.SPRING_SECURITY_CONTEXT.authentication.principal.getUsername()}</h5>
    <#else>
        <h5>Hello, guest. Please, login</h5>
    </#if>
    <div>This is main page of Books World </div>

    <h4>${message?ifExists}</h4>
</@head.page>