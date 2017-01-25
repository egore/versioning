/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular
		.module('versioningApp')
		.filter('permissionName', permissionName);

	permissionName.$inject = ['$filter'];

	function permissionName($filter) {
		return function(input) {
			return $filter('translate')('PERMISSION_' + input);
		};
	}
})();
