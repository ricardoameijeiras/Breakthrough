package core.figures;

import java.util.List;

import core.figures.pieces.Piece;

import java.util.ArrayList;

public class PiecesColor {
	
    private List<Piece> alivePieces;
    private List<Piece> deadPieces;

    public PiecesColor(){
    	
        alivePieces = new ArrayList<Piece>();
        deadPieces = new ArrayList<Piece>();
        
    }

    public List<Piece> getAliveSticks() {
    	
        return alivePieces;
        
    }

   public List<Piece> getDeadSticks() {
	   
        return deadPieces;
        
    }

    public void addPiece(Piece piece){
    	
        alivePieces.add(piece);
        
    }

    public void eatPiece(Piece piece){
    	
        alivePieces.remove(piece);
        deadPieces.add(piece);
        
    }

	public void revivePiece(Piece piece) {

		deadPieces.remove(piece);
		alivePieces.add(piece);
		
	}

}
