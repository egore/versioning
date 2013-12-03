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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.collections4.CollectionUtils;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "user")
public class User extends IntegerDbObject {

	private static final long serialVersionUID = 5341258879773745459L;

	private String name;
	private String login;
	private String password;
	private String email;
	private List<Role> roles = new ArrayList<>(0);

	@Column(nullable = false, length = 255)
	@NotNull
	@Size(min = 1, max = 255)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false, length = 63)
	@NotNull
	@Size(min = 1, max = 63)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(nullable = false, length = 40)
	@NotNull
	@Size(min = 40, max = 40)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(nullable = false, length = 255)
	@NotNull
	@Size(min = 1, max = 255)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id"))
	@OrderBy("name")
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public boolean hasRole(Role role) {
		if (CollectionUtils.isEmpty(roles)) {
			return false;
		}
		return roles.contains(role);
	}

	public boolean hasPermission(Permission permission) {
		if (CollectionUtils.isEmpty(roles)) {
			return false;
		}
		for (Role role : roles) {
			if (role.hasPermission(permission)) {
				return true;
			}
		}
		return false;
	}

}
