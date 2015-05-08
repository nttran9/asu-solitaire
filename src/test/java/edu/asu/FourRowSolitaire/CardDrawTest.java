package edu.asu.FourRowSolitaire;

import static org.testng.AssertJUnit.*;

import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import edu.asu.FourRowSolitaire.util.SikuliXUtil;

/**
 * @author Nelson Tran (nttran9)
 */
public class CardDrawTest
{
	private App fourRowSolitaire;
	
	@BeforeClass
	public void setUp() throws InterruptedException
	{
		// start new instance of Four Row Solitaire
		Thread gameThread = new Thread(() -> { FourRowSolitaire.main(null); });
		gameThread.setDaemon(true);
		gameThread.start();
		
		Thread.sleep(2000);
		fourRowSolitaire = new App("Four Row Solitaire");
		fourRowSolitaire.focus();
		
		// change cardback image to default (for image recognition)
		fourRowSolitaire.window().type(Key.F7);
		fourRowSolitaire.window().type(" ");
		for (int i = 0; i < 3; ++i)
			fourRowSolitaire.window().type(Key.TAB);
		fourRowSolitaire.window().type(" ");
		for (int i = 0; i < 3; ++i)
			fourRowSolitaire.window().type(Key.TAB);
		fourRowSolitaire.window().type(" ");
	}
	
	/**
	 * Test will pass if clicking on the DealDeck deals one card to the discard
	 * pile after setting the option to deal one card.
	 * 
	 * @throws FindFailed
	 * @throws InterruptedException
	 */
	//@Test(groups = { "functional" })
	public void testCardDraw_OneCard() throws FindFailed, InterruptedException
	{
		SikuliXUtil.clickImage("gamemenu.PNG");
		SikuliXUtil.clickImage("optionsmenu.PNG");
		SikuliXUtil.clickImage("drawone.PNG");
		SikuliXUtil.clickImage("acceptoptions.PNG");
		fourRowSolitaire.window().type(Key.ENTER);
		fourRowSolitaire.window().type(Key.F2);
		SikuliXUtil.clickImage("cardback.PNG");
		
		Pattern image = SikuliXUtil.getImagePattern("onecard.PNG");
		assertTrue(fourRowSolitaire.window().exists(image) != null);
	}
	
	/**
	 * Test will pass if clicking on the DealDeck deals three card to the discard
	 * pile after setting the option to deal three card.
	 * 
	 * @throws FindFailed
	 * @throws InterruptedException
	 */
	//@Test(groups = { "functional" })
	public void testCardDraw_ThreeCards() throws FindFailed, InterruptedException
	{
		SikuliXUtil.clickImage("gamemenu.PNG");
		SikuliXUtil.clickImage("optionsmenu.PNG");
		SikuliXUtil.clickImage("drawthree.PNG");
		SikuliXUtil.clickImage("acceptoptions.PNG");
		fourRowSolitaire.window().type(Key.ENTER);
		fourRowSolitaire.window().type(Key.F2);
		SikuliXUtil.clickImage("cardback.PNG");
		
		Pattern image = SikuliXUtil.getImagePattern("onecard.PNG");
		assertTrue(fourRowSolitaire.window().exists(image) != null);
	}
}
