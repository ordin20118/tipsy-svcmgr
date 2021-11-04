package tipsy.svcmgr.web.dao;

import java.util.Date;

import lombok.Data;
import tipsy.common.basic.AbstractSearchParam;

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
