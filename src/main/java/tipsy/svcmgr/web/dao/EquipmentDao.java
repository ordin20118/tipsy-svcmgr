package tipsy.svcmgr.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface EquipmentDao {
	
	public int insert(EquipmentDto equipment);
	
	public int updateByCode(EquipmentDto equipment);
	
	public EquipmentDto selectOne(@Param("equipId")Integer equipId);

	public List<EquipmentDto> selectListByIds(@Param("equipIds")List<Integer> equipIds);
	
	public List<EquipmentDto> searchName(@Param("keyword") String keyword);

}
