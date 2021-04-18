package core.figures;

import core.figures.pieces.Piece;

public class Square {
	
    private final Board board;
    private final int row;
    private int column;
    private Piece piece;
    private boolean legal;
    private int score;

    public Square(Board board, int row, int column, int score){
    	
        this.board = board;
        this.row = row;
        this.column = column;
        this.score = score;
        
    }

    public int getScore() {
		return score;
	}
    
    public void setScore(int score) {
		this.score = score;
	}

	public Piece getPiece() {
        return piece;
    }
    
    public void setPiece(Piece piece){
        this.piece = piece;
    }

    public Square getAdjacentSquare(int horizontal, int vertical) {
    	return board.getSquare(vertical + row, horizontal + column);
    }

	public void setLegal(boolean legal) {
		// TODO Auto-generated method stub
		this.legal = legal;
	}
    
	public boolean isLegal() {
		
		return legal;
		
	}
	
	@Override
	public String toString() {
		
		return String.format("%s%s", (char) ('A' + column), (1 + row));
		
	}
	
	public int getColumn() {
		
		return column;
		
	}
	
	public int getRow() {
		
		return row;
		
	}
	
}
