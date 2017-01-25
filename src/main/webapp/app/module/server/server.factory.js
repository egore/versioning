/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('Server', Server);

	function Server($resource) {
		return $resource('rest/server/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
