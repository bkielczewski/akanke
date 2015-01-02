'use strict';

angular.module('app').

    factory('FB', ['$rootScope', function ($rootScope) {
        var fbLoaded = false;
        var _fb = {
            loaded: fbLoaded,
            _init: function (params) {
                if (window.FB) {
                    angular.extend(window.FB, _fb);
                    angular.extend(_fb, window.FB);

                    _fb.loaded = true;

                    window.FB.init(params);

                    if (!$rootScope.$$phase) {
                        $rootScope.$apply();
                    }
                }
            }
        };

        return _fb;
    }]);