package tipsy.svcmgr.web.vo;

import java.util.Date;

import lombok.Data;

@Data
public class LiquorListItemVo extends ContentInfoVo {
	
	private Integer liquorId;
	private String	nameKr;
	private String	nameEn;
	private Integer	vintage;
	private Float	abv;
	private Integer	countryId;
	private Integer	regionId;
	private String	region;
	private Integer category1Id;
	private Integer category2Id;
	private Integer category3Id;
	private Integer category4Id;
	private String	category1Name;
	private String	category2Name;
	private String	category3Name;
	private String	category4Name;
	private String	repImg;
	private Date	regDate;
	private Date	updateDate;
	
}
