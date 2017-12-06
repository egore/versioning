/// <reference path="../../../../../../node_modules/@types/angular-resource/index.d.ts" />
//@ts-check

(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('Project', Project);

	/**
	 * @param {ng.resource.IResourceService} $resource
	 */
	function Project($resource) {
		return $resource('rest/project/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
