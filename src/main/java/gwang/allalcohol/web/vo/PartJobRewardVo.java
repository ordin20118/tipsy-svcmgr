package gwang.allalcohol.web.vo;

import lombok.Data;

@Data
public class PartJobRewardVo {

	private Float totalReward;		// 총 보상금 = 유보금 + 지급된 보상
	private Float reserveReward;	// 유보금
	private Float paidReward;		// 지급된 보상
	private Float rejectedReward;	// 거절된 보상
	
}
