/// <reference path="../../../../../../node_modules/@types/angular/index.d.ts" />
//@ts-check

(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('Server', Server);

	/**
	 * @param {ng.resource.IResourceService} $resource
	 */
	function Server($resource) {
		return $resource('rest/server/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
