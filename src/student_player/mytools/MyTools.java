package student_player.mytools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

import hus.HusBoardState;
import hus.HusMove;

public class MyTools {

    public static double getSomething(){
        return Math.random();
    }
    
    public static int evaluation (int player, int opponent, HusBoardState board_state){
    	int[][] pits = board_state.getPits();
    	int[] my_pits = pits[player];
    	int [] opp_pits = pits[opponent];
    	int my_result = 0;
    	int opp_result = 0;
    	int my_filledpits = 0;
    	int my_emptypits = 0;
    	int opp_filledpits = 0;
    	int opp_emptypits = 0;
    	
    	for (int i: my_pits){
    		//if (my_pits[i] > 1) my_filledpits++;
    	//	else if (opp_pits[i] == 1) my_emptypits++;
    		my_result = my_result + my_pits[i];
    	}
    	
    	for(int j: opp_pits){
    		//if (opp_pits[j] > 1) opp_filledpits++;
    		//else if (opp_pits[j] == 1) opp_emptypits++;
    		opp_result = opp_result + opp_pits[j];
    	}
    	// my_result = my_result + (int) (my_filledpits * 0.5) + (int) (opp_emptypits * 0.5);
    	// opp_result = (int) (opp_result + (opp_filledpits * 0.5));
    	return my_result - opp_result;
    }
    
//    public static 
//    public static int eval(HusBoardState board_state){
//    	int[][] pits = board_state.getPits();
//    	int[] my_pits = pits[player];
//    	int result = 0;
//    	for (int i: my_pits){
//    		result = result + my_pits[i];
//    	}
//    	return result;
//    }
    
//    public static HusMove aiAgent(HusBoardState board_state, int depth, int player, int opponent, int count, int[] my_pits, int[] op_pits){
//    	ArrayList<HusMove> moves = board_state.getLegalMoves();
//    	
//    	Collections.reverse(moves);
//    	HusMove bestMove = null;
//    	Integer bestscore = -980809;
//    	Integer alpha = Integer.MIN_VALUE;
//    	Integer beta = Integer.MAX_VALUE;
//    	
//    	for (int currmove = 0; currmove < moves.size(); currmove++){ //for each of the moves in the ArrayList (reversed)
//    		HusBoardState clone = (HusBoardState) board_state.clone();
//    		clone.move(moves.get(currmove)); //We can see the effects of a move on the cloned board
//    		int minValue = 0;
//    		if(count >= 4){
//    			if(count >= 4 && moves.get(currmove).getPit() > 26 && moves.get(currmove).getPit() < 19 && currmove <=  4 && currmove >= 2){
//    				minValue = mini(depth - 1, player, clone, alpha, beta);
//    			}
//    			else{
//    				minValue = mini(depth/4,player, clone, alpha, beta);
//    			}
//    		}
//    		else{
//    			minValue = mini(depth/4+3, player, clone, alpha, beta);
//    		}
//    		if(bestscore < minValue){
//    			bestMove = moves.get(currmove);
//    			bestscore = minValue;
//    		}    	
//    	}
//    	return bestMove;
//    }
//    
//    public static int mini(HusBoardState board_state, int depth, int player, int opponent, int alpha, int beta){
//    	if(board_state.gameOver() || depth == 0){
//    		return evaluation(player, opponent, board_state);
//    	}
//    	
//    	int v = Integer.MAX_VALUE;
//    	ArrayList <HusMove> moves = board_state.getLegalMoves();
//    	for(HusMove move: moves){
//    		HusBoardState clone = (HusBoardState) board_state.clone();
//    		clone.move(move);
//    		//int maxValue = max(clone, depth-1, player, opponent, alpha, beta);
//    		v = Math.max(v, max(clone, depth-1, player, opponent, alpha, beta));
//    		if(v <= alpha) return v;
//    		alpha = Math.max(alpha, v);
//    	}
//    	return beta;
//    }
//    
//    public static int max(HusBoardState board_state, int depth, int player, int opponent, int alpha, int beta){
//    	if(board_state.gameOver() || depth == 0){
//    		return evaluation(player, opponent, board_state);
//    	}
//    	
//    	int v = Integer.MIN_VALUE;
//    	ArrayList <HusMove> moves = board_state.getLegalMoves();
//    	for(HusMove move: moves){
//    		HusBoardState clone = (HusBoardState) board_state.clone();
//    		clone.move(move);
//    		//int minValue = mini(clone, depth-1, player, opponent, alpha, beta);
//    		v = Math.max(v, mini(clone, depth-1, player, opponent, alpha, beta));
//    		if(v >= beta) return v;
//    		alpha = Math.max(alpha,v);
//    	}
//    	return v;
//    }
    
