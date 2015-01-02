<#import "layouts/main.ftl" as layout />
<@layout.main title="Akanke">

<div class="intro-header">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="intro-message">
                    <h1>Akanke</h1>

                    <h3>Tech Blogging Engine</h3>
                    <hr class="intro-divider">
                    <ul class="list-inline">
                        <li>
                            <a href="/blog/" class="btn btn-primary btn-lg">Read the Blog</a>
                        </li>
                    </ul>
                    <ul class="list-inline intro-social-buttons">
                        <li>
                            <a href="http://kielczewski.eu/akanke" class="btn btn-default btn-lg"><i
                                    class="fa fa-external-link fa-fw"></i></a>
                        </li>
                        <li>
                            <a href="https://github.com/bkielczewski/akanke"
                               class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i></a>
                        </li>
                        <li>
                            <a href="https://twitter.com/bkielczewski" class="btn btn-default btn-lg"><i
                                    class="fa fa-twitter fa-fw"></i></a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="content-section-a">
    <div class="container">
        <div class="row">
            <div class="col-lg-5 col-sm-6">
                <h2 class="section-heading"><a>Tags</a></h2>

                <p class="lead">These are tags from the articles.</p>
            </div>

            <div class="col-lg-5 col-lg-offset-2 col-sm-6 tagcloud">
                <#assign tags=documentService.tagCounts />
                <#if tags?? && tags?size &gt; 0>
                    <#list tags?keys as tag>
                        <a href="/tag/${tag}/" data-count="${tags[tag]}">${tag}</a>
                    </#list>
                </#if>
            </div>
        </div>
    </div>
</div>

<div class="content-section-b">
    <div class="container">
        <div class="row">
            <div class="col-lg-5 col-sm-6">
                <h2 class="section-heading"><a>Popular</a></h2>

                <p class="lead">Measured by the number of social interactions.</p>
            </div>
            <div class="col-lg-5 col-lg-offset-2 col-sm-6 popular">
                <ul class="list-unstyled">
                    <#list documentService.getMostPopular(10) as post>
                        <li><a href="${post.id}">${post.title}</a></li>
                    </#list>
                </ul>
            </div>
        </div>
    </div>
</div>

</@layout.main>