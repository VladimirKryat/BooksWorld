<#import "parts/header.ftl" as p>
<#import "parts/loginForm.ftl" as l>
<@p.page>

    <!--если message!=null выводим его-->
    <#if message??>
        <div class="alert alert-danger" role="alert">
            ${message}.
        </div>
    </#if>
    <@l.login true/>
</@p.page>