/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('VcsHostDetailController', VcsHostDetailController);

	VcsHostDetailController.$inject = ['$route', '$location', 'VcsHost', 'Project'];

	function VcsHostDetailController($route, $location, VcsHost, Project) {
		/* jshint validthis: true */
		var vm = this;

		vm.assigned_projects = [];

		activate();

		function activate() {
			if ($route.current.params.id == 'new') {
				vm.vcs_host = new VcsHost();
				vm.save = function() {
					vm.vcs_host.$save(function() {
						$location.path('/vcs_hosts');
					});
				};
			} else {
				VcsHost.get({id: $route.current.params.id}).$promise.then(function(vcs_host) {
					vm.vcs_host = vcs_host;
					Project.query({ids: vcs_host.projectIds}).$promise.then(function(assigned_projects) {
						vm.assigned_projects = assigned_projects;
					});
				});
				vm.save = function() {
					vm.vcs_host.$update(function() {
						$location.path('/vcs_hosts');
					});
				};
			}
		}
	}
})();
