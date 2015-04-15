package edu.asu.FourRowSolitaire.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

import edu.asu.FourRowSolitaire.FourRowSolitaire;

/**
 * Utility methods for the SikuliX API.
 * 
 * @author Nelson Tran (nttran9)
 */
public class SikuliXUtil
{
	/**
	 * <p>
	 * Converts an image located at /testing/<b>fileName</b> to a 
	 * {@link org.sikuli.script.Pattern} object.
	 * </p>
	 * 
	 * <p>
	 * <b>Note:</b> I believe that it would make more sense to use the 
	 * {@link org.sikuli.script.ImagePath#add(String)} method to make the image
	 * resource directory inside the FourRowSolitaire fat jar available by 
	 * adding the directory to the image paths that SikuliX sees. However, 
	 * that doesn't seem to work, so this is the alternative solution that I 
	 * will settle with.
	 * 
	 * @param fileName The file name of the image including the extension
	 * @return A {@link org.sikuli.script.Pattern} object of the image
	 * @see {@link http://stackoverflow.com/a/13605411}
	 */
	public static Pattern getImagePattern(String fileName)
	{
		// create Image object of image file
		final String FILE_PATH = "testing/";
		URL imageURL = FourRowSolitaire.class.getClassLoader().getResource(FILE_PATH + fileName);
		System.out.println(imageURL);
		Image image = new ImageIcon(imageURL).getImage();
		System.out.println(image);
		
		// create BufferedImage with transparency
		BufferedImage bufferedImage = new BufferedImage(
			image.getWidth(null), 
			image.getHeight(null), 
			BufferedImage.TYPE_INT_ARGB
		);
		
		// draw the image onto the buffered image
		Graphics2D bGr = bufferedImage.createGraphics();
		bGr.drawImage(image, 0, 0, null);
		bGr.dispose();
		
		// create Pattern from BufferedImage
		return new Pattern(bufferedImage);
	}
	
	/**
	 * Click on the image passed to this method by parameter. This method will
	 * block until the image is found on the screen and clicked on. The image
	 * to be clicked on should be in <b>/edu/asu/FourRowSolitaire/images/</b> in
	 * the .jar file.
	 * 
	 * @param fileName The file name (or the relative path) of the image
	 * @throws FindFailed The image could not be found on the screen
	 * @throws InterruptedException Sleep is interrupted
	 */
	public static void clickImage(String fileName) throws FindFailed, InterruptedException
	{
		App app = new App("Four Row Solitaire");
		Region windowRegion = app.window();
		
		Pattern image = getImagePattern(fileName);
		if (windowRegion.exists(image) != null) {
			windowRegion.click(image);
		}
		
		Thread.sleep(200);
	}
}
