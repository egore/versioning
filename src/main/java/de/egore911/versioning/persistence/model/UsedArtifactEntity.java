package de.egore911.versioning.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Christoph Brill &lt;christoph.brill@cgm.com&gt;
 * @since 27.04.2017 12:21
 */
@Entity(name = "UsedArtifact")
@Table(name = "used_artifact")
public class UsedArtifactEntity extends AbstractArtifactEntity {

	private static final long serialVersionUID = -7211659693730169027L;

	private VerificationEntity verification;
	private LocalDateTime lastSeen;

    @ManyToOne(optional = false)
    @JoinColumn(name = "verification_id", nullable = false)
    @NotNull
    public VerificationEntity getVerification() {
        return verification;
    }

    public void setVerification(VerificationEntity verification) {
        this.verification = verification;
    }

    @Column(name = "last_seen")
	public LocalDateTime getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(LocalDateTime lastSeen) {
		this.lastSeen = lastSeen;
	}
}
