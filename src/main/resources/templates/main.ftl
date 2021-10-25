<#import "parts/header.ftl" as p>
<@p.page>
    <h5>Hello, guest</h5>
    <div>This is a studies project of Spring Boot.</div>
    <h4>${message?ifExists}</h4>
</@p.page>