    public static int alphabeta(HusBoardState board_state, int depth, 
    		boolean max, int player, int opponent, int alpha, int beta){
    	//System.out.println("alpha: "+alpha+" beta: "+beta+" depth: "+depth);
    	
    	if(board_state.gameOver() || depth == 0){
    		int[] result = (newMethod(board_state, player, opponent));
    		//System.out.println("Value is: "+result[0]);
    		return result[0];
    	}
    	
    	
    	
    	int v;
    	ArrayList <HusMove> moves = board_state.getLegalMoves();
    	if(max) {
    		v = Integer.MIN_VALUE; //NEGATIVE INFINIT
    		for (HusMove move: moves){
    			HusBoardState clone = (HusBoardState) board_state.clone();
    			clone.move(move);
    			v = Math.max(alpha, alphabeta(clone, depth-1, false, player, opponent, alpha, beta));
    			//if(v >= beta) return v;
    			alpha = Math.max(alpha, v);
    			if (beta <= alpha) {
    				return beta;
    			}
    		}
    		return alpha; //return v?
    	}
    	else{
    		v = Integer.MAX_VALUE; //POSITIVE INFINITY
    		for (HusMove move: moves){
    			HusBoardState clone = (HusBoardState) board_state.clone();
    			clone.move(move);
    			v = Math.min(beta, alphabeta(clone, depth-1, true, player, opponent, alpha, beta));
    			//if (v <= alpha) return v;
    			beta = Math.min(beta, v);
    			if (beta <= alpha) {
    				return alpha;
    			}
    		}
    		return beta; //return v?
    	}
    }
    
    public static int minimax2(HusBoardState board_state, int depth, boolean max, int player, int opponent){
    	if(board_state.gameOver() || depth == 0){
    		int[] result = (newMethod(board_state, player, opponent));
    		System.out.println("Value is: "+result[0]);
    		return result[0];
    	}
    	
    	int mini = Integer.MAX_VALUE;
    	int maxi = Integer.MIN_VALUE;
    	
    	ArrayList <HusMove> moves = board_state.getLegalMoves();
    	int scores[] = new int[moves.size()];
    	
    	if (max){
    		for(int current: scores){
    			HusBoardState clone = (HusBoardState) board_state.clone();
        		clone.move(moves.get(current));
        		scores[current] = minimax(clone, depth-1, !max, player, opponent);
        		//NEED TO LOOK INTO THIS
        		if(scores[current] < mini){
        			mini = scores[current];
        		}
        		else if (scores[current] > maxi){
        			maxi = scores[current];
        		}
    		}
    		
    		return maxi;
    	}
    	else{
    		for(int current: scores){
    			HusBoardState clone = (HusBoardState) board_state.clone();
    			clone.move(moves.get(current));
    			scores[current] = minimax(clone, depth-1, !max, player, opponent);
    			//NEED TO LOOK INTO THIS
    			if(scores[current] < mini){
    				mini = scores[current];
    		}
    		else if (scores[current] > maxi){
    			maxi = scores[current];
    			}
    		}
    		
    		return mini;
    	}
    }
    
