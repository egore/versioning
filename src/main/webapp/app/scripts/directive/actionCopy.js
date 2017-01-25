/// <reference path="../../../../../../typings/globals/angular/index.d.ts" />

(function() {
	'use strict';

	angular.module('versioningApp')
		.directive('actionCopy', actionCopy);

	function actionCopy(ActionCopyService) {
		return {
			templateUrl: 'app/scripts/directive/actionCopy.html',
			restrict: 'E',
			replace: true,
			scope: {
				entity: '=',
				maven_repository_lookup: '=',
				maven_repositories: '='
			},
			link: function(scope, elm, attrs, ctrl) {
				scope.service = ActionCopyService;
			}
		};
	}
})();
