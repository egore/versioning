/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('ActionCopyService', ActionCopyService);

	function ActionCopyService() {
		var vm = {};

		vm.addActionCopy = addActionCopy;
		vm.removeActionCopy = removeActionCopy;
		vm.addMavenArtifact = addMavenArtifact;
		vm.addSpacerUrl = addSpacerUrl;
		vm.removeMavenArtifact = removeMavenArtifact;
		vm.removeSpacerUrl = removeSpacerUrl;

		function addActionCopy(entity) {
			var newVariable = {};
			if (!entity.actionCopies) {
				entity.actionCopies = [newVariable];
			} else {
				entity.actionCopies.push(newVariable);
			}
		}

		function removeActionCopy(entity, actionCopy) {
			var index = entity.actionCopies.indexOf(actionCopy);
			if (index !== -1) {
				entity.actionCopies.splice(index, 1);
			}
		}

		function addMavenArtifact(actionCopy) {
			actionCopy.mavenArtifact = {
				groupId: '',
				artifactId: '',
				packaging: ''
			};
		}

		function addSpacerUrl(actionCopy) {
			actionCopy.spacerUrl ='';
		}

		function removeMavenArtifact(actionCopy) {
			delete actionCopy.mavenArtifact;
		}

		function removeSpacerUrl(actionCopy) {
			delete actionCopy.spacerUrl;
		}

		return vm;
	}
})();
