package tipsy.svcmgr.web.dao;

import java.util.Date;

import lombok.Data;
import tipsy.common.basic.AbstractSearchParam;

@Data
public class PartJobDto extends AbstractSearchParam {
	
	public static final int JOB_TYPE_NEW 		= 0;		// 새로운 데이터
	public static final int JOB_TYPE_UPDATE 	= 1 ;		// 수정
	public static final int JOB_TYPE_REQUEST 	= 2 ;		// 추가 요청
	
	public static final int JOB_STATE_WAIT 			= 0 ;	// 승인 대기중
	public static final int JOB_STATE_COMPLETE 		= 1 ;	// 승인 완료
	public static final int JOB_STATE_DENIED  		= 2 ;	// 승인 거부
	public static final int JOB_STATE_PART_DENIED  	= 3 ;	// 부분 거부
	
	// data type 0:맥주, 1:사케, 2:전통주, 3:와인, 4:보드카,칵테일..
	public static final int DATA_TYPE_BEER = 0;
	public static final int DATA_TYPE_SAKE = 1;
	public static final int DATA_TYPE_KOR_TRADITIONAL = 2;
	public static final int DATA_TYPE_WINE = 3;	
	
	private Integer	partJobId;
	private Integer type;
	private Integer workerId;
	private Integer	dataId;
	private Integer dataType;
	private Integer state;
	private String 	info;
	private Float	reward;
	private Float	paidReward;
	private String	rewardDesc;
	private Date 	regDate;
	private Date 	approvDate;
	
}
