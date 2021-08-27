package gwang.allalcohol.web.test.algorithm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SW_1204 {

	public static void main(String[] args) throws FileNotFoundException {
		 
		System.setIn(new FileInputStream("C:\\Users\\GwangA\\Documents\\workspace\\all_alcohol\\src\\main\\java\\gwang\\allalcohol\\web\\test\\algorithm\\input_file\\SW_1204.txt"));
		Scanner sc = new Scanner(System.in);
		
		int caseCnt = sc.nextInt();
		
		for(int i=0; i<caseCnt; i++) {
			
			int testNum = sc.nextInt();
			
			System.out.println("[Test"+testNum+"]");
						
			int[] scores = new int[101];
             
			
            // 1000개의 student 성적 데이터 수신
            for(int j=0; j<1000; j++) {
                int score = sc.nextInt();
                //System.out.print(""+next+" ");                
                scores[score]++;                		
            }
         
            int max = 0;
            int maxIdx = 0;
            // find max
            for(int j=0; j<scores.length; j++) {
            	if(scores[j] >= max) {
            		maxIdx = j;
            		max = scores[j];
            	}
            }
         
    		System.out.println("#"+testNum+" "+maxIdx);			
			
		}
	}

}
