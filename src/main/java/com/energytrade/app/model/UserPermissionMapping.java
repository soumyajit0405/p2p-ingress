package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the user_devices database table.
 * 
 */
@Entity
@Table(name="user_permission_mapping")
@NamedQuery(name="UserPermissionMapping.findAll", query="SELECT u FROM UserPermissionMapping u")
public class UserPermissionMapping implements Serializable {
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

	@ManyToOne
	@JoinColumn(name="user_id")
	private AllUser allUser;

	@ManyToOne
	@JoinColumn(name="permission_id")
	private UserPermissionPl pemission;

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

	public AllUser getAllUser() {
		return allUser;
	}

	public void setAllUser(AllUser allUser) {
		this.allUser = allUser;
	}

	public UserPermissionPl getPemission() {
		return pemission;
	}

	public void setPemission(UserPermissionPl pemission) {
		this.pemission = pemission;
	}


}