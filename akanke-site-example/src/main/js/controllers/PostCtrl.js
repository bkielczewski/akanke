'use strict';

angular.module('app')

    .controller('PostCtrl', ['$scope', '$window', '$timeout', 'hljs', function ($scope, $window, $timeout, hljs) {

        hljs.initHighlightingOnLoad();

    }]);