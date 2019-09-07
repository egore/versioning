/// <reference path="../../../../../typings/index.d.ts" />
(function() {
    'use strict';

    angular
        .module('versioningApp', ['ngResource', 'ngRoute', 'ui.bootstrap', 'ngSanitize', 'appframework', 'angularMoment'])
        .config(['$locationProvider', config])
        .run(run);

    function config($locationProvider) {
        $locationProvider.hashPrefix('');
    }

    function run(amMoment) {
        amMoment.changeLocale('de');
    }

})();

