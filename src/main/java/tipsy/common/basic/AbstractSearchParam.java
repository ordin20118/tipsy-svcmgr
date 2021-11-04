package tipsy.common.basic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.Data;

@Data
public abstract class AbstractSearchParam {

	protected PagingVO  paging;
	protected OrderByVO orderby;	
	
	
	
	public PagingVO getPaging() {
		if(paging == null) {
			paging = new PagingVO();
		}
		return paging;
	}
	
	public String getUrlParam() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		StringBuffer urlParam = new StringBuffer("");
		if(paging != null) {
			urlParam.append(paging.getUrlParam());
		}
		if(orderby != null) {
			urlParam.append(orderby.getUrlParam());
		}
		
		Method[] methods = this.getClass().getDeclaredMethods();
		for(int i=0; i<methods.length; i++) {
			String name = methods[i].getName();
			if(name.startsWith("get")) {
				Object value = methods[i].invoke(this);
				
				String fieldName01 = name.substring(3, 4);
				String fieldName02 = name.substring(4);
				String fieldName = fieldName01.toLowerCase() + fieldName02;
				
				if(value != null) {
					urlParam.append(fieldName + "=" + value + "&");
				}
			}
			
		}
		return urlParam.toString();
	}
	
	
	
}
