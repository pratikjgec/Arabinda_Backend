package com.arobinda.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.arobinda.model.Complain;
import com.arobinda.model.Content;
import com.arobinda.model.Facility;
import com.arobinda.model.Notice;
import com.arobinda.model.Otp;
import com.arobinda.model.PeopleSurvey;
import com.arobinda.model.SurveyData;
import com.arobinda.repo.ContentRepo;
import com.arobinda.repo.FacilityRepo;
import com.arobinda.repo.UserRepo;


import com.arobinda.repo.NoticeRepo;
import com.arobinda.repo.OtpRepo;
import com.arobinda.repo.PeopleSurveyRepo;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private NoticeRepo noticeRepo;
	@Autowired
	private ContentRepo contentRepo;
	@Autowired
	private OtpRepo otpRepo;
	@Autowired
	private FacilityRepo facilityRepo;
	@Autowired
	private PeopleSurveyRepo peopleSurveyRepo;
	
	@Value("${fast2sms_API_KEY}")
	private  String  API_KEY;


	public ResponseEntity<String> complainRegister(Complain complain) {
	
		Optional<Otp> otpDetails=otpRepo.findByMobile(complain.getMobile());
		
		 if (!otpDetails.isEmpty() && complain.getOtp().equalsIgnoreCase(otpDetails.get().getOtp())) {
			 
			 Optional<Complain> complainDetails=userRepo.findByMobile(complain.getMobile());
			 if(!complainDetails.isEmpty() && complainDetails.get().getStatus().equalsIgnoreCase("unresolved")) {
				 
				 otpRepo.delete(otpDetails.get());
				 return ResponseEntity.ok("Complain Already Registered Kindly use different Phone no!!!");
			 }
			 else {
				 complain.setOtp("");
				 userRepo.save(complain);
				 otpRepo.delete(otpDetails.get());
				 return ResponseEntity.ok("Complain registered successfully!!!");
			 }
		}
		else {
			 return ResponseEntity.badRequest().body("Entered OTP is not valid!!!");
		 }
	}

	public List<Complain> getComplain(Complain complain) {
		
		return userRepo.findByStatus(complain.getStatus());
	}
	
	public Optional<Complain> getComplainByMobile(Complain complain) {
		
		Optional<Complain> complainDetails=userRepo.findByMobile(complain.getMobile());
	    if(complainDetails!=null) {
	    	
	    	return complainDetails;
        }
	    return null;
	}

	public ResponseEntity<String> markComplainResolved(Complain complain) {
		
		Optional<Complain> complainDetails=userRepo.findByMobile(complain.getMobile());
		 if (complainDetails!=null) {
			 
			 Complain complainData=complainDetails.get();
			 complainData.setStatus(complain.getStatus());
			 userRepo.save(complainData);
			 return ResponseEntity.ok("Complain Resolved!!");
		 }
	         return ResponseEntity.badRequest().body("Complain not found!!");
	}

	public ResponseEntity<String> noticeSubmit(Notice notice) {
		
		if (!notice.getNotice().isEmpty()) {
			
			noticeRepo.save(notice);
		}
		 return ResponseEntity.ok("Notice Uploaded successfully");
	}

	public List<Notice> getNotice() {
		
		List<Notice> notices=noticeRepo.findAll();
		
		return notices;
	}

	public ResponseEntity<String> markNoticeInactive(Notice notice) {
		
		Optional<Notice> noticeDetails=noticeRepo.findById(notice.getId());
		if(noticeDetails!=null) {
			notice.setIsActive(0);
			noticeRepo.save(notice);
		}
		return null;
	}

	public ResponseEntity<String> uploadAboutUs(Content content) {
		
			
			if (contentRepo.findAll().isEmpty() &&!content.getAboutUs().isEmpty()) {
				
				contentRepo.save(content);
				 return ResponseEntity.ok("About Us Content Uploaded successfully");
			}
			else {
				 return ResponseEntity.ok("About Us Content Already there/ Trying to updated empty content!!");
			}
		
		}

	public List<Content> getAboutUs() {
		
		return contentRepo.findAll();
	}

	public Optional<Content> updateAboutUs(Content content) {
		
		Optional<Content> aboutUs=contentRepo.findById(content.getId());
		if(aboutUs!=null && !content.getAboutUs().isEmpty()) {
			
			aboutUs.get().setAboutUs(content.getAboutUs());
			contentRepo.save(aboutUs.get());
		}
		return aboutUs;
	}

	public String generateOTP(Complain complain) throws IOException {
			
		
	
		
			OkHttpClient client = new OkHttpClient();
			String SENDER_ID="DLT_SENDER_ID";
		//	String SENDER_ID="";
			SecureRandom secureRandom = new SecureRandom();
			int randomNo= 1000 + secureRandom.nextInt(9000);
			String message= String.valueOf(randomNo);
			String recipient=complain.getMobile();
			//String API_KEY="EezvjH6bTcNlrPDXa42gkB38n5osyK17JwQfFRVMtSiLdpZ0UO30Au2Z57nmcCeoYGIMUD1TQ8FaBWqr";
	        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
	        RequestBody body = RequestBody.create(mediaType, 
	            "sender_id=" + SENDER_ID + 
	            "&message=" + message + 
	            "&language=english&route=p&numbers=" + recipient);
	        
	        Request request = new Request.Builder()
	            .url("https://www.fast2sms.com/dev/bulkV2")
	            .post(body)
	            .addHeader("authorization", API_KEY)
	            .addHeader("cache-control", "no-cache")
	            .addHeader("content-type", "application/x-www-form-urlencoded")
	            .build();

			
	        Otp otp=new Otp();
	        otp.setMobile(recipient);
	        otp.setOtp(message);
	        otpRepo.save(otp);
	        
	        Response response = client.newCall(request).execute();
	        return(response.body().string());
	}

	public String surveyRegister(SurveyData surveyData) throws Exception {
		
		
		Facility facility=facilityRepo.save(surveyData.getFacility());
	//	Facility facility=facilityRepo.save(null);
		
		List<PeopleSurvey> peopleList=surveyData.getPeopleSurvey();
		
		for (PeopleSurvey person : peopleList) {
			person.setFacilityId(facility.getId());
		}

		peopleSurveyRepo.saveAll(peopleList);
		
		
		return "Survey Registration Sucessful..!!";
	}

	public List<PeopleSurvey> getAllFamilyHead() {
		
		return peopleSurveyRepo.findAllFamilyHead();
	}

	public SurveyData getSurveyData(int facility_id) {
		
		Facility facility=facilityRepo.findByFacilityId(facility_id);
		List<PeopleSurvey> peopleList=peopleSurveyRepo.findAllByFacilityId(facility_id);
		SurveyData surveyData=new SurveyData();
		surveyData.setPeopleSurvey(peopleList);
		surveyData.setPropleFacility(facility);
		return surveyData;
	}
	
	

}
