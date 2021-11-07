package tipsy.svcmgr.web.dao;

import javax.annotation.Resource;

@Resource(name="ManageLogDao")
public interface ManageLogDao {
	
	public int insert(ManageLogDto log);

}
