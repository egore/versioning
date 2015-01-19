package de.egore911.versioning.ui.beans;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import de.egore911.versioning.persistence.model.Permission;
import de.egore911.versioning.persistence.model.Project;
import de.egore911.versioning.util.security.RequiresPermission;

@ManagedBean(name = "compareProjectsBean")
@RequestScoped
@RequiresPermission(Permission.ADMIN_SETTINGS)
public class CompareProjectsBean {

	private Project projectA;
	private Project projectB;
	
	public Project getProjectA() {
		return projectA;
	}
	
	public void setProjectA(Project projectA) {
		this.projectA = projectA;
	}
	
	public Project getProjectB() {
		return projectB;
	}
	
	public void setProjectB(Project projectB) {
		this.projectB = projectB;
	}
	
}
