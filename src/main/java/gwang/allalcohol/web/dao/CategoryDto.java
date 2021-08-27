package gwang.allalcohol.web.dao;

import java.util.Date;

import gwang.common.basic.AbstractSearchParam;
import lombok.Data;

@Data
public class CategoryDto extends AbstractSearchParam {

	public static final int STATUS_ACTIVE 	= 0;	// 활성
	public static final int STATUS_DEACTIVE 	= 1 ;	// 비활성
	
	private String	categId;
	private Integer rawCategId;
	private String 	categName;
	private Integer level;
	private String	sortSeq;
	private Integer status;
	private Date 	regDate;
	private Date 	updateDate;
	
}
