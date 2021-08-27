package gwang.allalcohol.web.test.algorithm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SW_1208 {

	public static void main(String[] args) throws FileNotFoundException {
		
		System.setIn(new FileInputStream("C:\\Users\\GwangA\\Documents\\workspace\\all_alcohol\\src\\main\\java\\gwang\\allalcohol\\web\\test\\algorithm\\input_file\\SW_1208.txt"));
		Scanner sc = new Scanner(System.in);
		 
		for(int s=0; s<10 ; s++) {
             int T;
             int cnt = 0;

             while(sc.hasNext()) {

                 T = sc.nextInt();

                 //System.out.println("[T]:"+T);

                 int[] boxes = new int[100];
                 
                 // 100개의 box 데이터 수신
                 for(int i=0; i<100; i++) {
                     int next = sc.nextInt();
                     //System.out.print(""+next+" ");
                     boxes[i] = next;		
                 }

                 cnt++;
                 int maxIdx = 0, minIdx = 0;
                 for(int testCase = 1; testCase <= T; testCase++) {

                	 maxIdx = findMax(boxes);
                     minIdx = findMin(boxes);	

                     if((boxes[maxIdx] - boxes[minIdx]) <= 1) {
                         break;
                     }
                     

                     boxes[maxIdx] = boxes[maxIdx] - 1;
                     boxes[minIdx] = boxes[minIdx] + 1;
                     
                 }
                 
                 maxIdx = findMax(boxes);
                 minIdx = findMin(boxes);
                 
                 System.out.println("#"+cnt+" "+(boxes[maxIdx] - boxes[minIdx]));
                 
             }
         }
	}
	
	public static int findMax(int[] arr) {
	
		int maxIdx = 0;
		int max = 0;
		for(int i=0; i<arr.length; i++) {
		    if(arr[i] >= max) {
		    	maxIdx = i;
		        max = arr[maxIdx];
		    }
		}
		for(int i=0; i<arr.length; i++) {
		    if(arr[i] == max) {
		    	maxIdx = i;
		    }
		}
		
		return maxIdx;
	}
	
	public static int findMin(int[] arr) {
		
		int minIdx = 0;
		int min = arr[0];
		for(int i=0; i<arr.length; i++) {
		    if(arr[i] <= min) {
		    	minIdx = i;
		        min = arr[minIdx];
		    }
		}
		
		for(int i=0; i<arr.length; i++) {
		    if(arr[i] == min) {
		    	minIdx = i;
		    }
		}		
		
		return minIdx;
	}
}
