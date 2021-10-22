<#import "parts/header.ftl" as p>
<@p.page>
    <h3>List of users</h3>
    <table>
    <thead>
    <tr>
        <th>userID</th>
        <th>Username</th>
        <th>Password</th>
        <th>State</th>
        <th>Roles</th>
    </tr>
    </thead>

    <#list users as user>
        <tr>
            <td>&nbsp${user.userId}&nbsp</td>
            <td>&nbsp${user.username}&nbsp</td>
            <td>&nbsp${user.password}&nbsp</td>
            <td>&nbsp${user.state}&nbsp</td>
            <td>&nbsp
                <#list user.roles as role>
                    <i> ${role.name()}<#sep>, </i>
                </#list>
            </td>
            <td>&nbsp<a href="/user/${user.userId}">edit</a> &nbsp</td>
        </tr>
    </#list>
</@p.page>