/// <reference path="../../../../../../node_modules/@types/angular/index.d.ts" />
//@ts-check

(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('MavenRepository', MavenRepository);

	/**
	 * @param {ng.IHttpService} $http
	 * @param {ng.ILocationService} $location
	 */
	function MavenRepository($resource, $http) {
		return $resource('rest/maven_repository/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
