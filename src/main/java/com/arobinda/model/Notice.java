package com.arobinda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String notice;
	private String issue_date;
	private int is_active=1;
	private String created_date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getIssue_date() {
		return issue_date;
	}
	public void setIssue_date(String issue_date) {
		this.issue_date = issue_date;
	}
	public int getIs_active() {
		return is_active;
	}
	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	
	
	
}
