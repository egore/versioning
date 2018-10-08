/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.egore911.versioning.persistence.model;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public enum Permission {
	/** Users, Roles */
	SHOW_USERS,
	ADMIN_USERS,
	SHOW_ROLES,
	ADMIN_ROLES,
	/** VCS Hosts, Projects, and Servers */
	ADMIN_SETTINGS,
	/** Create versions */
	CREATE_VERSIONS,
	ADMIN_VERSIONS,
	SHOW_VERSIONS,
	/** Create versions */
	DEPLOY,
	/** Verifications */
	SHOW_VERIFICATIONS,
	ADMIN_VERIFICATIONS,
	/** VCS hosts */
	ADMIN_VCSHOSTS,
	SHOW_VCSHOSTS,
	/** Tag transformer */
	ADMIN_TAGTRANSFORMERS,
	SHOW_TAGTRANSFORMERS,
	/** Servers */
	ADMIN_SERVERS,
	SHOW_SERVERS,
	/** Projects */
	ADMIN_PROJECTS,
	SHOW_PROJECTS,
	/** Maven repositories */
	ADMIN_MAVENREPOSITORIES,
	SHOW_MAVENREPOSITORIES
}
