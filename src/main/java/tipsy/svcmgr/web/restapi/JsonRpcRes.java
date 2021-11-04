package tipsy.svcmgr.web.restapi;

import lombok.Data;

@Data
public class JsonRpcRes {

	private int id;
	private String jsonrpc;
	private String result;
	private String error;
}
