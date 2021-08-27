package gwang.allalcohol.web.dao;

import java.util.Date;
import lombok.Data;

@Data
public class RawCategoryDto {
	private Integer id;
	private Integer parent;
	private String 	name;
	private Date 	regDate;
}
