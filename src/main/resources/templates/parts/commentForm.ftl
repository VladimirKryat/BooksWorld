<#macro addCommentForm path>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add comment
    </a>
    <div class="collapse <#if comment??>show</#if>" id="collapseExample">

        <form method="post" action="${path}" enctype="multipart/form-data">
            <div class="form-group">

                <div class="form-group col-sm-4 pl-0 mt-3">
                    <#--${(textError??)?string('is-invalid','')}
                                    (textError??) проверяет на null
                                    (textError??)? проверяем истинность boolean значения
                                    (condition)?string(trueExpression,falseExpression) string означает, что нужно привести true/falseExpression к string
                    -->
                    <#--Таким образом получаем message валидации модели если произошла ошибка валидации-->
                    <textarea type="text" class="form-control ${(textError??)?string('is-invalid','')}" type="text" rows="3" name="text" value="<#if comment??>${comment.text!"TEXT"}<#else>Edit text..</#if>"></textarea>
                    <#if textError??>
                        <div class="invalid-feedback">${textError}</div>
                    </#if>
                </div>

                <div class="form-group col-sm-4 pl-0">
                    <div class="custom-file">
                        <input type="file" class="file-path"  id="customFileLangHTML" name="file">
                    </div>
                    <#if fileError??>
                        <div class="invalid-feedback">${fileError}</div>
                    </#if>
                </div>
                <div class="form-group" id = "radioStars">

<#--получаем введённое раннее значение Stars или оставляем дефолтное = 5-->
                    <#assign starsValue=5>
                    <#if comment??>
                        <#if comment.stars??>
                            starsValue=comment.stars
                        </#if>
                    </#if>
                    <#assign starsValue=5>
                    <div class="form-check form-check-inline">
                        <label class="form-check-label ${(starsError??)?string('is-invalid','')}" for="inlineRadio1">Stars:</label>
                        <#if starsError??>
                            <div class="invalid-feedback">${starsError}</div>
                        </#if>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="stars" value=1 id="inlineRadio1" <#if starsValue==1>checked</#if>>
                        <label class="form-check-label" for="inlineRadio1">1</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="stars" value=2 id="inlineRadio2" <#if starsValue==2>checked</#if>>
                        <label class="form-check-label" for="inlineRadio2">2</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="stars" value=3 id="inlineRadio3" <#if starsValue==3>checked</#if>>
                        <label class="form-check-label" for="inlineRadio3">3</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="stars" value=4 id="inlineRadio4" <#if starsValue==4>checked</#if>>
                        <label class="form-check-label" for="inlineRadio4">4</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="stars" value=5 id="inlineRadio5" <#if starsValue==5>checked</#if>>
                        <label class="form-check-label" for="inlineRadio5">5</label>
                    </div>

                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <!--        <div><label for="remember-me"> <input type="checkbox" id="remember-me" name="remember-me">Запомнить меня</label><br/>-->
            </div>
            <button type="submit" class="btn btn-success mb-3">Добавить</button>
        </form>
    </div>
</#macro>
<#macro listCommentCard>
    <div class="card-columns">
        <#list comments as commentItem>
            <div class="card my-3" style="width: 18rem;">
                <#if commentItem.filename??>
                    <img class="card-img-top" src="/img/${commentItem.filename}">
                </#if>
                <div class="card-body">
                    <h5 class="card-title">
                        ${(commentItem.author.username)!'Guest'}
                    </h5>
                    <h6 class="card-subtitle mb-2 text-muted">
                        ${commentItem.starsByShape()}
                    </h6>
                    <p class="card-text">${commentItem.text}</p>
                </div>
            </div>
        </#list>
    </div>
</#macro>