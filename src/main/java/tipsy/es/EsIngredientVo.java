package tipsy.es;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import tipsy.common.basic.AbstractSearchParam;

@Data
public class EsIngredientVo {

	public static final int UPLOAD_STATE_OK 	= 0;	// 사용 가능
	public static final int UPLOAD_STATE_NO 	= 1;	// 사용 불가능
	public static final int UPDATE_STATE_OK 	= 0;	// 수정 가능
	public static final int UPDATE_STATE_NO 	= 1;	// 수정 불가능
	public static final int UPDATE_STATE_WAIT 	= 2;	// 승인 대기중
	
	private Integer	ingdId;
	private String 	nameKr;
	private String	nameEn;
	private Integer uploadState;
	private Integer updateState;
	private Integer	category1Id;
	private Integer	category2Id;
	private Integer	category3Id;
	private Integer	category4Id;
	private String 	description;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssZ",  timezone="Asia/Seoul")
	private Date 	regDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssZ",  timezone="Asia/Seoul")
	private Date 	updateDate;
	
	private Integer regAdmin;
	private String	regAdminName;
	private Integer updateAdmin;
	private String	updateAdminName;
	
	// join data
	private String	category1Name;
	private String	category2Name;
	private String	category3Name;
	private String	category4Name;
	
	private String	repImg;
	
	private String	procCode;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ssZ",  timezone="Asia/Seoul")
	private Date	esUpdateDate;
	
	@Override
	public String toString() {
		return ingdId + "";
	}
	
}
