/// <reference path="../../../../../../typings/globals/angular/index.d.ts" />

(function() {
	'use strict';

	angular.module('versioningApp')
		.directive('projectName', projectName);

	function projectName() {
		return {
			templateUrl: 'app/scripts/directive/projectName.html',
			restrict: 'E',
			replace: true,
			scope: {
				project: '='
			}
		};
	}
})();
