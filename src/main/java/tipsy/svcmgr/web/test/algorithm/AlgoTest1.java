package tipsy.svcmgr.web.test.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgoTest1 {

	public static void main(String[] args) {
	

		class Solution {
		    public String[] solution(String[] record) {
		        String[] answer = {};
		        
		        Map<String, String> nickMap = new HashMap<String, String>();
		        List<String[]> queue = new ArrayList<String[]>();
		         
		        if(record.length >= 1 && record.length <= 100000) {
		             // 배열 사이즈 확인, 반복문 시작
		            for(int i=0; i<record.length; i++) {            
		                // 공백 기준 토큰화
		                String[] info =  record[i].split(" ");

		                // 제일 앞 영문인지 확인 (Enter, Leave, Change)
		                if(info[0].equals("Enter")) {
		                    nickMap.put(info[1], info[2]);
		                    queue.add(info);
		                } else if(info[0].equals("Leave")) {
		                    //nickMap.put(info[1], info[2]);
		                    queue.add(info);
		                } else if(info[0].equals("Change")) {
		                    nickMap.put(info[1], info[2]);
		                }

		            }

		            answer = new String[queue.size()];
		            int i = 0;
		            for(String[] tmp : queue) {
		                if(tmp[0].equals("Enter")) {
		                    answer[i++] = nickMap.get(tmp[1])+"님이 들어왔습니다.";
		                } else {
		                    answer[i++] = nickMap.get(tmp[1])+"님이 나갔습니다.";
		                }
		            }
		        }
		        
		        return answer;
		    }
		}

	}

}
