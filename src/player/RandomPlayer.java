package player;

import java.util.List;
import java.util.Random;

import core.figures.Board;
import core.figures.Move;
import core.figures.Side;
import core.figures.Square;
import core.figures.pieces.Piece;

public class RandomPlayer extends BreakthruPlayer{

	private Random random;
	
	public RandomPlayer(Board board, Side side) {
		
		super(board, side);
		random = new Random();
		
	}

	public Move decideMove(){
		
		List<Piece> pieces = board.getPieceColor(side).getAliveSticks();
		List<Square> legalMoves;
		Piece randomPiece;
		
		do {
			
			int randomPieceindex = random.nextInt(pieces.size());
			randomPiece = pieces.get(randomPieceindex);
			legalMoves = randomPiece.checkLegalMoves(0);
		
		}while(legalMoves.size() == 0);
		
		int randomMoveIndex = random.nextInt(legalMoves.size());
		Square targetSquare = legalMoves.get(randomMoveIndex);
		
		return new Move(randomPiece.getSquare(), targetSquare, targetSquare.getPiece());
	
	}
	
}
