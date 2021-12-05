<#macro bookForm path>
    <form action="${path}" method="post" enctype="multipart/form-data">
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
            <div class="form-group">
                <div class="custom-file">
                    <input type="file" class="file-path"  id="customFileLangHTML" name="file" />
                </div>
                <#if fileError??>
                    <div class="invalid-feedback">${fileError}</div>
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
    <#import "pager.ftl" as pager>
    <div class="card-columns">

        <#list books.getContent() as bookItem>
            <div class="card my-3" style="width: 18rem;">
                <#if bookItem.filename??>
                    <img class="card-img-top" src="/img/book/${bookItem.filename}" alt=""/>
                </#if>
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
                        <#if bookItem.description??>
                            <#if bookItem.description?length gte 255>
                                ${bookItem.description?substring(1,252)}...
                            <#else >
                                ${bookItem.description}
                            </#if>
                        </#if>
                    </p>
                    <div class="card-footer">
                        <#if bookItem.genres??>
                            <#list bookItem.genres as genreBook>
                                <div>${genreBook}</div>
                            </#list>
                        </#if>
                    </div>
                    <div class="card-footer text-center">
                        <a href="book/${bookItem.bookId}/like">
                            <#if bookItem.meIsLiked>
                                <i class="fas fa-heart"></i>
                            <#else>
                                <i class="far fa-heart"></i>
                            </#if>
                            ${bookItem.likes}
                        </a>
                    </div>
                </div>
            </div>
        </#list>
    </div>
    <@pager.pager url books/>
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