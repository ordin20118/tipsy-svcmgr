package gwang.allalcohol.web.vo;

import java.util.List;

import gwang.allalcohol.web.dao.ImageDto;
import gwang.allalcohol.web.dao.PartJobDto;
import gwang.allalcohol.web.dao.PartTimeWorkerDto;
import lombok.Data;

@Data
public class JoinedPartJobVo {
	
	private PartJobDto job;
	private PartTimeWorkerDto worker;
	private Object content;
	private List<ImageDto> images;

}
