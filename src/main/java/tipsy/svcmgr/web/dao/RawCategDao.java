package tipsy.svcmgr.web.dao;

import java.util.List;
import javax.annotation.Resource;

@Resource(name="RawCategDao")
public interface RawCategDao {

	public List<RawCategDto> selectAll();

}
