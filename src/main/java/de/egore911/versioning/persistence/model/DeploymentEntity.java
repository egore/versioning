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

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import de.egore911.appframework.persistence.model.IntegerDbObject;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "deployment", uniqueConstraints = { @UniqueConstraint(name = "deployment__version_server", columnNames = {
		"id_version", "id_server" }) })
public class DeploymentEntity extends IntegerDbObject {

	private static final long serialVersionUID = 1049276965538023559L;

	private VersionEntity version;
	private ServerEntity server;
	private LocalDateTime deployment;
	private LocalDateTime undeployment;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_version", nullable = false)
	@NotNull
	public VersionEntity getVersion() {
		return version;
	}

	public void setVersion(VersionEntity version) {
		this.version = version;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_server", nullable = false)
	@NotNull
	public ServerEntity getServer() {
		return server;
	}

	public void setServer(ServerEntity server) {
		this.server = server;
	}

	public LocalDateTime getDeployment() {
		return deployment;
	}

	public void setDeployment(LocalDateTime deployment) {
		this.deployment = deployment;
	}

	public LocalDateTime getUndeployment() {
		return undeployment;
	}

	public void setUndeployment(LocalDateTime undeployment) {
		this.undeployment = undeployment;
	}
}
