'use strict';

angular.module('app')

    .directive('facebook', ['FB', function (FB) {
        return {
            restrict: "E",
            replace: true,
            template: "<div id='fb-root'></div>",
            compile: function () {
                return {
                    post: function (scope, el, attrs) {
                        var fbAppId = attrs.appId || '';

                        var fb_params = {
                            appId: attrs.appId || "",
                            cookie: attrs.cookie || true,
                            status: attrs.status || true,
                            xfbml: attrs.xfbml || true
                        };

                        // Setup the post-load callback
                        window.fbAsyncInit = function () {
                            FB._init(fb_params);
                            scope.$emit('facebookLoaded');
                        };

                        (function (d, s, id) {
                            var js, fjs = d.getElementsByTagName(s)[0];
                            if (d.getElementById(id)) return;
                            js = d.createElement(s);
                            js.id = id;
                            js.async = true;
                            js.src = "//connect.facebook.net/en_US/all.js";
                            fjs.parentNode.insertBefore(js, fjs);
                        }(document, 'script', 'facebook-jssdk', fbAppId));
                    }
                }
            }
        };
    }]);