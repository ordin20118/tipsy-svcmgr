package tipsy.svcmgr.web.test.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Kakao_2020_0912_02 {
	
	public static Map<String, Integer> courseMap = new HashMap<String, Integer>();

	public static void main(String[] args) {
		
		String[] orders = {"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"}; 
		int[] course = {2,3,4};
		
		for(int i=0; i< orders.length; i++) {
			String order = orders[i];
			
			String[] arr = order.split("");
			Arrays.sort(arr);
			order = String.join("", arr);
			
			for(int j=0; j<course.length; j++) {
				makeSet(order.split(""), new boolean[order.length()], 0, order.length(), course[j]);
			}
		}
		
		// value 기준 map 정렬
		 List<String> keySetList = new ArrayList<String>(courseMap.keySet());
		 Collections.sort(keySetList, (o1, o2) -> (courseMap.get(o1).compareTo(courseMap.get(o2))));
        
         List<String> answer = new ArrayList<String>();
         
         Map<Integer, Integer> sizeMap = new HashMap<Integer, Integer>();
         for(int i=keySetList.size()-1; i>=0; i--) {
        	 String key = keySetList.get(i);
        	 if(courseMap.get(key) >= 2) {
        		 
        		 //System.out.println(String.format("Key : %s, Value : %s", key, courseMap.get(key)));
        		 
        		 int size = key.length();
        		 if(sizeMap.containsKey(size)) {
        			 if(courseMap.get(key) == sizeMap.get(size)) {
        				 answer.add(key);
        			 }
        		 } else {
        			 sizeMap.put(size, courseMap.get(key));
        			 answer.add(key);
        		 }
        		         		 
        		 
        	 } else {
        		 break;
        	 }
         }
         
         // key 기준 오름차순
         Collections.sort(answer);
         for (String key : answer) {
 			 System.out.println("key:"+key);
 		 }
         
         
         // Map List 정렬
// 		 List<Map.Entry<String, Integer>> maplist = new LinkedList<>(courseMap.entrySet());
// 		 
// 		 Collections.sort(maplist, new Comparator<Map.Entry<String, Integer>>() {
//             @Override
//             public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
//            	
//            	 if(o1.getKey().split("")[0].compareTo(o2.getKey().split("")[0]) == 1) {
//            		 return 1;
//            	 } else if(o1.getKey().split("")[1].compareTo(o2.getKey().split("")[1]) == -1){
//            		 return -1;
//            	 } else {
//            		 return 0;
//            	 }
//             }
//         });
//
// 		 // 결과 출력
// 		 for (Map.Entry<String, Integer> map : maplist) {
// 			 //System.out.println("key:"+map.getKey()+":"+map.getValue());
// 		 }
		

	}
	
	public static void makeSet(String[] arr, boolean[] visited, int start, int n, int r) {
		if(r == 0) {
	        print(arr, visited, n);			
	        return;
	    } 

	    for(int i=start; i<n; i++) {
	        visited[i] = true;
	        makeSet(arr, visited, i + 1, n, r - 1);
	        visited[i] = false;
	    }	    
	}
	
	public static void print(String[] arr, boolean[] visited, int n) {
		String tmp = "";
		for(int i=0; i<n ; i++) {
			if(visited[i]) {
				tmp += arr[i];
			}
		}
		
		//System.out.println(tmp);
		
		// Map 확인 후 없으면 key로 넣고 int는 1
		if(courseMap.containsKey(tmp)) {
			courseMap.put(tmp, courseMap.get(tmp)+1);
		} else {
			courseMap.put(tmp, 1);
		}
	}
	
	

}
