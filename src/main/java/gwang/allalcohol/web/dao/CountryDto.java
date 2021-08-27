package gwang.allalcohol.web.dao;

import java.util.Date;

import gwang.common.basic.AbstractSearchParam;
import lombok.Data;

@Data
public class CountryDto extends AbstractSearchParam {

	public static final int TYPE_COUNTRY 		= 1;	// 국가
	public static final int TYPE_REGION 		= 2 ;	// 지역
	
	private Integer	countryId;
	private String 	name;
	private Integer type;
	private String	alpha2;
	private String 	alpha3;
	private String 	image;
	private Date 	regDate;
	private Date 	updateDate;
	
}
