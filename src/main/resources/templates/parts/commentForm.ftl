<#macro comment path>
    <form method="post" action="${path}">
        <div >
            <label for="text"> Text   <textarea type="text" id="text" name="text"></textarea><br/></label>
            <label for="stars"> Stars <input id="stars" type="text" name="stars"><br/></label>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <!--        <div><label for="remember-me"> <input type="checkbox" id="remember-me" name="remember-me">Запомнить меня</label><br/>-->
        </div>
        </div>
        <button type="submit" value="Add comment">Добавить</button>
    </form>
</#macro>