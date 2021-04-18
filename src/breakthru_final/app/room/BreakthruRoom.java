package breakthru_final.app.room;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import core.figures.Board;
import core.figures.PiecesColor;
import core.figures.Side;
import core.figures.pieces.Flagship;
import core.figures.pieces.Stick;
import player.AlphaBetaPlayer;
import player.BreakthruPlayer;

public class BreakthruRoom {

    private final Board board;
    private final Map<Side, BreakthruPlayer> players;
    private Side side;
    private boolean action;
    private boolean firstTurnAI;

    public BreakthruRoom(){
    	
        PiecesColor whitePieces = new PiecesColor();
        PiecesColor yellowPieces = new PiecesColor();
        this.side = side;
        this.board = new Board(whitePieces, yellowPieces);

        //Set up white pieces
        for(int i = 3; i < 8; i++){
            whitePieces.addPiece(new Stick(board.getSquare(i, 1), Side.WHITE));
            whitePieces.addPiece(new Stick(board.getSquare(i, 9), Side.WHITE));
            whitePieces.addPiece(new Stick(board.getSquare(1, i), Side.WHITE));
            whitePieces.addPiece(new Stick(board.getSquare(9, i), Side.WHITE));
        }

        //Set up yellow pieces
        yellowPieces.addPiece(new Flagship(board.getSquare(5, 5), Side.YELLOW));

        for(int j = 4; j < 7; j++){
            yellowPieces.addPiece(new Stick(board.getSquare(j, 3), Side.YELLOW));
            yellowPieces.addPiece(new Stick(board.getSquare(j, 7), Side.YELLOW));
            yellowPieces.addPiece(new Stick(board.getSquare(3, j), Side.YELLOW));
            yellowPieces.addPiece(new Stick(board.getSquare(7, j), Side.YELLOW));
        }
        
        players = new HashMap<Side, BreakthruPlayer>();
        
        sideSelection();
     
        players.put(getSide(), new BreakthruPlayer(board, getSide()));
        
        if(getSide() == Side.WHITE) {
        	
        	players.put(Side.YELLOW, new AlphaBetaPlayer(board, Side.YELLOW));
        	
        }else
        	players.put(Side.WHITE, new AlphaBetaPlayer(board, Side.WHITE));
        
    }
    

    private void sideSelection() {
    	
    	action = false;
    	
    	JFrame frame = new JFrame("Select your side");
        JPanel pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.LINE_AXIS));
        pane.setSize(300, 200);
        JButton button1 = new JButton("White side + Player First Turn ");
        JButton button2 = new JButton("Yellow side + Player First Turn");
        JButton button3 = new JButton("White side + AI First Turn");
        JButton button4 = new JButton("Yellow side + AI First Turn");

        
        button1.addActionListener(new ActionListener(){  
		  	public void actionPerformed(ActionEvent e){  
		  		
		        setSide(Side.WHITE);
		        firstTurnAI = false;
		        action = true;
		        
		       }  
		    });  
          
        button2.addActionListener(new ActionListener(){  
		   	public void actionPerformed(ActionEvent e){  
		   		setSide(Side.YELLOW);
		   		firstTurnAI = false;
		   		action = true;
		       }  
		    }); 
        
        button3.addActionListener(new ActionListener(){  
		   	public void actionPerformed(ActionEvent e){  
		   		setSide(Side.WHITE);
		   		firstTurnAI = true;
		   		action = true;
		       }  
		    }); 
        
        button4.addActionListener(new ActionListener(){  
		   	public void actionPerformed(ActionEvent e){  
		   		setSide(Side.YELLOW);
		   		firstTurnAI = true;
		   		action = true;
		       }  
		    }); 
          
        pane.add(button1);
      //  pane.add(Box.createHorizontalGlue());
        pane.add(button2);
        pane.add(Box.createVerticalGlue());
        pane.add(button3);
      //  pane.add(Box.createHorizontalGlue());
        pane.add(button4);
        
        frame.add(pane, BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(785, 300));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    	try {
    		
    		do {
				Thread.sleep(0);
    		}while(!action);
    		
    		frame.setVisible(false);
    		
		} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
    }

    public boolean isFirstTurnAI() {
    	
    	return firstTurnAI;
    	
    }
    
	public Board getBoard(){
    	
        return board;
        
    }
    
    public Side getSide() {
    	
    	return side;
    	
    }
    
    public BreakthruPlayer getPlayer(Side side) {
    	
    	return players.get(side);
    	
    }

	public void setSide(Side side) {
		
		this.side = side;
		
	}

}
