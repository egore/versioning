/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('HomeController', HomeController);

	function HomeController() {
		/* jshint validthis: true */
		var vm = this;

		vm.title = 'Welcome';
	}
})();
