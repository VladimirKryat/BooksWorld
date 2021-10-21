<#macro login path>
    <form method="post" action="${path}">
        <div >
            <label for="username"> Name:   <input type="text" id="username" name="username"><br/></label>
            <label for="password"> Password: <input id="password" type="password" name="password"><br/></label>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <!--        <div><label for="remember-me"> <input type="checkbox" id="remember-me" name="remember-me">Запомнить меня</label><br/>-->
        </div>
        </div>
        <button type="submit">Подтвердить</button>
    </form>
</#macro>

<#macro logout>
    <form method="post" action="/logout">
        <button type="submit">Sign Out</button>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
    </form>
</#macro>