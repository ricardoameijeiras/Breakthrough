package player;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import core.figures.Board;
import core.figures.Move;
import core.figures.Side;

public class AlphaBetaPlayer extends BreakthruPlayer{

	private static final int INITIAL_DEPTH = 3;
	private Move bestMove;
	private Move globalBestMove;
	private int currentDepth;
	private static final int TIMEOUT_MILISECONDS = 6000;
	public long[][][] ZobristTable;
	private long start;
	private boolean timeout;
	private Side chosenSide;
	private HashMap<Long, Integer> moveList = new HashMap<Long, Integer>();
	private Map<Long, Integer> orderedList = new TreeMap<Long, Integer>();
	int i = 0;
	
	public AlphaBetaPlayer(Board board, Side side) {

		super(board, side);	
		chosenSide = side;
		
	}

	public Move decideMove(int abCounter){
		
		timeout = false;
		start = System.currentTimeMillis();
		
		
		for(int d  = 0;; d++) {
			
			if(d > 0) {
				
				globalBestMove = bestMove;
				moveList.put(hashing(board), board.evaluate(chosenSide));
				orderedList = moveOrder(moveList);
				System.out.println("Completed search with depth " + currentDepth + ". Best move so far " + globalBestMove);
				
				if(globalBestMove.getInitialSquare().getPiece().toString() == "F" && currentDepth == 3 && !(globalBestMove.getTargetSquare().getRow() == 0 || globalBestMove.getTargetSquare().getRow() == 10 || globalBestMove.getTargetSquare().getColumn() == 0 || globalBestMove.getTargetSquare().getColumn() == 10))
					return globalBestMove;
				
			}
			
			currentDepth = INITIAL_DEPTH + d;
			maximizer(currentDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, abCounter);
			
			if(timeout) {
				
				return globalBestMove;
				
			}
		
		}
		
	}
	
	public static <K, V extends Comparable<V>> Map<K, V> moveOrder(final Map<K, V> map) {
		
	    Comparator<K> valueComparator =  new Comparator<K>() {
	        public int compare(K k1, K k2) {
	            int compare = map.get(k2).compareTo(map.get(k1));
	            if (compare == 0) return 1;
	            else return compare;
	        }
	    };
	    
	    Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
	    sortedByValues.putAll(map);
	    
	    return sortedByValues;
	    
	}

	private int maximizer(int depth, int alpha, int beta, int abCounter) {
		
		if(System.currentTimeMillis() - start > TIMEOUT_MILISECONDS) {
			
			timeout = true;
			return alpha;
			
		}
		
		if(depth == 0) {
			
			return board.evaluate(chosenSide);
			
		}
		
		List<Move> legalMoves = checkAllLegalMoves(abCounter);
		
		for (Move move : legalMoves) {
			
			makeMove(move);
			
			side = side.opposite();
			int rating = minimizer(depth - 1, alpha, beta, abCounter); 
			side = side.opposite();
			undoMove(move);
			
			if(rating > alpha) {
				
				alpha = rating;
				
				if(depth == currentDepth) 
					bestMove = move;
				
			}
			
			if(alpha >= beta)
				return alpha;
			
		}
		
		return alpha;	
	
	}
	
	private long hashing(Board board) {
		
		Random random = new Random();
		long hash = 0;
		int p = 0;
	
		ZobristTable = new long[11][11][11];
		
		for(int i = 0; i < 11; i++)
			for(int j = 0; j < 11; j++)
				for(int k = 0; k < 11; k++)
					ZobristTable[i][j][k] = random.nextLong();
		
		for (int r = Board.SIZE - 1; r >= 0; r--){
    		 
             for (int c = 0; c < Board.SIZE; c++){
            	 
            	 if(board.getSquare(r, c).getPiece() != null) {
            		 
            		 if(board.getSquare(r, c).getPiece().toString() == "S") {
            			 p = 1;
            		 }else if(board.getSquare(r, c).getPiece().toString() == "s") {
            			 p = 2;
            		 }else if(board.getSquare(r, c).getPiece().toString() == "F") {
            			 p = 3;
            		 }
            		 
            	 }
            	
            	 hash ^= ZobristTable[r][c][p];
            	 
             }
             
        }
		
		return hash;
		
	}
	
	private int minimizer(int depth, int alpha, int beta, int abCounter) {
		
		if(depth == 0) {
			//side.opposite
			return board.evaluate(chosenSide);
			
		}
		
		List<Move> legalMoves = checkAllLegalMoves(abCounter);
		
		for(Move move : legalMoves) {
			
			makeMove(move);
			side = side.opposite();
			int rating = maximizer(depth - 1, alpha, beta, abCounter); 
			side = side.opposite();
			undoMove(move);
			
			if(rating <= beta)
				beta = rating;
			
			if(alpha >= beta)	
				return beta;
			
		}
		
		return beta;
		
	}
	
}
