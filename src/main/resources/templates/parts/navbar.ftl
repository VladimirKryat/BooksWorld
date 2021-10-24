<#include "security.ftl">
<#import "loginForm.ftl" as l>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">SpringBootProject</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/comment">Comment</a>
            </li>
            <#if isAdmin>
            <li class="nav-item">
                <a class="nav-link" href="/user">User List</a>
            </li>
            </#if>
        </ul>
        <#if known>
            <div class="navbar-text">${name}</div>
            <div class="navbar-nav nav ml-1">
                <form method="post" action="/logout">
                    <button type="submit" class="btn btn-light nav-link">Sign Out</button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}">
                </form>
            </div>
        <#else >
        <div class="navbar-nav nav justify-content-end mr-5">
                <a class="btn btn-light nav-link" href="/login">Log In</a>
                <a class="btn btn-light nav-link" href="/signup">Sign Up</a>
        </div>
        </#if>

    </div>
</nav>