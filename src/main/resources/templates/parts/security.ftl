<#-- объявляем переменные-->
<#--проверяем контекст Спринга на налчие-->
<#assign
known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
    <#--        получим объект User если он авторизован в SpringSecurity (principal - UserDetails)-->
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal.getUser()
    userId = user.getUserId()
    name = Session.SPRING_SECURITY_CONTEXT.authentication.principal.getUsername()
    isAdmin = Session.SPRING_SECURITY_CONTEXT.authentication.principal.isAdmin()
    >
<#else >
    <#assign
    <#--            значения заглушки-->
    name = "unknown"
    isAdmin = false
    userId = -1
    >
</#if>