/// <reference path="../../../../../../node_modules/@types/angular/index.d.ts" />
/// <reference path="../../../../../../node_modules/@types/angular-route/index.d.ts" />
/// <reference path="../../../../../../node_modules/@types/angular-ui-bootstrap/index.d.ts" />
//@ts-check

(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('VersionListController', VersionListController);

	VersionListController.$inject = ['$location', '$rootScope', '$http', 'Version', '$uibModal', '$route', 'Deployment', 'Paging'];

	/**
	 * @param {ng.ILocationService} $location
	 * @param {ng.IRootScopeService} $rootScope
	 * @param {ng.IHttpService} $http
	 * @param {ng.ui.bootstrap.IModalService} $uibModal
	 * @param {ng.route.IRouteService} $route
	 */
	function VersionListController($location, $rootScope, $http, Version, $uibModal, $route, Deployment, Paging) {
		/* jshint validthis: true */
		var vm = this;

		vm.paging = Paging.pager();

		vm.deployments = [];
		vm.deployments_lookup = {};

		vm.add = add;
		vm.searchFilter = searchFilter;
		vm.remove = remove;

		activate();

		function activate() {
			vm.paging.predicate = 'created';
			vm.paging.reverse = true;
			vm.paging.factory = Version;
			vm.paging.loadData();

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

		function remove(version) {
			$uibModal.open({
				templateUrl: 'appframework/controller/confirm.html',
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
