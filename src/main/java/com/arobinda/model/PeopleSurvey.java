package com.arobinda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PeopleSurvey {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String fullName;
	private String fatherName;
	private String relationwithHO;
	private String sex;
	private String dob;
	private String adharNo;
	private String cast;
	private String stipend;
	private String scholarShip;
	private String mobile;
	private String sangsad;
	private int facilityId;
	
	public int getId() {
		return id;
	}
	public void setId(int surveyId) {
		this.id = surveyId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getRelationwithHO() {
		return relationwithHO;
	}
	public void setRelationwithHO(String relationwithHO) {
		this.relationwithHO = relationwithHO;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAdharNo() {
		return adharNo;
	}
	public void setAdharNo(String adharNo) {
		this.adharNo = adharNo;
	}
	public String getCast() {
		return cast;
	}
	public void setCast(String cast) {
		this.cast = cast;
	}
	public String getStipend() {
		return stipend;
	}
	public void setStipend(String stipend) {
		this.stipend = stipend;
	}
	public String getScholarShip() {
		return scholarShip;
	}
	public void setScholarShip(String scholarShip) {
		this.scholarShip = scholarShip;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSangsad() {
		return sangsad;
	}
	public void setSangsad(String sangsad) {
		this.sangsad = sangsad;
	}
	public int getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}
}
