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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import de.egore911.appframework.persistence.model.IntegerDbObject;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "action_replacement")
public class ActionReplacementEntity extends IntegerDbObject {

	private static final long serialVersionUID = 3366431865064864620L;

	private ProjectEntity project;
	private ServerEntity server;
	private List<String> wildcards = new ArrayList<>(0);
	private List<ReplacementEntity> replacements = new ArrayList<>(0);
	private List<String> replacementfiles = new ArrayList<>(0);

	@ManyToOne
	@JoinColumn(name = "id_project")
	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

	@ManyToOne
	@JoinColumn(name = "id_server")
	public ServerEntity getServer() {
		return server;
	}

	public void setServer(ServerEntity server) {
		this.server = server;
	}

	@OrderBy("value")
	@ElementCollection
	@CollectionTable(name = "wildcard", joinColumns = @JoinColumn(name = "id_action_replace", nullable = false))
    @Column(name = "value")
	public List<String> getWildcards() {
		return wildcards;
	}

	public void setWildcards(List<String> wildcards) {
		this.wildcards = wildcards;
	}

	@OneToMany(mappedBy = "actionReplacement", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("variable,value")
	public List<ReplacementEntity> getReplacements() {
		return replacements;
	}

	public void setReplacements(List<ReplacementEntity> replacements) {
		this.replacements = replacements;
	}

	@OrderBy("value")
	@ElementCollection
	@CollectionTable(name = "replacementfile", joinColumns = @JoinColumn(name = "id_action_replace", nullable = false))
    @Column(name = "value")
	public List<String> getReplacementfiles() {
		return replacementfiles;
	}

	public void setReplacementfiles(List<String> replacementfiles) {
		this.replacementfiles = replacementfiles;
	}

}
