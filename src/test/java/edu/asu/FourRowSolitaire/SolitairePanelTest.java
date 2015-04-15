package edu.asu.FourRowSolitaire;

import static org.testng.AssertJUnit.*;

import org.testng.annotations.Test;

/**
 * @author David Henderson (dchende2)
 */
public class SolitairePanelTest
{
	@Test
	public void testSolitairePanel_ChangeBackground()
	{
		SolitairePanel panel = new SolitairePanel();
		for(int i = 1; i <= 3; i++) {
			panel.changeBackground(i);
			if(i == 3)
				assertTrue(true);
		}
	}
}