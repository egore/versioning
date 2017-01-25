/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('DeploymentsController', DeploymentsController);

	DeploymentsController.$inject = ['$http', '$location'];

	function DeploymentsController($http, $location) {
		/* jshint validthis: true */
		var vm = this;

		vm.deploy = deploy;
		vm.deployAll = deployAll;

		activate();

		function activate() {
			$http.get('rest/deployment/deployable').then(function(response) {
				vm.deployableVersionsPerServer = response.data;
			});
		}

		function deploy(server, version) {
			$http.get('rest/deployment/deploy/' + server.id + '/' + version.id).then(function() {
				$location.reload;
			});
		}

		function deployAll(server) {
			$http.get('rest/deployment/deploy/' + server.id).then(function() {
				$location.reload;
			});
		}

	}
})();
