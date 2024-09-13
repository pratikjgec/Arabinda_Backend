package com.arobinda.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
	private String  API_KEY;


	public ResponseEntity<?> complainRegister(Complain complain) throws IOException {
	
		Optional<Otp> otpDetails=otpRepo.findByMobile(complain.getMobile());
		Map<String, Object> response = new HashMap<>();
       
       
		 if (!otpDetails.isEmpty() && complain.getOtp().equalsIgnoreCase(otpDetails.get().getOtp())) {
			 
			 Optional<Complain> complainDetails=userRepo.findByMobile(complain.getMobile());
			 if(!complainDetails.isEmpty() && complainDetails.get().getStatus().equalsIgnoreCase("unresolved")) {
				 
				 otpRepo.delete(otpDetails.get());
				 response.put("status", "failed");
				 response.put("message", "Complain Already Registered Kindly use different Phone no!!!");
				 return new ResponseEntity<>(response,HttpStatus.OK);
			 }
			 else {
				 complain.setOtp("");
				 SecureRandom secureRandom = new SecureRandom();
			     int complainId = 1000 + secureRandom.nextInt(9000);
			     complain.setComplain_id(String.valueOf(complainId));
				 userRepo.save(complain);
				 otpRepo.delete(otpDetails.get());
				 
			//	 String smsResp=smsSend(complain.getMobile(),complainId,"ComplainRegister");
				 response.put("status", "success");
				 response.put("message", "Complain Id-"+complainId+" Complain registered successfully!!!");
			//	 response.put("sms_response", smsResp);
				 return new ResponseEntity<>(response,HttpStatus.OK);
			 }
		}
		else {
			response.put("status", "failed");
			response.put("message", "Entered OTP is not valid!!!");
			 return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		 }
	}



	public List<Complain> getAllcomplain() {
		
		return userRepo.findAll();
	}
	
	public Optional<Complain> getComplainByComplainId(String complainId) {
		
		Optional<Complain> complainDetails=userRepo.findByComplainId(complainId);
		
	    if(complainDetails.isPresent()) {
	    	
	    	return complainDetails;
        }
	    return null;
	}

	public ResponseEntity<?> markComplainResolved(Complain complain) throws IOException {
		Map<String, Object> response = new HashMap<>();
		Optional<Complain> complainDetails=userRepo.findByMobile(complain.getMobile());
		String complainId=complain.getComplain_id();
		 if (complainDetails!=null) {
			 
			 Complain complainData=complainDetails.get();
			 complainData.setStatus(complain.getStatus());
			 userRepo.save(complainData);
		//	 String smsResp=smsSend(complain.getMobile(),complainId,"ComplainResolved");
			 response.put("status", "success");
			 response.put("message", "Complain Id-"+complainId+" resolved successfully!!!");
		//	 response.put("sms_response", smsResp);
			 return new ResponseEntity<>(response,HttpStatus.OK);
			 
		 }
		 response.put("status", "failed");
		 response.put("message", "Complain Id-"+complainId+" not found!!");
		 
		 return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}

	public String noticeSubmit(Notice notice) {
		
		if (!notice.getNotice().isEmpty()) {
			
			noticeRepo.save(notice);
		}
		 return "Notice Uploaded successfully";
	}

	public List<Notice> getNotice() {
		
		List<Notice> notices=noticeRepo.findAll();
		
		return notices;
	}

	public String markNoticeInactive(Notice notice) {
		
		Optional<Notice> noticeDetails=noticeRepo.findById(notice.getId());
		if(noticeDetails!=null) {
			notice.setIsActive(0);
			 noticeRepo.save(notice);
			return "Notice Mark in-acitive successfully";
		}
		return  "No Notice Found, notice id may incorrect";
	}

	public ResponseEntity<String> publishAboutUs(Content content) {
		
			
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
	        @SuppressWarnings("deprecation")
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
		
		
		return "Survey Registration Sucessfull !!";
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
	
	@SuppressWarnings("deprecation")
	private  String smsSend(String mobile, int complainId, String type) throws IOException {
		
		OkHttpClient client = new OkHttpClient();
		String SENDER_ID="DLT_SENDER_ID";
		RequestBody body=null;
		String SMS_URL = "https://www.fast2sms.com/dev/bulkV2";
		String API_KEY_NEW="";
		String name="Potu";
		int full=15000;
		int due=13000;
		int paid=2000;
		
		RestTemplate restTemplate = new RestTemplate();
		if(type.equalsIgnoreCase("ComplainRegister")) {
//			String message="12345"; // Registered template ID as per fast2sms
//	        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//	         body = RequestBody.create(mediaType, 
//	            "sender_id=" + SENDER_ID + 
//	            "&message=" + message + 
//	            "&language=english&route=p&numbers=" + mobile);
			 String variablesValues = String.format("%s|%s|%s|%s", name, paid, full, due);
			 String url = SMS_URL+ "?authorization=" + API_KEY_NEW
			            + "&route=dlt" 
			            + "&sender_id=nabrun"
			            + "&message=173080"
			            + "&variables_values="+variablesValues
			            + "&numbers="+mobile
			            + "&flash=0";
			 
			
			 ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

		        // Return the response from the API
		        return response.getBody();
			
		}
		
		if(type.equalsIgnoreCase("ComplainResolved")) {
			String message="12345"; // Registered template ID as per fast2sms
	        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
	         body = RequestBody.create(mediaType, 
	            "sender_id=" + SENDER_ID + 
	            "&message=" + message + 
	            "&language=english&route=p&numbers=" + mobile);
		}



	     Request request = new Request.Builder()
	             .url("https://www.fast2sms.com/dev/bulkV2")
	             .post(body)
	             .addHeader("authorization", API_KEY)
	             .addHeader("cache-control", "no-cache")
	             .addHeader("content-type", "application/x-www-form-urlencoded")
	             .build();
	     Response response = client.newCall(request).execute();
	     
	     return(response.body().string());
		
	}
	
	

}
