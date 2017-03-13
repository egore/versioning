/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('VcsHostListController', VcsHostListController);

	VcsHostListController.$inject = ['$location', '$rootScope', 'VcsHost', '$uibModal', '$route'];

	function VcsHostListController($location, $rootScope, VcsHost, $uibModal, $route) {
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
			VcsHost.query(function(vcs_hosts) {
				vm.vcs_hosts = vcs_hosts;
			});
		}

		function order(predicate) {
			vm.reverse = (vm.predicate === predicate) ? !vm.reverse : false;
			vm.predicate = predicate;
		}

		function add() {
			$location.path('/vcs_hosts/new');
		}

		function searchFilter(string) {
			$rootScope.search = string;
		}

		function remove(vcs_host) {
			$uibModal.open({
				templateUrl: 'appframework/confirm.html',
				controller: 'ConfirmController',
				controllerAs: 'vm',
				resolve: {
					entityname: function() { return vcs_host.name; },
					okCallback: function() {
						return function() {
							vcs_host.$delete(function() {
								$route.reload();
							});
						};
					}
				}
			});
		}

	}
})();
