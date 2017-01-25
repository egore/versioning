/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('ProjectListController', ProjectListController);

	ProjectListController.$inject = ['$location', '$rootScope', '$uibModal', '$route', 'Project', 'Server', 'VcsHost'];

	function ProjectListController($location, $rootScope, $uibModal, $route, Project, Server, VcsHost) {
		/* jshint validthis: true */
		var vm = this;

		vm.projects = [];
		vm.servers = {};
		vm.vcs_hosts = {};
		vm.predicate = 'name';
		vm.reverse = false;
		vm.total = 0;
		vm.pagesize = 20;
		vm.currentPage = 1;
		vm.maxPages = 20;

		vm.order = order;
		vm.add = add;
		vm.searchFilter = searchFilter;
		vm.remove = remove;
		vm.loadData = loadData;

		activate();

		function activate() {
			vm.is_loading = true;
			VcsHost.query(function(vcs_hosts) {
				Server.query(function(servers) {
					loadData();
					angular.forEach(servers, function(server) {
						vm.servers[server.id] = server;
					});
				});
				angular.forEach(vcs_hosts, function(vcs_host) {
					vm.vcs_hosts[vcs_host.id] = vcs_host;
				});
			})
		}

		function order(predicate) {
			vm.reverse = (vm.predicate === predicate) ? !vm.reverse : false;
			vm.predicate = predicate;
			vm.loadData();
		}

		function add() {
			$location.path('/projects/new');
		}

		function searchFilter(string) {
			$rootScope.search = string;
		}

		function remove(project) {
			$uibModal.open({
				templateUrl: 'app/scripts/controller/confirm.html',
				controller: 'ConfirmController',
				controllerAs: 'vm',
				resolve: {
					entityname: function() { return project.name; },
					okCallback: function() {
						return function() {
							project.$delete(function() {
								$route.reload();
							});
						};
					}
				}
			});
		}

		function loadData() {
			vm.is_loading = true;
			Project.query({'limit': vm.pagesize, 'offset': ((vm.currentPage - 1) * vm.pagesize), 'sortColumn': vm.predicate, 'ascending': !vm.reverse}, function(projects) {
				var total = (arguments[1]('Result-Count'));
				if (!total) {
					total = projects.length;
				}
				vm.total = total;
				vm.projects = projects;
				vm.is_loading = false;
			});
		}

	}
})();
