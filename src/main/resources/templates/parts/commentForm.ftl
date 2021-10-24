<#macro comment path>
    <form method="post" action="${path}" enctype="multipart/form-data">
        <div class="form-group">

            <div class="form-group col-sm-4 pl-0 mt-3">
                <textarea class="form-control" type="text" rows="3" name="text" placeholder="Text.."></textarea>
            </div>

            <div class="form-group col-sm-4 pl-0">
                <div class="custom-file">
                    <input type="file" class="file-path validate"  id="customFileLangHTML" name="file">
                </div>
            </div>
            <div class="form-group" id = "radioStars">

                <div class="form-check form-check-inline">
                    <label class="form-check-label" for="inlineRadio1">Stars:</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="stars" value=1 id="inlineRadio1">
                    <label class="form-check-label" for="inlineRadio1">1</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="stars" value=2 id="inlineRadio2">
                    <label class="form-check-label" for="inlineRadio2">2</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="stars" value=3 id="inlineRadio3">
                    <label class="form-check-label" for="inlineRadio3">3</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="stars" value=4 id="inlineRadio4">
                    <label class="form-check-label" for="inlineRadio4">4</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="stars" value=5 id="inlineRadio5">
                    <label class="form-check-label" for="inlineRadio5">5</label>
                </div>

            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <!--        <div><label for="remember-me"> <input type="checkbox" id="remember-me" name="remember-me">Запомнить меня</label><br/>-->
        </div>
        <button type="submit" class="btn btn-success mb-3">Добавить</button>
    </form>
</#macro>