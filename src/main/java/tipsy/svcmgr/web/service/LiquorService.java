package tipsy.svcmgr.web.service;

import java.util.List;
import tipsy.svcmgr.web.dao.PartTimeWorkerDto;
import tipsy.svcmgr.web.vo.LiquorContentInfoVo;
import tipsy.svcmgr.web.vo.LiquorListItemVo;

public interface LiquorService {

	public LiquorContentInfoVo genLiquorInfo(int tid, int liquorId) throws Exception;
	
	public List<LiquorListItemVo> genLiquorListItem(int tid, List<Integer> liquorIds) throws Exception;
	
}