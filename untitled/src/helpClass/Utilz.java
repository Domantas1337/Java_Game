package helpClass;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Utilz {

	public static BufferedImage getBufferedImage(String fileName){
		BufferedImage img = null;
		InputStream is = LoadSave.class.getClassLoader().getResourceAsStream(fileName);
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

}
