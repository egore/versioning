/// <reference path="../../../../../../node_modules/@types/angular/index.d.ts" />
/// <reference path="../../../../../../node_modules/@types/angular-resource/index.d.ts" />
//@ts-check

(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('Deployment', Deployment);

	/**
	 * @param {ng.resource.IResourceService} $resource
	 * @param {ng.IHttpService} $http
	 */
	function Deployment($resource, $http) {
		return $resource('rest/deployment/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
