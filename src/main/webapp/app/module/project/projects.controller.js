/// <reference path="../../../../../../node_modules/@types/angular/index.d.ts" />
/// <reference path="../../../../../../node_modules/@types/angular-ui-bootstrap/index.d.ts" />
//@ts-check

(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('ProjectListController', ProjectListController);

	ProjectListController.$inject = ['$location', '$rootScope', '$uibModal', '$route', 'Project', 'Server', 'VcsHost', 'Paging'];

	/**
	 * @param {ng.ILocationService} $location
	 * @param {ng.IRootScopeService} $rootScope
	 * @param {ng.ui.bootstrap.IModalService} $uibModal
	 */
	function ProjectListController($location, $rootScope, $uibModal, $route, Project, Server, VcsHost, Paging) {
		/* jshint validthis: true */
		var vm = this;

		vm.paging = Paging.pager();

		vm.projects = [];
		vm.servers = {};
		vm.vcs_hosts = {};

		vm.add = add;
		vm.searchFilter = searchFilter;
		vm.remove = remove;

		activate();

		function activate() {
			vm.paging.predicate = 'name';
			vm.paging.factory = Project;

			vm.paging.is_loading = true;
			VcsHost.query(function(vcs_hosts) {
				Server.query(function(servers) {
					vm.paging.loadData();
					angular.forEach(servers, function(server) {
						vm.servers[server.id] = server;
					});
				});
				angular.forEach(vcs_hosts, function(vcs_host) {
					vm.vcs_hosts[vcs_host.id] = vcs_host;
				});
			});
		}

		function add() {
			$location.path('/projects/new');
		}

		function searchFilter(string) {
			$rootScope.search = string;
		}

		function remove(project) {
			$uibModal.open({
				templateUrl: 'appframework/controller/confirm.html',
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

	}
})();
