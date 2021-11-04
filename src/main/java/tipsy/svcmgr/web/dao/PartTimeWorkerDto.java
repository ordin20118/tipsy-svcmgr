package tipsy.svcmgr.web.dao;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import tipsy.common.basic.AbstractSearchParam;

@Data
@EqualsAndHashCode(callSuper=false)
public class PartTimeWorkerDto extends AbstractSearchParam {

	private Integer	workerId;
	private String 	name;
	private String 	code;
	private String	bank;
	private String 	bankAccount;
	private Date 	regDate;
	private Date 	updateDate;
	
}
