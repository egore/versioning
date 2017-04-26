/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('VersionListController', VersionListController);

	VersionListController.$inject = ['$location', '$rootScope', '$http', 'Version', '$uibModal', '$route', 'Deployment', 'Paging'];

	function VersionListController($location, $rootScope, $http, Version, $uibModal, $route, Deployment, Paging) {
		/* jshint validthis: true */
		var vm = this;

		vm.paging = Paging;
		vm.deployments = [];
		vm.deployments_lookup = {};

		vm.add = add;
		vm.searchFilter = searchFilter;
		vm.isSelected = isSelected;
		vm.remove = remove;

		activate();

		function activate() {
			vm.paging.predicate = 'created';
			vm.paging.reverse = true;
			vm.paging.factory = Version;
			vm.paging.loadData();

			$http.get('rest/permissions').then(function(response) {
				vm.permissions = response.data;
			});

			Deployment.query(function(deployments) {
				vm.deployments = deployments;
				angular.forEach(deployments, function(deployment) {
					vm.deployments_lookup[deployment.id] = deployment;
				});
			});
		}

		function add() {
			$location.path('/versions/new');
		}

		function searchFilter(string) {
			$rootScope.search = string;
		}

		function isSelected(permission, version) {
			return version.permissions.indexOf(permission) !== -1;
		}

		function remove(version) {
			$uibModal.open({
				templateUrl: 'appframework/confirm.html',
				controller: 'ConfirmController',
				controllerAs: 'vm',
				resolve: {
					entityname: function() { return version.name; },
					okCallback: function() {
						return function() {
							version.$delete(function() {
								$route.reload();
							});
						};
					}
				}
			});
		}

	}
})();
