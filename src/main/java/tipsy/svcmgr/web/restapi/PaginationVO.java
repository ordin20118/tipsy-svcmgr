package tipsy.svcmgr.web.restapi;

import lombok.Data;

@Data
public class PaginationVO {

	private long searchCount;
	private int nowPage;
	private int rowPerPage;

}
