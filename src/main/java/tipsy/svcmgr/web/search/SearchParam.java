package tipsy.svcmgr.web.search;

import java.util.List;

import lombok.Data;
import tipsy.common.basic.AbstractSearchParam;
import tipsy.svcmgr.web.controller.param.WebSearchParam;

@Data
public class SearchParam {
	
	private String orgKeyword;
	private String keyword;
	
	public List<SearchField> searchField;
	
	public WebSearchParam orgParam;
	
	// paging
	public int from;
	public int size; // page
	public SortField sort;
	
	public static String keywordFiter(String keyword) {
		// 공백 제거 및 소문자화
		keyword = keyword.replaceAll(" ", "");
		keyword = keyword.toLowerCase();
		
		// 특수문자(special character) 제거
		// 특수문자 정의 ARRAY		

		String[] scA = {"*", "+", "$", "|", "."};
		String[] scB = {"(", ")", "{", "}", "^", "[", "]"};
		String[] scC = {"!", "#", "%", "&", "@", "\"", "'", ":", ";", "-", "`",
						"<", ">", "~"};
	    
	    //String[] exceptSc = {"+", "@"}; // 특정 특수문자 Arr
		 
	   	// 특정 특수문자 제외한, 특수문자 제거
	    for (String item : scA) {	    	
		     if (keyword.contains(item)) {		    	 
		    	 keyword = keyword.replaceAll("["+item+"]", "");		    	 
		     } 
	    }
	    for (String item : scB) {	    	
		     if (keyword.contains(item)) {		    	 
		    	 keyword = keyword.replaceAll("\\"+item, "");		    	 
		     } 
	    }	    
	    for (String item : scC) {	    	
		     if (keyword.contains(item)) {		    	 
		    	 keyword = keyword.replaceAll(item, "");		    	 
		     } 
	    }
		
		return keyword;
	}
		
	public Integer getFieldType(int idx) {
		return searchField.get(idx).getFieldType();
	}
	
	public Object getFieldValue(int idx) {
		return searchField.get(idx).getFieldValue();
	}
	
}
	

@Data
class SearchField {
	
	public static final String SEARCH_TYPE_MATCH = "match";
	
	public String searchType; // match, term..
	public String searchFieldName;
	public Integer fieldType; // 0:제품, 1:카테고리, 2:제조사
	public Object fieldValue;
	public Double weight;

}

@Data
class SortField {
	
	public static final String ORDER_TYPE_DESC = "desc";
	public static final String ORDER_TYPE_ASC = "asc";
	
	public static enum Type {
	    SCORE,
	    STRING,
	    INT,
	    FLOAT,
	    LONG,
	    DOUBLE
	}
	
	private String field;
	private Type type = Type.SCORE;  
	private String orderType = ORDER_TYPE_DESC;
}