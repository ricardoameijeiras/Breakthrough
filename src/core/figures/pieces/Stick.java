package core.figures.pieces;

import java.util.ArrayList;
import java.util.List;

//import breakthru.core.img.ImageHandling;
import core.figures.Side;
import core.figures.Square;
import core.img.ImageHandling;

public class Stick extends Piece{

	static int score;
	
    public Stick(Square square, Side side) {
    	
    	super(square, side, getScore(), ImageHandling.getImg("stick", side));
    	
    }
    
    public String toString(){
    	
        if(side == Side.WHITE) {
            return "S";
        }else
            return "s";

    }
    
    public int getToken() {
    	
    	if(side == Side.WHITE) {
            return 2;
        }else
            return 1;
    	
    }
    
    public List<Square> checkLegalMoves(int abCounter){
    	List<Square> legalMoves = new ArrayList<Square>();
    	Square targetSquare;
    	
    	legalMoves.addAll(checkLinearMoves(1, 0));
    	legalMoves.addAll(checkLinearMoves(-1, 0));
    	legalMoves.addAll(checkLinearMoves(0, 1));
    	legalMoves.addAll(checkLinearMoves(0, -1));
    	
 
    	if(abCounter == 0)
    	   	for(int h = -1; h <= 1; h += 2){
    		
    	   		for(int v = -1; v <= 1; v += 2) {
    			
    	   			targetSquare = square.getAdjacentSquare(h, v);
    			
    	   			if(targetSquare != null && targetSquare.getPiece() != null && targetSquare.getPiece().side != side)
    	   				legalMoves.add(targetSquare);
    			
    	   		}
    		
    	   	}
    	
		return legalMoves;
		
    }
    
    public int getScoreStick() {
    	return score;
    }
    
}
