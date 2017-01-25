/// <reference path="../../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.factory('ActionExtractionService', ActionExtractionService);

	function ActionExtractionService() {
		var vm = {};

		vm.addActionExtraction = addActionExtraction;
		vm.removeActionExtraction = removeActionExtraction;
		vm.addExtraction = addExtraction;
		vm.removeExtraction = removeExtraction;
		vm.addMavenArtifact = addMavenArtifact;
		vm.addSpacerUrl = addSpacerUrl;
		vm.removeMavenArtifact = removeMavenArtifact;
		vm.removeSpacerUrl = removeSpacerUrl;

		function addActionExtraction(entity) {
			var newVariable = {
				extractions: [{
					source: '',
					destination: ''
				}]
			};
			if (!entity.actionExtractions) {
				entity.actionExtractions = [newVariable];
			} else {
				entity.actionExtractions.push(newVariable);
			}
		}

		function removeActionExtraction(entity, actionExtraction) {
			var index = entity.actionExtractions.indexOf(actionExtraction);
			if (index !== -1) {
				entity.actionExtractions.splice(index, 1);
			}
		}

		function addExtraction(actionExtraction) {
			var newVariable = {
				source: '',
				destination: ''
			};
			if (!actionExtraction.extractions) {
				actionExtraction.extractions = [newVariable];
			} else {
				actionExtraction.extractions.push(newVariable);
			}
		}

		function removeExtraction(actionExtraction, extraction) {
			var index = actionExtraction.extractions.indexOf(extraction);
			if (index !== -1) {
				actionExtraction.extractions.splice(index, 1);
			}
		}

		function addMavenArtifact(actionExtraction) {
			actionExtraction.mavenArtifact = {
				groupId: '',
				artifactId: '',
				packaging: ''
			};
		}

		function addSpacerUrl(actionExtraction) {
			actionExtraction.spacerUrl ='';
		}

		function removeMavenArtifact(actionExtraction) {
			delete actionExtraction.mavenArtifact;
		}

		function removeSpacerUrl(actionExtraction) {
			delete actionExtraction.spacerUrl;
		}

		return vm;
	}
})();
