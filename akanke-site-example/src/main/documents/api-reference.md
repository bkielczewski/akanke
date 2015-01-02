<!--
title: API Reference
description: API documentation for reference
date: 01/01/2015
tags: Akanke, API
-->
This is an API documentation for reference, that may be useful when constructing custom templates. <!--more-->

## Document

    String getId();

    String getFile();

    String getTitle();

    String getDescription();

    getContents();

    Collection<String> getTags();

    Date getDatePublished();

    FacebookStats getFacebookStats();

## FacebookStats


    int getCommentsboxCount();

    int getShareCount();

    int getLikeCount();

## Page

    long getTotalElements();

Returns the total amount of elements.

    int	getTotalPages();

Returns the number of total pages.

    List<T>	getContent();

Returns the page content as List.

    int	getNumber();

Returns the number of the current page.

    int	getNumberOfElements();

Returns the number of elements currently on this page.

    boolean	hasContent()

Returns whether it has content at all.

    boolean	hasNext()

Returns if there is a next.

    boolean	hasPrevious()

Returns if there is a previous.

    boolean	isFirst()

Returns whether the current is the first one.

    boolean	isLast()

Returns whether the current is the last one.