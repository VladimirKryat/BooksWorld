<#import "parts/header.ftl" as p>
<@p.page>
    <h4>Change User info</h4>
    <h5>${user.username}</h5>
    <form method="post">
        <div class="form-group my-3">
            <h4 class="text-danger">
                ${message?ifExists}
            </h4>
            <div class="form-group row my-0 py-0">
                <div class="form-group mr-1">
                    <input type="text" class="form-control ${(usernameError??)?string('is-invalid','')}"
                           value="${user.username!''}" name="username" placeholder="Login:">
                    <#if usernameError??>
                        <small class="form-text text-muted invalid-feedback">${usernameError}</small>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="email" class="form-control ${(emailError??)?string('is-invalid','')}"
                           value="${(user.email)!''}" id="email"
                           name="email" placeholder="Email:">
                    <#if emailError??>
                        <small class="form-text text-muted invalid-feedback">${emailError}</small>
                    </#if>
                </div>

                    <#if emailError??>
                        <div class="invalid-feedback">${emailError}</div>
                    </#if>

            </div>
            <div class="form-group row my-0 py-0">
                <div class="form-group mr-1">
                    <input type="text" name="password" class="form-control ${(passwordError??)?string('is-invalid','')}"  placeholder="Password:">
                    <#if passwordError??>
                        <small class="form-text text-muted invalid-feedback">${passwordError}</small>
                    <#else >
                        <small class="form-text text-muted">Input Password for change user info</small>
                    </#if>
                </div>
            </div>
            <div class="form-group row my-0 py-0">
                <div class="form-group mr-1">
                    <input type="text" class="form-control ${(newPasswordError??)?string('is-invalid','')}"
                           name="newPassword" placeholder="New password:">
                    <#if newPasswordError??>
                        <small class="form-text text-muted invalid-feedback">${newPasswordError}</small>
                    <#else >
                        <small class="form-text text-muted">Must be 8-20 characters long.</small>
                    </#if>
                </div>

                <div class="form-group">
                    <input type="text" class="form-control ${(passwordConfirmError??)?string('is-invalid','')}"
                           name="passwordConfirm" placeholder="Repeat new password:">
                    <#if passwordConfirmError??>
                        <small class="form-text text-muted invalid-feedback">${passwordConfirmError}</small>
                    <#else >
                        <small class="form-text text-muted">Must be equals of new password</small>
                    </#if>
                </div>
            </div>
            <div class="form-group row">
                <button type="submit" class="btn btn-primary mt-2">Save</button>
            </div>
            <input type="hidden" value="${user.userId}" name="userId">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </div>
    </form>
</@p.page>