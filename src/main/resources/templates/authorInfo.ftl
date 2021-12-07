<#import "parts/header.ftl" as p>
<#import "parts/authorForm.ftl" as a>
<#import "parts/bookForm.ftl" as b>
<@p.page>
    <div class="d-flex justify-content-center">
        <div class="card w-75">
            <@a.cardBody author/>
        </div>
    </div>
    <@b.listBookCard false/>
</@p.page>