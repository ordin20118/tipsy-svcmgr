package gwang.allalcohol.web.restapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RpcUpload extends JsonRPC20 {
	
	UploadParams params;
	
}

// Data라는 어노테이션을 통해서 get,set을 작성하지 않고도 사용가능
@Data
class UploadParams {
	
	private String ccid;		// 업로드할 복합 컨텐츠의 ID
	private String version;		// 업로드할 복합 컨텐츠의 Root CID
	@JsonProperty ("gas_level")	// gasLevel을 JSON에서는 gas_level로 사
	private String gasLevel;	// 업로드 시 발생할 gas량 ( slow, average, fast )
	@JsonProperty ("node_id")
	private String nodeId;		// 업로드한 IPFS의 노드ID
	private String target;		// 대상 네트워크의 시스템 종류( eth, eos, ... )
	@JsonProperty ("prv_path")
	private String prvPath;		// 사용자의 keyFile 경
	private String password;	// keyFile의 패스워드
	
}
