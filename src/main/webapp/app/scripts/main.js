/// <reference path="../../../../../typings/index.d.ts" />
(function() {
	angular
		.module('versioningApp')
		.controller('MainCtrl', MainCtrl);

	MainCtrl.$inject = ['$location', '$http', '$scope', '$rootScope'];

	/**
	 * @param {ng.ILocationService} $location
	 * @param {ng.IHttpService} $http
	 */
	function MainCtrl($location, $http, $scope, $rootScope) {
		/* jshint validthis: true */
		var vm = this;

		vm.permissions = [];

		vm.currentModule = currentModule;
		vm.hasPermission = hasPermission;
		vm.getVersion = getVersion;

		activate();

		function activate() {
			$scope.$watch('state.user', function(newValue, oldValue) {
				if (newValue !== undefined && newValue.username != undefined) {
					vm.permissions = $rootScope.state.permissions;
					$http.get('rest/version_info').then(function(response) {
						vm.version = response.data;
					});
				} else {
					vm.permissions = [];
				}
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
		}

		function hasPermission(permission) {
			return $.inArray(permission, vm.permissions) >= 0;
		}

		function getVersion() {
			if (vm.version) {
				var version = vm.version.maven;
				if (version.indexOf('-SNAPSHOT', version.length - '-SNAPSHOT'.length) !== -1) {
					version += ' (' + vm.version.git + ')';
				}
				return version;
			}
		}

	}
})();
