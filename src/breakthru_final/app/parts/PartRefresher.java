package breakthru_final.app.parts;

public class PartRefresher {

	private static GUI breakthruPart;
	
	public static void refresh() {
		
		if(breakthruPart == null)
	
			return;	
		
	 	breakthruPart.setFocus(null);
		
	}

	public static void setBreakthruPart(GUI breakthruPart){
	
		PartRefresher.breakthruPart = breakthruPart;
		
	}
	
}
