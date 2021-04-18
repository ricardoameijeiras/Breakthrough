package breakthru_final.app.parts;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import breakthru_final.app.room.BreakthruRoom;
import core.figures.Board;
import core.figures.Move;
import core.figures.Side;
import core.figures.Square;
import core.figures.pieces.Piece;
import core.img.ImageHandling;
import player.BreakthruPlayer;

public class GUI extends JFrame{

	private static Piece selectedPiece;
	private static BreakthruPlayer firstPlayer, secondPlayer;
	private static Side side;
	private static Board board;
	private static boolean secondMove1st;
	public static boolean secondMove2nd;
	private static String pieceType;
	private static boolean eatenPiece;
	public static boolean secondMoveLegal;
	private static Piece previousPiece;
	private static Piece typeEatenPiece;
	private static Square initialSquare;
	private static Square targetSquare;
	private static BreakthruRoom breakthruRoom;
	protected static JButton[][] squares;
	private Container contents;
	private int counter = 1;
	private boolean enemyTurn = false;
	private static String columns = "ABCDEFGHIJK";
	private static boolean sideChanged;
	private static File logs;
	
	public GUI(){
		
		breakthruRoom = new BreakthruRoom();
		squares = new JButton[Board.SIZE + 1][Board.SIZE + 1];
		side = GUI.getBreakthruRoom().getSide();
		board = GUI.getBreakthruRoom().getBoard();
		firstPlayer = GUI.getBreakthruRoom().getPlayer(side);
		secondPlayer = GUI.getBreakthruRoom().getPlayer(side.opposite());
		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		logs = new File("logs.txt");
		
		if(logs.delete()){
		    logs.createNewFile();
		}
		
		GUI gui = new GUI();
		gui.createComposite();
		
	}

