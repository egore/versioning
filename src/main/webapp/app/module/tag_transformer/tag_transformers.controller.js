/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('TagTransformerListController', TagTransformerListController);

	TagTransformerListController.$inject = ['$location', '$rootScope', 'TagTransformer', '$uibModal', '$route'];

	function TagTransformerListController($location, $rootScope, TagTransformer, $uibModal, $route) {
		/* jshint validthis: true */
		var vm = this;

		vm.predicate = 'name';
		vm.reverse = false;
		vm.order = order;
		vm.add = add;
		vm.searchFilter = searchFilter;
		vm.remove = remove;

		activate();

		function activate() {
			TagTransformer.query(function(tag_transformers) {
				vm.tag_transformers = tag_transformers;
			});
		}

		function order(predicate) {
			vm.reverse = (vm.predicate === predicate) ? !vm.reverse : false;
			vm.predicate = predicate;
		}

		function add() {
			$location.path('/tag_transformers/new');
		}

		function searchFilter(string) {
			$rootScope.search = string;
		}

		function remove(tag_transformer) {
			$uibModal.open({
				templateUrl: 'app/scripts/controller/confirm.html',
				controller: 'ConfirmController',
				controllerAs: 'vm',
				resolve: {
					entityname: function() { return tag_transformer.name; },
					okCallback: function() {
						return function() {
							tag_transformer.$delete(function() {
								$route.reload();
							});
						};
					}
				}
			});
		}

	}
})();
