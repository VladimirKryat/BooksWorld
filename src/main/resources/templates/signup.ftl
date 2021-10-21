<#import "parts/header.ftl" as p>
<#import "parts/loginForm.ftl" as l>
<@p.page>

    <!--если message!=null выводим его-->
    <#if message??>
        ${message}
        </br>
    </#if>
    Add new user

    <@l.login "/signup"/>
</@p.page>