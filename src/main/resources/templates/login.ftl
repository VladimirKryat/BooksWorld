<#import "parts/header.ftl" as p>
<#import "parts/loginForm.ftl" as l>
<@p.page>
    <#if RequestParameters.error??>
        <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
            <div class="alert alert-danger" role="alert">
                <#--вернём сообщение присылаемое от Security-->
                ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
            </div>
        </#if>
    </#if>

    <@l.login false/>

</@p.page>