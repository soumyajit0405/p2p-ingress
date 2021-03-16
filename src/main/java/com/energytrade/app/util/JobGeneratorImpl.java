package com.energytrade.app.util;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONObject;

import com.energytrade.app.dao.BlockchainDao;
import com.energytrade.app.dao.UserBlockchainKeyRepository;
import com.energytrade.app.model.AllUser;


public class JobGeneratorImpl {
	
	private JobGenerator jobgenerator;
	
	public void registerJObGenerator(JobGenerator ngenerator) 
    { 
        this.jobgenerator = ngenerator; 
    } 
	
	public void generateJob(String type, String url,JSONObject data, int val, AllUser alluser , int bcTxId, BlockchainDao dao) 
    { 
  
        // An Async task always executes in new thread 
        new Thread(new Runnable() { 
            public void run() 
            { 
                // check if listener is registered. 
                if (jobgenerator != null) { 
  
                    // invoke the callback method of class A 
                    try {
                    	jobgenerator.triggerBlockChain(type, url, data,val,alluser, bcTxId,dao);
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
                } 
            } 
        }).start(); 
    }
	
	}


