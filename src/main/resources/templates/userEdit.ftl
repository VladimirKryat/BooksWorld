<#import "parts/header.ftl" as p>
<@p.page>
    User Editor
    <form action="/user" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="hidden" value="${user.userId}" name="userID">
        <input type="text" value="${user.username}" name="username">
        <input type="text" value="${user.password}" name="password">
        <div>
            <#list states as state>
                <label for="state"><input type="radio" value="${state}" name="state" <#if user.state==state>checked</#if> > ${state}</label>
            </#list>
        </div>
        <#list roles as role>
            <div>
<#--               ${user.roles?seq_contains(role)?string("checked","")} возвращает значение взависимости от наличие такой роли у пользователя -->
                <label for="${role}"> <input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked","")}>${role}</label>
            </div>
        </#list>
        <button type="submit">Save</button>
    </form>
</@p.page>