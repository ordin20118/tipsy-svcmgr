package gwang.allalcohol.web.restapi;

import java.util.ArrayList;

import gwang.common.util.KeyStringValue;
import lombok.Data;


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
