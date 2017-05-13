/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('MavenRepositoryListController', MavenRepositoryListController);

	MavenRepositoryListController.$inject = ['$location', '$rootScope', 'MavenRepository', '$uibModal', '$route', '$scope', 'Paging'];

	function MavenRepositoryListController($location, $rootScope, MavenRepository, $uibModal, $route, $scope, Paging) {
		/* jshint validthis: true */
		var vm = this;

		vm.paging = Paging.pager();

		vm.add = add;
		vm.searchFilter = searchFilter;
		vm.remove = remove;

		activate();

		function activate() {
			vm.paging.predicate = 'name';
			vm.paging.factory = MavenRepository;
			vm.paging.loadData();

			$scope.$watch('search', function() {
				vm.paging.currentPage = 1;
				vm.paging.loadData();
			});
		}

		function add() {
			$location.path('/maven_repositories/new');
		}

		function searchFilter(string) {
			$rootScope.search = string;
		}

		function remove(maven_repository) {
			$uibModal.open({
				templateUrl: 'appframework/confirm.html',
				controller: 'ConfirmController',
				controllerAs: 'vm',
				resolve: {
					entityname: function() { return maven_repository.name; },
					okCallback: function() {
						return function() {
							maven_repository.$delete(function() {
								$route.reload();
							});
						};
					}
				}
			});
		}

	}
})();
