<#import "parts/header.ftl" as p>
    <#import "parts/authorForm.ftl" as a>
<@p.page>
    <div class="card" style="width: 18rem;">
        <@a.cardBody author/>
    </div>
</@p.page>