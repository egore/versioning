/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('ActionCheckoutService', ActionCheckoutService);

	function ActionCheckoutService() {
		var vm = {};

		vm.addActionCheckout = addActionCheckout;
		vm.removeActionCheckout = removeActionCheckout;

		function addActionCheckout(entity) {
			var newVariable = {
				extractions: [{
					targetPath: ''
				}]
			};
			if (!entity.actionCheckouts) {
				entity.actionCheckouts = [newVariable];
			} else {
				entity.actionCheckouts.push(newVariable);
			}
		}

		function removeActionCheckout(entity, actionCheckout) {
			var index = entity.actionCheckouts.indexOf(actionCheckout);
			if (index !== -1) {
				entity.actionCheckouts.splice(index, 1);
			}
		}

		return vm;
	}
})();
