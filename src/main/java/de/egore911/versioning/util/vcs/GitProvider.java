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
package de.egore911.versioning.util.vcs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.TransportGitSsh;
import org.eclipse.jgit.transport.OpenSshConfig.Host;
import org.eclipse.jgit.util.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import de.egore911.versioning.persistence.model.ProjectEntity;

/**
 * Git based information provider, based on jgit.
 *
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class GitProvider extends Provider {

	private static final Logger LOG = LoggerFactory
			.getLogger(GitProvider.class);

	static {
		// Wire up our logging redirection
		JSch.setLogger(new JschToSlf4j());
	}

	private JschConfigSessionFactory sshSessionFactory;

	public GitProvider(ProjectEntity project) {
		super(project);
		if (StringUtils.isNotEmpty(project.getVcsHost().getSshkey())) {
			sshSessionFactory = new JschConfigSessionFactory() {
				@Override
				protected void configure(Host hc, Session session) {
				}
				@Override
				protected void configureJSch(JSch jsch) {
					super.configureJSch(jsch);
					try {
						File sshkeyFile = File.createTempFile("sshkey", "id_rsa");
						sshkeyFile.deleteOnExit();
						try (FileOutputStream fos = new FileOutputStream(sshkeyFile)) {
							IOUtils.write(project.getVcsHost().getSshkey(), fos, StandardCharsets.US_ASCII);
						}
						jsch.addIdentity(sshkeyFile.getAbsolutePath());
					} catch (JSchException | IOException e) {
						LOG.error(e.getMessage(), e);
					}
				}
			};
		}
	}

	@Override
	public boolean tagExistsImpl(String tagName) {
		InMemoryRepository repo = initRepository();

		// Ask for the remote tags
		LsRemoteCommand command = new LsRemoteCommand(repo);
		initCredentials(command);
		command.setTags(true);
		try {
			Collection<Ref> tags = command.call();
			for (Ref tag : tags) {
				// Tag found, all is fine
				if (tag.getName().equals("refs/tags/" + tagName)) {
					return true;
				}
			}
			// Tag not found
			return false;
		} catch (GitAPIException e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
	}

	private void initCredentials(LsRemoteCommand command) {
		command.setTransportConfigCallback(new TransportConfigCallback() {
			@Override
			public void configure(Transport transport) {
				if (transport instanceof TransportGitSsh && sshSessionFactory != null) {
					SshTransport sshTransport = (TransportGitSsh) transport;
					sshTransport.setSshSessionFactory(sshSessionFactory);
				}
			}
		});
	}

	private InMemoryRepository initRepository() {
		DfsRepositoryDescription repoDesc = new DfsRepositoryDescription(
				"git - " + project.getName() + " on "
						+ project.getVcsHost().getName());
		InMemoryRepository repo = new InMemoryRepository(repoDesc) {
			@Override
			public org.eclipse.jgit.util.FS getFS() {
				// Hack the InMemoryRepository to have a valid FS, eventhough it
				// does not have one. Otherwise the LsRemoteCommand will crash
				// with a NPE

				FS fs = super.getFS();
				if (fs == null) {
					fs = FS.DETECTED;
				}
				return fs;
			}
		};

		// Mock the configuration to contain the origin url
		StoredConfig config = repo.getConfig();
		config.setString("remote", "origin", "url",
				project.getCompleteVcsPath());
		return repo;
	}

	@Override
	protected List<Tag> getTagsImpl() {
		InMemoryRepository repo = initRepository();
		List<Tag> result = new ArrayList<>();

		// Ask for the remote tags
		LsRemoteCommand command = new LsRemoteCommand(repo);
		initCredentials(command);
		command.setTags(true);
		try {
			Collection<Ref> tags = command.call();
			result.addAll(tags.stream()
					.map(tag -> new Tag(null, tag.getName().replace("refs/tags/", "")))
					.collect(Collectors.toList()));
			return result;
		} catch (GitAPIException e) {
			LOG.error(e.getMessage(), e);
			return Collections.emptyList();
		}
	}
}
