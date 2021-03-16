package com.energytrade.app.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the device_pl database table.
 * 
 */
@Entity
@Table(name="all_blockchain_transactions_status_pl")
@NamedQuery(name="AllBlockchainTransactionsStatusPl.findAll", query="SELECT d FROM AllBlockchainTransactionsStatusPl d")
public class AllBlockchainTransactionsStatusPl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="blc_tx_status_id")
	private int blcTxStatusId;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_ts")
	private Date createdTs;

	@Column(name="blc_tx_status_name")
	private String blcTxStatusName;
	
	@Column(name="blc_tx_status_desc")
	private String blcTxStatusDesc;

	private byte softdeleteflag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="sync_ts")
	private Date syncTs;

	@Column(name="updated_by")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_ts")
	private Date updatedTs;

	//bi-directional many-to-one association to UserDevice
	@OneToMany(mappedBy="status")
	private List<AllBlockchainTransaction> allBlockchainTransaction;

	public AllBlockchainTransactionsStatusPl() {
	}


	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}


	public int getBlcTxStatusId() {
		return blcTxStatusId;
	}


	public void setBlcTxStatusId(int blcTxStatusId) {
		this.blcTxStatusId = blcTxStatusId;
	}


	public String getBlcTxStatusName() {
		return blcTxStatusName;
	}


	public void setBlcTxStatusName(String blcTxStatusName) {
		this.blcTxStatusName = blcTxStatusName;
	}


	public String getBlcTxStatusDesc() {
		return blcTxStatusDesc;
	}


	public void setBlcTxStatusDesc(String blcTxStatusDesc) {
		this.blcTxStatusDesc = blcTxStatusDesc;
	}


	public byte getSoftdeleteflag() {
		return this.softdeleteflag;
	}

	public void setSoftdeleteflag(byte softdeleteflag) {
		this.softdeleteflag = softdeleteflag;
	}

	public Date getSyncTs() {
		return this.syncTs;
	}

	public void setSyncTs(Date syncTs) {
		this.syncTs = syncTs;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedTs() {
		return this.updatedTs;
	}

	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}


	public List<AllBlockchainTransaction> getAllBlockchainTransaction() {
		return allBlockchainTransaction;
	}


	public void setAllBlockchainTransaction(List<AllBlockchainTransaction> allBlockchainTransaction) {
		this.allBlockchainTransaction = allBlockchainTransaction;
	}

	
}