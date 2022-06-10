package tipsy.svcmgr.web.dao;

import java.util.Date;

import lombok.Data;
import tipsy.common.basic.AbstractSearchParam;

@Data
public class EquipmentDto extends AbstractSearchParam {

	public static final int UPLOAD_STATE_OK 	= 0;	// 사용 가능
	public static final int UPLOAD_STATE_NO 	= 1;	// 사용 불가능
	public static final int UPDATE_STATE_OK 	= 0;	// 수정 가능
	public static final int UPDATE_STATE_NO 	= 1;	// 수정 불가능
	public static final int UPDATE_STATE_WAIT 	= 2;	// 승인 대기중
	
	private Integer	equipId;
	private String 	nameKr;
	private String	nameEn;
	private Integer uploadState;
	private Integer updateState;
	private String	category1Id;
	private String	category2Id;
	private String	category3Id;
	private String	category4Id;
	private String 	description;
	private Date 	regDate;
	private Date 	updateDate;
	
	private Integer regAdmin;
	private Integer updateAdmin;
	
	// sub data
	private String 	dispCategory;
	
	// join data
	private String	category1Name;
	private String	category2Name;
	private String	category3Name;
	private String	category4Name;	
	private String	repImg;
	
	private String	regAdminName;
	private String	updateAdminName;
	
}
