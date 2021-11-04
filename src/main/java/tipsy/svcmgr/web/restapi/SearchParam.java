package tipsy.svcmgr.web.restapi;

import java.util.ArrayList;

import lombok.Data;
import tipsy.common.util.KeyStringValue;


@Data
public class SearchParam {
	private int nowPage;
	private int rowPerPage;
	private ArrayList<KeyStringValue> fields;
	
	public void addSearchField(KeyStringValue sFiels) {
		if(fields == null) {
			fields = new ArrayList<KeyStringValue>();
		}
		fields.add(sFiels);
	}
}
