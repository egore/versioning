/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('TagTransformerDetailController', TagTransformerDetailController);

	TagTransformerDetailController.$inject = ['$route', '$location', '$http', 'TagTransformer', 'VcsHost'];

	function TagTransformerDetailController($route, $location, $http, TagTransformer, VcsHost) {
		/* jshint validthis: true */
		var vm = this;

		vm.vcs_hosts = [];
		vm.testInput = "";
		vm.testOutput = "";
		vm.performTest = performTest;

		activate();

		function activate() {
			if ($route.current.params.id == 'new') {
				vm.tag_transformer = new TagTransformer();
				vm.save = function() {
					vm.tag_transformer.$save(function() {
						$location.path('/tag_transformers');
					});
				};
			} else {
				TagTransformer.get({id: $route.current.params.id}).$promise.then(function(tag_transformer) {
					vm.tag_transformer = tag_transformer;
				});
				vm.save = function() {
					vm.tag_transformer.$update(function() {
						$location.path('/tag_transformers');
					});
				};
			}
		}

		function performTest() {
			vm.testOutput = '';
			$http.get('rest/tag_transformer/test', {params:{'testInput': vm.testInput, 'searchPattern': vm.tag_transformer.searchPattern, 'replacementPattern': vm.tag_transformer.replacementPattern}}).then(function(response) {
				vm.testOutput = response.data;
			});
		}

	}
})();
