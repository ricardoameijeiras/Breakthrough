package core.figures;

import core.figures.pieces.Piece;

public class Board {

    public static final int SIZE = 11;
    private Square [][] squares;
    private PiecesColor whitePieces;
    public PiecesColor yellowPieces;
    public int bonusGold = 0;
    public int bonusWhite = 0;
    
    public Board(PiecesColor whitePieces, PiecesColor yellowPieces) {
    	
        squares = new Square[SIZE][SIZE];
        this.whitePieces = whitePieces;
        this.yellowPieces = yellowPieces;

        for (int r = 0; r < SIZE; r++) {

            for (int c = 0; c < SIZE; c++) {
            	
                squares[r][c] = new Square(this, r, c, 0);
                
            }
            
        }
        
    }

    public Square getSquare(int row, int column){
    	
    	if(row < 0 || row >= SIZE || column < 0 || column >= SIZE) {
    		
    		return null;
    		
    	}else
    		return squares[row][column];
    	
    }

    public PiecesColor getPieceColor(Side side){
    	
        if(side == Side.WHITE) {
        	
            return whitePieces;
            
        }else
            return yellowPieces;
        
    }

    public void display(){
    	
    	 for (int r = Board.SIZE - 1; r >= 0; r--){
    		 
             for (int c = 0; c < Board.SIZE; c++){
            	 
                 if (squares[r][c].getPiece() != null){
                	 
                     System.out.print(squares[r][c].getPiece());
                     
                 }
                 else
                     System.out.print(".");
                 
             }
             
             System.out.println();
            
        }
        
    }
    
    public int evaluate(Side side) {
    	
    	int whiteScore = 0, yellowScore = 0;
    	
    	//setSquareScores();
    	
    	for(Piece p : whitePieces.getAliveSticks()) {
    		
    		//if(p.toString() == "S")
    		p.setScore(2);
    		
    		whiteScore += p.getScore();
    		
    	}
    	
    	bonusGold = 0;
    	
    	for(Piece p : yellowPieces.getAliveSticks()) {
    	
    		if(p.toString() == "F") {
    			
    			if(flagshipFreePath(p)) {
    				bonusGold += 50;
    			}
    			
    			bonusGold += flagshipCovered(p) * 2;
    			
    			if(flagshipInDanger(p) == 0) {
    				
    				p.setScore(10);
    				
    			}else {
    				
    				p.setScore(10 - flagshipInDanger(p));
    				
    			}
    			
    		}else if(p.toString() == "s") {
    			p.setScore(3);
    			
    		}
    		
    		if(p.toString() == "F" && (p.getSquare().getRow() == 0 || p.getSquare().getRow() == 10 || p.getSquare().getColumn () == 0 || p.getSquare().getRow() == 10 )) {
    			bonusGold += 5;
    		}	
    		
    		yellowScore += p.getScore();
    		
    	}
    	
    	if(side == Side.YELLOW) {
    		
    		return yellowScore + bonusGold - whiteScore;
    		
    	}else
    		return whiteScore - yellowScore - bonusGold;
    	
    }

    private boolean flagshipFreePath(Piece p) {

    	for(int r = 0; r <= p.getSquare().getRow(); r++) {
    		
    		if(r != 0)
    			if(p.getSquare().getAdjacentSquare(-r, 0) != null)	
    				if(p.getSquare().getAdjacentSquare(-r, 0).getPiece() != null) 
    					return false;
    		
    	}
    	
    	for(int r = p.getSquare().getRow(); r <= Board.SIZE; r++) {
    		
    		if(r != p.getSquare().getRow())
    			if(p.getSquare().getAdjacentSquare(r - p.getSquare().getRow(), 0) != null)	
    				if(p.getSquare().getAdjacentSquare(r - p.getSquare().getRow(), 0).getPiece() != null)
    					return false;
    		
    	}
    	
    	for(int c = 0; c <= p.getSquare().getColumn(); c++) {
    		
    		if(c != 0)
    			if(p.getSquare().getAdjacentSquare(0, -c) != null)	
    				if(p.getSquare().getAdjacentSquare(0, -c).getPiece() != null) 
    					return false;
    		
    	}
    	
    	for(int c = p.getSquare().getColumn(); c <= Board.SIZE; c++) {
    	
    			if(c != p.getSquare().getColumn())
    				if(p.getSquare().getAdjacentSquare(0, c - p.getSquare().getColumn()) != null)
    					if(p.getSquare().getAdjacentSquare(0, c - p.getSquare().getColumn()).getPiece() != null) 
    						return false;
    		
    	}
    
    	return true;
    	
	}

	public int flagshipInDanger(Piece flagship) {
    	    	
    	int piecesAround = 0;
    	
    	for(int h = -1; h <= 1; h += 2){
    		
    		for(int v = -1; v <= 1; v += 2) {
    			
    			if(flagship.getSquare().getAdjacentSquare(h, v) != null)
    				if(flagship.getSquare().getAdjacentSquare(h, v).getPiece() != null)
    					if(flagship.getSquare().getAdjacentSquare(h, v).getPiece().getSide() == Side.WHITE)
    						piecesAround++;
    			
    			}
    		
    	}
    
		return piecesAround;
    	
    }
    
	public int flagshipCovered(Piece flagship) {
		
    	
    	int piecesAround = 0;
    	
    	for(int h = -1; h <= 1; h += 2){
    		
    		for(int v = -1; v <= 1; v += 2) {
    			
    			if(flagship.getSquare().getAdjacentSquare(h, v) != null)
    				if(flagship.getSquare().getAdjacentSquare(h, v).getPiece() != null)
    					if(flagship.getSquare().getAdjacentSquare(h, v).getPiece().getSide() == Side.YELLOW)
    						piecesAround++;
    			
    			}
    		
    	}
    
		return piecesAround;	
	
	}
    
}
