package de.egore911.versioning.persistence.model;

import de.egore911.appframework.persistence.model.IntegerDbObject;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Christoph Brill &lt;christoph.brill@cgm.com&gt;
 * @since 27.04.2017 12:21
 */
@MappedSuperclass
public abstract class AbstractArtifactEntity extends IntegerDbObject {

	private static final long serialVersionUID = 6027171398694692199L;

	private String groupId;
	private String artifactId;
	private String version;


	@Column(nullable = false, name = "group_id")
	@NotNull
	@Size(max = 255)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(nullable = false, name = "artifact_id")
	@NotNull
	@Size(max = 255)
	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	@Column(nullable = false, name = "version")
	@NotNull
	@Size(max = 255)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
