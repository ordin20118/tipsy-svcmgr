package gwang.common.basic;

import lombok.Data;

@Data
public class OrderByVO {

	public OrderByVO() {
	}
	
	public OrderByVO(String field, String sorting) {
		this.field = field;
		this.sorting = sorting;
	}
	
	public static final String SORT_ASC  = "asc";
	public static final String SORT_DESC = "desc";
	
	private String field;
	private String sorting = SORT_DESC;
	
	public String getStmt() {
		
		StringBuffer sb = new StringBuffer();
		if(field != null && !field.trim().equals("") ) {
			sb.append(field);
			
			if(sorting != null && !sorting.trim().equals("")) {
				sb.append(" " +sorting);
			}
			return sb.toString();
		} else {
			return null;
		}
	}
	
	
	public String getUrlParam() {
		if(field == null) {
			return null;
		} else {
			String urlParam = 
					"orderby.field="+field+"&" +
					"orderby.sorting="+sorting+"&"	
					;	
			return urlParam;
		}
	}
}
