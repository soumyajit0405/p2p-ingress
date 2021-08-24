package com.energytrade.app.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.jmx.Agent;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.energytrade.AppStartupRunner;
import com.energytrade.app.dto.UserAgentsDto;
import com.energytrade.app.dto.UserDeviceDto;
import com.energytrade.app.model.AgentMeterPl;
import com.energytrade.app.model.AllBlockchainTransaction;
import com.energytrade.app.model.AllUser;
import com.energytrade.app.model.DevicePl;
import com.energytrade.app.model.DeviceRepository;
import com.energytrade.app.model.GeneralConfig;
import com.energytrade.app.model.StateBoardMapping;
import com.energytrade.app.model.UserAgents;
import com.energytrade.app.model.UserBlockchainKey;
import com.energytrade.app.model.UserDevice;
import com.energytrade.app.util.CommonUtility;
import com.energytrade.app.util.CustomMessages;
import com.energytrade.app.util.HttpConnectorHelper;
import com.energytrade.app.util.JobGenerator;
import com.energytrade.app.util.JobGeneratorImpl;
import com.energytrade.app.util.JobGeneratorUtil;


@Transactional
@Repository
public class UserProfileDao extends AbstractBaseDao
{
	@Autowired
    UserDeviceRepository userdevicerepo;
	
	@Autowired
    AllUserRepository alluserrepo;
	
	@Autowired
	BlockchainDao bcdao;
	
	@Autowired
	HttpConnectorHelper httpconnectorhelper;
	
	@Autowired
    UserBlockchainKeyRepository userbcrepo;
	
	@Autowired
	AllBlockchainTransactionRepository allbcrepo;
	
	@Autowired
	UserAgentsRepository userAgentRepo;
	
