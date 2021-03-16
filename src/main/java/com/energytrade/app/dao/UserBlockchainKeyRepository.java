package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
public interface UserBlockchainKeyRepository extends JpaRepository<UserBlockchainKey, Long>
{
 
	@Modifying
    @Query("update UserBlockchainKey a set a.authToken=?1 where a.blockChainUserId=?2")
     void updateAuthToken(String authToken,String userId);
	
	@Query("select a from  UserBlockchainKey a  where a.allUser.userId=?1")
	UserBlockchainKey getUserBlockChainKey(int userId);
	
	
	@Query("Select COALESCE(max(a.userBlockchainKeysId),0) from UserBlockchainKey a ")
    int getBcUserCount();
	
	@Modifying
    @Query("update UserBlockchainKey a set a.userAddress=?1,a.privateKey=?2,a.startDate=?3,a.blockChainUserId=?4 where a.allUser.userId=?5")
     void updateBlockChainUser(String address,String privateKey,Timestamp ts, String bcUserId, int userId);
}