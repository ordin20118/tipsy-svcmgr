package tipsy.svcmgr.web.test;

import java.text.DecimalFormat;

public class ImagePathTest {

	public static void main(String[] args) {
		

		imageIdToPath(Long.parseLong("47"));
		
		imageIdToPath(Long.parseLong("12200000000"));
		
		//imageIdToPath(Long.parseLong("2000002000"));
		//imageIdToPath(Long.parseLong("200000000000"));
		
	}
	
	private static DecimalFormat df = new DecimalFormat("000,000,000");
	public static String imageIdToPath(long id) {
		String numPath = df.format(id);
		System.out.print("=> " + numPath + "\n");
		numPath = numPath.substring(0, 7).replace(',', '/');
		System.out.print("=> " + numPath + "\n");
		return numPath;
	}

}
