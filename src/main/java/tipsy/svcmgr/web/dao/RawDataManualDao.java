package tipsy.svcmgr.web.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Repository;

import tipsy.common.basic.BasicDao;
import tipsy.common.util.GenException;



@Repository
public class RawDataManualDao extends BasicDao {

	public static final String PREFIX = "tom.revupia.cacheupdater.dao.RawDataDao";
	
	//@Resource(name = "sqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	
	
	public int flush(String statement, List<Object> list, boolean force) throws GenException {
		return flush(statement, list, force, PREFIX);
	}
	
	public int flush(String statement, List<Object> list, boolean force, int qty) throws GenException {
		return flush(statement, list, force, PREFIX, qty);
	}
	
	public int flush(String statement, List<Object> list, boolean force, String prefix) throws GenException {
		/*
		int retRows = 0;
		if(force && list.size() > 0) {
			retRows = runInsertStatement(prefix + "." + statement, list);
			list.clear();
		} else if(list.size() > 200) {
			retRows = runInsertStatement(prefix + "." + statement, list);
			list.clear();
		}
		return retRows;
		*/
		return flush(statement, list, force, prefix, 200);
	}
	
	
	public int flush(String statement, List<Object> list, boolean force, String prefix, int qty) throws GenException {
		int retRows = 0;
		if(force && list.size() > 0) {
			retRows = runInsertStatement(prefix + "." + statement, list);
			list.clear();
		} else if(list.size() > qty) {
			retRows = runInsertStatement(prefix + "." + statement, list);
			list.clear();
		}
		return retRows;
	}
	
	
}