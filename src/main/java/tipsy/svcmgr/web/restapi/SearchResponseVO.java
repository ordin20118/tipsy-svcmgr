package tipsy.svcmgr.web.restapi;

import java.util.ArrayList;

import lombok.Data;


@Data
public class SearchResponseVO {
	
	private long took;
	private String status;
	private PaginationVO pagination;
	private ArrayList<MetaResultVO> result;
}
