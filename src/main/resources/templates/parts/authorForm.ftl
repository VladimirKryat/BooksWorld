<#macro authorForm path>
    <form action="${path}" method="post">
        <div class="form-group col-sm-3">
            <label for="exampleFormControlInput1">Имя автора</label>
            <input type="text" name="name" class="form-control" placeholder="ex: А.С. Пушкин">
        </div>
        <div class="form-group col-sm-3">
            <label for="dateOfDeath">Дата рождения</label>
            <input type="date" name="birthday" class="form-control" >
        </div>
        <div class="form-group col-sm-3">
            <label for="dateOfDeath">Дата смерти</label>
            <input type="date" name="dateOfDeath" class="form-control">
        </div>
        <div class="form-group col-sm-3">
            <button type="submit" class="btn btn-primary">Submit</button>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</#macro>