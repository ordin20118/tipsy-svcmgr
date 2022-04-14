package tipsy.svcmgr.web.controller.param;

import lombok.Data;
import tipsy.common.basic.AbstractSearchParam;

@Data
public class WebSearchParam extends AbstractSearchParam {
	
	private String orgKeyword;
	private String keyword;

}
