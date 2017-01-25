/// <reference path="../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular
		.module('versioningApp')
		.config(['$translateProvider', config]);

/*
	angular
		.module('versioningApp')
		.factory('nonTranslatedTextFinder', function () {
			return {
				setLocale: function () {},
				getInterpolationIdentifier: function () { return 'nonTranslatedTextFinder'; },
				interpolate: function (string) { return '[[' + string + ']]'; }
			}
		});
*/

	function config($translateProvider, $translate) {
		$translateProvider
			.useSanitizeValueStrategy('escape')
			.useStaticFilesLoader({
				prefix: 'app/scripts/i18n/',
				suffix: '.json'
			})
//			.useInterpolation('nonTranslatedTextFinder')
			.registerAvailableLanguageKeys(['en', 'de'], {
				'en_*': 'en',
				'de_*': 'de'
			})
			.fallbackLanguage('en')
			.determinePreferredLanguage();
		moment.locale($translateProvider.preferredLanguage());
	}
})();
