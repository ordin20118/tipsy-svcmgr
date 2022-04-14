package tipsy.svcmgr.web.search;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SearchResult {
	
	public static final int SEARCH_WORD_TYPE_NORMAL = 0;
	public static final int SEARCH_WORD_TYPE_SENTENCE = 1;
	
	public String indexName;	
	public List<HitData> hits;
	public SearchParam param;
	//public List<ReviseResultVo> revise;
	
	private int searchWordType;
	
	public List<Integer> getHitIds() {
		ArrayList<Integer> list = new ArrayList<>();
		
		if(hits != null && hits.size() > 0) {
			for(int i=0; i<hits.size(); i++) {
				System.out.println("["+hits.get(i).getDataId()+"]-["+hits.get(i).getScore()+"]");
				list.add(hits.get(i).getDataId());
			}	
		}
		
		return list;
	}
	
	public Float getScore(int idx) {
		return hits.get(idx).getScore();
	}

	public List<String> queryExplains(){
		List<String> queryExplains = new ArrayList<String>();
		
		if(param != null && param.getSearchField() != null) {
			for(int i=0; i<param.searchField.size(); i++) {
				SearchField field = param.searchField.get(i);
				String tmpExplain = "Field : "+field.getSearchFieldName() + ", Value : " + field.getFieldValue() + 
									", Search Type : "+field.getSearchType()
									+ ", Weight : "+field.getWeight();
				queryExplains.add(tmpExplain);
			}	
		}
		
		return queryExplains;
	}

}


@Data
class HitData {
	public Integer dataId;
	public Float score;
}
