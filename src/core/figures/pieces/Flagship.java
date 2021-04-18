package core.figures.pieces;

import java.util.ArrayList;
import java.util.List;

import core.figures.Side;
import core.figures.Square;
import core.img.ImageHandling;

public class Flagship extends Piece{

    static int score_flagship;
	
    public Flagship(Square square, Side side) {
    	
        super(square, side, getScore(), ImageHandling.getImg("flagship", side));
    	
    }

    public String toString(){
        
            return "F";

    }
    
    public int getToken() {
    	
    	return 0;
    	
    }
    
    public List<Square> checkLegalMoves(int abCounter){
    	
    	List<Square> legalMoves = new ArrayList<Square>();
    	Square targetSquare;
    	
    	if(abCounter == 1)
    		return legalMoves;
    	
    	legalMoves.addAll(checkLinearMoves(1, 0));
    	legalMoves.addAll(checkLinearMoves(-1, 0));
    	legalMoves.addAll(checkLinearMoves(0, 1));
    	legalMoves.addAll(checkLinearMoves(0, -1));
    	
    	
    	for(int h = -1; h <= 1; h += 2){
    		
    		for(int v = -1; v <= 1; v += 2) {
    			
    			targetSquare = square.getAdjacentSquare(h, v);
    			
    		    if(targetSquare != null && targetSquare.getPiece() != null && targetSquare.getPiece().side != side)
    			    legalMoves.add(targetSquare);
    			
    		}
    		
    	}
    	
    	return legalMoves;
    	
    }
    
    public static int getScoreFlagship() {
		return score_flagship;
	}
	
/*	public void setScoreFlagship(int score_flagship) {
		// TODO Auto-generated method stub
		Flagship.score_flagship = score_flagship;
	}*/
    
}
