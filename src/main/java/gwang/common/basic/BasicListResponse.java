package gwang.common.basic;

import java.util.List;

import org.json.JSONObject;

import gwang.common.util.GenException;
import lombok.Data;

@Data
public class BasicListResponse {

	
	public static final int STATE_SUCCESS = 0;
	public static final int STATE_ERROR   = 1;
	
	public static final int STATE_DUPLICATION = 10;
	public static final int STATE_UNDEFINE = 11;
	public static final int STATE_NO_DATA = 12;
	
	public static final String STATE_SUCCESS_MESSAGE = "success";
	
	public static final int TX_CODE_ENOUGH_RENA = 501;				
	public static final int TX_CODE_NOT_ENOUGH_RENA = 502;
	
	private AbstractSearchParam  sParam;
	private List<?> list;
	
	private Object data;
	
	
	public BasicListResponse() {
		state = STATE_SUCCESS;
		stateMessage = STATE_SUCCESS_MESSAGE;
	}
	public BasicListResponse(int state) {
		this.state = state;
	}
	public BasicListResponse(int state, String errorMessage) {
		this.state = state;
		this.errorMessage = errorMessage;
	}
	
	
	private int state;
	private String stateMessage;
	private String errorMessage;
	
	
	public String genErrorJson() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("state", state);
		jsonObj.put("error_message", errorMessage);
		
		return jsonObj.toString();
	}
	
	public String genErrorJson(GenException e) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("state", e.getErrorCode());
		jsonObj.put("error_message", e.getMessage());
		
		return jsonObj.toString();
	}
	
}
