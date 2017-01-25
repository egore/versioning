/// <reference path="../../../../../../typings/globals/angular/index.d.ts" />

(function() {
	'use strict';

	angular.module('versioningApp')
		.directive('actionReplacement', actionReplacement);

	function actionReplacement(ActionReplacementService) {
		return {
			templateUrl: 'app/scripts/directive/actionReplacement.html',
			restrict: 'E',
			replace: true,
			scope: {
				entity: '='
			},
			link: function(scope, elm, attrs, ctrl) {
				scope.service = ActionReplacementService;
			}
		};
	}
})();