    public HashMap<String,Object> addDevice(HashMap<String,Object> deviceDetails) {
         
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	HashMap<String,Object> inputDetails = new  HashMap<String, Object>();
    	JSONObject data = new JSONObject();
        try {
        	Float deviceCapacity=0.0f;
        	BigDecimal bgdeviceCapacity=new BigDecimal(0);
        	ArrayList<HashMap<String,String>> deviceList=(ArrayList<HashMap<String,String>>)deviceDetails.get("devices");
        	String userId=(String)deviceDetails.get("userId");
        	AllUser alluser=alluserrepo.getUserIdById(Integer.parseInt(userId));
        	List<UserDevice> listOfUserDevice=new ArrayList<UserDevice>();
        	int count=userdevicerepo.getUserDeviceCount();
        	// GeneralConfig bcConfig = userdevicerepo.getBlockChainConfig("block_chain");
        	String bcStatus = AppStartupRunner.configValues.get("blockChain");
        	ArrayList<JSONObject> listofDevices = new ArrayList<JSONObject>();
        	for(int i=0;i<deviceList.size();i++) {
        		JSONObject device= new JSONObject();
        		count= count+1;
        		UserDevice  userdevice=new UserDevice();
        		userdevice.setUserDeviceId(count);
        		userdevice.setActiveStatus((byte)1);
        		deviceCapacity=Float.parseFloat(deviceList.get(i).get("deviceCapacity"));
        		bgdeviceCapacity=BigDecimal.valueOf(deviceCapacity);
        		userdevice.setDeviceCapacity(bgdeviceCapacity);
        		DevicePl devicepl=userdevicerepo.getDevice(Integer.parseInt(deviceList.get(i).get("deviceId")));
        		// Code to populate block chain device list
        		AgentMeterPl agentMeter = userdevicerepo.getAgentMeterDetails(devicepl.getDeviceTypeName());
        		device.put("DeviceID",devicepl.getDeviceTypeName());
        		device.put("MeterID",Integer.toString(agentMeter.getAgentMeterId()));
        		userdevice.setDevicePl(devicepl);
        		userdevice.setAllUser(alluser);
        		listOfUserDevice.add(userdevice);
        		listofDevices.add(device);
        		
        	}
        	userdevicerepo.saveAll(listOfUserDevice);
        	
//        	JSONObject device= new JSONObject();
//        	device.put("DeviceID","NetMeter");
//    		device.put("MeterID","6");
//    		listofDevices.add(device);
    		
        //	data = "{\"username\":"\""+alluser.getFullName()+"\""}";
        	if (bcStatus.equalsIgnoreCase("Y")) {
     		   String blockChainURL = AppStartupRunner.configValues.get("blockChainUATUrl");
        		DeviceRepository dcb= alluserrepo.getDeviceRepository(alluser.getUniqueServiceNumber());
        		UserBlockchainKey bcUser = bcdao.preCreateBlockchainKey(alluser);
            	AllBlockchainTransaction tx= bcdao.preCreateBlockchainTransaction(bcUser,"USER_CREATED");
        	data.put("username",alluser.getFullName());
        	int countOfDevices = listofDevices.size();
        	for (int i=0;i<countOfDevices;i++) {
        		data.put("deviceId"+(i+1),listofDevices.get(i).get("DeviceID"));
            	data.put("meterId"+(i+1),Integer.parseInt((String) listofDevices.get(i).get("MeterID")));
        	}
        	for (int i=countOfDevices;i<4;i++) {
        		data.put("deviceId"+(i+1),"device"+(i+1));
            	data.put("meterId"+(i+1),99);
        	}
        	
        	data.put("url",dcb.getUrl());
        	data.put("phoneNumber",alluser.getPhoneNumber());
        	JobGeneratorImpl obj = new JobGeneratorImpl(); 
            JobGenerator ngenerator = new JobGeneratorUtil(); 
            obj.registerJObGenerator(ngenerator);
            obj.generateJob("CREATE_USER",blockChainURL+"/api/createUser",data,1,alluser, tx.getAllBlockchainTrxId(),bcdao); 
        	//bcdao.addBlockchainTransaction(responseFrombcnetwork.get("Tx"), "USER_CREATED");
//     	  data = new JSONObject();
//     	  data.put("userid", responseFrombcnetwork.get("User_ID"));
//     	  TimeUnit.SECONDS.sleep(5);
//     	  responseFrombcnetwork = httpconnectorhelper.sendPost("http://159.89.162.194:6380/login", data, 1);
//     	  bcdao.updateAuthToken(responseFrombcnetwork.get("accessToken"), (String)data.get("userid"));
     	   }
        	
     	   
        	response.put("message",CustomMessages.getCustomMessages("SUC"));
      	   response.put("key","200");
        	
        }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
            response.put("message",CustomMessages.getCustomMessages("ISE"));
     	   response.put("key","500");
           
        }
        return response;
    }
    
    public HashMap<String,Object> addDeviceP2P(HashMap<String,Object> deviceDetails) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	HashMap<String,Object> inputDetails = new  HashMap<String, Object>();
    	JSONObject data = new JSONObject();
        try {
        	List<UserAgents> userAgents = userAgentRepo.getUserByAgent((int)deviceDetails.get("agentId"));
        	if(userAgents.size() == 0) {
        		response.put("message","No User registered with this Agent");
            	   response.put("key","404");
            	   return response;
        	}
        	Float deviceCapacity=0.0f;
        	BigDecimal bgdeviceCapacity=new BigDecimal(0);
        //	ArrayList<HashMap<String,Object>> deviceList=(ArrayList<HashMap<String,Object>>)deviceDetails.get("devices");
        	
        	List<UserDevice> listOfUserDevice=new ArrayList<UserDevice>();
        	int count=userdevicerepo.getUserDeviceCount();
        	// GeneralConfig bcConfig = userdevicerepo.getBlockChainConfig("block_chain");
        	String bcStatus = AppStartupRunner.configValues.get("blockChain");
        	ArrayList<JSONObject> listofDevices = new ArrayList<JSONObject>();
        	//for(int i=0;i<deviceList.size();i++) {
        		JSONObject device= new JSONObject();
        		count= count+1;
        		UserDevice  userdevice=new UserDevice();
        		userdevice.setUserDeviceId(count);
        		userdevice.setActiveStatus((byte)1);
        		try {
        		Double capacity = (Double)deviceDetails.get("deviceCapacity");
        		bgdeviceCapacity=BigDecimal.valueOf(capacity);
        		} catch(ClassCastException e) {
        			int capacity = (int)deviceDetails.get("deviceCapacity");
        			bgdeviceCapacity=BigDecimal.valueOf(capacity);
        		}
        		
        		userdevice.setDeviceCapacity(bgdeviceCapacity);
        		DevicePl devicepl=userdevicerepo.getDeviceByName((String)deviceDetails.get("deviceCategory"));
        		// Code to populate block chain device list
        		if(devicepl == null) {
        			devicepl=userdevicerepo.getDeviceByName("OTHERS");
        		}
//        		AgentMeterPl agentMeterId = userdevicerepo.getAgentMeterDetails(devicepl.getDeviceTypeName());
//        		device.put("DeviceID",devicepl.getDeviceTypeName());
//        		device.put("MeterID",Integer.toString(agentMeterId.getAgentMeterId()));
        		userdevice.setDevicePl(devicepl);
        		userdevice.setAllUser(userAgents.get(0).getAllUser());
        		userdevice.setForecastEnabled((String)deviceDetails.get("forecastEnabledFlag"));
        		userdevice.setDataAcquistionEnabled((String)deviceDetails.get("dataAquisitionEnabledFlag"));
        		userdevice.setMeterId((int)deviceDetails.get("meterId"));
        		userdevice.setDeviceName((String)deviceDetails.get("deviceName"));
        		userdevice.setMeterType((String)deviceDetails.get("meterType"));
        		userdevice.setMeterModelNumber((String)deviceDetails.get("meterModelNumber"));
        	//	userdevice.setPortNumber((String)deviceDetails.get("portNumber"));
        	
        		
        //	}
        	userdevicerepo.save(userdevice);
        	
//        	JSONObject device= new JSONObject();
//        	device.put("DeviceID","NetMeter");
//    		device.put("MeterID","6");
//    		listofDevices.add(device);
    		
        //	data = "{\"username\":"\""+alluser.getFullName()+"\""}";
        	if (bcStatus.equalsIgnoreCase("Y")) {
     		   String blockChainURL = AppStartupRunner.configValues.get("blockChainUATUrl");
        		DeviceRepository dcb= alluserrepo.getDeviceRepository(userAgents.get(0).getAllUser().getUniqueServiceNumber());
        		UserBlockchainKey bcUser = bcdao.preCreateBlockchainKey(userAgents.get(0).getAllUser());
            	AllBlockchainTransaction tx= bcdao.preCreateBlockchainTransaction(bcUser,"USER_CREATED");
        	data.put("username",userAgents.get(0).getAllUser().getFullName());
        	int countOfDevices = listofDevices.size();
        	for (int i=0;i<countOfDevices;i++) {
        		data.put("deviceId"+(i+1),listofDevices.get(i).get("DeviceID"));
            	data.put("meterId"+(i+1),Integer.parseInt((String) listofDevices.get(i).get("MeterID")));
        	}
        	for (int i=countOfDevices;i<4;i++) {
        		data.put("deviceId"+(i+1),"device"+(i+1));
            	data.put("meterId"+(i+1),99);
        	}
        	
        	data.put("url",dcb.getUrl());
        	data.put("phoneNumber",userAgents.get(0).getAllUser().getPhoneNumber());
        	JobGeneratorImpl obj = new JobGeneratorImpl(); 
            JobGenerator ngenerator = new JobGeneratorUtil(); 
            obj.registerJObGenerator(ngenerator);
            obj.generateJob("CREATE_USER",blockChainURL+"/api/createUser",data,1,userAgents.get(0).getAllUser(), tx.getAllBlockchainTrxId(),bcdao); 
        	//bcdao.addBlockchainTransaction(responseFrombcnetwork.get("Tx"), "USER_CREATED");
//     	  data = new JSONObject();
//     	  data.put("userid", responseFrombcnetwork.get("User_ID"));
//     	  TimeUnit.SECONDS.sleep(5);
//     	  responseFrombcnetwork = httpconnectorhelper.sendPost("http://159.89.162.194:6380/login", data, 1);
//     	  bcdao.updateAuthToken(responseFrombcnetwork.get("accessToken"), (String)data.get("userid"));
     	   }
        	
     	   
        	response.put("message",CustomMessages.getCustomMessages("SUC"));
      	   response.put("key","200");
        	
        }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
            response.put("message",CustomMessages.getCustomMessages("ISE"));
     	   response.put("key","500");
           
        }
        return response;
    }
    
    
    public HashMap<String,Object> addUserAgent(HashMap<String, Object> request) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	JSONObject data = new JSONObject();
        try {
        	int userId = (int)request.get("userId");
        	List<UserAgents> userAgents = userAgentRepo.getUserAgent(userId);
        	if(userAgents.size() > 0) {
        		response.put("message",CustomMessages.getCustomMessages("UAAE"));
            	   response.put("key","404");
            	   return response;
        	}
        	AllUser user = alluserrepo.getUserIdById(userId);
        	if(user == null) {
        		response.put("message",CustomMessages.getCustomMessages("NSU"));
           	   response.put("key","404");
           	   return response;
        	}
        	UserAgents userAgent = new UserAgents();
        	userAgent.setAllUser(user);
        	userAgent.setAgentName((String)request.get("agentName"));
        	userAgent.setDwAgentId((int)request.get("dwAgentId"));
        	userAgent.setWifissId((String)request.get("wifiSsid"));
        	userAgent.setMacAddress((String)request.get("macAddress"));
        	userAgentRepo.save(userAgent);
        	response.put("message",CustomMessages.getCustomMessages("SUC"));
      	   response.put("key","200");
        	
        }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
            response.put("message",CustomMessages.getCustomMessages("ISE"));
     	   response.put("key","500");
           
        }
        return response;
    }
    
    public HashMap<String,Object> getUserAgent(int userId) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	JSONObject data = new JSONObject();
        try {
        	AllUser user = alluserrepo.getUserIdById(userId);
        	if(user == null) {
        		response.put("message",CustomMessages.getCustomMessages("NSU"));
           	   response.put("key","404");
           	   return response;
        	}
        	List<UserAgents> userAgents = userAgentRepo.getUserAgent(userId);
        	List<UserAgentsDto> userAgentsdtoList = new ArrayList<>();
        	for (UserAgents agent: userAgents) {
        		UserAgentsDto userAgentsdto = new UserAgentsDto();
        		userAgentsdto.setDwAgentId(agent.getDwAgentId());
        		userAgentsdto.setAgentName(agent.getAgentName());
        		userAgentsdto.setMacAddress(agent.getMacAddress());
        		userAgentsdto.setWifiSsid(agent.getWifissId());
        		userAgentsdto.setUserAgentId(agent.getId());
        		userAgentsdtoList.add(userAgentsdto);
        	}
        	response.put("message",CustomMessages.getCustomMessages("SUC"));
      	   response.put("key","200");
      	   if (userAgentsdtoList.isEmpty()) {
      		 response.put("message","No User Agent Found");
      	   } else {
      	   response.put("userAgents",userAgentsdtoList);
      	   }
        	
        }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
            response.put("message",CustomMessages.getCustomMessages("ISE"));
     	   response.put("key","500");
           
        }
        return response;
    }
    
    public HashMap<String,Object> deleteDevice(HashMap<String,Object> deviceDetails) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	byte deleteFlag=1;
        try {
        	String userId=(String)deviceDetails.get("userId");
        	ArrayList<Integer> deviceList=(ArrayList<Integer>)deviceDetails.get("devices");
        	for(int i=0;i<deviceDetails.size();i++) {
        		userdevicerepo.deleteDevice(deleteFlag, Integer.parseInt(userId), deviceList.get(i));
        	}
        	response.put("message",CustomMessages.getCustomMessages("SUC"));
      	   response.put("key","200");
        	
        }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
            response.put("message",CustomMessages.getCustomMessages("ISE"));
     	   response.put("key","500");
           
        }
        return response;
    }
    
 public HashMap<String,Object> getAllUserDevices(int userId) {
        
    	HashMap<String,Object> response=new HashMap<String, Object>();
    	List<UserDeviceDto> listOfDeviceDto=new ArrayList<UserDeviceDto>();
    	
        try {
        	List<UserDevice> listOfDevices=userdevicerepo.getUserDeviceById(userId);
        	for(int i=0;i<listOfDevices.size();i++) {
        		UserDeviceDto userdevicedto=new UserDeviceDto();
        		userdevicedto.setDeviceTypeId(listOfDevices.get(i).getDevicePl().getDeviceTypeId());
        		userdevicedto.setDeviceTypeName(listOfDevices.get(i).getDevicePl().getDeviceTypeName());
        		userdevicedto.setUserDeviceId(listOfDevices.get(i).getUserDeviceId());
        		userdevicedto.setCapacity(listOfDevices.get(i).getDeviceCapacity());
        		listOfDeviceDto.add(userdevicedto);

        	String bcStatus = AppStartupRunner.configValues.get("blockChain");
        	if (bcStatus.equalsIgnoreCase("Y")) {
        	UserBlockchainKey ubcKey = userbcrepo.getUserBlockChainKey(userId);
        	if (ubcKey != null) {
        	AllBlockchainTransaction tx = allbcrepo.getTxData(ubcKey.getUserBlockchainKeysId());
        	
        	if (tx!=null) {
        	String txStatus = tx.getStatus().getBlcTxStatusName();
        	response.put("blockChainStatus",txStatus);
        	}
        	}
        	else {
        		response.put("blockChainStatus","Successful");
        	}
        		
        	} else {
        		response.put("blockChainStatus","Successful");
        	}
        	response.put("message",CustomMessages.getCustomMessages("SUC"));
      	   response.put("key","200");
      	 
      	 response.put("devices",listOfDeviceDto);
        	}
        	
        }
        catch (Exception e) {
            System.out.println("Error in checkExistence" + e.getMessage());
            e.printStackTrace();
            response.put("message",CustomMessages.getCustomMessages("ISE"));
     	   response.put("key","500");
           
        }
        return response;
    }
 
 public HashMap<String,Object> getAllUserDevicesP2P(int userId) {
     
 	HashMap<String,Object> response=new HashMap<String, Object>();
 	List<UserDeviceDto> listOfDeviceDto=new ArrayList<UserDeviceDto>();
 	
     try {
     	List<UserDevice> listOfDevices=userdevicerepo.getUserDeviceById(userId);
     	for(int i=0;i<listOfDevices.size();i++) {
     		UserDeviceDto userdevicedto=new UserDeviceDto();
     		userdevicedto.setDeviceTypeId(listOfDevices.get(i).getDevicePl().getDeviceTypeId());
     		userdevicedto.setDeviceTypeName(listOfDevices.get(i).getDevicePl().getDeviceTypeName());
     		userdevicedto.setUserDeviceId(listOfDevices.get(i).getUserDeviceId());
     		userdevicedto.setCapacity(listOfDevices.get(i).getDeviceCapacity());
     			userdevicedto.setMeterId(listOfDevices.get(i).getMeterId());	
     		if (listOfDevices.get(i).getDeviceName() != null) {
     			userdevicedto.setDeviceName(listOfDevices.get(i).getDeviceName());	
     		}
     		if (listOfDevices.get(i).getMeterType() != null) {
     			userdevicedto.setMeterType(listOfDevices.get(i).getMeterType());	
     		}
     		if (listOfDevices.get(i).getMeterModelNumber() != null) {
     			userdevicedto.setMeterModelnumber(listOfDevices.get(i).getMeterModelNumber());	
     		}
     		if (listOfDevices.get(i).getPortNumber() != null) {
     			userdevicedto.setPortNumber(listOfDevices.get(i).getPortNumber());	
     		}
     			listOfDeviceDto.add(userdevicedto);

     	String bcStatus = AppStartupRunner.configValues.get("blockChain");
     	if (bcStatus.equalsIgnoreCase("Y")) {
     	UserBlockchainKey ubcKey = userbcrepo.getUserBlockChainKey(userId);
     	if (ubcKey != null) {
     	AllBlockchainTransaction tx = allbcrepo.getTxData(ubcKey.getUserBlockchainKeysId());
     	
     	if (tx!=null) {
     	String txStatus = tx.getStatus().getBlcTxStatusName();
     	response.put("blockChainStatus",txStatus);
     	}
     	}
     	else {
     		response.put("blockChainStatus","Successful");
     	}
     		
     	} else {
     		response.put("blockChainStatus","Successful");
     	}
     	response.put("message",CustomMessages.getCustomMessages("SUC"));
   	   response.put("key","200");
   	 
   	 response.put("devices",listOfDeviceDto);
     	}
     	
     }
     catch (Exception e) {
         System.out.println("Error in checkExistence" + e.getMessage());
         e.printStackTrace();
         response.put("message",CustomMessages.getCustomMessages("ISE"));
  	   response.put("key","500");
        
     }
     return response;
 }
          
}