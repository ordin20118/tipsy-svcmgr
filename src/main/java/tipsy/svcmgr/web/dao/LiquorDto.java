package tipsy.svcmgr.web.dao;

import java.util.Date;

import lombok.Data;
import tipsy.common.basic.AbstractSearchParam;

@Data
public class LiquorDto extends AbstractSearchParam {

	public static final int UPLOAD_STATE_OK 	= 0;	// 사용 가능
	public static final int UPLOAD_STATE_NO 	= 1;	// 사용 불가능
	public static final int UPDATE_STATE_OK 	= 0;	// 수정 가능
	public static final int UPDATE_STATE_NO 	= 1;	// 수정 불가능
	public static final int UPDATE_STATE_WAIT 	= 2;	// 승인 대기중
	
	public static final int TYPE_BOTTLE			= 0; 	// 병
	public static final int TYPE_CAN			= 1; 	// 캔
	public static final int TYPE_PLASTIC		= 2; 	// 패트병
	
	private Integer	liquorId;
	private String 	nameKr;
	private String	nameEn;
	private Integer type;
	private Integer uploadState;
	private Integer updateState;
	private Float 	abv;
	private Integer	volume;
	private Integer countryId;
	private String	region;
	private String	categoryId;
	private Float	price;
	private String 	description;
	private String	history;
	private Date 	regDate;
	private Date 	updateDate;
	
	private String 	dispCountry;
	private String 	dispCategory;
	
	// sub data
	private String	adminName;
	
	
}
