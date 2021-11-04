package tipsy.svcmgr.web.test.algorithm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SW_1213 {

	public static void main(String[] args) throws FileNotFoundException {
		
		System.setIn(new FileInputStream("C:\\Users\\GwangA\\Documents\\workspace\\all_alcohol\\src\\main\\java\\gwang\\allalcohol\\web\\test\\algorithm\\input_file\\SW_1213.txt"));
		Scanner sc = new Scanner(System.in);
		
		for(int i=1; i<=10; i++) {
			
			int t = sc.nextInt();
			int findCnt = 0;
			int startIdx = 0;
			
			sc.nextLine();
			String keyword = sc.nextLine();
			String caseStr = sc.nextLine();
			
			while(caseStr.indexOf(keyword, startIdx) != -1) {
				findCnt++;
				startIdx = caseStr.indexOf(keyword, startIdx) + 1;
				
				if(startIdx > caseStr.length()) {
					break;
				}
			}
						
			System.out.println("#" + t + " " + findCnt);			
		}	

	}

}
