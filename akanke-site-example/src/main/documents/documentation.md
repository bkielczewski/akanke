<!--
title: Documentation
description: General overview to get you started with creating a custom front-end.
date: 01/01/2015
tags: Akanke, Documentation
-->
You probably want to create a custom front-end for your own site, so this is to tell you how to do it. <!--more-->

## What's where

By default:

* `./config/application.properties` - this is a configuration file
* `./documents` - document directory, you put your Markdown files there
* `./documents/resources` - resources, you put there whatever you reference in documents (like images)
* `./resources` - resources used by front-end (css, js, images)
* `./templates` - templates defining views

## Configuration

The configuration file is in `/config/application.properties`.
The interesting settings together with their defaults are:

    akanke.base-url=http://localhost:8080
    akanke.facebook.app-id=
    akanke.documents.path=./documents
    akanke.resources.path=./resources
    akanke.templates.path=./templates

Akanke can pull information about your documents from Facebook like number of likes, comments and shares.
To make that work make sure `akanke.base-url` is set and makes sense.

The rest is for switching default directories, to switch themes for example.

All changes to that file require restart of Akanke.

## The views

Akanke uses [Freemarker](http://freemarker.org) at the moment as its template engine.

The supported templates with their intended use and template-specific variables:

* `index.ftl` - the main page.
* `blog.ftl` - a list of posts.
    * `Page<Document> posts` - a `Page` instance holding `Document` instances.
* `post.ftl` - a post
    * `Document document` - a `Document` instance.
    * `Map<String, Map<String, Integer> count` - number of documents by year, month.
* `archive_year_month.ftl` - an archive of documents in selected year and month.
    * `String year` - selected year
    * `String month` - selected month
    * `Integer count` - number of documents by month in selected year and month.
* `archive_year.ftl` - an archive of documents in selected year.
    * `String year` - selected year
    * `Map<String, Integer> count` - number of documents by month in selected year.
* `archive_tag.ftl` - an archive of documents tagged by selected tag.
    * `String tag` - selected tag
* `error.ftl` - this is an optional error page

Templates are mapped to URLs:

Index:

    http://domain/

Blog:

    http://domain/blog/
    http://domain/blog/page/x/

Post:

    http://domain/blog/year/month/document-id/

Archive:

    http://domain/year/
    http://domain/year/page/x/
    http://domain/year/month/
    http://domain/year/month/page/x/
    http://domain/tag/tag-name
    http://domain/tag/tag-name/page/x/

## Common properties

Besides from template-specific view properties, the following properties are supported:

* `String baseUrl` - base url as set in the configuration
* `String facebookAppId` - Facebook app id as set in the configuration
* `DocumentService documentService` - Exposes `DocumentService` to the view, see the [API reference][1]
* `ViewHelperService viewHelperService` - Exposes `ViewHelperService` to the view, look down for [API reference][1]

[1]: /2015/01/api-reference/

## Adding pages

You can add additional pages anytime, by creating a template for it. The name of the template without the extension
identifies the page, which is then accessible by URL:

    http://domain/page-id/

The page view has access to all the common view properties.

## Adding documents

You just put Markdown files in `/documents`. You can nest them in subdirectories as much as you like.
The only important part is document id, which is composed from:

* publication year
* publication month
* the file name without the extension.

This is important, because it's a part of the URL of the article. So if the file is named `test-12.md` published 05/2015, the URL will be:

    http://domain/2015/05/test-123/

If you want to refer to an image from the document, you put it under `/documents/resources`. For example if you put
`image.png` to `/documents/resources/2015/05`, the URL will be:

    http://domain/wp-content/uploads/2015/05/image.png

## Meta information

You can specify optional meta-information in a comment block somewhere in the document, like this:

    title: Documentation
    description: General overview to get you started with creating a custom front-end.
    date: 01/01/2015
    tags: Akanke, Documentation

The meaning of those properties is:

* title - display-friendly title, if absent this is created from the file name
* description - this can be used to feed the page, empty if absent
* date - published date, guessed from file creation time
* tags - comma-separated lists of tags