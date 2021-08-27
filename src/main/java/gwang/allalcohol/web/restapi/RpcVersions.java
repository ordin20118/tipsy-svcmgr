package gwang.allalcohol.web.restapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RpcVersions extends JsonRPC20 {
	VersionsParams params;
}

@Data
class VersionsParams {
	
	@JsonProperty ("api_v")
	private String apiV;			// CCIS API Version
	private String ccid;			// 삭제할 복합 컨텐츠의 ID
	@JsonProperty ("pub_key")
	private String pubKey;			// 사용자의 이더리움 계정 주소 
	private String version;			// 복합 컨텐츠의 Root CID
	private String target;			// 대상 네트워크의 시스템 종류( eth, eos, ... )
	
}
