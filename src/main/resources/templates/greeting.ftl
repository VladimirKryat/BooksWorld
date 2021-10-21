<#import "parts/header.ftl" as p>
<#import "parts/loginForm.ftl" as l>
<@p.page>
    <div>Hello, ${name}</div>
    <a href="/comment">Comment</a>
    <@l.logout/>
</@p.page>