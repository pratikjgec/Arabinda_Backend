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
	private String created_date;
	private String remarks;
	private String job_card_no;
	private String income_source;
	private String land_mode;
	private String if_any_rent;
	private String cattle;
	private String house;
	private String pmy;
	private String vehicle;
	private String water_source;
	private String phe;
	private String toilet;
	private String gas;
	private String electric;
	private String tax;
	private String ssy;
	private String ration;
	private String sastha_sathi;
	private String shg;
	private String new_voter;
	private String ks_cc;
	private String modified_date;
	private String created_by;
	private String modified_by;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getJob_card_no() {
		return job_card_no;
	}
	public void setJob_card_no(String job_card_no) {
		this.job_card_no = job_card_no;
	}
	public String getIncome_source() {
		return income_source;
	}
	public void setIncome_source(String income_source) {
		this.income_source = income_source;
	}
	public String getLand_mode() {
		return land_mode;
	}
	public void setLand_mode(String land_mode) {
		this.land_mode = land_mode;
	}
	public String getIf_any_rent() {
		return if_any_rent;
	}
	public void setIf_any_rent(String if_any_rent) {
		this.if_any_rent = if_any_rent;
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
	public String getWater_source() {
		return water_source;
	}
	public void setWater_source(String water_source) {
		this.water_source = water_source;
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
	public String getRation() {
		return ration;
	}
	public void setRation(String ration) {
		this.ration = ration;
	}
	public String getSastha_sathi() {
		return sastha_sathi;
	}
	public void setSastha_sathi(String sastha_sathi) {
		this.sastha_sathi = sastha_sathi;
	}
	public String getShg() {
		return shg;
	}
	public void setShg(String shg) {
		this.shg = shg;
	}
	public String getNew_voter() {
		return new_voter;
	}
	public void setNew_voter(String new_voter) {
		this.new_voter = new_voter;
	}
	public String getKs_cc() {
		return ks_cc;
	}
	public void setKs_cc(String ks_cc) {
		this.ks_cc = ks_cc;
	}
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getModified_by() {
		return modified_by;
	}
	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}
	

}
