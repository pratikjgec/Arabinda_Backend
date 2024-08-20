package com.arobinda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Facility {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String sansad;
	private String houseNo;
	private String surveyor;
	private String createdDate;
	private String remarks;
	private String jobCardNo;
	private String incomeSource;
	private String landMode;
	private String ifAnyRent;
	private String cattle;
	private String house;
	private String pmy;
	private String  vehicle;
	private String waterSource;
	private String phe;
	private String toilet;
	private String gas;
	private String electric;
	private String tax;
	private String  ssy;
	private String retion;
	private String sasthaSathi;
	private String shg;
	private String newVoter;
	private String ks_cc;
	
	public int getId() {
		return id;
	}
	public void setPeopleId(int id) {
		this.id = id;
	}
	public String getJobCardNo() {
		return jobCardNo;
	}
	public void setJobCardNo(String jobCardNo) {
		this.jobCardNo = jobCardNo;
	}
	public String getIncomeSource() {
		return incomeSource;
	}
	public void setIncomeSource(String incomeSource) {
		this.incomeSource = incomeSource;
	}
	public String getLandMode() {
		return landMode;
	}
	public void setLandMode(String landMode) {
		this.landMode = landMode;
	}
	public String getIfAnyRent() {
		return ifAnyRent;
	}
	public void setIfAnyRent(String ifAnyRent) {
		this.ifAnyRent = ifAnyRent;
	}
	public String getCattle() {
		return cattle;
	}
	public void setCattle(String cattle) {
		this.cattle = cattle;
	}
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		this.house = house;
	}
	public String getPmy() {
		return pmy;
	}
	public void setPmy(String pmy) {
		this.pmy = pmy;
	}
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	public String getWaterSource() {
		return waterSource;
	}
	public void setWaterSource(String waterSource) {
		this.waterSource = waterSource;
	}
	public String getPhe() {
		return phe;
	}
	public void setPhe(String phe) {
		this.phe = phe;
	}
	public String getToilet() {
		return toilet;
	}
	public void setToilet(String toilet) {
		this.toilet = toilet;
	}
	public String getGas() {
		return gas;
	}
	public void setGas(String gas) {
		this.gas = gas;
	}
	public String getElectric() {
		return electric;
	}
	public void setElectric(String electric) {
		this.electric = electric;
	}
	public String getTax() {
		return tax;
	}
	public void setTax(String tax) {
		this.tax = tax;
	}
	public String getSsy() {
		return ssy;
	}
	public void setSsy(String ssy) {
		this.ssy = ssy;
	}
	public String getRetion() {
		return retion;
	}
	public void setRetion(String retion) {
		this.retion = retion;
	}
	public String getSasthaSathi() {
		return sasthaSathi;
	}
	public void setSasthaSathi(String sasthaSathi) {
		this.sasthaSathi = sasthaSathi;
	}
	public String getShg() {
		return shg;
	}
	public void setShg(String shg) {
		this.shg = shg;
	}
	public String getNewVoter() {
		return newVoter;
	}
	public void setNewVoter(String newVoter) {
		this.newVoter = newVoter;
	}
	public String getKs_cc() {
		return ks_cc;
	}
	public void setKs_cc(String ks_cc) {
		this.ks_cc = ks_cc;
	}
	public String getSansad() {
		return sansad;
	}
	public void setSansad(String sansad) {
		this.sansad = sansad;
	}
	public String getHouseNo() {
		return houseNo;
	}
	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}
	public String getSurveyor() {
		return surveyor;
	}
	public void setSurveyor(String surveyor) {
		this.surveyor = surveyor;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	

}
