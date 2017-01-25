/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('User', User);

	function User($resource) {
		return $resource('rest/user/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
