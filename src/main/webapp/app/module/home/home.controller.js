/// <reference path="../../../../../../node_modules/@types/angular/index.d.ts" />
//@ts-check

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
