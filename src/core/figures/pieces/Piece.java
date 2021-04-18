package core.figures.pieces;

import core.figures.Square;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import core.figures.Side;

public abstract class Piece {
    private static  int score;
    protected Square square;
    protected final Side side;
    protected final BufferedImage img;
    public abstract List<Square> checkLegalMoves(int abCounter);
    
    protected Piece(Square square, Side side, int score, BufferedImage img){
        this.square = square;
        this.square.setPiece(this);
        this.score = score;
        this.side = side;
        this.img = img;
    }

    public Square getSquare() {
		// TODO Auto-generated method stub
		return square;
	}
    
	public void setSquare(Square square) {
		// TODO Auto-generated method stub
		this.square = square;
	}
	
	public BufferedImage getImage() {
		
		return img;
		
	}

	
	
	public Side getSide() {
		
		return side;
		
	}
    
    protected void checkSquare(List <Square> legalMoves, Square targetSquare){
    	
    	if(targetSquare != null && (targetSquare.getPiece() == null || targetSquare.getPiece().side != side))
    		legalMoves.add(targetSquare);
    	
    }
    
    protected List <Square> checkLinearMoves(int horizontal, int vertical){
    	List<Square> legalMoves = new ArrayList<Square>();
    	Square targetSquare = square.getAdjacentSquare(horizontal, vertical);
    	    	
    	while(targetSquare != null){
    		
    		if(targetSquare.getPiece() == null){
    			
    			legalMoves.add(targetSquare);
    			
    		}else if (targetSquare.getPiece().side == side) {
    		
    			break;
    			
    		}else {
    			
    			//legalMoves.add(targetSquare);
                break;
    		}
    		
    		targetSquare = targetSquare.getAdjacentSquare(horizontal, vertical);
    		
    			
    	}
    	
    	return legalMoves;
    	
    }

	public static int getScore() {
		// TODO Auto-generated method stub
		return score;
	}
	
	public void setScore(int score) {
		// TODO Auto-generated method stub
		this.score = score;
	}
	
}
