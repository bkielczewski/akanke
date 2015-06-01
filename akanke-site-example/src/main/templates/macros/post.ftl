<#macro summary post>
    <#assign moreLink><a class="more" href="${post.id}">Read more</a></#assign>
<article itemscope itemtype="http://schema.org/Article">
    <header>
        <h1><a itemprop="url" href="${post.id}"><span itemprop="name">${post.title}</span></a></h1>
        <@meta post/>
    </header>
    <section itemprop="headline">
    ${viewHelperService.extractExcerpt(post.contents, moreLink)}
    </section>
</article>
</#macro>

<#macro meta post>
<ul class="meta list-inline hidden-xs">
    <li><i class="fa fa-clock-o"></i>${viewHelperService.relativeDate(post.datePublished)}</li>
    <li itemprop="datePublished"><i class="fa fa-calendar-o"></i>${post.datePublished?string("dd/MM/yyyy")}</li>
    <li><a href="${post.id}#comments"><i class="fa fa-comments-o"></i>${post.facebookStats.commentCount}</a></li>
    <li><a href="${post.id}"><i class="fa fa-share-square-o"></i>${post.facebookStats.shareCount}</a></li>
    <#if post.tags?has_content>
        <li itemprop="keywords" class="tags">
            <i class="fa fa-tags"></i>
            <#list post.tags as tag>
                <a href="/tag/${tag}/">${tag}</a><#if tag_has_next>, </#if>
            </#list>
        </li>
    </#if>
</ul>
</#macro>

<#macro pagination page url>
    <#if page.totalPages &gt; 1>
    <nav>
        <ul class="pagination">
            <#if page.hasPrevious()>
                <li><a href="${url}/page/${page.number}/"><span aria-hidden="true">&laquo;</span><span class="sr-only">Previous</span></a>
                </li>
            </#if>
            <#list 1..page.totalPages as i>
                <li <#if (page.number + 1) == i>class="active"</#if>><a href="${url}/page/${i}/">${i}</a></li>
            </#list>
            <#if page.hasNext()>
                <li><a href="${url}/page/${page.number + 2}/"><span aria-hidden="true">&raquo;</span><span
                        class="sr-only">Next</span></a></li>
            </#if>
        </ul>
    </nav>
    </#if>
</#macro>
