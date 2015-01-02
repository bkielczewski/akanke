<#import "layouts/main.ftl" as layout />
<#import "macros/post.ftl" as postHelper />
<#import "/spring.ftl" as spring />
<#assign title>${tag}</#assign>
<@layout.main title="${title}" nav="blog" url="/tag/${tag}/page/${posts.number + 1}/">
<div class="container page-top">
    <div class="row">
        <div class="col-md-9 col-md-push-3">
            <h3 class="section-heading text-center"><a href="/tag/${tag}">${title}</a></h3>

            <#list posts.content as post>
                <@postHelper.summary post />
            </#list>

            <@postHelper.pagination posts "/tag/${tag}" />
        </div>
        <div class="col-md-3 col-md-pull-9 sidebar">
        </div>
    </div>
</div>
</@layout.main>