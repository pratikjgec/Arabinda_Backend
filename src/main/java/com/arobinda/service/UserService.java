package com.arobinda.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

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


	String systemDate() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    Date date = new Date();  
    formatter.setTimeZone(TimeZone.getTimeZone("IST"));
    return formatter.format(date); 
	}
	
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
			     complain.setCreated_date(systemDate());
				 userRepo.save(complain);
				 otpRepo.delete(otpDetails.get());
				 
			//	 String smsResp=smsSend(complain.getMobile(),complainId,"ComplainRegister");
				 response.put("status", "success");
				 response.put("message", "Complain Id-"+complainId+" Complain registered successfully!!!");
			//	 response.put("sms_response", smsResp);
				 smsSend(complain.getMobile(),complainId,"ComplainRegistered");
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
	
	public Optional<Complain> getComplainByComplainId(Complain complain) {
		
		Optional<Complain> complainDetails=userRepo.findBycomplainIdAndMobile(complain.getComplain_id(),complain.getMobile());
		
	    if(complainDetails.isPresent()) {
	    	
	    	return complainDetails;
        }
	    return null;
	}
	
	
	public ResponseEntity<?> markComplainResolved(Complain complain) throws IOException {
		Map<String, Object> response = new HashMap<>();
		Optional<Complain> complainDetails=userRepo.findUnresolvedBycomplainId(complain.getComplain_id());
		
		 if (complainDetails!=null) {
			 
			 Complain complainData=complainDetails.get();
			 complainData.setStatus("Resolved");
			 complainData.setResolved_date(systemDate());
			 complainData.setResolved_by(complain.getResolved_by());
			 userRepo.save(complainData);
			 response.put("status", "success");
			 response.put("message", "Complain Id-"+complain.getComplain_id()+" resolved successfully!!!");
			 smsSend(complainData.getMobile(),Integer.parseInt(complain.getComplain_id()),"ComplainResolved");
			 return new ResponseEntity<>(response,HttpStatus.OK);
			 
		 }
		 response.put("status", "failed");
		 response.put("message", "Complain Id-"+complain.getComplain_id()+" not found!!");
		 
		 return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}

	public String noticeSubmit(Notice notice) {
		
		if (!notice.getNotice().isEmpty()) {
			notice.setCreated_date(systemDate());
			noticeRepo.save(notice);
		}
		 return "Notice Uploaded successfully";
	}

	public List<Notice> getNotice() {
		
		List<Notice> notices=noticeRepo.findAllActiveNotice();
		
		return notices;
	}

	public String markNoticeInactive(int id) {
		
		Optional<Notice> noticeDetails=noticeRepo.findById(id);
		if(noticeDetails!=null) {
			noticeDetails.get().setIs_active(0);
			 noticeRepo.save(noticeDetails.get());
			return "Notice Mark in-acitive successfully";
		}
		return  "No Notice Found, notice id may incorrect";
	}

	public ResponseEntity<String> publishAboutUs(Content content) {
		
			
			if (contentRepo.findAll().isEmpty() &&!content.getAbout_us().isEmpty()) {
				
				contentRepo.save(content);
				 return ResponseEntity.ok("About Us Content Uploaded successfully");
			}
			else {
				 return ResponseEntity.ok("About Us Content Already there/ Trying to updated empty content!!");
			}
		
		}

	public List<Content> getAboutUs() {
		systemDate();
		return contentRepo.findAll();
	}

	public Optional<Content> updateAboutUs(Content content) {
		
		Optional<Content> aboutUs=contentRepo.findById(content.getId());
		if(aboutUs!=null && !content.getAbout_us().isEmpty()) {
			
			aboutUs.get().setAbout_us(content.getAbout_us());
			contentRepo.save(aboutUs.get());
		}
		return aboutUs;
	}

	public String generateFreeOTP(Complain complain) throws IOException {
		
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
		
		
		surveyData.getFacility().setCreated_date(systemDate());
		surveyData.getFacility().setModified_date(systemDate());
		Facility facility=facilityRepo.save(surveyData.getFacility());
	//	Facility facility=facilityRepo.save(null);
		
		List<PeopleSurvey> peopleList=surveyData.getPeopleSurvey();
		
		for (PeopleSurvey person : peopleList) {
			
			person.setCreated_date(systemDate());
			person.setModified_date(systemDate());
			person.setFacility_id(facility.getId());
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
	
	private  String smsSend(String mobile, int complainId, String smsType)  {
		
		String SENDER_ID="NSCOP";
		String route="dlt";
		String SMS_URL = "https://www.fast2sms.com/dev/bulkV2";
		String url="";
		
		RestTemplate restTemplate = new RestTemplate();
		if(smsType.equalsIgnoreCase("ComplainRegistered")) {
			
			//message id is as per fast2sms portal
			String massageId="175449";
			 String variablesValues = String.format("%s", complainId);
			 url = SMS_URL+ "?authorization=" + API_KEY
			            + "&route="+route 
			            + "&sender_id="+SENDER_ID
			            + "&message="+massageId
			            + "&variables_values="+variablesValues
			            + "&numbers="+mobile
			            + "&flash=0";
		}
		
		if(smsType.equalsIgnoreCase("ComplainResolved")) {
			
			//message id is as per fast2sms portal
			String massageId="175448";
			String variablesValues = String.format("%s", complainId);
			 url = SMS_URL+ "?authorization=" + API_KEY
			            + "&route="+route 
			            + "&sender_id="+SENDER_ID
			            + "&message="+massageId
			            + "&variables_values="+variablesValues
			            + "&numbers="+mobile
			            + "&flash=0";
		}
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		return response.getBody();

	}

	public String generateOTP(Complain complain) {
		
		String SENDER_ID="NSCOP";
		String route="dlt";
		String message_id="173486";
		String recipent=complain.getMobile();
		SecureRandom secureRandom = new SecureRandom();
		int randomOTPNo= 1000 + secureRandom.nextInt(9000);
		
		 String variablesValues = String.format("%s|", randomOTPNo);
		
		String SMS_URL = "https://www.fast2sms.com/dev/bulkV2";
		
		RestTemplate restTemplate = new RestTemplate();
		
			 String url = SMS_URL+ "?authorization=" + API_KEY
			            + "&route="+route 
			            + "&sender_id="+SENDER_ID
			            + "&message="+message_id
			            + "&variables_values="+variablesValues
			            + "&numbers="+recipent
			            + "&flash=0";
			
	   ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

			   Otp otp=new Otp();
		       otp.setMobile(recipent);
		       otp.setOtp(String.valueOf(randomOTPNo));
		       otpRepo.save(otp);
		       
		return response.getBody();

	}

	public Complain complainRemarksUpdate(Complain complain) {
		
	 
		Optional<Complain> complainDetails=userRepo.findBycomplainId(Integer.parseInt(complain.getComplain_id()));
		
		//	String complainId=complainDetails.get().getComplain_id();
			
			 if (complainDetails!=null) {
				 
				 Complain complainData=complainDetails.get();
				 complainData.setRemarks(complain.getRemarks());
				  return userRepo.save(complainData);
				 
			 }
			return null;
	}
}
