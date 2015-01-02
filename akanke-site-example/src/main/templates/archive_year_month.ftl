<#import "layouts/main.ftl" as layout />
<#import "macros/post.ftl" as postHelper />
<#import "/spring.ftl" as spring />
<#assign title>Archive - <@spring.message "month_" + month /> ${year}</#assign>
<@layout.main title="${title}" nav="blog" url="/${year}/${month}/page/${posts.number + 1}/">
<div class="container page-top">
    <div class="row">
        <div class="col-md-9 col-md-push-3">
            <h3 class="section-heading text-center"><a href="/${year}/${month}/">${title}</a></h3>

            <#list posts.content as post>
                <@postHelper.summary post />
            </#list>

            <@postHelper.pagination posts "/${year}/${month}" />
        </div>
        <div class="col-md-3 col-md-pull-9 sidebar">
            <ul>
                <li>
                    <h3 class="section-heading"><a href="/${year}/">${year}</a></h3>
                    <ul>
                        <li><a href="/${year}/${month}/"><@spring.message "month_" + month/>
                            (${count})</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</div>
</@layout.main>