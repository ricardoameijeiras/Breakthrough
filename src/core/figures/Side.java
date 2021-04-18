package core.figures;

public enum Side {
	
    WHITE,
    YELLOW;
	
	public Side opposite() {
		if(this == WHITE) {
			
			return YELLOW;
			
		}else
			return WHITE;
		
	}
	
}