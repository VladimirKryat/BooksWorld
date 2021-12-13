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
                <label for="genres"> Выберите жанр</label>
                <select class="custom-select  ${(genresNameError??)?string('is-invalid','')}"
                        name="genresName" multiple>
                    <#list allGenres as genre>
                        <option value=${genre}
                                <#--                                >-->
                                <#--                            ${genre}-->
                                <#if book??&&book.genres??>${book.genres?seq_contains(genre)?string("selected","")}
                                </#if>>${genre}
                        </option>
                    </#list>
                </select>
                <#if genresNameError??>
                    <div class="invalid-feedback">${genresNameError}</div>
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

<#macro listBookCard books>
    <#import "bookForm.ftl" as bf>
    <div class="card-columns">
        <#list books as bookItem>
            <div class="card" style="width: 18rem;">
                <#if bookItem.filename??>
                    <img class="card-img-top" src="/img/book/${bookItem.filename}" alt=""/>
                </#if>
                <div class="card my-3" style="width: 18rem;">
                    <@bf.cardBookDto bookItem false/>
                </div>
            </div>
        </#list>
    </div>
</#macro>

<#macro cardBookDto bookItem isFullDescription>
    <#include "security.ftl">
    <div class="card-body">
        <h5 class="card-title">
            <a class="btn btn-info" href="/bookInfo?book=${bookItem.bookId}">${bookItem.title}</a>
        </h5>
        <h6 class="card-subtitle mb-2 text-muted">

            <#list bookItem.authors as author>
                <a class="btn btn-info" href="/authorInfo?author=${author.authorId}">${author.name}</a><#sep>, </#list>
        </h6>
        <p class="card-text">
            <#if bookItem.description??>
                <#if isFullDescription>
                    ${bookItem.description}
                <#elseif bookItem.description?length gte 255>
                    ${bookItem.description?substring(0,252)}...
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
        <#if isManager>
            <div class="card-footer text-center">
                <a href="/manager/bookEditor?book=${bookItem.bookId}">Change book</a>
                <a class="btn btn-outline-danger" href="/manager/bookDelete?book=${bookItem.bookId}">Delete book</a>
            </div>
        </#if>
    </div>
</#macro>

<#--    Form for Filter BookList by Genre or Sorted by Likes-->
<#macro filterNavBar>
    <nav class="navbar navbar-light bg-light">
        <a class="navbar-brand"></a>
        <form class="form-inline" method="get">
            <label for="inputState">Genre:</label>
            <select id="inputState" class="form-control ml-2 mr-3" name="genreName">
                <option disabled selected value="NONE">Choose genre</option>
                <#if allGenres??>
                    <#list allGenres as genre>
                        <option <#if filterBook??&&filterBook.genreName??&&filterBook.genreName==genre>selected</#if>>${genre}</option>
                    </#list>
                </#if>
            </select>

            <label for="selectOrder">Sorted:</label>
            <select class="form-control ml-2 mr-3" name="sortedByLikes" id="selectOrder">
                <option value="NONE" <#if filterBook??&&filterBook.sortedByLikes=="NONE">selected</#if>>None</option>
                <option value="ASC" <#if filterBook??&&filterBook.sortedByLikes=="ASC">selected</#if>>By likes ▲</option>
                <option value="DESC" <#if filterBook??&&filterBook.sortedByLikes=="DESC">selected</#if>>By likes ▼</option>
            </select>


            <button class="btn btn-outline-success my-2 my-sm-0 mr-3" type="submit">Search</button>
            <a class="btn btn-outline-success my-2 my-sm-0" type="reset" href="/bookList">Clear</a>

        </form>
    </nav>

</#macro>