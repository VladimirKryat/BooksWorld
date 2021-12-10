<#macro addCommentForm path ownerId>
    <#if !comment??>
        <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
            Add comment
        </a>
    </#if>
    <div class="collapse <#if comment??>show</#if>" id="collapseExample">

        <form method="post" action="${path}" enctype="multipart/form-data">
            <div class="form-group">

                <div class="form-group col-sm-4 pl-0 mt-3">
                    <#--Таким образом получаем message валидации модели если произошла ошибка валидации-->
                    <textarea class="form-control ${(textError??)?string('is-invalid','')}" type="text" rows="3" name="text" ><#if comment??>${comment.text!"TEXT"}<#else>Edit text..</#if></textarea>
                    <#if textError??>
                        <div class="invalid-feedback">${textError}</div>
                    </#if>
                </div>

                <div class="form-group col-sm-4 pl-0">
                    <div class="custom-file">
                        <input type="file" class="file-path"  id="customFileLangHTML" name="file" />
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
                              <#assign starsValue=comment.stars>
                        </#if>
                    </#if>
                    <div class="form-check form-check-inline">
                        <label class="form-check-label ${(starsError??)?string('is-invalid','')}" for="inlineRadio1">Stars:</label>
                        <#if starsError??>
                            <div class="invalid-feedback">${starsError}</div>
                        </#if>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="stars" value="1" id="inlineRadio1" <#if starsValue==1>checked="checked"</#if> />
                        <label class="form-check-label" for="inlineRadio1">1</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="stars" value="2" id="inlineRadio2" <#if starsValue==2>checked="checked"</#if> />
                        <label class="form-check-label" for="inlineRadio2">2</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="stars" value="3" id="inlineRadio3" <#if starsValue==3>checked="checked"</#if> />
                        <label class="form-check-label" for="inlineRadio3">3</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="stars" value="4" id="inlineRadio4" <#if starsValue==4>checked="checked"</#if> />
                        <label class="form-check-label" for="inlineRadio4">4</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="stars" value="5" id="inlineRadio5" <#if starsValue==5>checked="checked"</#if> />
                        <label class="form-check-label" for="inlineRadio5">5</label>
                    </div>

                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <#if comment??>
                    <input type="hidden" name="commentId" value="${comment.commentId}" />
                </#if>
                <#if ownerId?? && ownerId gt 0>
                    <input type="hidden" name="ownerId" value="${ownerId}" />
                </#if>
<#--сюда попадает ссылка на страницу откуда пришёл пользователь для редактирования коммента-->
                <#if refUrl??>
                    <input type="hidden" name="refUrl" value="${refUrl}" />
                </#if>

            </div>
            <button type="submit" class="btn btn-success mb-3">Сохранить</button>
        </form>
    </div>
</#macro>
<#macro listCommentCard commentsPage>
    <#import "pager.ftl" as pg>
    <#include "security.ftl">
    <div class="card-columns" id="idCardColumns">
        <#list commentsPage.getContent() as commentItem>
            <div class="card my-3" style="width: 18rem;" data-id="${commentItem.commentId}">
                <#if commentItem.filename??>
                    <a class="btn btn-primary" data-toggle="collapse" href="#collapseImg${commentItem.commentId}" role="button" aria-expanded="false" aria-controls="collapseImg${commentItem.commentId}">
                        Show image
                    </a>
                    <div class="collapse btn-sm" id="collapseImg${commentItem.commentId}">
                        <img class="card-img-top " src="/img/comment/${commentItem.filename}" />
                    </div>
                </#if>
                <div class="card-body">
                    <h5 class="card-title">
                        <#if commentItem.user??>
                            ${(commentItem.user.username)}
                        <#else >
                            Guest
                        </#if>

                    </h5>
                    <h6 class="card-subtitle mb-2 text-muted">
                        ${commentItem.starsByShape()}
                    </h6>
                    <p class="card-text">
                        ${commentItem.text!''}
                        <#if commentItem.user?? && commentItem.user.userId==userId>
<#--Ссылка на редактирование своего коммента-->
                            <a href="/commentedit?comment=${commentItem.commentId}">🖊</a>
<#-- НЕОБХОДИМО ИСПРАВИТЬ ССЫЛКУ-->
                        </#if>

                    </p>
                </div>
            </div>
        </#list>
    </div>
    <@pg.pager url commentsPage "comments"/>
</#macro>

