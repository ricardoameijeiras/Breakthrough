package player;

import java.util.ArrayList;
import java.util.List;
import core.figures.Board;
import core.figures.Move;
import core.figures.PiecesColor;
import core.figures.Side;
import core.figures.Square;
import core.figures.pieces.Piece;


public class BreakthruPlayer {
	//Common methods of every breakthru player
	protected Board board;
	protected Side side;
	public int counter_moves;
	public static Piece previousPiece;
	public boolean action, motion;
	Square flagshipSquare = null;
	
	public BreakthruPlayer(Board board, Side side) {
		this.board = board;
		this.side = side;
	}
	
	public String makeMove(Move move) {
		
		if(move.getTargetPiece() != null) {
			
			PiecesColor opponent = board.getPieceColor(side.opposite());
			opponent.eatPiece(move.getTargetPiece());
			
		}
		//Updating the square
		Piece movingPiece = move.getInitialSquare().getPiece();
				
		if(move.getTargetSquare() != null || movingPiece != null) {
			movingPiece.setSquare(move.getTargetSquare());
			move.getTargetSquare().setPiece(movingPiece); 
			move.getInitialSquare().setPiece(null);		 
		}
		
		return movingPiece.getSquare().getPiece().toString();
		
	}
	
	public void undoMove(Move move) {
		
		Square initialSquare = move.getInitialSquare();
		Square targetSquare = move.getTargetSquare();
		Piece targetPiece = move.getTargetPiece();
		Piece movingPiece = targetSquare.getPiece();
		
		movingPiece.setSquare(initialSquare);
		initialSquare.setPiece(movingPiece);
		targetSquare.setPiece(null);
		
		if(targetPiece != null) {
			
			targetPiece.setSquare(targetSquare);
			targetSquare.setPiece(targetPiece);
			PiecesColor opponentPieces = board.getPieceColor(side.opposite());
			opponentPieces.revivePiece(targetPiece);
			
		}
		
	}
	
	public Move decideMove(int abCounter) {
		
		return null;
	
	}
	
	public List<Move> checkAllLegalMoves(int abCounter){
		
		List<Piece> alivePieces = board.getPieceColor(side).getAliveSticks();
		List<Move> legalMoves = new ArrayList<Move>();
		List<Move> flagship = new ArrayList<Move>();
		//Move previousMove = new Move(null, null, null);		
		//boolean isMoveAdded = true;
		
		
		for(Piece p : alivePieces) {
			
			if(p.toString() == "F") {
				flagshipSquare = p.getSquare();
			}
			if(abCounter == 1 && (previousPiece == p || p.getSquare().getPiece().toString() == "F"))
				continue;
				
			for(Square s : p.checkLegalMoves(counter_moves)) {
				
				Move move = new Move(p.getSquare(), s, s.getPiece());
				
				if(move.getInitialSquare().getPiece().toString() == "F" && (move.getTargetSquare().getRow() == 0 || move.getTargetSquare().getRow() == 10 || move.getTargetSquare().getColumn() == 0 || move.getTargetSquare().getColumn() == 10)) {
					
					flagship.add(move);
					return flagship;
					
				}
					
				if(move.getInitialSquare().getPiece().toString() == "S"	){
					
					if(flagshipInDanger(move)) {
						
						flagship.add(move);
						return flagship;
						
					}
					
				}
				
				checkActionMoves(p.getSquare(), s);
				
				if(action){
					
					move.setAction(true);
					move.setMotion(false);
				
				}else{
				
					move.setAction(false);
					move.setMotion(true);
				
				}
				
				move.getTargetSquare().setLegal(true);
				legalMoves.add(move);
				
			}	
			
		}
		
		return legalMoves;
		
	}
	
	public boolean flagshipInDanger(Move move) {
	    	
	    	for(int h = -1; h <= 1; h += 2){
	    		
	    		for(int v = -1; v <= 1; v += 2) {
	    			
	    			if(move.getInitialSquare().getAdjacentSquare(h, v) != null)
	    				if(move.getInitialSquare().getAdjacentSquare(h, v).getPiece() != null)
	    					if(move.getTargetSquare() == move.getInitialSquare().getAdjacentSquare(h, v) && move.getInitialSquare().getAdjacentSquare(h, v).getPiece().toString() == "F") {
	    	
	    						return true;
	    						
	    					}
	    			}
	    		
	    	}
	    
			return false;
	    	
	    }
		
	
	public void checkActionMoves(Square initialSquare, Square targetSquare){
		
		boolean test = false;
	
		for(int h = -1; h < 2; h+=2){
			
			for(int v = -1; v < 2; v+=2){
				
				if(initialSquare.getAdjacentSquare(h, v) == targetSquare){
					
					test = true;
					break;
				
				}			
				
			}
			
		}	
		
		if(test){
			
			action = true;
			motion = false;
			
		}else{
			
			action = false;
			motion = true;
			
		}	
		
	}
	
	
	public String toString() {
		
		return "User";
		
	}

	public boolean isPieceEaten(Move move) {
		
		if(move.getTargetPiece() != null) 
			return true;
		
		return false;
		
	}
	

	
}

