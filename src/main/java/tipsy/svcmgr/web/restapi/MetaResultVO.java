package tipsy.svcmgr.web.restapi;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MetaResultVO {

	// JSON으로는 score로 사용하고 코드에서는 score로 사
	@JsonProperty("_score")
	private float score;
	private String ccid;
	private String version;
	@JsonProperty("meta-type")
	private String metaType;

}
