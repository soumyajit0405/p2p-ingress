package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.energytrade.app.model.AllBlockchainTransaction;
import com.energytrade.app.model.AllBlockchainTransactionsStatusPl;
import com.energytrade.app.model.AllElectricityBoard;
import com.energytrade.app.model.AllOtp;
import com.energytrade.app.model.AllState;
import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.StateBoardMapping;
import com.energytrade.app.model.UserBlockchainKey;
import com.energytrade.app.model.UserRolesPl;
import com.energytrade.app.model.UserTypePl;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AllBlockchainTransactionRepository extends JpaRepository<AllBlockchainTransaction, Long>
{
 
	@Query("Select a from AllBlockchainTransactionsStatusPl a where a.blcTxStatusName=?1 ")
    AllBlockchainTransactionsStatusPl getStatus(String status);
	
	@Query(value="SELECT * FROM all_blockchain_transactions where blockchain_user_id = ?1 order by created_ts desc limit 1",nativeQuery = true)
    AllBlockchainTransaction getTxData(int ubcId);
	
	
	@Query("Select COALESCE(max(a.allBlockchainTrxId),0) from AllBlockchainTransaction a ")
    int getTxCount();
	
	@Modifying
    @Query("update AllBlockchainTransaction a set a.blockChainTrxId=?1,a.transactionType=?2,a.status.blcTxStatusId=?4 where a.allBlockchainTrxId=?3")
     void updateBlockChainTx(String bctxId,String type, int abcTxId, int bcTxStatus);
}