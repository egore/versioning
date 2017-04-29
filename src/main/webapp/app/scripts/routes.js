/// <reference path="../../../../../typings/index.d.ts" />
(function() {
	'use strict';

	angular.module('versioningApp')
		.config(config);

	config.$inject = ['$routeProvider'];

	/**
	 * @param {ng.route.IRouteProvider} $routeProvider
	 */
	function config($routeProvider) {

		/**
		 * @param {string} route
		 * @param {string} templateUrl
		 * @param {string} controller
		 */
		function addRoute(route, templateUrl, controller) {
			$routeProvider.when(route, {
				templateUrl: templateUrl,
				controller: controller,
				controllerAs: 'vm'
			});
		}

		addRoute('/', 'app/module/home/home.html', 'HomeController');

		addRoute('/versions', 'app/module/version/versions.html', 'VersionListController');
		addRoute('/versions/:id', 'app/module/version/version.html', 'VersionDetailController');
		addRoute('/deployments', 'app/module/deployment/deployments.html', 'DeploymentsController');

		addRoute('/vcs_hosts', 'app/module/vcs_host/vcs_hosts.html', 'VcsHostListController');
		addRoute('/vcs_hosts/:id', 'app/module/vcs_host/vcs_host.html', 'VcsHostDetailController');
		addRoute('/projects', 'app/module/project/projects.html', 'ProjectListController');
		addRoute('/projects/:id', 'app/module/project/project.html', 'ProjectDetailController');
		addRoute('/servers', 'app/module/server/servers.html', 'ServerListController');
		addRoute('/servers/:id', 'app/module/server/server.html', 'ServerDetailController');
		addRoute('/tag_transformers', 'app/module/tag_transformer/tag_transformers.html', 'TagTransformerListController');
		addRoute('/tag_transformers/:id', 'app/module/tag_transformer/tag_transformer.html', 'TagTransformerDetailController');
		addRoute('/maven_repositories', 'app/module/maven_repository/maven_repositories.html', 'MavenRepositoryListController');
		addRoute('/maven_repositories/:id', 'app/module/maven_repository/maven_repository.html', 'MavenRepositoryDetailController');

		addRoute('/verifications', 'app/module/verification/verifications.html', 'VerificationListController');
		addRoute('/verifications/:id', 'app/module/verification/verification.html', 'VerificationDetailController');

	}

})();
