/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('VersionListController', VersionListController);

	VersionListController.$inject = ['$location', '$rootScope', '$http', 'Version', '$uibModal', '$route', 'Deployment'];

	function VersionListController($location, $rootScope, $http, Version, $uibModal, $route, Deployment) {
		/* jshint validthis: true */
		var vm = this;

		vm.predicate = 'created';
		vm.reverse = true;
		vm.total = 0;
		vm.pagesize = 20;
		vm.currentPage = 1;
		vm.maxPages = 20;
		vm.deployments = [];
		vm.deployments_lookup = {};

		vm.order = order;
		vm.add = add;
		vm.searchFilter = searchFilter;
		vm.isSelected = isSelected;
		vm.remove = remove;
		vm.loadData = loadData;

		activate();

		function activate() {
			loadData();

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

		function order(predicate) {
			vm.reverse = (vm.predicate === predicate) ? !vm.reverse : false;
			vm.predicate = predicate;
			vm.loadData();
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

		function loadData() {
			vm.is_loading = true;
			Version.query({'limit': vm.pagesize, 'offset': ((vm.currentPage - 1) * vm.pagesize), 'sortColumn': vm.predicate, 'ascending': !vm.reverse}, function(versions) {
				var total = (arguments[1]('Result-Count'));
				if (!total) {
					total = versions.length;
				}
				vm.total = total;
				vm.versions = versions;
				vm.is_loading = false;
			});
		}

	}
})();
