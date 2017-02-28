/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('UserListController', UserListController);

	UserListController.$inject = ['$location', '$rootScope', 'User', 'Role', '$uibModal', '$route'];

	function UserListController($location, $rootScope, User, Role, $uibModal, $route) {
		/* jshint validthis: true */
		var vm = this;

		vm.predicate = 'name';
		vm.reverse = false;
		vm.order = order;
		vm.add = add;
		vm.searchFilter = searchFilter;
		vm.roleLookup = [];
		vm.remove = remove;

		activate();

		function activate() {
			Role.query(function(roles) {
				angular.forEach(roles, function(element) {
					vm.roleLookup[element.id] = element;
				});
				User.query(function(users) {
					vm.users = users;
				});
			});
		}

		function order(predicate) {
			vm.reverse = (vm.predicate === predicate) ? !vm.reverse : false;
			vm.predicate = predicate;
		}

		function add() {
			$location.path('/users/new');
		}

		function searchFilter(string) {
			$rootScope.search = string;
		}

		function remove(user) {
			$uibModal.open({
				templateUrl: 'app/scripts/controller/confirm.html',
				controller: 'ConfirmController',
				controllerAs: 'vm',
				resolve: {
					entityname: function() { return user.name; },
					okCallback: function() {
						return function() {
							user.$delete(function() {
								$route.reload();
							});
						};
					}
				}
			});
		}

	}
})();
