/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of 
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.egore911.versioning.persistence.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "deployment", uniqueConstraints = { @UniqueConstraint(name = "deployment__version_server", columnNames = {
		"id_version", "id_server" }) })
public class Deployment extends IntegerDbObject {

	private Version version;
	private Server server;
	private LocalDateTime deployment;
	private LocalDateTime undeployment;

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_version", nullable = false)
	@NotNull
	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "id_server", nullable = false)
	@NotNull
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getDeployment() {
		return deployment;
	}

	public void setDeployment(LocalDateTime deployment) {
		this.deployment = deployment;
	}

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getUndeployment() {
		return undeployment;
	}

	public void setUndeployment(LocalDateTime undeployment) {
		this.undeployment = undeployment;
	}
}
