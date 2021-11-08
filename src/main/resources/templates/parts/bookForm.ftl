<#macro bookForm path>
    <form action="${path}" method="post">
        <div class="form-group col-sm-4">
            <div class="form-group">
                <label for="exampleFormControlInput1">Название</label>
                <input type="text" name="title"
                       class="form-control  ${(titleError??)?string('is-invalid','')}" placeholder="title"
                        <#if book??> value="${book.title?if_exists}" </#if>>
                <#if titleError??>
                    <div class="invalid-feedback">${titleError}</div>
                </#if>
            </div>
            <div class="form-group">
                <label for="authors"> Выберите автора</label>
                <select class="custom-select  ${(authorsError??)?string('is-invalid','')}"
                        name="authors" multiple>
                    <#list allAuthors as author>
                        <option value=${author.authorId}
                                <#if book??>${book.authors?seq_contains(author)?string("selected","")}
                                </#if>>${author.name}</option>
                    </#list>
                </select>
                <#if authorsError??>
                    <div class="invalid-feedback">${authorsError}</div>
                </#if>
            </div>
            <div class="form-group">
                <label for="description">Краткое описание</label>
                <textarea class="form-control ${(descriptionError??)?string('is-invalid','')}" name="description" rows="4"><#if book??>${book.description?if_exists}</#if></textarea>
                <#if descriptionError??>
                    <div class="invalid-feedback">${descriptionError}</div>
                </#if>
            </div>
            <div class="form-group col-sm-3">
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <#if book?? && book.bookId??>
                <input type="hidden" name="bookId" value="${book.bookId}"/>
            </#if>
        </div>
    </form>
</#macro>

<#macro listBookCard>
    <#include "security.ftl">
    <div class="card-columns">
        <#list books as bookItem>
            <div class="card my-3" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">
                        <#if isManager>
                            <a href="/manager/bookEditor?book=${bookItem.bookId}">${bookItem.title}</a>
                        <#else >
                            ${bookItem.title}
                        </#if>

                    </h5>
                    <h6 class="card-subtitle mb-2 text-muted">
                        <#if isManager>
                            <#list bookItem.authors as author>
                                <a class="btn btn-info" href="/manager/authorEditor?author=${author.authorId}">${author.name}</a><#sep>, </#list>
                        <#else >
                            <#list bookItem.authors as author>
                            <a class="btn btn-info" href="/authorInfo?author=${author.authorId}">${author.name}</a><#sep>, </#list>
                        </#if>
                    </h6>
                    <p class="card-text">
                        ${bookItem.description!""}
                    </p>
                </div>
            </div>
        </#list>
    </div>
</#macro>

<#macro oldTableOfBook>
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
</#macro>