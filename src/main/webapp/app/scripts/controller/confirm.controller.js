/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.controller('ConfirmController', ConfirmController);

	ConfirmController.$inject = ['$uibModalInstance', 'entityname', 'okCallback'];

	function ConfirmController($uibModalInstance, entityname, okCallback) {
		/* jshint validthis: true */
		var vm = this;

		vm.entityname = entityname;
		vm.ok = ok;
		vm.cancel = cancel;
		vm.confirmButtons = [{ value: 'ok', label: 'Delete'}];
		vm.cancelButtons = [{ value: 'abort', label: 'Abort' }];

		function ok(value) {
			$uibModalInstance.dismiss(value);
			okCallback();
		}

		function cancel(value) {
			$uibModalInstance.dismiss(value);
		}
	}

})();
