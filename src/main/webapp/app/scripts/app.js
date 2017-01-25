/// <reference path="../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular
		.module('versioningApp', ['ngResource', 'ngRoute', 'ui.bootstrap', 'ngSanitize', 'pascalprecht.translate', 'angularMoment', 'ngFileUpload'])
		.config(['$locationProvider', config]);

	function config($locationProvider) {
		$locationProvider.hashPrefix('');
	}
})();
