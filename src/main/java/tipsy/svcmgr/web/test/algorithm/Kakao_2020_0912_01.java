package tipsy.svcmgr.web.test.algorithm;

public class Kakao_2020_0912_01 {

	public static void main(String[] args) {
		
		String new_id = "=.=";
		String[] new_id_arr = new_id.split("");
		String specialRegex = "[^-_.||a-z0-9]";
		String dotRegex = "\\.{2,}";
		
		
		new_id = new_id.toLowerCase();
		System.out.println("[toLowerCase]:"+new_id);
		
		new_id = new_id.replaceAll(specialRegex, "");
		System.out.println("[replaceAll]:"+new_id);
		
		new_id = new_id.replaceAll(dotRegex, ".");
		System.out.println("[make dot short]:"+new_id);
		
		new_id_arr = new_id.split("");
		if(new_id_arr[new_id_arr.length-1].equals(".")) {
			if(new_id.length() == 1) {
				new_id = "";
			} else {
				new_id = new_id.substring(0,new_id.length()-1);
			}
		}
		new_id_arr = new_id.split("");
		if(new_id_arr[0].equals(".")) {
			if(new_id.length() == 1) {
				new_id = "";
			} else {
				new_id = new_id.substring(1,new_id.length());
			}			
		}
		
		System.out.println("[remove dot]:"+new_id);
		
		
		
		if(new_id != null && new_id.equals("")) {
			new_id = "a";
			System.out.println("[insert a]:"+new_id);
		}
		
		
		
		if(new_id.length() >= 16) {
			new_id = new_id.substring(0, 15);
			new_id_arr = new_id.split("");
			if (new_id_arr[14].equals(".")){
				new_id = new_id.substring(0, 14);
			}
			System.out.println("[16자 이상일 경우]:"+new_id);
		}

		new_id_arr = new_id.split("");
		if(new_id_arr[new_id_arr.length-1].equals(".")) {
			if(new_id.length() == 1) {
				new_id = "";
			} else {
				new_id = new_id.substring(0,new_id.length()-1);
			}
		}
		
		System.out.println("[6단계]:"+new_id);
		
		new_id_arr = new_id.split("");
		if(new_id.length() <= 2) {
			// 길이가 3이 될때까지 마지막 문자를 붙임
			while(new_id.length() < 3) {
				new_id += new_id_arr[new_id_arr.length-1];
			}
		}
		
		System.out.println("[7단계]:"+new_id);
		
	}

}
