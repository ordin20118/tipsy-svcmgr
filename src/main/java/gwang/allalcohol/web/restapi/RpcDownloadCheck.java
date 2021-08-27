package gwang.allalcohol.web.restapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RpcDownloadCheck extends JsonRPC20 {
	DownloadCheckParams params;
}

@Data
class DownloadCheckParams {
	
	@JsonProperty ("api_v")
	private String apiV;			// CCIS API Version
	private String ccid;			// 삭제할 복합 컨텐츠의 ID
	@JsonProperty ("pub_key")
	private String pubKey;			// 다운로드 요청자의 블록체인 계정
	private String version;			// 복합 컨텐츠의 Root CID
	@JsonProperty ("content_path")
	private String contentPath;		// 컨텐츠 파일에 대한 경로 
	private String target;			// 대상 네트워크의 시스템 종류( eth, eos, ... )
	
}
