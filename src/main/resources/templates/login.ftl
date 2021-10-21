<#import "parts/header.ftl" as p>
<#import "parts/loginForm.ftl" as l>
<@p.page>
    Login page
    <@l.login "/login"/>
    <a href="/signup">Зарегистрироваться</a>
</@p.page>