/// <reference path="../../../../../../node_modules/@types/angular/index.d.ts" />
/// <reference path="../../../../../../node_modules/@types/angular-route/index.d.ts" />
//@ts-check

(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('ProjectDetailController', ProjectDetailController);

	ProjectDetailController.$inject = ['$route', '$location', 'Project', 'VcsHost', 'TagTransformer', 'Server', 'MavenRepository'];

	/**
	 * @param {ng.route.IRouteService} $route
	 * @param {ng.ILocationService} $location
	 */
	function ProjectDetailController($route, $location, Project, VcsHost, TagTransformer, Server, MavenRepository) {
		/* jshint validthis: true */
		var vm = this;

		vm.vcs_hosts = [];
		vm.vcs_hosts_lookup = {};
		vm.tag_transformers = [];
		vm.tag_transformers_lookup = {};
		vm.maven_repositories = [];
		vm.maven_repositories_lookup = {};

		vm.isSelectedServer = isSelectedServer;
		vm.toggleSelectionServer = toggleSelectionServer;

		activate();

		function activate() {
			MavenRepository.query(function(maven_repositories) {
				vm.maven_repositories = maven_repositories;
				angular.forEach(maven_repositories, function(maven_repository) {
					vm.maven_repositories_lookup[maven_repository.id] = maven_repository;
				});
			});
			VcsHost.query(function(vcs_hosts) {
				TagTransformer.query(function(tag_transformers) {
					Server.query(function(servers) {
						vm.servers = servers;
					});
					vm.tag_transformers = tag_transformers;
					angular.forEach(tag_transformers, function(tag_transformer) {
						vm.tag_transformers_lookup[tag_transformer.id] = tag_transformer;
					});

					if ($route.current.params.id == 'new') {
						vm.project = new Project();
						vm.project.vcsHostId = vcs_hosts.length > 0 ? vcs_hosts[0].id : undefined;
						vm.save = function() {
							vm.project.$save(function() {
								$location.path('/projects');
							}, function(error) {
								console.log(error);
							});
						};
					} else {
						Project.get({id: $route.current.params.id}).$promise.then(function(project) {
							vm.project = project;
						});
						vm.save = function() {
							vm.project.$update(function() {
								$location.path('/projects');
							}, function(error) {
								console.log(error);
							});
						};
					}
				});
				vm.vcs_hosts = vcs_hosts;
				angular.forEach(vcs_hosts, function(vcs_host) {
					vm.vcs_hosts_lookup[vcs_host.id] = vcs_host;
				});
			});
		}

		function isSelectedServer(serverId) {
			return vm.project && vm.project.configuredServerIds && vm.project.configuredServerIds.indexOf(serverId) !== -1;
		}

		function toggleSelectionServer(serverId) {
			var index = vm.project.configuredServerIds.indexOf(serverId);
			if (index !== -1) {
				vm.project.configuredServerIds.splice(index, 1);
			} else {
				vm.project.configuredServerIds.push(serverId);
			}
		}


	}
})();
