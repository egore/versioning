/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('MavenRepository', MavenRepository);

	function MavenRepository($resource, $http) {
		return $resource('rest/maven_repository/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
