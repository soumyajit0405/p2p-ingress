package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the user_type_pl database table.
 * 
 */
@Entity
@Table(name="user_permission_pl")
@NamedQuery(name="UserPermissionPl.findAll", query="SELECT u FROM UserPermissionPl u")
public class UserPermissionPl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_permission_id")
	private int userPermissionId;

	@Column(name="active_status")
	private byte activeStatus;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	private byte softdeleteflag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	@Column(name="user_permission_name")
	private String userPermissionName;


	public UserPermissionPl() {
	}


	public int getUserPermissionId() {
		return userPermissionId;
	}


	public void setUserPermissionId(int userPermissionId) {
		this.userPermissionId = userPermissionId;
	}


	public byte getActiveStatus() {
		return activeStatus;
	}


	public void setActiveStatus(byte activeStatus) {
		this.activeStatus = activeStatus;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Date getCreatedTs() {
		return createdTs;
	}


	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}


	public byte getSoftdeleteflag() {
		return softdeleteflag;
	}


	public void setSoftdeleteflag(byte softdeleteflag) {
		this.softdeleteflag = softdeleteflag;
	}


	public Date getSyncTs() {
		return syncTs;
	}


	public void setSyncTs(Date syncTs) {
		this.syncTs = syncTs;
	}


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public Date getUpdatedTs() {
		return updatedTs;
	}


	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}


	public String getUserPermissionName() {
		return userPermissionName;
	}


	public void setUserPermissionName(String userPermissionName) {
		this.userPermissionName = userPermissionName;
	}


}