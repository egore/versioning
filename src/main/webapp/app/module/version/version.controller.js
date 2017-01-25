/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('VersionDetailController', VersionDetailController);

	VersionDetailController.$inject = ['$route', '$location', 'Version', 'Project', '$http'];

	function VersionDetailController($route, $location, Version, Project, $http) {
		/* jshint validthis: true */
		var vm = this;

		vm.projects = [];
		vm.tags = [];

		vm.selectProject = selectProject;

		activate();

		function activate() {
			Project.query(function(projects) {
				vm.projects = projects;
				if ($route.current.params.id == 'new') {
					vm.version = new Version();
					vm.version.project = vm.projects[0];
					loadTags();
					vm.save = function() {
						vm.version.$save(function() {
							$location.path('/versions');
						});
					};
				} else {
					Version.get({id: $route.current.params.id}).$promise.then(function(version) {
						vm.version = version;
						loadTags();
					});
					vm.save = function() {
						vm.version.$update(function() {
							$location.path('/versions');
						});
					};
				}
			});
		}

		function loadTags() {
			$http.get('rest/project/' + vm.version.project.id + '/tags').then(function(response) {
				vm.tags = response.data;
			});
		}
	
		function selectProject(project) {
			vm.version.project = project;
			loadTags();
		}
	}
})();