    public static int minimax(HusBoardState board_state, int depth, boolean max, int player, int opponent){
    	if(board_state.gameOver() || depth == 0){
    		return evaluation(player, opponent, board_state);
    	}
    	
    	ArrayList <HusMove> moves = board_state.getLegalMoves();
    	int scores[] = new int[moves.size()];
    	int mini = Integer.MAX_VALUE;
    	int maxi = Integer.MIN_VALUE;
    	
    	for (int current: scores){
    		HusBoardState clone = (HusBoardState) board_state.clone();
    		clone.move(moves.get(current));
    		scores[current] = minimax(clone, depth-1, !max, player, opponent);
    		if(scores[current] < mini){
    			mini = scores[current];
    		}
    		else if (scores[current] > maxi){
    			maxi = scores[current];
    		}
    	}
    	
    	if (max) return maxi;
    	
    	return mini;
    }
    
    /*
     * 
     *  
     *  TRYING NEW CODE. NOT FINALISED YET*/
    //********************************************************************************************************************//
    
    public static HashMap<HusMove, Integer> captureValueMap (HusBoardState board_state, int player_id, int opponent_id){
    	
    	ArrayList<HusMove> moves = board_state.getLegalMoves();
    	HashMap<HusMove, Integer> captureMap = new HashMap<HusMove, Integer>();
    	int[][] pits = board_state.getPits();
    	int[] my_pits = pits[player_id];
    	
    	if(moves.size() > 0){
    		for(HusMove move: moves){
    			//int initialPit = move.getPit();
    			//int seedsInPit = my_pits[move.getPit()];
    			//int resultantPit = (initialPit + seedsInPit)%32;
    			//if(my_pits[resultantPit] > 0){
    			Random rand = new Random();

    			int  n = rand.nextInt(50) + 1;
    			captureMap.put(move, 0);
    			//}
    		}
    	}
    	
    	return captureMap;
    	
    }
    
    public static int netGain (HusBoardState board_state, HusMove move, int player_id, int opponent_id){
    	
    	HusBoardState clone = (HusBoardState) board_state.clone();
    	clone.move(move); 
//    	if(board_state.getTurnNumber() > 4){
//    	//System.out.println("Initial Player: "+board_state.getTurnPlayer()+" Clone Player: "+clone.getTurnPlayer());
//    	}
//    	if(board_state.getTurnNumber() > 4){
//    	//System.out.println("one player_id: "+player_id);
//    	}
    	int initialNoOfSeeds = playerSeeds(board_state, player_id);
//    	if(board_state.getTurnNumber() > 4){
//    		if(player_id==1){
//    			//System.out.println("initialNoOfSeeds: "+initialNoOfSeeds);
//    		}
//    	}
    	int resultantNoOfSeeds = playerSeeds(clone, player_id);
//    	if(board_state.getTurnNumber() > 4){
//    		if(player_id==1){
//    			System.out.println("resultantNoOfSeeds: "+resultantNoOfSeeds);
//    		}
//    	}
    	int capture = resultantNoOfSeeds - initialNoOfSeeds;
//    	if(board_state.getTurnNumber() > 4){
//    		if(player_id == 1){
//    			System.out.println(player_id+"'s capture: "+capture);
//    		}
//    	}
    	//else System.out.println(player_id+"'s capture: "+capture);
    	return resultantNoOfSeeds;
    	
    	
    }
    
    public static int netLoss(HusBoardState board_state, HusMove move, int player_id, int opponent_id){
    	
    	HusBoardState clone = (HusBoardState) board_state.clone();
    	clone.move(move); // this actually might be performing the switch perspective
    	//switch it to the opponent 
    	HashMap<HusMove, Integer> opp_captureList = captureValueMap(clone, opponent_id, player_id); //check if turn id is opponents
    	int maximizedValue = 0;
    	for (HusMove currmove: opp_captureList.keySet()){
    		maximizedValue = Math.max(maximizedValue, netGain(clone, currmove, opponent_id, player_id)); //Check her if opponent is selected
    		
    	}
//    	if(board_state.getTurnNumber() > 4){
//    		System.out.println("Lost: "+maximizedValue);
//    	}
    	return maximizedValue;
    	
    }
    
