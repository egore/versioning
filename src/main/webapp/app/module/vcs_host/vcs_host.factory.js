/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('VcsHost', VcsHost);

	function VcsHost($resource) {
		return $resource('rest/vcs_host/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
