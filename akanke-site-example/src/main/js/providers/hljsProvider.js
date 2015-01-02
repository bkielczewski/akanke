'use strict';

angular.module('app').

    factory('hljs', ['$window', function ($window) {
        return $window.hljs;
    }]);