    public static int newEval(HusBoardState board_state, HusMove move, int player_id, int opponent_id){
    	if(board_state.gameOver()){
    		if(board_state.getWinner() == player_id) return Integer.MAX_VALUE - 5;
    		else return Integer.MIN_VALUE + 5;
    	}
    	int maximizedAdvantage, resultantLoss;
    	maximizedAdvantage = netGain(board_state, move, player_id, opponent_id);
    	resultantLoss = netLoss(board_state, move, player_id, opponent_id);
//    	//if(board_state.getTurnNumber() > 4){
//    	System.out.println("RESULTS== Gain: "+maximizedAdvantage+" Loss: "+resultantLoss);
//    	//}
    	return maximizedAdvantage;
    }
   
    /*Evaluation by new method*/
    public static int[] newMethod(HusBoardState board_state, int player_id, int opponent_id){
    	if(board_state.gameOver()){
    		if(board_state.getWinner() == opponent_id){
    			return new int[]{Integer.MIN_VALUE + 5, Integer.MIN_VALUE + 5};
    		}
    		else if (board_state.getWinner() == player_id){
    			return new int[]{Integer.MAX_VALUE - 5, Integer.MAX_VALUE - 5};
    		}
    	}
    	
    	return new int[]{playerSeeds(board_state, player_id),playerSeeds(board_state, opponent_id)}; 
    }
    
    public static int playerSeeds(HusBoardState board_state, int player){
    	int[][] pits = board_state.getPits();
    	int[] my_pits = pits[player];
    	int player_seeds = 0;
    	
//    	if(board_state.getTurnNumber() > 4){
//    		if(player == 1){
//    			System.out.println(player+"'s My pits: "+Arrays.toString(my_pits));
//    		}
//    	}
    	
    	for (int i = 0; i < my_pits.length; i++){
    		player_seeds = player_seeds + my_pits[i];
    	}
//    	if(board_state.getTurnNumber() > 4){
//    	System.out.println(player+"'s Player seeds: "+player_seeds);
//    	}
    	return player_seeds;
    }
    
    public static int newAlphaBeta(HusBoardState board_state, int depth, boolean max, int player, int opponent, int alpha, int beta, HusMove move){
    	
    	//Makes clone and plays the move
    	HusBoardState initial_clone = (HusBoardState) board_state.clone();
    	initial_clone.move(move);
    	
    	if(board_state.gameOver() || depth == 0){
    		int[] result = newMethod(initial_clone, player, opponent);
    		return result[0];
    	}
    	
    	
    	//gets the legal moves of the new board state
    	HashMap<HusMove, Integer> captureMoves = captureValueMap(initial_clone, player, opponent);
	   	ArrayList<HusMove> captureList = hashMapToArrayList(captureMoves);
	    ArrayList<HusMove> sorted = mergeSort(captureMoves, captureList);
	    
    	
	    if(max) {
    		for (HusMove currmove: sorted){
    			HusBoardState clone = (HusBoardState) board_state.clone();
    			alpha = Math.max(alpha, newAlphaBeta(clone, depth-1, false, player, opponent, alpha, beta, currmove));
    			//if(v >= beta) return v;
    			if (beta <= alpha) break;
    		}
    		if(alpha == Integer.MIN_VALUE){
    			System.out.println("Alpha: "+alpha);
    		}
    		return alpha; //return v?
    	}
    	else{
    		for (HusMove currmove: sorted){
    			HusBoardState clone = (HusBoardState) board_state.clone();
    			beta = Math.min(beta, newAlphaBeta(clone, depth-1, true, player, opponent, alpha, beta, currmove));
    			//if (v <= alpha) return v;
    			if (beta <= alpha) break;
    		}
    		if(beta == Integer.MIN_VALUE){
    			System.out.println("Beta: "+alpha);
    		}
    		return beta; //return v?
    	}
    }
    
    public static ArrayList<HusMove> hashMapToArrayList (HashMap<HusMove, Integer> captureValueMap){
  	  
  	  ArrayList<HusMove> captureList = new ArrayList<HusMove>(); 
  	  
  	  for (HusMove m : captureValueMap.keySet()){
  		  captureList.add(m); 
  	  }
  	  
  	  return captureList; 
    }
    