	public void createComposite() {
		
		contents = getContentPane();
		contents.setLayout(new GridLayout(Board.SIZE + 1, Board.SIZE + 1));
		
		ButtonHandler buttonHandler = new ButtonHandler();
		
		for (int r = Board.SIZE - 1; r >= -1; r--) {
			
			for(int c = 0; c < Board.SIZE; c++){
				
				if(r != -1) {
					squares[r][c] = new JButton();
						
					contents.add(squares[r][c]);
					squares[r][c].addActionListener(buttonHandler);
				}else
					contents.add(new JLabel("      " + columns.charAt(c)));

			}
			if(r != -1)
				contents.add(new JLabel("        " + Integer.toString(r + 1)));
			
		}
		
		setSize(600, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocus(null);
		PartRefresher.setBreakthruPart(this);
		
	}
	
	private class ButtonHandler implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			resetLegalSquares();
			PartRefresher.refresh();
			Object source = e.getSource();
			
			for (int r = Board.SIZE - 1; r >= 0; r--) {
				
				for(int c = 0; c < Board.SIZE; c++){
					
					if(source == squares[r][c]) {
						if(breakthruRoom.getBoard().getSquare(r, c).getPiece() == null && counter == 1) {
							targetSquare = null;
							
						}else{
							
							if(enemyTurn) {
								enemyTurn = false;
								
							}else if(counter == 1 && !enemyTurn) {
								resetLegalSquares();
								initialSquare = breakthruRoom.getBoard().getSquare(r, c);
								
								setFocus(initialSquare);
								targetSquare = null;
								counter++;
								
							}else {
							
							targetSquare = breakthruRoom.getBoard().getSquare(r, c);
							counter = 1;
							
							}
						}
					}
				}
			}
			
			if(initialSquare != null && targetSquare != null)
				processMovement(initialSquare, targetSquare);
			
		}

				
	}
	
	public void setFocus(Square initialSquare) {
		
		List<Square> legalMoves = null;
		
		for (int r = Board.SIZE - 1; r >= 0; r--) {
			
			for(int c = 0; c < Board.SIZE; c++){
				
				squares[r][c].setBackground(Color.BLACK);
				Piece piece = breakthruRoom.getBoard().getSquare(r, c).getPiece();
				
				if(piece == null) {
					
					squares[r][c].setIcon(new ImageIcon(ImageHandling.getBlankIcon()));
				
				}else
					squares[r][c].setIcon(new ImageIcon(piece.getImage()));
				
				}
			
		}
		
		if(initialSquare != null && (side == GUI.getBreakthruRoom().getSide() || GUI.getBreakthruRoom().isFirstTurnAI())) {
			
			Piece p = initialSquare.getPiece();
			
			if(!GUI.getBreakthruRoom().isFirstTurnAI()) {
				
				legalMoves = p.checkLegalMoves(firstPlayer.counter_moves);
				
			}else {
				
				legalMoves = p.checkLegalMoves(secondPlayer.counter_moves);
		
			}
			
			for(Square sq : legalMoves) {
				
				for(int h = -1; h <= 1; h += 2){
		    		
		    		for(int v = -1; v <= 1; v += 2) {
		    			
		    			if(p == previousPiece && secondMove1st) { 
		    				 //don't display   					
		    			}else
		    				squares[sq.getRow()][sq.getColumn()].setBackground(Color.GREEN);
		    			
		    		}
		    	}
			}
		}
	}
		
	private void processMovement(Square initialSquare, Square targetSquare) {
		
		resetLegalSquares();
		Piece piece = initialSquare.getPiece();
		selectedPiece = piece;
		
		if(piece != null && piece.getSide() == GUI.getBreakthruRoom().getSide()) {
			
			for(Square square : piece.checkLegalMoves(firstPlayer.counter_moves)) {
				
				if(((previousPiece == selectedPiece) && secondMove1st)|| secondMove1st && selectedPiece.toString() == "F") {
   
    				square.setLegal(false);
    				
				}else
					square.setLegal(true);
				
				for(int h = -1; h <= 1; h += 2){
		    		
		    		for(int v = -1; v <= 1; v += 2) {
		    			
		    			if(selectedPiece.getSquare().getAdjacentSquare(h, v)!= null && selectedPiece.getSquare().getAdjacentSquare(h, v).getPiece() != null && selectedPiece.getSquare().getAdjacentSquare(h, v).getPiece().getSide() != selectedPiece.getSide() && secondMove1st)
		    				selectedPiece.getSquare().getAdjacentSquare(h, v).setLegal(false);
		    			
		    		}
		    					
		    	}
		    		
			}		
			
		}
		
		movementAction(initialSquare, targetSquare);
		
	}
	
	private void movementAction(Square initialSquare, Square targetSquare) {
		
		if((side == GUI.getBreakthruRoom().getSide() && !GUI.getBreakthruRoom().isFirstTurnAI()) || (side == GUI.getBreakthruRoom().getSide().opposite() && GUI.getBreakthruRoom().isFirstTurnAI())){
		
			if(targetSquare.isLegal()){
				
				Piece targetPiece = targetSquare.getPiece();
				Move move = new Move(initialSquare, targetSquare, targetPiece);
				
				typeEatenPiece = move.getTargetPiece();
				eatenPiece = firstPlayer.isPieceEaten(move);
				previousPiece = selectedPiece;
				pieceType = firstPlayer.makeMove(move);
				PartRefresher.refresh();
				System.out.println("Movement made " + initialSquare + " => " + targetSquare);
				
				 try {
					 PrintWriter out = new PrintWriter(new FileWriter(logs, true));
					 out.append("Movement made from side " + side.toString() + ": "  + initialSquare + " => " + targetSquare + '\n');
					 out.close();
				    
				    } catch (IOException e) {
				      e.printStackTrace();
				    }
				
				if(selectedPiece != null && selectedPiece.toString() == "F" && (targetSquare.getColumn() == 0 || targetSquare.getColumn() == 10 || targetSquare.getRow() == 0 || targetSquare.getRow() == 10))
					gameOver(Side.YELLOW);
					
				if(typeEatenPiece != null)
					if(typeEatenPiece.toString() == "F")
						gameOver(side);
				
				if(eatenPiece || secondMove1st) {
						
					secondMove1st = false;
					eatenPiece = false;
					side = side.opposite();
					enemyTurn = true;
									
					
				}else { 
					
					if(pieceType.equals("F")){
						
						secondMove1st = false;
						eatenPiece = false;
						enemyTurn = true;
						side = side.opposite();
						
					}else {
						
						enemyTurn = false;
						secondMove1st= true;
						if(side == Side.WHITE && (board.getPieceColor(side).getAliveSticks().size() == 1 || side == Side.YELLOW)) {
							side = side.opposite();
						}
						
						PartRefresher.refresh();
						
					}
					
				}
				
			}
			
			
			resetLegalSquares();
			
		}else if((side == GUI.getBreakthruRoom().getSide().opposite() &&  !GUI.getBreakthruRoom().isFirstTurnAI()) || (side == GUI.getBreakthruRoom().getSide() && GUI.getBreakthruRoom().isFirstTurnAI())) {
			PartRefresher.refresh();
			enemyTurn = false;
			Move move = secondPlayer.decideMove(secondPlayer.counter_moves);
		
			secondMoveLegal = true;
			
			if(move.getTargetSquare()!=null)
				if(move.getTargetSquare().isLegal()) {
					
					Piece enemyPiece = move.getInitialSquare().getPiece();
					secondPlayer.makeMove(move);
					PartRefresher.refresh();
					typeEatenPiece = move.getTargetPiece();
					System.out.println("Movement made " + move.getInitialSquare() + " => " + move.getTargetSquare());
					
					 try {
						 PrintWriter out = new PrintWriter(new FileWriter(logs, true));
						 out.append("Movement made from side " + side.toString() + ": " + move.getInitialSquare() + " => " + move.getTargetSquare() + '\n');
						 out.close();
					    
					    } catch (IOException e) {
					      e.printStackTrace();
					    }
					
					if(enemyPiece != null && enemyPiece.toString() == "F" && (move.getTargetSquare().getColumn() == 0 || move.getTargetSquare().getColumn() == 10 || move.getTargetSquare().getRow() == 0 || move.getTargetSquare().getRow() == 10))
						gameOver(side);	
					
					if(typeEatenPiece != null)
						if(typeEatenPiece.toString() == "F")
							gameOver(side);	
					
					if(secondPlayer.counter_moves == 0) {
						
						if(move.isAction() || enemyPiece.toString() == "F") {
							
							side = side.opposite();
							secondPlayer.counter_moves = 0;
							
						}else {
							
							secondPlayer.counter_moves++;
							BreakthruPlayer.previousPiece = move.getTargetSquare().getPiece();
							
							if(side == Side.WHITE && (board.getPieceColor(side).getAliveSticks().size() == 1 || side == Side.YELLOW)) {
								side = side.opposite();
							
							sideChanged = false;
							
							}
							
						}
						
					}else if(secondPlayer.counter_moves == 1) {
						
						secondPlayer.counter_moves++;
						
					}
				}
				
				if(secondPlayer.counter_moves == 2) {
					
					side = side.opposite();
					secondPlayer.counter_moves = 0;
					BreakthruPlayer.previousPiece = null;
					
				}
				
				PartRefresher.refresh();
				resetLegalSquares();
		
				if(!sideChanged) {
					movementAction(initialSquare, targetSquare);
				}
				
		}
				
	}
	

	private void gameOver(Side side) {
	
		    JButton show = new JButton("Game Over");
	        show.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                try {
						Thread.sleep(1000);
						System.exit(0);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        });
	    
	        JFrame frame = new JFrame(side.toString() + " side wins");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setLayout(new GridBagLayout());
	        frame.setPreferredSize(new Dimension(300, 400));
	        frame.add(show);
	        frame.pack();
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	        
	}

	private void resetLegalSquares(){
        
		for (int r = 0; r < Board.SIZE; r++){
			
            for (int c = 0; c < Board.SIZE; c++){
            	
                board.getSquare(r, c).setLegal(false);
            	
            }
            
        }
		
	}
	
	public static BreakthruRoom getBreakthruRoom(){
		
        return breakthruRoom;
        
    }
	
}
	

	
	
