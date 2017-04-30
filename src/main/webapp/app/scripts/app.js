/// <reference path="../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular
		.module('versioningApp', ['ngResource', 'ngRoute', 'ui.bootstrap', 'ngSanitize', 'appframework'])
		.config(['$locationProvider', config]);

	function config($locationProvider) {
		$locationProvider.hashPrefix('');
	}
})();
