package com.energytrade.app.dto;

import java.math.BigDecimal;

import com.energytrade.app.model.AllElectricityBoard;

public  class UserDeviceDto {

	private int deviceTypeId;

	private String deviceTypeName;
	
	private int userDeviceId;
	
	private int meterId;
	
	private String deviceName;
	
	private String meterType;
	
	private String meterModelnumber;
	
	private String portNumber;
	
	public int getMeterId() {
		return meterId;
	}

	public void setMeterId(int meterId) {
		this.meterId = meterId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getMeterModelnumber() {
		return meterModelnumber;
	}

	public void setMeterModelnumber(String meterModelnumber) {
		this.meterModelnumber = meterModelnumber;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	private BigDecimal capacity;

	public BigDecimal getCapacity() {
		return capacity;
	}

	public void setCapacity(BigDecimal capacity) {
		this.capacity = capacity;
	}
	public int getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(int deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getDeviceTypeName() {
		return deviceTypeName;
	}

	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}

	public int getUserDeviceId() {
		return userDeviceId;
	}

	public void setUserDeviceId(int userDeviceId) {
		this.userDeviceId = userDeviceId;
	}
	
}
