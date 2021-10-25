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
                    <input type="text" value="${user.username}" name="username" placeholder="Login:${user.username}">
                    <small class="form-text text-muted">Login must be unique</small>
                </div>
                <div class="form-group">
                    <input type="email" value="${(user.email)!''}" id="email" name="email" placeholder="Email:${(user.email)!''}">
                    <small class="form-text text-muted">Email</small>
                </div>
            </div>
            <div class="form-group row">
                <div class="form-group mr-1 has-validation">
                    <input type="text" name="oldPassword" <#if message??>class="form-control is-invalid"</#if>  placeholder="Password:">
                    <small class="form-text text-muted">Input Password for change user info</small>
                </div>
                <div class="form-group">
                    <input type="text" name="password" placeholder="New Password:">
                    <small id="passwordHelp" class="form-text text-muted">Must be 8-20 characters long.</small>
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