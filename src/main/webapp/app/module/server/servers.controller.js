/// <reference path="../../../../../../node_modules/@types/angular/index.d.ts" />
/// <reference path="../../../../../../node_modules/@types/angular-ui-bootstrap/index.d.ts" />
/// <reference path="../../../../../../node_modules/@types/angular-route/index.d.ts" />
//@ts-check

(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('ServerListController', ServerListController);

	ServerListController.$inject = ['$location', '$rootScope', 'Server', '$uibModal', '$route'];

	/**
	 * @param {ng.ILocationService} $location
	 * @param {ng.IRootScopeService} $rootScope
	 * @param {ng.ui.bootstrap.IModalService} $uibModal
	 * @param {ng.route.IRouteService} $route
	 */
	function ServerListController($location, $rootScope, Server, $uibModal, $route) {
		/* jshint validthis: true */
		var vm = this;

		vm.predicate = 'name';
		vm.reverse = false;
		vm.order = order;
		vm.add = add;
		vm.searchFilter = searchFilter;
		vm.remove = remove;

		activate();

		function activate() {
			Server.query(function(servers) {
				vm.servers = servers;
			});
		}

		function order(predicate) {
			vm.reverse = (vm.predicate === predicate) ? !vm.reverse : false;
			vm.predicate = predicate;
		}

		function add() {
			$location.path('/servers/new');
		}

		function searchFilter(string) {
			$rootScope.search = string;
		}

		function remove(server) {
			$uibModal.open({
				templateUrl: 'appframework/controller/confirm.html',
				controller: 'ConfirmController',
				controllerAs: 'vm',
				resolve: {
					entityname: function() { return server.name; },
					okCallback: function() {
						return function() {
							server.$delete(function() {
								$route.reload();
							});
						};
					}
				}
			});
		}

	}
})();
