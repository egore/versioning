/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('Deployment', Deployment);

	function Deployment($resource, $http) {
		return $resource('rest/deployment/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
