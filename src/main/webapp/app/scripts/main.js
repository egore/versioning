/// <reference path="../../../../../typings/index.d.ts" />
(function() {
	angular
		.module('versioningApp')
		.controller('MainCtrl', MainCtrl);

	MainCtrl.$inject = ['$location', '$http'];

	/**
	 * @param {ng.ILocationService} $location
	 * @param {ng.IHttpService} $http
	 */
	function MainCtrl($location, $http) {
		/* jshint validthis: true */
		var vm = this;

		vm.permissions = [];

		vm.currentModule = currentModule;
		vm.hasPermission = hasPermission;

		activate();

		function activate() {
			$http.get('rest/version_info').then(function(response) {
				vm.version = response.data;
				$http.get('rest/permissions/my').then(function(response) {
					vm.permissions = response.data;
				});
			});
		}

		function currentModule() {
			var currentPath = $location.path();
			if (currentPath.startsWith('/')) {
				currentPath = currentPath.substring(1);
			}
			var firstSlash = currentPath.indexOf('/');
			if (firstSlash >= 0) {
				currentPath = currentPath.substring(0, firstSlash);
			}
			return currentPath;
		};

		function hasPermission(permission) {
			return $.inArray(permission, vm.permissions) >= 0;
		}

	}
})();
