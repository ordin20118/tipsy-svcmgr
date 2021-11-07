package tipsy.svcmgr.web.dao;

import java.util.Date;

import lombok.Data;
import tipsy.common.basic.AbstractSearchParam;

@Data
public class ManageLogDto extends AbstractSearchParam {

	public static final int JOB_CODE_INSERT_LIQUOR 		= 1001;	// 술 정보 등록
	public static final int JOB_CODE_UPDATE_LIQUOR 		= 1002;	// 술 정보 수정
	public static final int JOB_CODE_REMOVE_LIQUOR 		= 1003;	// 술 정보 제거(영구)
	
	public static final String JOB_NAME_INSERT_LIQUOR 	= "술 정보 등록";
	public static final String JOB_NAME_UPDATE_LIQUOR 	= "술 정보 수정";
	public static final String JOB_NAME_REMOVE_LIQUOR 	= "술 정보 영구 제거";
		
	private Integer	id;
	private Integer	adminId;
	private Integer jobCode;
	private String	jobName;
	private Integer contentId;
	private Integer contentType;
	private Date 	regDate;
		
}
