/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('ActionReplacementService', ActionReplacementService);

	function ActionReplacementService() {
		var vm = {};

		vm.addActionReplacement = addActionReplacement;
		vm.removeActionReplacement = removeActionReplacement;
		vm.addWildcard = addWildcard;
		vm.removeWildcard = removeWildcard;
		vm.addReplacement = addReplacement;
		vm.removeReplacement = removeReplacement;
		vm.addReplacementfile = addReplacementfile;
		vm.removeReplacementfile = removeReplacementfile;

		function addActionReplacement(entity) {
			var newVariable = {'wildcards': ['']};
			if (!entity.actionReplacements) {
				entity.actionReplacements = [newVariable];
			} else {
				entity.actionReplacements.push(newVariable);
			}
		}

		function removeActionReplacement(entity, actionReplacement) {
			var index = entity.actionReplacements.indexOf(actionReplacement);
			if (index !== -1) {
				entity.actionReplacements.splice(index, 1);
			}
		}

		function addWildcard(actionReplacement) {
			var newVariable = '';
			if (!actionReplacement.wildcards) {
				actionReplacement.wildcards = [newVariable];
			} else {
				actionReplacement.wildcards.push(newVariable);
			}
		}

		function removeWildcard(actionReplacement, wildcard) {
			var index = actionReplacement.wildcards.indexOf(wildcard);
			if (index !== -1) {
				actionReplacement.wildcards.splice(index, 1);
			}
		}

		function addReplacement(actionReplacement) {
			var newVariable = {'variable': '', 'value': ''};
			if (!actionReplacement.replacements) {
				actionReplacement.replacements = [newVariable];
			} else {
				actionReplacement.replacements.push(newVariable);
			}
		}

		function removeReplacement(actionReplacement, replacement) {
			var index = actionReplacement.replacements.indexOf(replacement);
			if (index !== -1) {
				actionReplacement.replacements.splice(index, 1);
			}
		}

		function addReplacementfile(actionReplacement) {
			var newVariable = '';
			if (!actionReplacement.replacementfiles) {
				actionReplacement.replacementfiles = [newVariable];
			} else {
				actionReplacement.replacementfiles.push(newVariable);
			}
		}

		function removeReplacementfile(actionReplacement, replacementfile) {
			var index = actionReplacement.replacementfiles.indexOf(replacementfile);
			if (index !== -1) {
				actionReplacement.replacementfiles.splice(index, 1);
			}
		}

		return vm;
	}
})();
