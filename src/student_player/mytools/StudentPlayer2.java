package student_player.mytools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import boardgame.Move;
import hus.HusBoardState;
import hus.HusMove;
import hus.HusPlayer;

public class StudentPlayer2 extends HusPlayer {
	
	public StudentPlayer2() { super("260568533"); }
	
	static int count = 0;
    Timer timer = new Timer();
    int moveNumber = 0;
    private long start = 0l;
    private long limit = 1850l;

	@Override
	public Move chooseMove(HusBoardState board_state) {
		moveNumber++;
    	System.out.println("moveNumber: " +moveNumber);
    	count++;
        // Get the contents of the pits so we can use it to make decisions.
    	
    	this.start = System.currentTimeMillis();
        int[][] pits = board_state.getPits();

        // Use ``player_id`` and ``opponent_id`` to get my pits and opponent pits.
        int[] my_pits = pits[player_id];
        //int my_seeds = 0;
//        for (int i = 0; i < 32; i++){
//    		System.out.println("my_pits seeds: "+my_pits[i]+" seed on pit "+i);
//    		my_seeds = my_seeds + my_pits[i];
//    	}
        //System.out.println("Total number of my seeds:"+my_seeds);
        int[] op_pits = pits[opponent_id];

        // Use code stored in ``mytools`` package.
       // MyTools.getSomething();

        // Get the legal moves for the current board state.
       ArrayList<HusMove> moves = board_state.getLegalMoves();
       //Collections.reverse(moves); // processing on the inner pits first. HOW DO WE TEST THE GOODNESS OF THIS?
       //HusMove move = moves.get(0);
       int alpha = Integer.MIN_VALUE;
       int beta = Integer.MAX_VALUE;
      int[] scores = new int[moves.size()];
       int maxValue = Integer.MIN_VALUE;
       HusMove bestMove = null;
       
       
       //**********TESTING NEW CODE**********//
//       HashMap<HusMove, Integer> relayList = MyTools.captureValueMap(board_state, player_id, opponent_id);
//   	   ArrayList<HusMove> aList = MyTools.hashMapToArrayList(relayList);
//   	   ArrayList<HusMove> sorted = MyTools.mergeSort(relayList, aList); 
//   	   int[] scores = new int[sorted.size()];
//       
//     for(int i = sorted.size() -1; i>=0; i--){
   	 //**********TESTING NEW CODE**********//
       bestMove = moves.get(0);
       for(int i = 0; i < moves.size(); i++){
    	  HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
          cloned_board_state.move(moves.get(i));
          //scores[i] = (int) MyTools.getSomething();
          //scores[i] = MyTools.mini(cloned_board_state, 40, player_id, opponent_id, alpha, beta);
    	  scores[i] = MyTools.minimax(cloned_board_state, 5, false, player_id, opponent_id);
          //scores[i] = alphabeta(cloned_board_state, 6, false, player_id, opponent_id, alpha, beta); //Its working on 9 check if its violating the timing?
         //System.out.println(scores[i]+", "+maxValue);
        // scores[i] = MyTools.newAlphaBeta(board_state, 5, false, player_id, opponent_id, alpha, beta, sorted.get(i));
        // System.out.println("The value returned is "+scores[i]);
    	   if(scores[i] > maxValue){
    		   maxValue = scores[i];
    		   bestMove = moves.get(i);
    	   }
       }
       
       
       
       
       //ERROR CASE FOUND: When all returned values are less than 0, what do we do in that case? ASK ANDI. Initialized max value to -100
       //System.out.println(player_id);
       System.out.println("Maxvalue is: "+maxValue);
       return bestMove;

	}
	
    public int alphabeta(HusBoardState board_state, int depth, 
    		boolean max, int player, int opponent, int alpha, int beta){
    	
    	if(board_state.gameOver() || depth == 0){
    		int[] result = (newMethod(board_state, player, opponent));
    		return result[0];
    	}
    	
    	if(System.currentTimeMillis() - this.start >= this.limit){
    		return newMethod(board_state, player, opponent)[0];
    	}
    	
    	int v;
    	
    	ArrayList <HusMove> moves = board_state.getLegalMoves();
    	if(max) {
    		v = Integer.MIN_VALUE; //NEGATIVE INFINITY
    		for (HusMove move: moves){
    			HusBoardState clone = (HusBoardState) board_state.clone();
    			clone.move(move);
    			v = Math.max(alpha, alphabeta(clone, depth-1, false, player, opponent, alpha, beta));
    			alpha = Math.max(alpha, v);
    			if (beta <= alpha) {
    				return beta;
    			}
    			
    			if(System.currentTimeMillis() - this.start >= this.limit){
    				break;
    			}
    		}
    		return alpha; 
    	}
    	else{
    		v = Integer.MAX_VALUE; //POSITIVE INFINITY
    		for (HusMove move: moves){
    			HusBoardState clone = (HusBoardState) board_state.clone();
    			clone.move(move);
    			v = Math.min(beta, alphabeta(clone, depth-1, true, player, opponent, alpha, beta));
    			beta = Math.min(beta, v);
    			if (beta <= alpha) {
    				return alpha;
    			}
    			
    			if(System.currentTimeMillis() - this.start >= this.limit){
    				break;
    			}
    		}
    		return beta; 
    	}
    }
    
    /*Evaluation by new method*/
    public int[] newMethod(HusBoardState board_state, int player_id, int opponent_id){
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

}
