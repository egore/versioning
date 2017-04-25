/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('Verification', Verification);

	function Verification($resource) {
		return $resource('rest/verification/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
