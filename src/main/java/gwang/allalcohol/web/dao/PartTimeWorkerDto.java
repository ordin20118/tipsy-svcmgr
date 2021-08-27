package gwang.allalcohol.web.dao;

import java.util.Date;

import gwang.common.basic.AbstractSearchParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
