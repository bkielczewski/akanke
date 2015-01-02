<#import "layouts/main.ftl" as layout />
<#import "macros/post.ftl" as postHelper />
<@layout.main title=post.title description=post.description nav="blog" url=post.id>
<article itemscope itemtype="http://schema.org/Article" class="container page-top" ng-controller="PostCtrl">
    <div class="row">
        <div class="col-md-9 col-md-push-3">
            <header>
                <div class="hidden-xs pull-right">
                    <div class="fb-like" data-href="${baseUrl}${post.id}"
                         data-layout="button"
                         data-action="like" data-show-faces="false" data-share="true"></div>
                </div>
                <h1 itemprop="name"><a href="${post.id}" itemprop="url">${post.title}</a></h1>
                <@postHelper.meta post/>
            </header>
            <section itemprop="articleBody">
            ${post.contents}
            </section>

            <div class="fb-like" data-href="${baseUrl}${post.id}" data-layout="standard"
                 data-action="like" data-show-faces="true" data-share="true"></div>

            <div id="comments">
                <div class="fb-comments" data-href="${baseUrl}${post.id}"
                     data-numposts="5" data-colorscheme="light"></div>
            </div>
        </div>
        <div class="col-md-3 col-md-pull-9">
        </div>
    </div>
</article>
</@layout.main>