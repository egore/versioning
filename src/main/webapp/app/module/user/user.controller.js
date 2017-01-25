/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('UserDetailController', UserDetailController);

	UserDetailController.$inject = ['$route', '$location', 'User', 'Role'];

	function UserDetailController($route, $location, User, Role) {
		/* jshint validthis: true */
		var vm = this;

		vm.isSelected = isSelected;
		vm.toggleSelection = toggleSelection;

		activate();

		function activate() {
			Role.query(function(roles) {
				vm.roles = roles;
			});
			if ($route.current.params.id == 'new') {
				vm.user = new User();
				vm.user.roleIds = [];
				vm.save = function() {
					if (vm.user.password === '') {
						vm.user.password = null;
					}
					delete vm.user.passwordRepeated;
					vm.user.$save(function() {
						$location.path('/users');
					});
				};
			} else {
				User.get({id: $route.current.params.id}).$promise.then(function(user) {
					vm.user = user;
				});
				vm.save = function() {
					if (vm.user.password === '') {
						vm.user.password = null;
					}
					delete vm.user.passwordRepeated;
					vm.user.$update(function() {
						$location.path('/users');
					});
				};
			}
		}

		function isSelected(roleId) {
			return vm.user && vm.user.roleIds.indexOf(roleId) !== -1;
		}

		function toggleSelection(roleId) {
			var index = vm.user.roleIds.indexOf(roleId);
			if (index !== -1) {
				vm.user.roleIds.splice(index, 1);
			} else {
				vm.user.roleIds.push(roleId);
			}
		}
	}
})();
