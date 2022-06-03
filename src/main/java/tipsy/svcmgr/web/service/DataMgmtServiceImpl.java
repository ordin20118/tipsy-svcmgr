package tipsy.svcmgr.web.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import tipsy.common.basic.BasicListResponse;
import tipsy.common.configuration.Configuration;
import tipsy.common.configuration.LoggerName;
import tipsy.common.util.ImageResizer;
import tipsy.svcmgr.web.controller.param.ImageParam;
import tipsy.svcmgr.web.controller.param.PagingParam;
import tipsy.svcmgr.web.dao.AdminDao;
import tipsy.svcmgr.web.dao.AdminDto;
import tipsy.svcmgr.web.dao.BeerDao;
import tipsy.svcmgr.web.dao.BeerDto;
import tipsy.svcmgr.web.dao.CategoryDao;
import tipsy.svcmgr.web.dao.CategoryDto;
import tipsy.svcmgr.web.dao.CountryDao;
import tipsy.svcmgr.web.dao.CountryDto;
import tipsy.svcmgr.web.dao.ImageDao;
import tipsy.svcmgr.web.dao.ImageDto;
import tipsy.svcmgr.web.dao.LiquorDao;
import tipsy.svcmgr.web.dao.LiquorDto;
import tipsy.svcmgr.web.dao.ManageLogDao;
import tipsy.svcmgr.web.dao.ManageLogDto;
import tipsy.svcmgr.web.dao.PartJobDao;
import tipsy.svcmgr.web.dao.PartJobDto;
import tipsy.svcmgr.web.dao.PartTimeWorkerDao;
import tipsy.svcmgr.web.dao.PartTimeWorkerDto;
import tipsy.svcmgr.web.dao.RawCategoryDto;
import tipsy.svcmgr.web.vo.ContentInfoVo;
import tipsy.svcmgr.web.vo.JoinedPartJobVo;
import tipsy.svcmgr.web.vo.PartJobRewardVo;

@Service
public class DataMgmtServiceImpl implements DataMgmtService {

	private Logger log = LoggerFactory.getLogger(LoggerName.SVC);

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private ManageLogDao manageLogDao;
	
	@Autowired
	private PartTimeWorkerDao partTimeWorkerDao;
	
	@Autowired
	private PartJobDao partJobDao;
	
	@Autowired
	private BeerDao beerDao;
	
	@Autowired
	private LiquorDao liquorDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private CategoryDao categoryDao;
	

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
	@Override
	public BasicListResponse insertBeerInfo(int tid, BeerDto beer, PartTimeWorkerDto worker, PartJobDto job) throws Exception{
		BasicListResponse res = new BasicListResponse();

		log.debug("[worker]:"+worker);
		
		PartTimeWorkerDto findedWorker = partTimeWorkerDao.selectByNameAccount(worker);
		
		// insert beer
		beer.setUploadState(BeerDto.UPLOAD_STATE_NO);
		beer.setUpdateState(BeerDto.UPDATE_STATE_WAIT);
		
		log.debug("[beer]:"+beer);
		
		beerDao.insert(beer);

		log.debug("[" + tid + "][맥주 정보 입력 완료]");
		
		job.setWorkerId(findedWorker.getWorkerId());
		job.setDataId(beer.getBeerId());
		job.setDataType(PartJobDto.DATA_TYPE_BEER);
		job.setState(PartJobDto.JOB_STATE_WAIT);
		
		log.debug("[job]:"+job);
		
		// insert part job
		partJobDao.insert(job);
		
		res.setData(beer);
		res.setState(BasicListResponse.STATE_SUCCESS);
		res.setStateMessage(BasicListResponse.STATE_SUCCESS_MESSAGE);

		return res;
	}
	

	@Override
	public BasicListResponse insertSpiritsInfo(int tid, LiquorDto liquor) throws Exception {
		BasicListResponse res = new BasicListResponse();

		// insert liquor
		liquor.setUploadState(BeerDto.UPLOAD_STATE_NO);
		liquor.setUpdateState(BeerDto.UPDATE_STATE_WAIT);
		
		log.debug("[liquor]:"+liquor);
		
		liquorDao.insert(liquor);

		log.debug("[" + tid + "][증류주 정보 입력 완료]");
		
		// select admin info
		AdminDto admin = adminDao.selectByName(liquor.getAdminName());
		
		// insert manage log
		ManageLogDto log = new ManageLogDto();
		log.setAdminId(admin.getAdminId());
		log.setContentId(liquor.getLiquorId());
		log.setContentType(ContentInfoVo.CONTENT_TYPE_LIQUOR);
		log.setJobCode(ManageLogDto.JOB_CODE_INSERT_LIQUOR);
		log.setJobName(ManageLogDto.JOB_NAME_INSERT_LIQUOR);		
		manageLogDao.insert(log);
				
		res.setData(liquor);
		res.setState(BasicListResponse.STATE_SUCCESS);
		res.setStateMessage(BasicListResponse.STATE_SUCCESS_MESSAGE);

		return res;
	}


