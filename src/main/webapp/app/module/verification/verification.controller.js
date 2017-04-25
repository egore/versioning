/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('VerificationDetailController', VerificationDetailController);

	VerificationDetailController.$inject = ['$route', '$location', 'Verification', 'Project', '$http'];

	function VerificationDetailController($route, $location, Verification, Project, $http) {
		/* jshint validthis: true */
		var vm = this;

		vm.suggest = suggest;
		vm.splitIfNecessary = splitIfNecessary;

		activate();

		function activate() {
			if ($route.current.params.id == 'new') {
				vm.verification = new Verification();
				vm.save = function() {
					vm.verification.$save(function() {
						$location.path('/verifications');
					});
				};
			} else {
				Verification.get({id: $route.current.params.id}).$promise.then(function(verification) {
					vm.verification = verification;
				});
			}
		}
		
		function suggest(type, input) {
			if (!type || !input) {
				return;
			}
			return $http.get('rest/verification/suggest/' + type + '/' + input).then(function(response) {
				return response.data;
			});
		}

		function splitIfNecessary($event) {
			$event.originalEvent.clipboardData.items[0].getAsString(function(data) {
				var match = data.match(new RegExp("([^:]+):([^:]+):([^:]+)"));
				if (match) {
					vm.verification.groupId = match[1];
					vm.verification.artifactId = match[2];
					vm.verification.version = match[3];
				}
			});
			
		}
	}
})();
