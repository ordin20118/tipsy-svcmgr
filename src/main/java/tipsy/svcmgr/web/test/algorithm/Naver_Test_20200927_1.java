package tipsy.svcmgr.web.test.algorithm;

import org.bouncycastle.util.Arrays;

public class Naver_Test_20200927_1 {

	public static void main(String[] args) {


		String m = "kkaxbycyz";
		String k = "abc";
		
		String[] mArr = m.split("");
		String[] kArr = k.split("");

		String result = "";
		int j=0;
		int kIdx=0;
				
		while(j<mArr.length) {				
			
			if(kIdx >= kArr.length) {
				result += mArr[j];
				j++;
				continue;
			}
							
			if(!mArr[j].equals(kArr[kIdx])) {
				result += mArr[j];
				j++;
			} else {
				j++;
				kIdx++;
				continue;
			}
		}
			
		System.out.println("[result]:"+result);
		

	}

}