	@Override
	public BasicListResponse checkInPartTimeWorker(int tid, PartTimeWorkerDto worker) {
		BasicListResponse res = new BasicListResponse();
		
		try {
			
			worker.setBankAccount(worker.getBankAccount().replace("-", ""));
			
			PartTimeWorkerDto dupWorker = partTimeWorkerDao.selectByNameAccount(worker);
			
			log.debug("[CheckIn] 조회된 계정:"+dupWorker);
			
			if(dupWorker != null) {
				res.setState(BasicListResponse.STATE_SUCCESS);	
			} else {
				res.setState(BasicListResponse.STATE_ERROR);
			}
			
		} catch(Exception e) {
			log.error("[getPartJobList]Error:"+e.getMessage(), e);
			res.setState(BasicListResponse.STATE_ERROR);
		}
		
		return res;
	}

	public static final  DecimalFormat LEVEL_DF[] = {
			new DecimalFormat("00"),
			new DecimalFormat("0000"),
			new DecimalFormat("000000"),
			new DecimalFormat("00000000")
	};
	
	public BasicListResponse addCategory(int tid) {		
		
		BasicListResponse res = new BasicListResponse();
		
		List<RawCategoryDto> rawCategList = categoryDao.selectRawCateg();
		
		for(int i=0; i<rawCategList.size(); i++) {
			
			RawCategoryDto rawCateg = rawCategList.get(i);
			
			CategoryDto dbCateg = categoryDao.selectByRawId(rawCateg.getId());
			
			if(dbCateg == null) {
				
				if(rawCateg.getParent().intValue() == -1) {
					
					//기존에 디비에 없고 level 1이면 시퀀스 구해서 DB에 넣는다.	
					CategoryDto maxValueLevel1Cate = categoryDao.selectMaxSeq(null, 1);
					int maxVal = 1;
					
					if(maxValueLevel1Cate != null) {
						maxVal = Integer.parseInt(maxValueLevel1Cate.getCategId()) + 1;
					}
					
					CategoryDto newCateg = new CategoryDto();
					newCateg.setCategId(LEVEL_DF[0].format(maxVal));
					newCateg.setCategName(rawCateg.getName());
					newCateg.setRawCategId(rawCateg.getId());
					newCateg.setLevel(1);
					newCateg.setSortSeq(LEVEL_DF[0].format(maxVal));
					newCateg.setStatus(CategoryDto.STATUS_DEACTIVE);
					categoryDao.insert(newCateg);
					
					log.debug("["+tid+"] Insert New Category!! " + newCateg);
					
				} else {
					
					//기존에 디비에 없고 level 1이 아니면 부모를 가져와서 하위 레벨에 넣는다.
					CategoryDto dbParentCateg = categoryDao.selectByRawId(rawCateg.getParent());
					if(dbParentCateg == null) {
						log.error("["+tid+"] Not Exists Parent category. " + rawCateg);			
					} else {
						
						int parentLevel = dbParentCateg.getLevel();
						CategoryDto maxValueCate = categoryDao.selectMaxSeq(dbParentCateg.getCategId(), parentLevel+1);
						
						int maxVal = Integer.parseInt(dbParentCateg.getCategId() + "01");
						if(maxValueCate != null) {
							maxVal = Integer.parseInt(maxValueCate.getCategId()) + 1;
						}
							
						CategoryDto newCateg = new CategoryDto();
						newCateg.setCategId(LEVEL_DF[parentLevel].format(maxVal));
						newCateg.setCategName(rawCateg.getName());
						newCateg.setRawCategId(rawCateg.getId());
						newCateg.setLevel(parentLevel+1);
						newCateg.setSortSeq(LEVEL_DF[parentLevel].format(maxVal));
						newCateg.setStatus(CategoryDto.STATUS_DEACTIVE);
						categoryDao.insert(newCateg);
						
						log.debug("["+tid+"]		Insert New Category!! " + newCateg);
						
					}
				}
				
			} else {
				log.debug("["+tid+"]		Category already exists. " + rawCateg);			
			}
		}
		
		res.setState(BasicListResponse.STATE_SUCCESS);
		res.setStateMessage(BasicListResponse.STATE_SUCCESS_MESSAGE);
		
		return res;
	}
	

