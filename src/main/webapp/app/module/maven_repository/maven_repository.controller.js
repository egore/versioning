/// <reference path="../../../../../../node_modules/@types/angular/index.d.ts" />
//@ts-check

(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('MavenRepositoryDetailController', MavenRepositoryDetailController);

	MavenRepositoryDetailController.$inject = ['$route', '$location', 'MavenRepository', 'Project'];

	/**
	 * @param {ng.route.IRouteService} $route
	 * @param {ng.ILocationService} $location
	 */
	function MavenRepositoryDetailController($route, $location, MavenRepository, Project) {
		/* jshint validthis: true */
		var vm = this;

		vm.assigned_projects = [];

		activate();

		function activate() {
			if ($route.current.params.id == 'new') {
				vm.maven_repository = new MavenRepository();
				vm.save = function() {
					vm.maven_repository.$save(function() {
						$location.path('/maven_repositories');
					});
				};
			} else {
				MavenRepository.get({id: $route.current.params.id}).$promise.then(function(maven_repository) {
					vm.maven_repository = maven_repository;
					Project.query({ids: maven_repository.projectIds}).$promise.then(function(assigned_projects) {
						vm.assigned_projects = assigned_projects;
					});
				});
				vm.save = function() {
					vm.maven_repository.$update(function() {
						$location.path('/maven_repositories');
					});
				};
			}
		}
	}
})();
