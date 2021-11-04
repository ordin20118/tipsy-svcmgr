package tipsy.svcmgr.web.test.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NHN_Exam_20201024_01 {
	
	private static char[] alpha = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
						'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	private static int[] selCntArr = null;	
	private static Map<Character, Character> quickPlayer = new HashMap<Character, Character>();
	private static Map<Character, Integer> selCntMap = new HashMap<Character, Integer>();
	
	private static char[] players;
	private static char[] sit;
	private static char tagger = 'A';			// 술래
	private static char sugeonPlayer;	// 수건을 받은 사람
	private static int sugeonIdx = 0;	// 최근 수건의 위치
	
	private static void solution(int numOfAllPlayers, int numOfQuickPlayers, char[] namesOfQuickPlayers, int numOfGames, int[] numOfMovesPerGame){

		// 플레이어 설정
		players = new char[numOfAllPlayers];
		sit = new char[numOfAllPlayers-1];
		for(int i=0; i<numOfAllPlayers; i++) {
			players[i] = alpha[i];
			selCntMap.put(players[i], 0);
			if(i>0) {
				sit[i-1] = players[i];
			}
		}
		
//		for(int i=0; i<numOfAllPlayers; i++) {
//			System.out.print(" ["+players[i]+"]");
//		}
//		System.out.print("\n");
		
		// 각 플레이어별 술래 횟수 배열
		selCntArr = new int[players.length];		
		for(int i=0; i<namesOfQuickPlayers.length; i++) {
			quickPlayer.put(namesOfQuickPlayers[i], namesOfQuickPlayers[i]);
		}
				
		selCntMap.put('A', 1);
		sugeonIdx = 0;
		
		int i=0;
		while(i<numOfGames) {
						
			int moveCnt = numOfMovesPerGame[i];			
			sugeonIdx = getSugeonIdx(sugeonIdx, moveCnt);
			
			System.out.println("[현재 수건의 위치]:"+sugeonIdx);
			
			sugeonPlayer = sit[sugeonIdx];
			
			
			if(quickPlayer.containsKey(sugeonPlayer)) {
				// 술래보다 무조건 빠른 사람인 경우 어떤 처리 ?
				i++;
				int tmpSelCnt = selCntMap.get(tagger);			
				selCntMap.put(tagger, tmpSelCnt+1);
				continue;
			}
			
			System.out.println("move:"+moveCnt+" => ["+sugeonPlayer+"]가 걸렸다!");
			
			
			int tmpSelCnt = selCntMap.get(sugeonPlayer);			
			selCntMap.put(sugeonPlayer, tmpSelCnt+1);
			
			change(sugeonIdx, tagger);
			
			i++;
			
			System.out.print("\n");
		}
		
		
		for(int j=0; j<sit.length; j++) {
			System.out.println(sit[j] + " " + selCntMap.get(sit[j]));
		}
		System.out.println(tagger + " " + selCntMap.get(tagger));
		
	
	}
	
	private static int getSugeonIdx(int startIdx, int moveCnt) {
		
		int tmpSugeonIdx = 0;
		int tmp = moveCnt % sit.length;
		System.out.println("[startIdx]:"+startIdx);
		System.out.println("[moveCnt]:"+moveCnt);
		System.out.println("[tmp]:"+tmp);
		
		tmpSugeonIdx = startIdx + tmp;
		
		System.out.println("[tmpSugeonIdx]:"+tmpSugeonIdx);
		
		if(tmpSugeonIdx < 0) {
			tmpSugeonIdx = sit.length + tmpSugeonIdx;
		} else if(tmpSugeonIdx >= sit.length) {			
			tmpSugeonIdx = tmpSugeonIdx - sit.length - 1;
		}
		
		return tmpSugeonIdx;
		
	}
	
	// 해당 idx에 있던 플레이어는 술래가 되고 술래였던 player를 자리에 앉힘
	private static void change(int idx, char player) {
		tagger = sit[idx];
		sit[idx] = player;
	}
	
	private static class InputData {
		int numOfAllPlayers;
	    int numOfQuickPlayers;
	    char[] namesOfQuickPlayers;
	    int numOfGames;
	    int[] numOfMovesPerGame;
	}
	
	private static InputData processStdin() {
	    InputData inputData = new InputData();
	
	    try (Scanner scanner = new Scanner(System.in)) {
	      inputData.numOfAllPlayers = Integer.parseInt(scanner.nextLine().replaceAll("\\s+", ""));
	
	      inputData.numOfQuickPlayers = Integer.parseInt(scanner.nextLine().replaceAll("\\s+", ""));
	      inputData.namesOfQuickPlayers = new char[inputData.numOfQuickPlayers];
	      System.arraycopy(scanner.nextLine().trim().replaceAll("\\s+", "").toCharArray(), 0, inputData.namesOfQuickPlayers, 0, inputData.numOfQuickPlayers);
	
	      inputData.numOfGames = Integer.parseInt(scanner.nextLine().replaceAll("\\s+", ""));
	      inputData.numOfMovesPerGame = new int[inputData.numOfGames];
	      String[] buf = scanner.nextLine().trim().replaceAll("\\s+", " ").split(" ");
	      for(int i = 0; i < inputData.numOfGames ; i++){
	        inputData.numOfMovesPerGame[i] = Integer.parseInt(buf[i]);
	      }
	    } catch (Exception e) {
	      throw e;
	    }
	
	    return inputData;
	}
	
	public static void main(String[] args) throws Exception {
	    InputData inputData = processStdin();
	
	    solution(inputData.numOfAllPlayers, inputData.numOfQuickPlayers, inputData.namesOfQuickPlayers, inputData.numOfGames, inputData.numOfMovesPerGame);
	}

}