	@Override
	public BasicListResponse getCategoryChild(int tid, String categId) {
		
		BasicListResponse res = new BasicListResponse();
		
		try {
			CategoryDto parent = categoryDao.selectOne(categId);
			List<CategoryDto> childs = categoryDao.selectChild(categId, parent.getLevel());
			res.setList(childs);
			res.setState(BasicListResponse.STATE_SUCCESS);
			res.setStateMessage(BasicListResponse.STATE_SUCCESS_MESSAGE);
		
		} catch(Exception e) {
			log.error("[getCategoryChild] Error:"+e.getMessage(), e);
		}
		
		return res;
	}
	

	@Override
	public BasicListResponse searchBeer(int tid, String keyword) {
		
		BasicListResponse res = new BasicListResponse();
		
		try {
			
			List<BeerDto> beerList = beerDao.searchName(keyword);
			
			res.setList(beerList);
			res.setState(BasicListResponse.STATE_SUCCESS);
			res.setStateMessage(BasicListResponse.STATE_SUCCESS_MESSAGE);
		
		} catch(Exception e) {
			log.error("[searchBeer] Error:"+e.getMessage(), e);
		}
		
		return res;
	}



	@Override
	public BasicListResponse uploadImage(int tid, ImageDto imageParam) throws Exception {
		BasicListResponse res = new BasicListResponse();
		
		try {
			
			// 파일 저장
			String dirPath = Configuration.getInstance().getStringExtra("datapath.image");
			String subRoot = "";
			
			if(imageParam.getContentType() == ContentInfoVo.CONTENT_TYPE_LIQUOR) {
				subRoot = "/product";
			} else if(imageParam.getContentType() == ContentInfoVo.CONTENT_TYPE_MATERIAL) {
				subRoot = "/material";
			} else if(imageParam.getContentType() == ImageDto.IMAGE_CONTENT_TYPE_NATIONAL_FLAG) {
				subRoot = "/flag";
			}
			
			log.debug("[Upload Image] root path:"+dirPath);
			
			String originFilename = imageParam.getImageFile().getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
			Long size = imageParam.getImageFile().getSize();
						
			log.debug("originFilename : " + originFilename);
			log.debug("extensionName : " + extName);
			log.debug("size : " + size);
			//System.out.println("saveFileName : " + saveFileName);
			
			
			File orgFile = null;
			File resizeFile = null;
			
			String orgFileName = subRoot + "/" + imageParam.getContentType() + "_" + imageParam.getContentId() + "_org" + extName;
			String orgFilePath = dirPath + orgFileName;
			
			log.debug("orgFilePath : " + orgFilePath);
			
			orgFile = new File(orgFilePath);
			writeFile(imageParam.getImageFile(), orgFilePath);
			
			String resizeFileName = subRoot + "/" + imageParam.getContentType() + "_" + imageParam.getContentId() + "_300" + extName;
			String resizeFilePath = dirPath + resizeFileName;
			resizeFile = new File(resizeFilePath);
			ImageResizer.resize(orgFile, resizeFile, 300);
			
			imageParam.setPath(orgFileName);
			imageParam.setImageType(ImageDto.IMAGE_TYPE_ORIGINAL);
			imageDao.insert(imageParam);
			
			imageParam.setPath(resizeFileName);
			imageParam.setImageType(ImageDto.IMAGE_TYPE_REP);
			imageDao.insert(imageParam);
			
			res.setState(BasicListResponse.STATE_SUCCESS);
			res.setStateMessage(BasicListResponse.STATE_SUCCESS_MESSAGE);
			
		} catch(Exception e) {
			log.error("[uploadImage] Error:"+e.getMessage(), e);
		}
		
		return res;
	}
	

	private boolean writeFile(MultipartFile multipartFile, String imagePath) throws IOException{
		
		boolean result = false;
		byte[] data = multipartFile.getBytes();
		FileOutputStream fos = new FileOutputStream(imagePath);
		fos.write(data);
		fos.close();
		
		return result;
	}


