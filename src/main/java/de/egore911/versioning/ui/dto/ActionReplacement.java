/*
 * Copyright 2013-2018  Christoph Brill <egore911@gmail.com>
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
package de.egore911.versioning.ui.dto;

import java.util.List;

public class ActionReplacement extends AbstractAction {

	private List<String> wildcards;
	private List<Replacement> replacements;
	private List<String> replacementfiles;

	public List<String> getWildcards() {
		return wildcards;
	}

	public void setWildcards(List<String> wildcards) {
		this.wildcards = wildcards;
	}

	public List<Replacement> getReplacements() {
		return replacements;
	}

	public void setReplacements(List<Replacement> replacements) {
		this.replacements = replacements;
	}

	public List<String> getReplacementfiles() {
		return replacementfiles;
	}

	public void setReplacementfiles(List<String> replacementfiles) {
		this.replacementfiles = replacementfiles;
	}

}
