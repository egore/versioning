/// <reference path="../../../../../../typings/globals/angular/index.d.ts" />

(function() {
	'use strict';

	angular.module('versioningApp')
		.directive('actionCheckout', actionCheckout);

	function actionCheckout(ActionCheckoutService) {
		return {
			templateUrl: 'app/scripts/directive/actionCheckout.html',
			restrict: 'E',
			replace: true,
			scope: {
				entity: '='
			},
			link: function(scope, elm, attrs, ctrl) {
				scope.service = ActionCheckoutService;
			}
		};
	}
})();
