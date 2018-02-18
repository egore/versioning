/// <reference path="../../../../../../node_modules/@types/angular/index.d.ts" />
//@ts-check

(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('Version', Version);

	/**
	 * @param {ng.resource.IResourceService} $resource
	 */
	function Version($resource) {
		return $resource('rest/version/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
