package tipsy.svcmgr.web.dao;

import java.util.Date;

import lombok.Data;
import tipsy.common.basic.AbstractSearchParam;

@Data
public class RawCategDto extends AbstractSearchParam {
	
	private Integer	id;
	private Integer parent;
	private String 	name;
	private String 	nameEn;
	private Date 	regDate;
	
}
