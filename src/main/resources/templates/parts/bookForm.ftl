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