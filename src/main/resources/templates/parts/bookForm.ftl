<#macro bookForm path>
    <form action="${path}" method="post">
        <div class="form-group col-sm-4">
            <div class="form-group">
                <label for="exampleFormControlInput1">Название</label>
                <input type="text" name="title" class="form-control" placeholder="title">
            </div>
            <div class="form-group">
                <label for="authors"> Выберите автора</label>
                <select class="custom-select" name="authors" multiple>
                    <#list authors as author>
                        <option value=${author.authorId}>${author.name}</option>
                    </#list>
                    <#--            <option selected>Откройте это меню выбора</option>-->

                </select>
            </div>
            <div class="form-group">
                <label for="description">Краткое описание</label>
                <textarea class="form-control" name="description" rows="4"></textarea>
            </div>
            <div class="form-group col-sm-3">
                <button type="submit" class="btn btn-primary">Submit</button>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </div>
    </form>
</#macro>