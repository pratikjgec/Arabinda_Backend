package com.arobinda.model;

import java.util.List;

public class SurveyData {
	
	private List<PeopleSurvey> peopleSurvey;
	
	private Facility facility;
	

	public List<PeopleSurvey> getPeopleSurvey() {
		return peopleSurvey;
	}

	public void setPeopleSurvey(List<PeopleSurvey> peopleSurvey) {
		this.peopleSurvey = peopleSurvey;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setPropleFacility(Facility propleFacility) {
		this.facility = propleFacility;
	}
	
	
	

}
