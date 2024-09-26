package com.arobinda.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PeopleSurvey {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String full_name;
	private String father_name;
	private int family_head=0;
	private String relation_with_ho;
	private String sex;
	private String dob;
	private String adhar_no;
	private String caste;
	private String stipend;
	private String scholarship;
	private String mobile;
	private String created_date;
	private String modified_date;
	private String created_by;
	private String modified_by;

	private int facility_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getFather_name() {
		return father_name;
	}
	public void setFather_name(String father_name) {
		this.father_name = father_name;
	}
	public int getFamily_head() {
		return family_head;
	}
	public void setFamily_head(int family_head) {
		this.family_head = family_head;
	}
	public String getRelation_with_ho() {
		return relation_with_ho;
	}
	public void setRelation_with_ho(String relation_with_ho) {
		this.relation_with_ho = relation_with_ho;
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
	public String getAdhar_no() {
		return adhar_no;
	}
	public void setAdhar_no(String adhar_no) {
		this.adhar_no = adhar_no;
	}
	public String getCaste() {
		return caste;
	}
	public void setCaste(String caste) {
		this.caste = caste;
	}
	public String getStipend() {
		return stipend;
	}
	public void setStipend(String stipend) {
		this.stipend = stipend;
	}
	public String getScholarship() {
		return scholarship;
	}
	public void setScholarship(String scholarship) {
		this.scholarship = scholarship;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getFacility_id() {
		return facility_id;
	}
	public void setFacility_id(int facility_id) {
		this.facility_id = facility_id;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String systemDate) {
		this.created_date = systemDate;
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
