<#macro login isRegistration>
    <#if isRegistration>
        <#assign
        formPath="/signup"
        linkPath="/login"
        linkName="Log In"
        >
    <#else >
        <#assign
        formPath="/login"
        linkPath="/signup"
        linkName="Sign Up"
        >
    </#if>
    <form method="post" action="${formPath}" xmlns="http://www.w3.org/1999/html">
        <div class="form-group col-sm-3">
            <label for="username"> Login: </label>
            <input type="text" class="form-control" id="username" name="username" placeholder="Login">
        </div>
        <#if isRegistration>
            <div class="form-group col-sm-3">
                <label for="email"> Email: </label>
                <input type="email" class="form-control" id="email" name="email" placeholder="Email">
            </div>
        </#if>
        <div class="form-group col-sm-3">
            <label for="password"> Password: </label>
            <input type="password" class="form-control" id="password" name="password" placeholder="Password">
            <small id="passwordHelp" class="form-text text-muted">Must be 8-20 characters long.</small>
        </div>
        <div class="form-group col-sm-3">
            <button type="submit" class="btn btn-primary">Submit</button>
            <a href="${linkPath}" class="btn btn-primary">${linkName}</a>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>