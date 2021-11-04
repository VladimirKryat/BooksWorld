<#macro authorForm path>
    <form action="${path}" method="post">
        <div class="form-group col-sm-3">
            <label for="exampleFormControlInput1">Имя автора</label>
            <input type="text" name="name"
                   class="form-control ${(nameError??)?string('is-invalid','')}"
                   placeholder="ex: А.С. Пушкин"
                    <#if author??>
                        value="${author.name}"
                    </#if>
            >
            <#if nameError??>
                <div class="invalid-feedback">${nameError}</div>
            </#if>
        </div>
        <div class="form-group col-sm-3">
            <label for="birthday">Дата рождения</label>
            <input type="date" name="birthday"
                   class="form-control ${(birthdayError??)?string('is-invalid','')}"
                    <#if author??>
                        value="${author.birthday?if_exists}"
                    </#if>
            >
            <#if birthdayError??>
                <div class="invalid-feedback">${birthdayError}</div>
            </#if>
        </div>
        <div class="form-group col-sm-3">
            <label for="dateOfDeath">Дата смерти</label>
            <input type="date" name="dateOfDeath"
                   class="form-control ${(dateOfDeathError??)?string('is-invalid','')}"
                    <#if author??>
                        value="${author.dateOfDeath?if_exists}"
                    </#if>
            >
            <#if dateOfDeathError??>
                <div class="invalid-feedback">${dateOfDeathError}</div>
            </#if>
        </div>
        <div class="form-group col-sm-3">
            <button type="submit" class="btn btn-primary">Submit</button>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if author?? && author.authorId??>
            <input type="hidden" name="authorId" value="${author.authorId}"/>
        </#if>
    </form>
</#macro>