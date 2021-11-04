package tipsy.svcmgr.web.vo;

import java.util.List;

import lombok.Data;
import tipsy.svcmgr.web.dao.ImageDto;
import tipsy.svcmgr.web.dao.PartJobDto;
import tipsy.svcmgr.web.dao.PartTimeWorkerDto;

@Data
public class JoinedPartJobVo {
	
	private PartJobDto job;
	private PartTimeWorkerDto worker;
	private Object content;
	private List<ImageDto> images;

}
