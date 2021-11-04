package tipsy.svcmgr.web.test.algorithm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SW_1217 {

	public static void main(String[] args) throws FileNotFoundException {
		
		System.setIn(new FileInputStream("C:\\Users\\GwangA\\Documents\\workspace\\all_alcohol\\src\\main\\java\\gwang\\allalcohol\\web\\test\\algorithm\\input_file\\SW_1217.txt"));
		Scanner sc = new Scanner(System.in);	
		
		for(int t=0; t<10; t++) {
			
			int caseCnt = sc.nextInt();
			int val = sc.nextInt();
			int n = sc.nextInt();		
			int result  = mult(val, n);
			System.out.println("#" + caseCnt + " " + result);
			
		}		

	}
	
	public static int mult(int val, int n) {
		
		if(n <= 0) {
			return 1;
		} else {
			return val * mult(val, --n);
		}
		
	}

}
