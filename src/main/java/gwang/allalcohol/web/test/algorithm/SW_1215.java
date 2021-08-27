package gwang.allalcohol.web.test.algorithm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SW_1215 {

	public static void main(String[] args) throws FileNotFoundException {
		
		System.setIn(new FileInputStream("C:\\Users\\GwangA\\Documents\\workspace\\all_alcohol\\src\\main\\java\\gwang\\allalcohol\\web\\test\\algorithm\\input_file\\SW_1215.txt"));
		Scanner sc = new Scanner(System.in);
		
		String[][] board = new String[8][8];
		
		for(int t=1; t<=10; t++) {
			
			int findCnt = 0;
			int caseCnt = sc.nextInt();
			sc.nextLine();
			
			//System.out.println("caseCnt:"+caseCnt);
			
			for(int i=0; i<8; i++) {
				String caseStr = sc.nextLine();			
				String[] tmpArr = caseStr.split("");
				board[i] = tmpArr;
			}
			
			// ㅁㅁㅁㅁㅁ   len/2  만큼 앞뒤로 subStr
			// 회문 찾기
			// 가로
			int subLen = (int)caseCnt/2;
			int remainLen = (int)caseCnt%2;
			
			//System.out.println("subLen:"+subLen + "/remainLen:"  + remainLen);
			
			// 가로
			for(int i=0; i<board.length; i++) {								
				for(int j=0; j<=board.length-caseCnt; j++) {					
										
					// 좌
					String[] left = new String[subLen];
					for(int k=0; k<subLen; k++) {
						left[k] = board[i][j+k];
					}
					
					// 우		
					String[] right = new String[subLen];
					for(int k=0; k<subLen; k++) {	// 여기 주의
						//right[subLen-k-1] = board[i][j+subLen+remainLen+k];
						right[k] = board[i][j+subLen+remainLen+k];
					}					
					
					//print(left);
					//print(right);
					
					boolean isSame = true;
					// 좌,우 비교
					for(int k=0; k<subLen; k++) {
						if(!left[k].equals(right[subLen-k-1])) {
							isSame = false;
						}
					}
					
					if(isSame) {
						//System.out.println("[가로 회문]");
						findCnt++;
					} else {
						//System.out.println("");							
					}
				}
			}
			
			//System.out.println("[/////]"+board[3][3]);
			
			
			// 세로			
			for(int i=0; i<board.length; i++) {	
				for(int j=0; j<=board[i].length-caseCnt; j++) {
					
					// 좌
					String[] left = new String[subLen];
					for(int k=0; k<subLen; k++) {
						left[k] = board[j+k][i];
					}
					
					// 우			
					String[] right = new String[subLen];

					for(int k=0; k<subLen; k++) {	// 여기 주의
						right[k] = board[j+subLen+remainLen+k][i];
					}
					
					//print(left);
					//print(right);
					
					boolean isSame = true;
					// 좌,우 비교
					for(int k=0; k<subLen; k++) {
						if(!left[k].equals(right[subLen-k-1])) {
							isSame = false;
						}
					}
					
					if(isSame) {
						//System.out.println("[세로 회문]");
						findCnt++;
					} else {
						//System.out.println("");							
					}
					
				}				
			}
			
			
			
			System.out.println("#" + t + " " + findCnt);
		}
		
		
	}
	
	public static void print(String[] arr) {
		for(int i=0; i<arr.length; i++) {
			System.out.print(""+arr[i]);
		}
	}

}
