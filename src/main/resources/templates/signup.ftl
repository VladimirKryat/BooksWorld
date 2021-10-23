<#import "parts/header.ftl" as p>
<#import "parts/loginForm.ftl" as l>
<@p.page>

    <!--если message!=null выводим его-->
    ${message?ifExists}
    </br>
    Add new user

    <@l.login "/signup"/>
</@p.page>