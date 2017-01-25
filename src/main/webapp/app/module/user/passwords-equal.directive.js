/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular
		.module('versioningApp')
		.directive('equal', equal);

	function equal() {
		return {
			require: 'ngModel',
			restrict: 'A',
			link: function(scope, elm, attrs, ctrl) {
				scope.$watch(attrs.ngModel, function() {
					validate();
				});

				attrs.$observe('equal', function() {
					validate();
				});

				var validate = function() {
					var ownValue = ctrl.$viewValue || '';
					var compareValue = attrs.equal || '';
					ctrl.$setValidity('equal', ownValue === compareValue);
				};
			}
		};
	}

})();

