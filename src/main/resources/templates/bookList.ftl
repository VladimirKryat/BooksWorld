<#import "parts/header.ftl" as p>
<@p.page>
    <h3>List of books</h3>
    <table>
    <thead>
    <tr>
        <th>bookID</th>
        <th>&nbsp Title</th>
        <th>&nbsp Authors</th>
        <th>&nbsp Description</th>
    </tr>
    </thead>

    <#list books as book>
        <tr>
            <td>&nbsp${book.bookId}&nbsp</td>
            <td>&nbsp<a href="/manager/bookEditor?book=${book.bookId}">${book.title}</a>&nbsp</td>
            <td>&nbsp<#list book.authors as author>${author.name}, </#list>&nbsp</td>
            <td>&nbsp${book.description}&nbsp</td>
        </tr>
    </#list>
</@p.page>