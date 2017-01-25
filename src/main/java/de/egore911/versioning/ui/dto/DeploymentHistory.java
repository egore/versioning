package de.egore911.versioning.ui.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DeploymentHistory {

	private List<DeploymentHistoryEntry> entries = new ArrayList<>(0);
	private LocalDateTime minDate = LocalDateTime.now();
	private LocalDateTime maxDate = LocalDateTime.now();

	public List<DeploymentHistoryEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<DeploymentHistoryEntry> entries) {
		this.entries = entries;
	}

	public LocalDateTime getMinDate() {
		return minDate;
	}

	public void setMinDate(LocalDateTime minDate) {
		this.minDate = minDate;
	}

	public LocalDateTime getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(LocalDateTime maxDate) {
		this.maxDate = maxDate;
	}

	public int getLeft() {
		return 0;
	}

	public int getRight() {
		return (int) ChronoUnit.DAYS.between(minDate, maxDate);
	}

}