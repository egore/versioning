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
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@Entity
@Table(name = "action_replacement")
public class ActionReplacement extends IntegerDbObject {

	private static final long serialVersionUID = 3366431865064864620L;

	private Project project;
	private Server server;
	private List<Wildcard> wildcards = new ArrayList<>(0);
	private List<Replacement> replacements = new ArrayList<>(0);
	private List<Replacementfile> replacementFiles = new ArrayList<>(0);

	@ManyToOne
	@JoinColumn(name = "id_project")
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@ManyToOne
	@JoinColumn(name = "id_server")
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	@OneToMany(mappedBy = "actionReplacement", cascade = CascadeType.ALL)
	@OrderBy("value")
	public List<Wildcard> getWildcards() {
		return wildcards;
	}

	public void setWildcards(List<Wildcard> wildcards) {
		this.wildcards = wildcards;
	}

	@OneToMany(mappedBy = "actionReplacement", cascade = CascadeType.ALL)
	@OrderBy("variable,value")
	public List<Replacement> getReplacements() {
		return replacements;
	}

	public void setReplacements(List<Replacement> replacements) {
		this.replacements = replacements;
	}

	@OneToMany(mappedBy = "actionReplacement", cascade = CascadeType.ALL)
	@OrderBy("value")
	public List<Replacementfile> getReplacementFiles() {
		return replacementFiles;
	}

	public void setReplacementFiles(List<Replacementfile> replacementFiles) {
		this.replacementFiles = replacementFiles;
	}

}
