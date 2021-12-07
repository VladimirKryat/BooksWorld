<#macro pager url page>
    <#if page.getTotalPages() gt 7>
        <#assign
            totalPage = page.getTotalPages()
            currentPage = page.getNumber()
<#--        create value -1 to change on ... in list -->
            head = (currentPage>3)?then([0,-1],0..currentPage+2)
            tail = (currentPage<totalPage-4)?then([-1,totalPage-1],currentPage-2..totalPage-1)
            center = ((currentPage>3)&&(currentPage<totalPage-4))?then(currentPage-2..currentPage+2,[])
<#--            конкатенируем значения-->
            body = head+center+tail
        >
        <#else>
            <#assign body = 0..page.getTotalPages()-1>
    </#if>
<#--    choose page number-->
    <div class="mt-2">
        <nav>
            <ul class="pagination justify-content-center">
                <li class="page-item disabled">
                    <a class="page-link" href="#">Страницы</a>
                </li>
                <#list body as numberPage>
                    <#if numberPage == page.getNumber()>
                        <li class="page-item active">
                            <a class="page-link" href="#">${numberPage+1}</a>
                        </li>
                    <#elseif numberPage==-1>
                        <li class="page-item disabled">
                            <a class="page-link" href="#">...</a>
                        </li>
                    <#else >
                        <li class="page-item">
                            <a class="page-link" href="${url}page=${numberPage}&amp;size=${page.getSize()}">${numberPage+1}</a>
                        </li>
                    </#if>
                </#list>
            </ul>
        </nav>
        <#--choose size page-->
        <nav>
            <ul class="pagination justify-content-center">
                <li class="page-item disabled">
                    <a class="page-link" href="#">Количество</a>
                </li>
                <#list [1,3,6,9,12] as sizePage>
                    <#if sizePage == page.getSize()>
                        <li class="page-item active">
                            <a class="page-link" href="#">${sizePage}</a>
                        </li>
                    <#else >
                        <li class="page-item">
                            <a class="page-link" href="${url}page=${page.getNumber()}&amp;size=${sizePage}">${sizePage}</a>
                        </li>
                    </#if>
                </#list>
            </ul>
        </nav>
    </div>
</#macro>