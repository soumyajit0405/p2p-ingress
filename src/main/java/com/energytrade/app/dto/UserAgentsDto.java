package com.energytrade.app.dto;

import com.energytrade.app.model.AllElectricityBoard;

public  class UserAgentsDto {

	private int dwAgentId;
	
	private int userAgentId;

	public int getUserAgentId() {
		return userAgentId;
	}

	public void setUserAgentId(int userAgentId) {
		this.userAgentId = userAgentId;
	}

	private String agentName;
	
	private String macAddress;
	
	private String wifiSsid;

	
	public int getDwAgentId() {
		return dwAgentId;
	}

	public void setDwAgentId(int dwAgentId) {
		this.dwAgentId = dwAgentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getWifiSsid() {
		return wifiSsid;
	}

	public void setWifiSsid(String wifiSsid) {
		this.wifiSsid = wifiSsid;
	}

	
}
