package core.img;


import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import core.figures.Side;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHandling {

	private static Path path;
	
	static {
		
		try {
			
			URL location = ImageHandling.class.getProtectionDomain().getCodeSource().getLocation();
			//System.out.println(location);
			path = Paths.get(location.toURI().resolve("../src/imgs"));
			
		}catch (Exception e) {
		
			e.printStackTrace();
			
		}
		
	}
	
	public static BufferedImage getImg(String pieceName, Side side) {
		
		String imgName;
		
		if(side == Side.WHITE) {
			
			imgName = "white" + pieceName + ".png";
			
		}else
			imgName =  "yellow" + pieceName + ".png";
		
		
		String pathImg =  path.resolve(imgName).toString();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(pathImg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return img;
		
	}
	
	public static BufferedImage getBlankIcon() {
		
		String pathImg =  path.resolve("blank.png").toString();
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File(pathImg));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return img;
		
	}
	
}
