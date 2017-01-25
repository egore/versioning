/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('TagTransformer', TagTransformer);

	function TagTransformer($resource) {
		return $resource('rest/tag_transformer/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
