package com.arobinda.controller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.arobinda.model.Content;
import com.arobinda.model.Myuser;
import com.arobinda.model.Notice;
import com.arobinda.service.AdminService;
import com.arobinda.service.UserService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController()
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;

	@Value("${photo.upload.path}")
	private  String storageDir; 
	    
	    @PostMapping("/register")
	    public ResponseEntity<?> registerUser(@RequestBody Myuser user) {
	       
	    	 Map<String, Object> response = new HashMap<>();
	          response.put("status", "success");
	          response.put("message", adminService.registerUser(user));
	          return new ResponseEntity<>(response, HttpStatus.OK);
	    	
	    }
	    @GetMapping("/test")
	    public String text() {
	       
	        return "after login admin test";
	        
	    }

	    @GetMapping("/users")
	    public ResponseEntity<?> getUsers() {
	           
	    	Map<String, Object> response = new HashMap<>();
	          response.put("status", "success");
	          response.put("message", adminService.getUsers());
	          return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    
	    @PostMapping("/activeInactiveUser")
	    public ResponseEntity<?> activeInactiveUser(@RequestBody Myuser userDetails) {
	           
	    	Map<String, Object> response = new HashMap<>();
	          response.put("status", "success");
	          response.put("message", adminService.activeInactiveUser(userDetails));
	          return new ResponseEntity<>(response, HttpStatus.OK);
	    }
   

	// compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
	
    @PutMapping("/updateAboutUs")
    public ResponseEntity<?> updateAboutUs(@RequestBody Content content) {
      
    	Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "About us updated successfully");
        response.put("data", userService.updateAboutUs(content));
        return new ResponseEntity<>(response, HttpStatus.OK);
          
    }
    
    @PostMapping("/publishAboutUs")
    public ResponseEntity<?> publishAboutUs(@RequestBody Content content) {
           
    	Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", userService.publishAboutUs(content));
        return new ResponseEntity<>(response, HttpStatus.OK);
            
    }
    
    @PutMapping("/noticeInactive/{id}")
    public ResponseEntity<?> markNoticeInactive(@PathVariable int id) {
      
    	Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", userService.markNoticeInactive(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/noticeSubmit")
    public ResponseEntity<?> noticeSubmit(@RequestBody Notice notice) {
           
    	Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", userService.noticeSubmit(notice));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
}
