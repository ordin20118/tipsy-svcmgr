package gwang.allalcohol.web.restapi;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gwang.allalcohol.web.dao.BeerDao;
import gwang.allalcohol.web.dao.BeerDto;
import gwang.common.basic.BasicListResponse;
import gwang.common.configuration.Configuration;
import gwang.common.configuration.LoggerName;

//@Service("restApiService")
@Service
public class RestApiServiceImpl {
	
	protected Configuration config = Configuration.getInstance();
	private Logger log = LoggerFactory.getLogger(LoggerName.SVC);
	
	@Autowired
	private BeerDao couponDao; 
//
//	// 쿠폰 코드 생성
//	public BasicListResponse createCoupon(int tid, BeerDto coupon) {
//		
//		BasicListResponse res = new BasicListResponse();
//		
//		int n = 7; // n자리 쿠폰 
//		int retryCnt = 100;
//		String couponCode = "";		
//		
//		try {
//			
//			for(int k=0; k<retryCnt; k++) {
//				
//				char[] chs = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
//
//						'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
//
//						'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
//
//
//				Random rd = new Random();
//				StringBuilder sb = new StringBuilder();
//
//				for (int i = 0; i < n; i++) {
//					char ch = chs[rd.nextInt(chs.length)];
//					sb.append(ch);
//				}
//				
//				couponCode = "LUX" + sb.toString();
//				coupon.setCode(couponCode);
//				coupon.setState(BeerDto.STATE_ACTIVE);
//				
//				if(coupon.getDescription() == null || coupon.getDescription() == "") {
//					coupon.setDescription("10% 할인 쿠폰");
//				}
//				
//				// 중복 확인
//				BeerDto dupliCoupon = couponDao.selectOne(couponCode);
//				
//				if(dupliCoupon != null) {
//					continue;
//				} else {
//					couponDao.insert(coupon);	
//					break;
//				}			
//				
//			}
//			BeerDto newCoupon = couponDao.selectOne(couponCode);
//			res.setData(newCoupon);
//			res.setState(BasicListResponse.STATE_SUCCESS);
//			res.setStateMessage(BasicListResponse.STATE_SUCCESS_MESSAGE);
//		} catch(Exception e) {
//			log.error("[Create coupon error...]:" + e.getMessage(), e);
//			res.setState(BasicListResponse.STATE_ERROR);
//		}
//
//		return res;
//		
//	}
//	
//	
//	// 중복 확인
//	
//	// 쿠폰 조회
//	public BasicListResponse getCoupon(int tid, String code) {
//		
//		BasicListResponse res = new BasicListResponse();
//		
//		try {
//			BeerDto coupon = couponDao.selectOne(code);
//			res.setData(coupon);
//			res.setState(BasicListResponse.STATE_SUCCESS);
//			res.setStateMessage(BasicListResponse.STATE_SUCCESS_MESSAGE);
//		} catch(Exception e) {
//			log.error("[Get coupon error...]:" + e.getMessage(), e);
//			res.setState(BasicListResponse.STATE_ERROR);
//		}
//		
//		return res;
//	}
//	
//	public BasicListResponse useCoupon(int tid, String code) {
//		
//		BasicListResponse res = new BasicListResponse();
//		
//		try {
//			BeerDto coupon = couponDao.selectOne(code);
//			res.setData(coupon);
//			
//			if(coupon.getState() == BeerDto.STATE_ACTIVE) {
//				coupon.setState(BeerDto.STATE_USED);
//				coupon.setUseDate(new Date());
//				couponDao.updateByCode(coupon);	
//
//				res.setState(BasicListResponse.STATE_SUCCESS);
//				res.setStateMessage(BasicListResponse.STATE_SUCCESS_MESSAGE);
//			} else {
//				res.setState(BasicListResponse.STATE_ERROR);
//				res.setErrorMessage("Already Used Coupon ...");
//			}			
//			
//		} catch(Exception e) {
//			log.error("[Use coupon error...]:" + e.getMessage(), e);
//			res.setState(BasicListResponse.STATE_ERROR);
//		}
//		
//		return res;
//	}
//	
//	
//	
//	
//	// 쿠폰 리스트 조회
//	public BasicListResponse getCouponList(int tid, BeerDto coupon) {
//		
//		BasicListResponse res = new BasicListResponse();
//		
//		try {
//			
//			List<BeerDto> couponList = couponDao.selectList(coupon);
//			
//			res.setData(coupon);
//			res.setList(couponList);	
//			res.setState(BasicListResponse.STATE_SUCCESS);
//			res.setStateMessage(BasicListResponse.STATE_SUCCESS_MESSAGE);
//			
//		} catch(Exception e) {
//			log.error("[Get coupon list error...]:" + e.getMessage(), e);
//			res.setState(BasicListResponse.STATE_ERROR);
//		}
//		return res;
//	}
//	       	

	
}
