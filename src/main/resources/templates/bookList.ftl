<#import "parts/header.ftl" as p>
<@p.page>
    <h3>List of books</h3>
    <table class="table table-bordered">
        <thead class="thead-dark">
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
                <td>&nbsp<#list book.authors as author><a href="/manager/authorEditor?author=${author.authorId}">${author.name} </a><#sep>,</#list>&nbsp</td>
                <td>&nbsp${book.description}&nbsp</td>
            </tr>
        </#list>
    </table>
</@p.page>