	/* NEED TO CHANGE ALL OF THIS!
	 * BEGIN MERGESORT [WORKS 100%]
	 * We want to sort the arrayList of all the relaymoves to be sorted according to their capture size
	 * we keep hashmap part of the input so that we have something to refer to
	 * for the weight (captureSize) of the moves we are trying to sort.
	 */
  public static ArrayList<HusMove> mergeSort (HashMap<HusMove, Integer> relayList, ArrayList<HusMove> moves)  {
	 
	 if (moves.size() <= 1) return moves; 
	 
	 ArrayList<HusMove> left = new ArrayList<HusMove>();
	 ArrayList<HusMove> right = new ArrayList<HusMove>();
	 int middle = (moves.size())/2; 
	 for (int i = 0; i<middle; i++){
		 left.add(moves.get(i));
	 }
	 for (int i = middle; i < moves.size(); i++){
		 right.add(moves.get(i));
	 }
	 
	 left = mergeSort(relayList, left);
	 right = mergeSort(relayList, right);
	 
	 return merge(relayList, left,right); 
	 
 }

  public static ArrayList<HusMove> merge (HashMap<HusMove, Integer> relayList, ArrayList<HusMove> left, ArrayList<HusMove> right) { 
	 ArrayList<HusMove> result = new ArrayList<HusMove>(); 
	 while (!left.isEmpty() && !right.isEmpty()) { 
		 if (relayList.get(left.get(0)) <= relayList.get(right.get(0))) { 
			 result.add(left.remove(0)); 
		 }
		 else { 
			 result.add(right.remove(0)); 
		 }
	 }
	 while (!left.isEmpty()){
		 result.add(left.remove(0));
	 }
	 while (!right.isEmpty()){
		 result.add(right.remove(0));
	 }
	 return result; 
	 
 }
 /*
  * END MERGESORT
  */
  
  /*
   * 
   *  TRYING NEW CODE. NOT FINALISED YET*/
  //********************************************************************************************************************//
  
  //****** NEW ALPHA BETA WITH ORDERING ********//
  public static int abMoveOrdering(HusBoardState board_state, int depth, boolean max, int player, int opponent, int alpha, int beta){
  	//System.out.println("alpha: "+alpha+" beta: "+beta+" depth: "+depth);
  	
  	if(board_state.gameOver() || depth == 0){
  		int[] result = (newMethod(board_state, player, opponent));
  		return result[0];
  	}
  	
  	
  	
  	int v;
  	ArrayList <HusMove> moves = board_state.getLegalMoves();
    TreeMap<Integer, HusMove> legalMovesMap = new TreeMap<Integer, HusMove>();
    for(HusMove currmove: moves){
 	   HusBoardState clone = (HusBoardState) board_state.clone();
 	   clone.move(currmove);
 	   int[] eval = MyTools.newMethod(board_state, player, opponent);
 	   legalMovesMap.put(eval[0], currmove);  
    }
    
  	if(max) {
  		v = Integer.MIN_VALUE; //NEGATIVE INFINITY
  		ArrayList<HusMove> sortedMovesForMax = new ArrayList<HusMove>(legalMovesMap.values());
  		Collections.reverse(sortedMovesForMax);
  		for (HusMove move: sortedMovesForMax){
  			HusBoardState clone = (HusBoardState) board_state.clone();
  			clone.move(move);
  			alpha = Math.max(alpha, alphabeta(clone, depth-1, false, player, opponent, alpha, beta));
  			//if(v >= beta) return v;
  			//alpha = Math.max(alpha, v);
  			if (beta <= alpha){ //break;
  				return beta;
  			}
  		}
  		return alpha; //return v?
  	}
  	else{
  		ArrayList<HusMove> sortedMovesForMin = new ArrayList<HusMove>(legalMovesMap.values());
  		v = Integer.MAX_VALUE; //POSITIVE INFINITY
  		for (HusMove move: sortedMovesForMin){
  			HusBoardState clone = (HusBoardState) board_state.clone();
  			clone.move(move);
  			beta = Math.min(beta, alphabeta(clone, depth-1, true, player, opponent, alpha, beta));
  			//if (v <= alpha) return v;
  			//beta = Math.min(beta, v);
  			if (beta <= alpha) { //break;
  				return alpha;
  			}
  		}
  		return beta; //return v?
  	}
  }
  
  
  
}
