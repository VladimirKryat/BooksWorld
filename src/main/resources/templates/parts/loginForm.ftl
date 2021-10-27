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
    <form name="loginForm" method="post" action="${formPath}" xmlns="http://www.w3.org/1999/html">
        <div class="form-group col-sm-3">
            <label for="username"> Login: </label>
            <input type="text" class="form-control ${(usernameError??)?string('is-invalid','')}"
                   id="username" name="username" placeholder="Login"
                   <#if user??>value="${user.username}"</#if>
            >
            <#if usernameError??>
                <div class="invalid-feedback">${usernameError}</div>
            </#if>
        </div>
        <#if isRegistration>
            <div class="form-group col-sm-3">
                <label for="email"> Email: </label>
                <input type="email" class="form-control ${(emailError??)?string('is-invalid','')}"
                       id="email" name="email" placeholder="Email"
                       <#if user??>value="${user.email}"</#if>
                >
                <#if emailError??>
                    <div class="invalid-feedback">${emailError}</div>
                </#if>
            </div>
        </#if>
        <div class="form-group col-sm-3">
            <label for="password"> Password: </label>
            <input type="password" class="form-control ${(passwordError??)?string('is-invalid','')}"
                   id="password" name="password" placeholder="Password">
            <#if passwordError??>
                <small id="passwordHelp" class="invalid-feedback">${passwordError}</small>
            <#else >
                <small id="passwordHelp" class="form-text text-muted">Must be 8-20 characters long.</small>
            </#if>
        </div>
        <#if isRegistration>
            <div class="form-group col-sm-3">
                <label for="passwordConfirm"> PasswordConfirm: </label>
                <input type="password" class="form-control ${(passwordConfirmError??)?string('is-invalid','')}"
                       id="passwordConfirm" name="passwordConfirm" placeholder="Repeat password"
                >
                <#if passwordConfirmError??>
                    <div class="invalid-feedback">${passwordConfirmError}</div>
                </#if>
            </div>
        </#if>
        <div class="form-group col-sm-3">
            <button type="submit" class="btn btn-primary">Submit</button>
            <a href="${linkPath}" class="btn btn-primary">${linkName}</a>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>