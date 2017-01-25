/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('ServerDetailController', ServerDetailController);

	ServerDetailController.$inject = ['$route', '$location', 'Server', 'Project', 'VcsHost', '$http', 'ActionReplacementService', 'Upload', 'UploadDataUrl'];

	function ServerDetailController($route, $location, Server, Project, VcsHost, $http, ActionReplacementService, Upload, UploadDataUrl) {
		/* jshint validthis: true */
		var vm = this;

		vm.projects = [];
		vm.vcs_hosts = [];
		vm.history = {'entries':[]};
		vm.deployedVersions = [];
		vm.deployableVersions = [];
		vm.file = undefined;

		vm.isSelected = isSelected;
		vm.toggleSelection = toggleSelection;
		vm.addVariable = addVariable;
		vm.removeVariable = removeVariable;
		vm.ActionReplacementService = ActionReplacementService;

		activate();

		function activate() {
			Project.query(function(projects) {
				vm.projects = projects;
			});
			VcsHost.query(function(vcs_hosts) {
				vm.vcs_hosts = vcs_hosts;
			});
			if ($route.current.params.id == 'new') {
				vm.server = new Server();
				vm.save = function() {
					vm.server.$save(postSave);
				};
			} else {
				Server.get({id: $route.current.params.id}).$promise.then(function(server) {
					vm.server = server;
					if (vm.server.iconId) {
						vm.file = 'rest/binary_data/' + vm.server.iconId;
					}
				});
				$http.get('rest/history/server/' + $route.current.params.id).then(function (response) {
					vm.history = response.data;
				});
				$http.get('rest/server/' + $route.current.params.id + '/deployed').then(function (response) {
					vm.deployedVersions = response.data;
				});
				$http.get('rest/server/' + $route.current.params.id + '/deployable').then(function (response) {
					vm.deployableVersions = response.data;
				});
				vm.save = function() {
					vm.server.$update(postSave);
				};
			}
		}

		function postSave(server) {
			if (vm.file) {
				if (angular.isObject(vm.file)) {
					Upload.upload({url: 'rest/server/upload/' + server.id, data: {file: vm.file}}).then(function(response) {
						$location.path('/servers');
					}, function(response) {
						alert(response.data.message);
					});
				} else {
					$location.path('/servers');
				}
			} else if (server.iconId) {
				$http.delete('rest/binary_data/' + server.iconId).then(function(response) {
					$location.path('/servers');
				}, function(response) {
					alert(response.data.message);
				});
			} else {
				$location.path('/servers');
			}
		}

		function isSelected(projectId) {
			return vm.server && vm.server.configuredProjectIds.indexOf(projectId) !== -1;
		}

		function toggleSelection(projectId) {
			var index = vm.server.configuredProjectIds.indexOf(projectId);
			if (index !== -1) {
				vm.server.configuredProjectIds.splice(index, 1);
			} else {
				vm.server.configuredProjectIds.push(projectId);
			}
		}

		function addVariable() {
			var newVariable = {'name': '', 'value': ''};
			if (!vm.server.variables) {
				vm.server.variables = [newVariable];
			} else {
				vm.server.variables.push(newVariable);
			}
		}

		function removeVariable(variable) {
			var index = vm.server.variables.indexOf(variable);
			if (index !== -1) {
				vm.server.variables.splice(index, 1);
			}
		}

	}
})();
