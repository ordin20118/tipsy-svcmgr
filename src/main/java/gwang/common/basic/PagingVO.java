package gwang.common.basic;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PagingVO {
	
	public static final int DEF_PER_PAGE = 10;
	
	private int  perPage = 10;
	private int  nowPage = 1;
	private long totalCount = 0;
	private double pageRange = 10;
	//private HttpServletRequest request = null;

	public PagingVO() {
	}
	
	public PagingVO(int nowPage, int totalCount) {
		this.nowPage = nowPage;
		this.totalCount = totalCount;
	}
	
	public PagingVO(int nowPage, int totalCount, int perPage) {
		this.nowPage = nowPage;
		this.totalCount = totalCount;
		this.perPage = perPage;
	}

	public int getLastRow() {
		return this.perPage * this.nowPage;
	}

	public int getFirstRow() {
		return this.perPage * this.nowPage - this.perPage;
	}
	
	public int getMaxPage() {
		return (int) Math.ceil(this.totalCount / (this.perPage * 1.0d));
	}
	
	
	public Integer getPrevPage() {
		
		int startPage = getPages().get(0).intValue();
		int prevPage = (int)(startPage - pageRange);
		if(prevPage > 0) {
			 return new Integer(prevPage);
		} else {
			return null;
		}
	}
	
	public Integer getNextPage() {
		List<Integer> pages = getPages();
		int lastPage = pages.get(pages.size()-1).intValue();
		int nextPage = lastPage + 1;
		if(nextPage > getMaxPage()) {
			return null;
		} else {
			return new Integer(nextPage);
		}
	}
	
	public List<Integer> getPages() {
		int startPage = (int)((Math.ceil(nowPage/pageRange)-1)*pageRange)+1;
		List<Integer> pages = new ArrayList<Integer>();
		//int [] pages = new int[(int)pageRange];
		for(int i=0; i<pageRange; i++) {
			//pages[i] = startPage++;
			pages.add(startPage++);
			if(startPage > getMaxPage()) {
				break;
			}
		}
		return pages;
	}
	
	
	
	public String getUrlParam() {
		String urlParam = 
						"paging.perPage="+perPage+"&" 
						//"paging.nowPage="+nowPage+"&"	
						;
		return urlParam;
	}
	
}
