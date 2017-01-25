/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('Version', Version);

	function Version($resource) {
		return $resource('rest/version/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
