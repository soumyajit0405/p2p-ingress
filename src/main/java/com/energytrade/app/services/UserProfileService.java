package com.energytrade.app.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energytrade.app.dao.CommonDao;
import com.energytrade.app.dao.LoginDao;
import com.energytrade.app.dao.UserProfileDao;
import com.energytrade.app.dto.AllElectricityBoardDto;
import com.energytrade.app.dto.AllStateDto;
import com.energytrade.app.dto.StateBoardMappingDto;
import com.energytrade.app.dto.UserRolesPlDto;
import com.energytrade.app.dto.UserTypePlDto;
import com.energytrade.app.model.UserAgents;

@Service("userProfileService")
public class UserProfileService extends AbstractBaseService
{
    @Autowired
    private UserProfileDao userprofiledao;
    
    public HashMap<String,Object> addDevice(HashMap<String,Object> deviceDetails) {
        return this.userprofiledao.addDevice(deviceDetails);
    }
    
    public HashMap<String,Object> addDeviceP2P(HashMap<String,Object> deviceDetails) {
        return this.userprofiledao.addDeviceP2P(deviceDetails);
    }
    
    public HashMap<String,Object> deleteDevice(HashMap<String,Object> deviceDetails) {
        return this.userprofiledao.deleteDevice(deviceDetails);
    }
       
    public HashMap<String,Object> getAllUserDevices(int userId) {
        return this.userprofiledao.getAllUserDevices(userId);
    }
    
    public HashMap<String,Object> getAllUserDevicesP2P(int userId) {
        return this.userprofiledao.getAllUserDevicesP2P(userId);
    }
    public HashMap<String,Object> addUserAgent(HashMap<String, Object> request) {
        return this.userprofiledao.addUserAgent(request);
    }
    
    public HashMap<String,Object> getUserAgent(int userId) {
        return this.userprofiledao.getUserAgent(userId);
    }
    
       
    
}