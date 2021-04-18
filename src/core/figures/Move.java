package core.figures;

import core.figures.pieces.Piece;

public class Move {
	
	private Square initialSquare;
	private Square targetSquare;
	private Piece targetPiece;
	private boolean action, motion; 
	private int moveScore;
	
	public Move(Square initialSquare, Square targetSquare, Piece targetPiece) {
	
		this.initialSquare = initialSquare;
		this.targetSquare = targetSquare;
		this.targetPiece = targetPiece;
		
	}
	
	public Square getInitialSquare() {
		return initialSquare;
	}
	public void setInitialSquare(Square initialSquare) {
		this.initialSquare = initialSquare;
	}
	public Square getTargetSquare() {
		return targetSquare;
	}
	public void setTargetSquare(Square targetSquare) {
		this.targetSquare = targetSquare;
	}
	public Piece getTargetPiece() {
		return targetPiece;
	}
	public void setTargetPiece(Piece targetPiece) {
		this.targetPiece = targetPiece;
	}
	public String toString() {
		return initialSquare.getPiece() + ": " + initialSquare + " -> " + targetSquare;
	}

	public void setAction(boolean action) {
		this.action = action;
	}

	public void setMotion(boolean motion) {
		this.motion = motion;
	}

	public boolean isAction() {
		return action;
	}

	public void setMoveScore(int moveScore) {
		this.moveScore = moveScore;
	}

	public int getMoveScore() {
		return moveScore;
	}

	public boolean isMotion() {
		return motion;
	}
	
}
