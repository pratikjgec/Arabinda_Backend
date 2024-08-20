package com.arobinda.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.arobinda.model.Photo;
import com.arobinda.repo.PhotoRepo;

@Service
public class UserService_temp {


	
    @Value("${photo.upload.path}")
    private  String storageDir; 
    
    @Autowired 
    private PhotoRepo photoRepo;
    
    
//	public String sliderPhotoUpload(List<MultipartFile> photoList) throws IOException {
//		
//	      Path storagePath = Paths.get(storageDir);
//	      List<Photo> photoDetailsList=new ArrayList<>();
//	      Photo photoDetails=null;
//	      
//	      if (!Files.exists(storagePath)) {
//	            Files.createDirectories(storagePath);
//	      }
//	      int photoCount=1;
//		for(MultipartFile file:photoList) {
//			
//	        Path filePath = storagePath.resolve(photoCount+file.getOriginalFilename());
//	        
//	        if(Files.write(filePath, file.getBytes()) != null) {
//	        	
//	        	photoDetails=new Photo();
//	        	photoDetails.setFileName("Slider"+photoCount);
//	        	photoDetails.setFilePath(filePath.toString());
//	        	photoDetails.setCreatedDate(LocalDate.now().toString());
//	        	photoDetails.setUsedFor("Slider");
//	        	photoCount++;
//	        	photoDetailsList.add(photoDetails);
//	        	
//	        	}
//			}
//		if(	photoRepo.saveAll(photoDetailsList)!=null) {
//			return "Slider Photos Uploaded succesfully";
//		}
//		return null;
//			
//	}

//	public List<String> getImagePaths() {
//        // Fetching all image paths from the database
//        return photoRepo.findAll()
//                              .stream()
//                              .map(Photo::getFilePath)
//                              .collect(Collectors.toList());
//    }
	

}
