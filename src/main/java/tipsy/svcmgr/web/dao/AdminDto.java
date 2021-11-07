package tipsy.svcmgr.web.dao;

import java.util.Date;

import lombok.Data;
import tipsy.common.basic.AbstractSearchParam;

@Data
public class AdminDto extends AbstractSearchParam {

	public static final int ADMIN_STATUS_NORMAL = 0;	// 정상
	
	private Integer	adminId;
	private String 	name;
	private String 	nickname;
	private Integer	status;
	private String 	password;
	private String	email;
	private Date 	regDate;
	private Date 	updateDate;
		
}
