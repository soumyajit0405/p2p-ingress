package com.energytrade.app.dao;

import org.springframework.data.jpa.repository.Modifying;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.energytrade.app.model.AllElectricityBoard;
import com.energytrade.app.model.AllOtp;
import com.energytrade.app.model.AllState;
import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.DevicePl;
import com.energytrade.app.model.StateBoardMapping;
import com.energytrade.app.model.UserAgents;
import com.energytrade.app.model.UserRolesPl;
import com.energytrade.app.model.UserTypePl;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserAgentsRepository extends JpaRepository<UserAgents, Long>
{
    
	@Query("Select a from UserAgents a where a.allUser.userId=?1")
    List<UserAgents> getUserAgent( int userId);
}