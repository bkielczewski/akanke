<#import "layouts/main.ftl" as layout />
<#import "macros/post.ftl" as postHelper />
<#import "/spring.ftl" as spring />
<@layout.main title="Blog" nav="blog" url="/blog/">
<div class="container page-top" ng-controller="PostCtrl">
    <div class="row">
        <div class="col-md-9 col-md-push-3">
            <#list posts.content as post>
                <@postHelper.summary post />
            </#list>

            <@postHelper.pagination posts "/blog"/>
        </div>
        <div class="col-md-3 col-md-pull-9 sidebar">
            <ul>
                <#list count?keys as year>
                    <li>
                        <h3 class="section-heading"><a href="/${year}/">${year}</a></h3>
                        <ul>
                            <#list count[year]?keys as month>
                                <li><a href="/${year}/${month}/"><@spring.message "month_" + month/>
                                    (${count[year][month]})</a></li>
                            </#list>
                        </ul>
                    </li>
                </#list>
            </ul>
        </div>
    </div>
</div>
</@layout.main>