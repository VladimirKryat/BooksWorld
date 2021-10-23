<#macro comment path>
    <form method="post" action="${path}" enctype="multipart/form-data">
        <div >
            <label for="text"> Text   <textarea type="text" id="text" name="text"></textarea><br/></label>
            <label for="stars"> Stars <input id="stars" type="text" name="stars"><br/></label>
            <label for="file">File <input type="file" name="file"><br/></label>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <!--        <div><label for="remember-me"> <input type="checkbox" id="remember-me" name="remember-me">Запомнить меня</label><br/>-->
        </div>
        <button type="submit" value="Add comment">Добавить</button>
    </form>
</#macro>