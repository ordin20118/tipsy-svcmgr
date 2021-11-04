package tipsy.svcmgr.web.test.algorithm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SW_1206 {

	public static void main(String[] args) throws FileNotFoundException {
		
		System.setIn(new FileInputStream("C:\\Users\\GwangA\\Documents\\workspace\\all_alcohol\\src\\main\\java\\gwang\\allalcohol\\web\\test\\algorithm\\input_file\\SW_1206.txt"));
		Scanner sc = new Scanner(System.in);
		
		for(int t=1; t<=10; t++) {
			
			int caseCnt = sc.nextInt();
			int[] heights = new int[caseCnt];
			int goodViews = 0;
			
			for(int i=0; i<caseCnt; i++) {
				int height = sc.nextInt();
				heights[i] = height;				
			}	
			
			
						
            // 하나의 라인당 좌,우로 2칸의 여유가 있는 층의 총 개수를 구하라
            for(int j=2; j<caseCnt-2; j++) {
            	// 첫 번째와 마지막은 특별한 로직을 타도록
            	if(j == 2) {					// 첫 번째
            		
            		// 오른쪽 두개만 체크
            		int right = 0;
            		int right1 = heights[j] - heights[j+1];
            		int right2 = heights[j] - heights[j+2];
            		
            		if(right1 > 0 && right2 > 0 && right1 >= right2) {
            			right = right2;
            		} else if(right1 > 0 && right2 > 0 && right1 < right2) {
            			right = right1;
            		}
            		
            		if(right > 0) {
            			goodViews += right;
            		}            		
            		
            	} else if((j+1) >= caseCnt-2) {		// 마지막
            		// 왼쪽 두개만 체크
            		int left = 0;
            		int left1 = heights[j] - heights[j-1];
            		int left2 = heights[j] - heights[j-2];
            		
            		if(left1 > 0 && left2 > 0 && left1 >= left2) {
            			left = left2;
            		} else if(left1 > 0 && left2 > 0 && left1 < left2) {
            			left = left1;
            		}
            		
            		if(left > 0) {
            			goodViews += left;	
            		}            		
            		
            	} else {
            		
            		// 우
            		// 해당 인덱스의 오른쪽 두 배열의 길이를 구한다
            		// 차이가 작은 아이를 선택
            		// 좌도 마찬가지로 구한다
            		// 좌,우 중에 작은 값을 선택
            		// 0보다 크다면 ++
            		int left = 0, right = 0;
            		int left1 = heights[j] - heights[j-1];
            		int left2 = heights[j] - heights[j-2];
            		int right1 = heights[j] - heights[j+1];
            		int right2 = heights[j] - heights[j+2];
            		
            		if(left1 > 0 && left2 > 0 && left1 >= left2) {
            			left = left2;
            		} else if(left1 > 0 && left2 > 0 && left1 < left2) {
            			left = left1;
            		}
            		
            		if(right1 > 0 && right2 > 0 && right1 >= right2) {
            			right = right2;
            		} else if(right1 > 0 && right2 > 0 && right1 < right2) {
            			right = right1;
            		}
            		
            		if(left >= right) {
            			goodViews += right;
            		} else if(left < right) {
            			goodViews += left;
            		}
            		
            	}            	
            }
         
    		System.out.println("#"+t+" "+goodViews);	
    		
			
		}
	}

}
