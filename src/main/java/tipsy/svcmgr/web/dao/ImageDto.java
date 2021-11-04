package tipsy.svcmgr.web.dao;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import tipsy.svcmgr.web.vo.ContentInfoVo;

@Data
public class ImageDto extends ContentInfoVo {

	public static final int IMAGE_TYPE_REP 		= 0;	// 대표 이미지
	public static final int IMAGE_TYPE_NORMAL 	= 1;	// 일반 이미지
	public static final int IMAGE_TYPE_ORIGINAL = 99;	// 원본 이미지
	
	private Integer	imageId;
	private Integer imageType;
	private Integer contentId;
	private Integer	contentType;
	private String 	path;
	private Date 	regDate;	
	private MultipartFile imageFile;
	
}
