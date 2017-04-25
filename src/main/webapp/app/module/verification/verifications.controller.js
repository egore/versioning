/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('VerificationListController', VerificationListController);

	VerificationListController.$inject = ['$location', '$rootScope', 'Verification', '$uibModal', '$route'];

	function VerificationListController($location, $rootScope, Verification, $uibModal, $route) {
		/* jshint validthis: true */
		var vm = this;

		vm.orderGroupId = ['groupId','artifactId']
		vm.predicate = vm.orderGroupId;
		vm.reverse = false;
		vm.order = order;
		vm.add = add;
		vm.searchFilter = searchFilter;
		vm.remove = remove;

		activate();

		function activate() {
			Verification.query(function(verifications) {
				vm.verifications = verifications;
			});
		}

		function order(predicate) {
			vm.reverse = (vm.predicate === predicate) ? !vm.reverse : false;
			vm.predicate = predicate;
		}

		function add() {
			$location.path('/verifications/new');
		}

		function searchFilter(string) {
			$rootScope.search = string;
		}

		function remove(verification) {
			$uibModal.open({
				templateUrl: 'appframework/confirm.html',
				controller: 'ConfirmController',
				controllerAs: 'vm',
				resolve: {
					entityname: function() { return verification.name; },
					okCallback: function() {
						return function() {
							verification.$delete(function() {
								$route.reload();
							});
						};
					}
				}
			});
		}

	}
})();
