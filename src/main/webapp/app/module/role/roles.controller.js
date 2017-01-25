/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('RoleListController', RoleListController);

	RoleListController.$inject = ['$location', '$rootScope', '$http', 'Role', '$uibModal', '$route'];

	function RoleListController($location, $rootScope, $http, Role, $uibModal, $route) {
		/* jshint validthis: true */
		var vm = this;

		vm.predicate = 'name';
		vm.reverse = false;
		vm.order = order;
		vm.add = add;
		vm.searchFilter = searchFilter;
		vm.isSelected = isSelected;
		vm.remove = remove;

		activate();

		function activate() {
			Role.query(function(roles) {
				vm.roles = roles;
			});

			$http.get('rest/permissions').then(function(response) {
				vm.permissions = response.data;
			});
		}

		function order(predicate) {
			vm.reverse = (vm.predicate === predicate) ? !vm.reverse : false;
			vm.predicate = predicate;
		}

		function add() {
			$location.path('/roles/new');
		}

		function searchFilter(string) {
			$rootScope.search = string;
		}

		function isSelected(permission, role) {
			return role.permissions.indexOf(permission) !== -1;
		}

		function remove(role) {
			$uibModal.open({
				templateUrl: 'app/scripts/controller/confirm.html',
				controller: 'ConfirmController',
				controllerAs: 'vm',
				resolve: {
					entityname: function() { return role.name; },
					okCallback: function() {
						return function() {
							role.$delete(function() {
								$route.reload();
							});
						};
					}
				}
			});
		}

	}
})();
