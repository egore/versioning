/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('MavenRepositoryListController', MavenRepositoryListController);

	MavenRepositoryListController.$inject = ['$location', '$rootScope', 'MavenRepository', '$uibModal', '$route'];

	function MavenRepositoryListController($location, $rootScope, MavenRepository, $uibModal, $route) {
		/* jshint validthis: true */
		var vm = this;

		vm.predicate = 'name';
		vm.reverse = false;
		vm.total = 0;
		vm.pagesize = 10;
		vm.currentPage = 1;
		vm.maxPages = 20;

		vm.order = order;
		vm.add = add;
		vm.searchFilter = searchFilter;
		vm.remove = remove;
		vm.loadData = loadData;

		activate();

		function activate() {
			loadData();
		}

		function order(predicate) {
			vm.reverse = (vm.predicate === predicate) ? !vm.reverse : false;
			vm.predicate = predicate;
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

		function loadData() {
			vm.is_loading = true;
			MavenRepository.query({'limit': vm.pagesize, 'offset': ((vm.currentPage - 1) * vm.pagesize)}, function(maven_repositories) {
				var total = (arguments[1]('Result-Count'));
				if (!total) {
					total = maven_repositories.length;
				}
				vm.total = total;
				vm.maven_repositories = maven_repositories;
				vm.is_loading = false;
			});
		}

	}
})();
