package de.egore911.versioning.ui.dto;

import java.util.List;

import de.egore911.appframework.ui.dto.AbstractDto;
import de.egore911.versioning.persistence.model.Vcs;

public class VcsHost extends AbstractDto {

	private String name;
	private Vcs vcs;
	private String uri;
	private String username;
	private String password;
	private String passwordVerify;
	private String sshkey;
	private List<Integer> projectIds;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vcs getVcs() {
		return vcs;
	}

	public void setVcs(Vcs vcs) {
		this.vcs = vcs;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordVerify() {
		return passwordVerify;
	}

	public void setPasswordVerify(String passwordVerify) {
		this.passwordVerify = passwordVerify;
	}

	public String getSshkey() {
		return sshkey;
	}

	public void setSshkey(String sshkey) {
		this.sshkey = sshkey;
	}

	public List<Integer> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<Integer> projectIds) {
		this.projectIds = projectIds;
	}

}
