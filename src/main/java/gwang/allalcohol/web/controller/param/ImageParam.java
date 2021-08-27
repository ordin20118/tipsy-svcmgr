package gwang.allalcohol.web.controller.param;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ImageParam {
	
	Integer contentId;
	Integer contentType;
	String name;
	MultipartFile imageFile;
	

}
