<#import "parts/header.ftl" as p>
<#import "parts/loginForm.ftl" as l>
<@p.page>

    <!--если message!=null выводим его-->
    ${message?ifExists}
    <@l.login true/>
</@p.page>