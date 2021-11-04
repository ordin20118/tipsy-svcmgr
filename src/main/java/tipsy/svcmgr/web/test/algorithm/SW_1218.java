package tipsy.svcmgr.web.test.algorithm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class SW_1218 {

	public static void main(String[] args) throws FileNotFoundException {
		
		System.setIn(new FileInputStream("C:\\Users\\GwangA\\Documents\\workspace\\all_alcohol\\src\\main\\java\\gwang\\allalcohol\\web\\test\\algorithm\\input_file\\SW_1218.txt"));
		Scanner sc = new Scanner(System.in);	
		
		for(int t=1; t<=10; t++) {
			
			int isValid = 1;
			int caseCnt = sc.nextInt();
			sc.nextLine();
			
			String str = sc.nextLine();
			String[] strArr = str.split("");
			Stack stack = new Stack();
	
			for(int i=0; i<strArr.length; i++) {
				
				if(strArr[i].equals("(") || strArr[i].equals("{") || strArr[i].equals("[") || strArr[i].equals("<")) {
					stack.push(strArr[i]);
				} else {
					
					String tmp = (String)stack.pop();
					
					if (strArr[i].equals("]") && tmp.equals("[")) {
		               	continue;
		            } else if (strArr[i].equals("}") && tmp.equals("{")) {
		               	continue;
		            }  else if (strArr[i].equals(")") && tmp.equals("(")) {
		               	continue;
		            }  else if (strArr[i].equals(">") && tmp.equals("<")) {
		               	continue;
		            } else {
		            	isValid = 0;
		            	break;
		            }	
				}			
				
			}
			
			System.out.println("#" + t + " " + isValid);
			
		}

	}


}
