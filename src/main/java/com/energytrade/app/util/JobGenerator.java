package com.energytrade.app.util;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

import com.energytrade.app.dao.BlockchainDao;
import com.energytrade.app.model.AllUser;


public interface JobGenerator {
	
	void triggerBlockChain(String type, String url, JSONObject data, int val, AllUser alluser, int bcTxId, BlockchainDao dao ) throws ClassNotFoundException, SQLException;

}
