package com.energytrade.app.util;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.energytrade.app.dao.BlockchainDao;
import com.energytrade.app.model.AllUser;


public class JobGeneratorUtil implements JobGenerator {
 
		
	    @Override
		public void triggerBlockChain(String type, String url, JSONObject data, int val, AllUser alluser, int blockChainTxId, BlockchainDao bcdao) throws ClassNotFoundException, SQLException {
	    	 
	    	try {
	    		HttpConnectorHelper httpconnectorhelper = new HttpConnectorHelper();
	    		
	    	HashMap<String,String> responseFrombcnetwork = httpconnectorhelper.sendPost(url,data, val);
	     	   //HashMap<String,String> responseAfterParse = cm.parseInput(responseFrombcnetwork);
	     	   if(responseFrombcnetwork.get("Status").equalsIgnoreCase("Profile Updated")) {
	     	   bcdao.updateBlockChainUser(responseFrombcnetwork.get("address"),responseFrombcnetwork.get("Private_Key"), responseFrombcnetwork.get("address"), alluser.getUserId()); // Call BC API and put it in another method
	     	   bcdao.updateBlockchainTransaction(blockChainTxId, responseFrombcnetwork.get("Tx"), "USER_CREATED");
	     	   }
	    	}
	    	catch(Exception e) {
	    		e.printStackTrace();
	        	}
		}
	} 

