package tipsy.svcmgr.web.dao;

import java.util.Date;

import lombok.Data;
import tipsy.common.basic.AbstractSearchParam;

@Data
public class RawLiquorDto extends AbstractSearchParam {

	public static final int UPLOAD_STATE_OK 	= 0;	// 사용 가능
	public static final int UPLOAD_STATE_NO 	= 1;	// 사용 불가능
	public static final int UPDATE_STATE_OK 	= 0;	// 수정 가능
	public static final int UPDATE_STATE_NO 	= 1;	// 수정 불가능
	public static final int UPDATE_STATE_WAIT 	= 2;	// 승인 대기중
	
	public static final int TYPE_BOTTLE			= 0; 	// 병
	public static final int TYPE_CAN			= 1; 	// 캔
	public static final int TYPE_PLASTIC		= 2; 	// 패트병
	
	public static final int SITE_INTERNAL		= 0;
	public static final int SITE_WHISKYCOM		= 1;
	public static final int SITE_KANAWINE		= 2;
	
	private Integer	liquorId;
	private String 	nameKr;
	private String	nameEn;
	private Integer type;
	private Integer uploadState;
	private Integer updateState;
	private Integer vintage;
	private Float 	abv;
	private Integer	volume;
	private Integer countryId;
	private String	region;
	private Integer regionId;
	private Integer	category1Id;
	private Integer	category2Id;
	private Integer	category3Id;
	private Integer	category4Id;
	private Float	price;
	private String 	description;
	private String	history;
	private	Integer	site;
	private String	url;
	private Date 	regDate;
	private Date 	updateDate;
	
	private Integer regAdmin;
	private Integer updateAdmin;
	
	// sub data
	private String 	dispCountry;
	private String 	dispCategory;
	
	// join data
	private String	countryName;
	private String	category1Name;
	private String	category2Name;
	private String	category3Name;
	private String	category4Name;	
	private String	repImg;
	
	private String	regAdminName;
	private String	updateAdminName;
	
}
