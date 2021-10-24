<#import "parts/header.ftl" as p>
<@p.page>

    <div>
        User Editor
    </div>
    <form action="/user" method="post">
        <div class="form-group my-3">
            <div class="form-group row my-0 py-0">
                <div class="form-group mr-1">
                    <input type="text" value="${user.username}" name="username" placeholder="Login:${user.username}">
                    <small class="form-text text-muted">Login must be unique</small>
                </div>
                <div class="form-group">
                    <input type="text" value="${user.password}" name="password" placeholder="Password:${user.password}">
                    <small class="form-text text-muted">Password must be 8-20 characters long</small>
                </div>
            </div>
            <div class="form-group row my-1">
                <#list states as state>
                    <div class="form-check form-check-inline">
                        <input type="radio" class="form-check-input" value="${state}" name="state" <#if user.state==state>checked</#if> >
                        <label  class="form-check-label" for="state">${state}</label>
                    </div>
                </#list>
            </div>
            <div class="form-group row my-1">
            <#list roles as role>
                <div class="form-check form-check-inline">
                    <label class="form-check-label" for="${role}">${role}</label>
                    <input type="checkbox" class="form-check-input" name="${role}" ${user.roles?seq_contains(role)?string("checked","")}>
                </div>
            </#list>
            </div>

            <div class="form-group row">
                <button type="submit" class="btn btn-primary mt-2">Save</button>
            </div>
                <input type="hidden" value="${user.userId}" name="userID">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </div>
    </form>
</@p.page>