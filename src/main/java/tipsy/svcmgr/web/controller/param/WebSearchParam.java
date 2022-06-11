package tipsy.svcmgr.web.controller.param;

import lombok.Data;
import tipsy.common.basic.AbstractSearchParam;

@Data
public class WebSearchParam extends AbstractSearchParam {
	
	public static final String SEARCH_TARGET_LIQUOR = "liquor";
	public static final String SEARCH_TARGET_INGD 	= "ingredient";
	public static final String SEARCH_TARGET_EQUIP 	= "equipment";
	
	private String orgKeyword;
	private String keyword;
	
	private String target;
	
	private Integer categLv;
	private Integer categId;

}