	@Override
	public BasicListResponse getPartJobList(int tid, PagingParam param) {
		log.debug("[getPartJobList]" + param);
		BasicListResponse res = new BasicListResponse();
		
		try {
			
			List<PartJobDto> jobList = partJobDao.selectList(param);	
			List<JoinedPartJobVo> joinedJobList = new ArrayList<JoinedPartJobVo>();			
			
			for(int i=0; i<jobList.size(); i++) {
				int type = jobList.get(i).getDataType();
				
				JoinedPartJobVo joinedJob = new JoinedPartJobVo();
				joinedJob.setJob(jobList.get(i));
				if(type == PartJobDto.DATA_TYPE_BEER) {
					joinedJob.setContent(beerDao.selectOne(jobList.get(i).getDataId()));
				}
				
				// worker join
				PartTimeWorkerDto worker = partTimeWorkerDao.selectOne(jobList.get(i).getWorkerId());
				joinedJob.setWorker(worker);
				
				// 이미지 join
				joinedJob.setImages(imageDao.selectListByContent(jobList.get(i).getDataId(), type));
				joinedJobList.add(joinedJob);
			}
			
			Integer total = partJobDao.selectCount();
			param.getPaging().setTotalCount(total);
			
			res.setList(joinedJobList);
			res.setSParam(param);
			res.setState(BasicListResponse.STATE_SUCCESS);
			res.setStateMessage(BasicListResponse.STATE_SUCCESS_MESSAGE);
			
		} catch(Exception e) {
			log.error("[getPartJobList]Error:"+e.getMessage(), e);
		}

		return res;
	}


	@Override
	public BasicListResponse getPartJobList(int tid, PartTimeWorkerDto worker) {
		
		BasicListResponse res = new BasicListResponse();
		
		try {
			
			PartTimeWorkerDto workerInfo = partTimeWorkerDao.selectByNameAccount(worker);
			
			List<PartJobDto> jobList = partJobDao.selectListByWorker(workerInfo);
			List<JoinedPartJobVo> joinedJobList = new ArrayList<JoinedPartJobVo>();
			
			for(int i=0; i<jobList.size(); i++) {
				int type = jobList.get(i).getDataType();
				
				JoinedPartJobVo joinedJob = new JoinedPartJobVo();
				joinedJob.setJob(jobList.get(i));
				if(type == PartJobDto.DATA_TYPE_BEER) {
					joinedJob.setContent(beerDao.selectOne(jobList.get(i).getDataId()));
				}
				
				// 이미지도 따로 가져와야함
				joinedJob.setImages(imageDao.selectListByContent(jobList.get(i).getDataId(), type));
				joinedJobList.add(joinedJob);
			}
			
			PartJobRewardVo reward = getPartTimeWorkerReward(worker);
			
			res.setData(reward);
			res.setList(joinedJobList);
			res.setState(BasicListResponse.STATE_SUCCESS);
			res.setStateMessage(BasicListResponse.STATE_SUCCESS_MESSAGE);
			
		} catch(Exception e) {
			log.error("[getJobList] Error:"+e.getMessage(), e);
		}
		
		return res;
	}


	@Override
	public PartJobRewardVo getPartTimeWorkerReward(PartTimeWorkerDto worker) {
		
		PartJobRewardVo res = new PartJobRewardVo();
		
		try {
			
			log.debug("[worker]:"+worker);
			
			PartTimeWorkerDto workerInfo = partTimeWorkerDao.selectByNameAccount(worker);
			
			PartJobDto jobReward = null;
			
			// 유보금
			jobReward = partJobDao.selectPartJobReward(workerInfo.getWorkerId(), PartJobDto.JOB_STATE_WAIT);
			if(jobReward == null) {
				jobReward = new PartJobDto();
				jobReward.setReward(0f);
				jobReward.setPaidReward(0f);
			}
			log.debug("[유보금]:"+jobReward);
			res.setReserveReward(jobReward.getReward());;
			
			// 승인거부
			jobReward = partJobDao.selectPartJobReward(workerInfo.getWorkerId(), PartJobDto.JOB_STATE_DENIED);
			if(jobReward == null) {
				jobReward = new PartJobDto();
				jobReward.setReward(0f);
				jobReward.setPaidReward(0f);
			}
			log.debug("[승인 거부]:"+jobReward);
			res.setRejectedReward(jobReward.getReward());
			
			// 지급 완료 보상
			jobReward = partJobDao.selectPartJobReward(workerInfo.getWorkerId(), PartJobDto.JOB_STATE_COMPLETE);
			if(jobReward == null) {
				jobReward = new PartJobDto();
				jobReward.setReward(0f);
				jobReward.setPaidReward(0f);
			}
			log.debug("[지금 완료]:"+jobReward);
			res.setPaidReward(jobReward.getPaidReward());
			res.setRejectedReward(res.getRejectedReward() + jobReward.getReward() - jobReward.getPaidReward());
			res.setTotalReward(res.getPaidReward() + res.getReserveReward());
		
		} catch(Exception e) {
			log.error("[getPartTimeWorkerReward] Error:"+e.getMessage(), e);
		}
		
		return res;
	}

	
}