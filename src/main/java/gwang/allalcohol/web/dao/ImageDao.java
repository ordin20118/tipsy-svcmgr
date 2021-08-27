package gwang.allalcohol.web.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;

@Resource(name="ImageDao")
public interface ImageDao {
	
	public Integer insert(ImageDto image);
	
	public ImageDto selectOne(@Param("imageId")Integer imageId);
	
	public ImageDto selectByContentId(ImageDto image);
	
	public List<ImageDto> selectList();
	
	public List<ImageDto> selectListByContent(@Param("contentId")Integer contentId, @Param("contentType")Integer contentType);

}
