/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('Role', Role);

	function Role($resource) {
		return $resource('rest/role/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
