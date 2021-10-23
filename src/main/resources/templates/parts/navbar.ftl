<#include "security.ftl">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">SpringBootProject</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Main</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/greeting">Greeting</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/comment">Comment</a>
            </li>
            <#if isAdmin>
            <li class="nav-item">
                <a class="nav-link" href="/user">User List</a>
            </li>
            </#if>
        </ul>
        <div class="navbar-text">${name}</div>
    </div>
</nav>