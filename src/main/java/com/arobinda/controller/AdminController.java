package com.arobinda.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.ui.Model;
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
import com.arobinda.model.Photo;
import com.arobinda.repo.PhotoRepo;
import com.arobinda.service.AdminService;
import com.arobinda.service.UserService;
import com.arobinda.service.UserService_temp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
	    
	    @Autowired 
	    private PhotoRepo photoRepo;

	    
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
	    
//    
//    @PostMapping("/sliderPhotoUpload")
//    public ResponseEntity<String> sliderPhotoUpload(@RequestParam("file1") MultipartFile file1,
//    		@RequestParam("file2") MultipartFile file2,
//    		@RequestParam("file3") MultipartFile file3,
//    		@RequestParam("file4") MultipartFile file4,
//    		@RequestParam("file5") MultipartFile file5) throws IOException {
//           
//    	
//    	if(file1.isEmpty()||file2.isEmpty()||file3.isEmpty()||file4.isEmpty()||file5.isEmpty()) {
//    		return ResponseEntity.badRequest().body("Selected files are incorrect/ wrong file");
//    	}
//    	List<MultipartFile> photoList=new ArrayList<>();
//    	photoList.add(file1);
//    	photoList.add(file2);
//    	photoList.add(file3);
//    	photoList.add(file4);
//    	photoList.add(file5);
//    	
//    	
//    
//    	String response=adminService.sliderPhotoUpload(photoList);
//    	if(response!=null) {
//    	return ResponseEntity.ok().body("Slider Photos Uploaded succesfully");
//    	}
//    	
//    	return ResponseEntity.badRequest().body("Slider Photos Uploaded succesfully");
//           
//    }
    
    
    @PostMapping("/upload")
	public BodyBuilder uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {

		System.out.println("Original Image Byte Size - " + file.getBytes().length);
		Photo img = new Photo(file.getOriginalFilename(), file.getContentType(),
				compressBytes(file.getBytes()));
		photoRepo.save(img);
		return ResponseEntity.status(HttpStatus.OK);
	}

	@GetMapping(path = { "/get/{imageName}" })
	public Photo getImage(@PathVariable("imageName") String imageName) throws IOException {

		final Optional<Photo> retrievedImage = photoRepo.findByName(imageName);
		Photo img = new Photo(retrievedImage.get().getName(), retrievedImage.get().getType(),
				decompressBytes(retrievedImage.get().getPicByte()));
		return img;
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
    
    @PutMapping("/noticeInactive")
    public ResponseEntity<?> markNoticeInactive(@RequestBody Notice notice) {
      
    	Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", userService.markNoticeInactive(notice));
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
