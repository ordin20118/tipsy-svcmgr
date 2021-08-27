package gwang.allalcohol.web.test.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class NHN_20200122_Test_Exam {

	 //private static int[][] matrix;
	 private static int cnt=1;
	 
	public static void main(String[] args) {
		
		InputData inputData = processStdin();
	    solution(inputData.sizeOfMatrix, inputData.matrix);
	    
	}
	
	public static void solution(int sizeOfMatrix, int[][] matrix) {
		
		ArrayList<Integer> arr = new ArrayList<Integer>(); //각 영역의 넓이를 저장할 ArrayList 생성
		
//		int[][] map = {{1,1,1,0,1},
//					   {1,1,0,0,1},
//					   {1,0,1,1,0},
//					   {0,0,1,1,1},
//					   {0,0,1,1,1}};
		
		boolean[][] checkArr = new boolean[matrix.length][matrix[0].length]; //방문한 곳을 체크하기 위한 배열 행렬 생성
		
		for(int i=0; i<matrix.length; i++) {		
			for(int j=0; j<matrix[i].length; j++) {
				
				if(matrix[i][j] == 1) {
					dfs(i, j, checkArr, matrix);
					arr.add(cnt);
					cnt = 1;
				} else {
					continue;
				}
				
			}
		}
		
		Collections.sort(arr);
		
		// 출력
		System.out.println(arr.size());		
		for(int i=0; i<arr.size(); i++) {
			System.out.print(arr.get(i));
			if((i+1) == arr.size()) {
				//System.out.print("\n");
			} else {
				System.out.print(" ");
			}
		}
	}
	
	public static void dfs(int x, int y, boolean[][] checkArr,int[][] matrix) {
		
		 int[] X = {-1,0,1,0}; // X축의 상하좌우 이동을 위한 배열 
		 int[] Y = {0,1,0,-1}; // Y축의 상하좌우 이동을 위한 배열 (x,y 짝만 맞추어주면 상하좌우든 하좌우상 이든 순서 상관x)
		
		//System.out.println(x+","+y);
		
		checkArr[x][y] = true;
		matrix[x][y] = 0;
		
		for(int i=0; i<4; i++) {
		 
			int nextX = x + X[i];
			int nextY = y + Y[i];
			
			//상,하,좌,우 이동 중 범위가 넘어서는 경우 continue
			if(nextX < 0 || nextY < 0 || nextX >= checkArr.length || nextY >= checkArr.length) {
				continue;
			}
			
			//방문한곳은 continue
			if(checkArr[nextX][nextY]){
				continue;
			}
			
			//0은 벽이라서 이동할 경로가 벽이면 continue
			if(matrix[nextX][nextY] == 0) {
				checkArr[nextX][nextY] = true; continue;
			}
			
			dfs(nextX, nextY, checkArr, matrix);
			cnt++;
		}
		//System.out.println("END");
		//System.out.println();
	 }
	
	  private static class InputData {
	    int sizeOfMatrix;
	    int[][] matrix;
	  }

	
	  private static InputData processStdin() {
		    InputData inputData = new InputData();
	
		    try (Scanner scanner = new Scanner(System.in)) {
		      inputData.sizeOfMatrix = Integer.parseInt(scanner.nextLine().replaceAll("\\s+", ""));      
		      
		      inputData.matrix = new int[inputData.sizeOfMatrix][inputData.sizeOfMatrix];
		      for (int i = 0; i < inputData.sizeOfMatrix; i++) {
		        String[] buf = scanner.nextLine().trim().replaceAll("\\s+", " ").split(" ");
		        for (int j = 0; j < inputData.sizeOfMatrix; j++) {
		          inputData.matrix[i][j] = Integer.parseInt(buf[j]);
		        }
		      }
		    } catch (Exception e) {
		      throw e;
		    }
	
		    return inputData;
	  }
}
