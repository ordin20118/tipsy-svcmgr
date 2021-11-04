package tipsy.svcmgr.web.test.algorithm;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class MocTest {
    public static void main(String[] args) {
    	
    	int[] answers = {1,2,3,4,5};
        int[] answer = {};
        
        int[] p1 = {1,2,3,4,5};
        int[] p2 = {2,1,2,3,2,4,2,5};
        int[] p3 = {3,3,1,1,2,2,4,4,5,5};

        List<Result> result = new ArrayList<Result>();
        Result r1 = new Result(1);
        Result r2 = new Result(2);
        Result r3 = new Result(3);
        result.add(r1);
        result.add(r2);
        result.add(r3);

        for(int i=0; i<answers.length; i++) {
            if(p1[i%5] == answers[i]) {
                result.get(0).setResult(result.get(0).getResult()+1);
            }
            if(p2[i%8] == answers[i]) {
                result.get(1).setResult(result.get(1).getResult()+1);
            }
            if(p3[i%10] == answers[i]) {
               result.get(2).setResult(result.get(2).getResult()+1);
            }
        }

        Collections.sort(result, new Comparator<Result>(){
           
            @Override
			public int compare(Result o1, Result o2) {
				 if(o1.getResult() < o2.getResult()){
                    return 1;
                } else if(o1.getPNum() < o2.getPNum()){
                    return -1;
                } else {
                    return 0;
                }
			}
        });
        
        
        int count = 0;
        for(int i=0; i<result.size(); i++) {
            if(result.get(i).getResult() > 0){
              count++;
            }
        }
        answer = new int[count]; 
        for(int i=0; i<result.size(); i++) {
            if(result.get(i).getResult() > 0){
              answer[i] = result.get(i).getPNum();   
            }
        }
       

    }
}

 class Result {
     public int pNum;
     public int result;
     
     public int getPNum(){
         return this.pNum;
     }
     
     public void setPNum(int num){
         this.pNum = num;
     }
     public void setResult(int result){
         this.result = result;
     }
     
     public int getResult(){
         return this.result;
     }
       
     public Result(int num){
         this.pNum = num;
         this.result = 0;
     }
         
 }

