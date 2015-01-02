<#import "/spring.ftl" as spring />
<#macro main nav="" title="" description="" url="">
<!DOCTYPE html>
<html lang="en" ng-app="app">
<head prefix="og: http://ogp.me/ns#">
    <title>${title}</title>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <meta name="description" content="${description}"/>

    <meta property="fb:app_id" content="${facebookAppId}"/>

    <meta property="og:locale" content="en_US"/>
    <meta property="og:type" content="website"/>
    <meta property="og:title" content="${title}"/>
    <meta property="og:description" content="${description}"/>
    <meta property="og:url" content="${baseUrl}<#if url?has_content>${url}</#if>"/>
    <meta property="og:site_name" content=""/>
    <meta property="og:image" content=""/>
    <meta property="og:image:type" content="">
    <meta property="og:image:width" content="">
    <meta property="og:image:height" content="">

    <meta name="twitter:card" content=""/>
    <meta name="twitter:site" content=""/>
    <meta name="twitter:domain" content=""/>
    <meta name="twitter:creator" content=""/>

    <meta name="google-site-verification" content=""/>

    <link rel="stylesheet" href="<@spring.url "/public/css/all.css"/>" type="text/css"/>
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic"
          type="text/css">
    <link rel="image_src" href=""/>
    <link rel="canonical" href="">
</head>
<body ng-controller="MainCtrl">
<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" ng-click="isNavbarCollapsed = !isNavbarCollapsed">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/">Akanke</a>
        </div>
        <div class="collapse navbar-collapse" collapse="isNavbarCollapsed">
            <ul class="nav navbar-nav navbar-right">
                <li<#if nav == ""> class="active"</#if>><a href="/">Home</a></li>
                <li<#if nav == "example-page"> class="active"</#if>><a href="/example-page/">Example page</a></li>
                <li<#if nav == "blog"> class="active"</#if>><a href="/blog/">Blog</a></li>
            </ul>
        </div>
    </div>
</nav>

    <#nested />

<footer>
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <p class="copyright text-muted small">&copy; Bartosz Kielczewski</p>
            </div>
        </div>
    </div>
</footer>

<facebook app-id="${facebookAppId}"></facebook>
<script src="<@spring.url "/public/js/all.js"/>"></script>
</body>
</html>
</#macro>