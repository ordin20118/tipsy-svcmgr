package tipsy.svcmgr.web.test.algorithm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SW_1216 {

	public static void main(String[] args) throws FileNotFoundException {
		
		System.setIn(new FileInputStream("C:\\Users\\GwangA\\Documents\\workspace\\all_alcohol\\src\\main\\java\\gwang\\allalcohol\\web\\test\\algorithm\\input_file\\SW_1216.txt"));
		Scanner sc = new Scanner(System.in);		
		
		String[][] board = new String[100][100];
		int findLen = 100;
		int caseCnt = 0;
		boolean isFind = false;
		
		for(int t=1; t<=10; t++) {
			
			caseCnt = sc.nextInt();
			sc.nextLine();
		
			for(int i=0; i<100; i++) {
				String caseStr = sc.nextLine();			
				String[] tmpArr = caseStr.split("");
				board[i] = tmpArr;
			}
						
			
			isFind = false;
			findLen = 100;
			
			while(findLen > 0 && !isFind) {
			
				boolean rowFind = false, colFind = false;
				
				int subLen = (int)findLen/2;
				int remainLen = (int)findLen%2;
				
				//System.out.println("caseCnt:"+caseCnt);
				//System.out.println("subLen:"+subLen + "/remainLen:"  + remainLen);
				
				// 가로
				for(int i=0; i<board.length; i++) {		
					
					if(rowFind) {
						break;
					}						
					
					for(int j=0; j<=board.length-findLen; j++) {					
											
						// 좌
						String[] left = new String[subLen];
						for(int k=0; k<subLen; k++) {
							left[k] = board[i][j+k];
						}
						
						// 우		
						String[] right = new String[subLen];
						for(int k=0; k<subLen; k++) {	
							right[k] = board[i][j+subLen+remainLen+k];
						}					
												
						boolean isRowSame = true;
						// 좌,우 비교
						for(int k=0; k<subLen; k++) {
							if(!left[k].equals(right[subLen-k-1])) {
								isRowSame = false;
								break;
							}
						}
						
						if(isRowSame) {
							rowFind = true;
							//System.out.println("[가로에서 찾음]:"+findLen);
							break;
						}
					}
				}				
				
				
				// 세로			
				for(int i=0; i<board.length; i++) {	
					
					if(colFind) {
						break;
					}
					
					for(int j=0; j<=board[i].length-findLen; j++) {
						
						// 좌
						String[] left = new String[subLen];
						for(int k=0; k<subLen; k++) {
							left[k] = board[j+k][i];
						}
						
						// 우			
						String[] right = new String[subLen];

						for(int k=0; k<subLen; k++) {	
							right[k] = board[j+subLen+remainLen+k][i];
						}
						
						boolean isColSame = true;
						// 좌,우 비교
						for(int k=0; k<subLen; k++) {
							if(!left[k].equals(right[subLen-k-1])) {
								isColSame = false;
								break;
							}
						}
						
						if(isColSame) {
							colFind = true;
							//System.out.println("[세로에서 찾음]:"+findLen);
							break;
						}
						
					}				
				}
				
				if(rowFind || colFind) {
					System.out.println("#" + caseCnt + " " + findLen);
					isFind = true;
					break;
				}
				
				findLen--;				
			}
		}
	}
	
	public static void print(String[] arr) {
		for(int i=0; i<arr.length; i++) {
			System.out.print(""+arr[i]);
		}
	}

}
