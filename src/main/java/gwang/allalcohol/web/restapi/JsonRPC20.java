package gwang.allalcohol.web.restapi;

import lombok.Data;


@Data
public class JsonRPC20 {

	private String jsonrpc;
	private String method;
	private int id;
	
}
