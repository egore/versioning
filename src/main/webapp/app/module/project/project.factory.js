/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('Project', Project);

	function Project($resource) {
		return $resource('rest/project/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
