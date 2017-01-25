/// <reference path="../../../../../../typings/globals/angular/index.d.ts" />

(function() {
	'use strict';

	angular.module('versioningApp')
		.directive('actionExtraction', actionExtraction);

	function actionExtraction(ActionExtractionService) {
		return {
			templateUrl: 'app/scripts/directive/actionExtraction.html',
			restrict: 'E',
			replace: true,
			scope: {
				entity: '=',
				maven_repository_lookup: '=',
				maven_repositories: '='
			},
			link: function(scope, elm, attrs, ctrl) {
				scope.service = ActionExtractionService;
			}
		};
	}
})();
