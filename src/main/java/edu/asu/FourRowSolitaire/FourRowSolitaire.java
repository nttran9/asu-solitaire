/*
 * This file is a part of Four Row Solitaire
 *
 * Copyright (C) 2010 by Matt Stephen
 *
 * Four Row Solitaire is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Four Row Solitaire is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FourRowSolitaire.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.asu.FourRowSolitaire;

import java.awt.Desktop;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;

import javax.swing.*;

/**
 * Class: FourRowSolitaire
 * 
 * Description: The FourRowSolitaire class adds a menu to the SolitaireBoard Frame.
 * 
 * @author Matt Stephen
 */
public class FourRowSolitaire extends SolitaireBoard implements ActionListener
{
    public static final String version = ".40";
    
    private JMenuBar menuBar = new JMenuBar();

    private JMenu game = new JMenu("Game");
    private JMenu helpMenu = new JMenu("Help");

    private JMenuItem newGame = new JMenuItem("New Game");
    private JMenuItem undo = new JMenuItem("Undo Last Move");
    private JMenuItem hint = new JMenuItem("Hint");
    private JMenuItem statistics = new JMenuItem("Statistics");
    private JMenuItem options = new JMenuItem("Options");
    private JMenuItem appearance = new JMenuItem("Change Appearance");
    private JMenuItem exit = new JMenuItem("Exit");

    private JMenuItem help = new JMenuItem("View Help");
    private JMenuItem about = new JMenuItem("About Game");
    private JMenuItem checkUpdate = new JMenuItem("Check for Updates");

    public FourRowSolitaire()
    {
        //checkForUpdate();

        game.add(newGame);
        game.addSeparator();
        game.add(undo);
        game.add(hint);
        game.addSeparator();
        game.add(statistics);
        game.add(options);
        game.add(appearance);
        game.addSeparator();
        game.add(exit);

        newGame.addActionListener(this);
        undo.addActionListener(this);
        hint.addActionListener(this);
        statistics.addActionListener(this);
        options.addActionListener(this);
        appearance.addActionListener(this);
        exit.addActionListener(this);

        helpMenu.add(help);
        helpMenu.add(about);
        helpMenu.add(checkUpdate);

        help.addActionListener(this);
        about.addActionListener(this);
        checkUpdate.addActionListener(this);

        menuBar.add(game);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);

        newGame.setMnemonic('N');
        newGame.setAccelerator(KeyStroke.getKeyStroke("F2"));
        undo.setMnemonic('u');
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK));
        hint.setMnemonic('h');
        hint.setAccelerator(KeyStroke.getKeyStroke('h'));
        statistics.setMnemonic('s');
        statistics.setAccelerator(KeyStroke.getKeyStroke("F4"));
        options.setMnemonic('o');
        options.setAccelerator(KeyStroke.getKeyStroke("F5"));
        appearance.setMnemonic('a');
        appearance.setAccelerator(KeyStroke.getKeyStroke("F7"));
        exit.setMnemonic('x');

        help.setMnemonic('v');
        help.setAccelerator(KeyStroke.getKeyStroke("F1"));
        about.setMnemonic('a');

        loadData();
    }

    private void checkForUpdate()
    {
        try
        {
            URL url = new URL("http://www.utdallas.edu/~mas073100/FourRowSolitaire/version.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine = in.readLine();
            in.close();

            //Sometimes reads an html document if disconnected from internet
            if(!inputLine.contains("DOCTYPE") && !version.equals(inputLine))
            {
                JOptionPane.showMessageDialog(this, "There is a newer version available, " +
                        "click help > check for updates to get it.");
            }
        }
        catch(Exception ex){}
    }

    private void loadData()
    {
        String fileLocation = System.getProperty("user.home") + System.getProperty("file.separator");
        int count = 0, temp = 0, correctedStatistics = -2;
        int newDrawCount = 1, timerStatus = 0, deckNumber = 3, backgroundNumber = 2;
        int saved = 0, winAnimation = 0, winSounds = 0, drawCount = 1, deckThroughs = 1;
        int difficulty = 2, newDifficulty = 2;
        
        try
        {
            File file = new File(fileLocation + "frs-statistics.dat");
            file.createNewFile();
            DataInputStream input = new DataInputStream(new FileInputStream(file));

            if(input.available() > 0)
            {
                correctedStatistics = input.readInt();
                count++;
            }

            if(correctedStatistics == -2)
            {
                //No statistics file found
            }
            else if(correctedStatistics == -1)
            {
                //Statistics file is formatted to the new style of saving statistics
                while((input.available() > 0) && count < 49)
                {
                    temp = input.readInt();
                    switch(count)
                    {
                        case 37: drawCount = temp; break;
                        case 38: newDrawCount = temp; break;
                        case 39: deckNumber = temp; break;
                        case 40: backgroundNumber = temp; break;
                        case 41: timerStatus = temp; break;
                        case 42: winAnimation = temp; break;
                        case 43: winSounds = temp; break;
                        case 44: deckThroughs = temp; break;
                        case 45: difficulty = temp; break;
                        case 46: newDifficulty = temp; break;
                        case 47: saved = temp; break;

                        default: ; break;
                    }

                    count++;
                }
            }
            else
            {
                //Statistics file is formatted to the old style of saving statistics
                while((input.available() > 0) && count < 14)
                {
                    temp = input.readInt();
                    switch(count)
                    {
                        case 5: newDrawCount = temp; break;
                        case 6: timerStatus = temp; break;
                        case 7: deckNumber = temp; break;
                        case 8: backgroundNumber = temp; break;
                        case 9: saved = temp; break;
                        case 10: winAnimation = temp; break;
                        case 11: winSounds = temp; break;
                        case 12: drawCount = temp; break;
                        case 13: deckThroughs = temp; break;

                        default: ; break;
                    }

                    count++;
                }
            }

            input.close();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }

        super.setDeckNumber(deckNumber);
        super.setBackgroundNumber(backgroundNumber);
        super.setTimerStatus(timerStatus);
        super.setNewDrawCount(newDrawCount);
        super.setWinAnimationStatus(winAnimation);
        super.setWinSoundsStatus(winSounds);
        super.setDrawCount(drawCount);
        super.setDeckThroughs(deckThroughs);
        super.setDifficulty(difficulty);
        super.setNewDifficulty(newDifficulty);

        if(saved == 1)
        {
            LinkedList<Integer> cards = new LinkedList<Integer>();
            
            try
            {
                File file = new File(fileLocation + "frs-savedgame.dat");
                file.createNewFile();
                DataInputStream input = new DataInputStream(new FileInputStream(file));

                while(input.available() > 0)
                {
                    cards.add(input.readInt());
                }
                
                if(cards.size() == 66)
                {
                    super.createBoard(cards);
                }
                else
                {
                    System.err.println("Problem Loading Saved Game (More or Less Than 52 Cards Stored)... Starting New Game");
                    super.createBoard(null);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                System.err.println("Problem Loading Saved Game (Unknown Error)... Starting New Game");
                super.createBoard(null);
            }
        }
        else
        {
            super.createBoard(null);
        }

        if(correctedStatistics != -1)
        {
            super.saveOptions();
        }
    }

    public static void main(String[] args)
    {
        new FourRowSolitaire();
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == newGame)
        {
            int check = JOptionPane.showConfirmDialog(this, "Quitting the current game will result in a loss.\n" +
                    "Do you wish to continue?", "Continue?", JOptionPane.OK_CANCEL_OPTION);

            if(check == JOptionPane.YES_OPTION)
            {
                super.newGame(0);
                recordGame(GAME_LOST);
            }
            else
            {
                //If player wants to continue game
                return;
            }
        }
        else if(e.getSource() == undo)
        {
            super.undoMove();
        }
        else if(e.getSource() == hint)
        {
            super.getHint();
        }
        else if(e.getSource() == statistics)
        {
            String fileLocation = System.getProperty("user.home") + System.getProperty("file.separator") +
                    "frs-statistics.dat";

            int count = 0, temp = 0;
            int bestTime1e = 0, bestTime1m = 0, bestTime1h = 0,
                bestTime3e = 0, bestTime3m = 0, bestTime3h = 0;
            int gamesPlayed1e = 0, gamesWon1e = 0, winStreak1e = 0, lossStreak1e = 0,
            currentStreak1e = 0;
            int gamesPlayed1m = 0, gamesWon1m = 0, winStreak1m = 0, lossStreak1m = 0,
            currentStreak1m = 0;
            int gamesPlayed1h = 0, gamesWon1h = 0, winStreak1h = 0, lossStreak1h = 0,
            currentStreak1h = 0;
            int gamesPlayed3e = 0, gamesWon3e = 0, winStreak3e = 0, lossStreak3e = 0,
            currentStreak3e = 0;
            int gamesPlayed3m = 0, gamesWon3m = 0, winStreak3m = 0, lossStreak3m = 0,
            currentStreak3m = 0;
            int gamesPlayed3h = 0, gamesWon3h = 0, winStreak3h = 0, lossStreak3h = 0,
            currentStreak3h = 0;

            try
            {
                File file = new File(fileLocation);
                file.createNewFile();
                DataInputStream input = new DataInputStream(new FileInputStream(file));

                while((input.available() > 0) && count < 37)
                {
                    temp = input.readInt();
                    switch(count)
                    {
                        //case 0 is the format checker
                        case 1: gamesPlayed1e = temp; break;
                        case 2: gamesWon1e = temp; break;
                        case 3: winStreak1e = temp; break;
                        case 4: lossStreak1e = temp; break;
                        case 5: currentStreak1e = temp; break;
                        case 6: bestTime1e = temp; break;
    
                        case 7: gamesPlayed1m = temp; break;
                        case 8: gamesWon1m = temp; break;
                        case 9: winStreak1m = temp; break;
                        case 10: lossStreak1m = temp; break;
                        case 11: currentStreak1m = temp; break;
                        case 12: bestTime1m = temp; break;
    
                        case 13: gamesPlayed1h = temp; break;
                        case 14: gamesWon1h = temp; break;
                        case 15: winStreak1h = temp; break;
                        case 16: lossStreak1h = temp; break;
                        case 17: currentStreak1h = temp; break;
                        case 18: bestTime1h = temp; break;
    
                        case 19: gamesPlayed3e = temp; break;
                        case 20: gamesWon3e = temp; break;
                        case 21: winStreak3e = temp; break;
                        case 22: lossStreak3e = temp; break;
                        case 23: currentStreak3e = temp; break;
                        case 24: bestTime3e = temp; break;
    
                        case 25: gamesPlayed3m = temp; break;
                        case 26: gamesWon3m = temp; break;
                        case 27: winStreak3m = temp; break;
                        case 28: lossStreak3m = temp; break;
                        case 29: currentStreak3m = temp; break;
                        case 30: bestTime3m = temp; break;
    
                        case 31: gamesPlayed3h = temp; break;
                        case 32: gamesWon3h = temp; break;
                        case 33: winStreak3h = temp; break;
                        case 34: lossStreak3h = temp; break;
                        case 35: currentStreak3h = temp; break;
                        case 36: bestTime3h = temp; break;

                        default: ; break;
                    }

                    count++;
                }

                input.close();
            }
            catch(Exception ex)
            {
                System.out.println(ex);
            }

            int winPercentage1e;
            int winPercentage1m;
            int winPercentage1h;
            int winPercentage3e;
            int winPercentage3m;
            int winPercentage3h;

            if(gamesPlayed1e == 0)
            {
                winPercentage1e = 0;
            }
            else
            {
                winPercentage1e = 100 * gamesWon1e / gamesPlayed1e;
            }

            if(gamesPlayed1m == 0)
            {
                winPercentage1m = 0;
            }
            else
            {
                winPercentage1m = 100 * gamesWon1m / gamesPlayed1m;
            }

            if(gamesPlayed1h == 0)
            {
                winPercentage1h = 0;
            }
            else
            {
                winPercentage1h = 100 * gamesWon1h / gamesPlayed1h;
            }

            if(gamesPlayed3e == 0)
            {
                winPercentage3e = 0;
            }
            else
            {
                winPercentage3e = 100 * gamesWon3e / gamesPlayed3e;
            }

            if(gamesPlayed3m == 0)
            {
                winPercentage3m = 0;
            }
            else
            {
                winPercentage3m = 100 * gamesWon3m / gamesPlayed3m;
            }

            if(gamesPlayed3h == 0)
            {
                winPercentage3h = 0;
            }
            else
            {
                winPercentage3h = 100 * gamesWon3h / gamesPlayed3h;
            }

            JTextArea display = new JTextArea();
            display.append("One-Card Draw (Easy)\t\tThree-Card Draw (Easy)\n" +
            "Games Played: " + gamesPlayed1e + "\t\tGames Played: " + gamesPlayed3e +
            "\nGames Won: " + gamesWon1e + "\t\t\tGames Won: " + gamesWon3e + "\n" +
            "Win Percentage: " + winPercentage1e + "%\t\tWin Percentage: " + winPercentage3e +
            "%\n\nBest Streak: " + winStreak1e + "\t\t\tBest Streak: " + winStreak3e + "\n" +
            "Worst Streak: " + lossStreak1e + "\t\tWorst Streak: " + lossStreak3e + "\n" +
            "Current Streak: " + currentStreak1e + "\t\tCurrent Streak: " + currentStreak3e + "\n" +
            "Best Time: " + (bestTime1e / 60) + " m, " + (bestTime1e % 60) + " s" + "\t\t" + 
            "Best Time: " + (bestTime3e / 60) + " m, " + (bestTime3e % 60) + " s" + "\n" + 
            "===================================================================\n");

            display.append("One-Card Draw (Medium)\t\tThree-Card Draw (Medium)\n" +
            "Games Played: " + gamesPlayed1m + "\t\tGames Played: " + gamesPlayed3m +
            "\nGames Won: " + gamesWon1m + "\t\t\tGames Won: " + gamesWon3m + "\n" +
            "Win Percentage: " + winPercentage1m + "%\t\tWin Percentage: " + winPercentage3m +
            "%\n\nBest Streak: " + winStreak1m + "\t\t\tBest Streak: " + winStreak3m + "\n" +
            "Worst Streak: " + lossStreak1m + "\t\tWorst Streak: " + lossStreak3m + "\n" +
            "Current Streak: " + currentStreak1m + "\t\tCurrent Streak: " + currentStreak3m + "\n" +
            "Best Time: " + (bestTime1m / 60) + " m, " + (bestTime1m % 60) + " s" + "\t\t" + 
            "Best Time: " + (bestTime3m / 60) + " m, " + (bestTime3m % 60) + " s" + "\n" + 
            "===================================================================\n");

            display.append("One-Card Draw (Hard)\t\tThree-Card Draw (Hard)\n" +
            "Games Played: " + gamesPlayed1h + "\t\tGames Played: " + gamesPlayed3h +
            "\nGames Won: " + gamesWon1h + "\t\t\tGames Won: " + gamesWon3h + "\n" +
            "Win Percentage: " + winPercentage1h + "%\t\tWin Percentage: " + winPercentage3h +
            "%\n\nBest Streak: " + winStreak1h + "\t\t\tBest Streak: " + winStreak3h + "\n" +
            "Worst Streak: " + lossStreak1h + "\t\tWorst Streak: " + lossStreak3h + "\n" +
            "Current Streak: " + currentStreak1h + "\t\tCurrent Streak: " + currentStreak3h + "\n" +
            "Best Time: " + (bestTime1h / 60) + " m, " + (bestTime1h % 60) + " s" + "\t\t" + 
            "Best Time: " + (bestTime3h / 60) + " m, " + (bestTime3h % 60) + " s" + "\n");
            

            display.setOpaque(false);
            display.setBorder(null);
            display.setFont(UIManager.getFont("Label.font"));

            Object[] buttons = {"Close", "Reset"};
            int check = JOptionPane.showOptionDialog(this, display, "Statistics", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, buttons, buttons[0]);

            if(check == 1)
            {
                //Reset stats
                super.resetStats();
            }
            else
            {
                //Close
                return;
            }
        }
        else if(e.getSource() == options)
        {
            ChangeOptions co = new ChangeOptions(this, super.getNewDrawCount(), super.getTimerNextGameStatus(), super.getWinAnimationStatus(), super.getWinSoundsStatus(), super.getNewDifficulty());
            int drawCount = co.getDrawCount();
            int timerStatus = co.getTimer();
            int animationStatus = co.getAnimation();
            int soundsStatus = co.getSounds();
            int difficulty = co.getDifficulty();

            if(drawCount != -1)
            {
                super.setNewDrawCount(drawCount);
                super.setTimerStatus(timerStatus);
                super.setWinAnimationStatus(animationStatus);
                super.setWinSoundsStatus(soundsStatus);
                super.setNewDifficulty(difficulty);
            }

            super.saveOptions();
        }
        else if(e.getSource() == appearance)
        {
            ChangeAppearance ca = new ChangeAppearance(this, super.getDeckNumber(), super.getBackgroundNumber());
            int deckNumber = ca.getDeckNumber();
            int backgroundNumber = ca.getBackgroundNumber();
            ca.dispose();

            if(deckNumber != -1)
            {
                super.setAppearance(deckNumber, backgroundNumber);
            }

            super.saveOptions();
        }
        else if(e.getSource() == exit)
        {
            super.wl.windowClosing(null);
        }

        else if(e.getSource() == help)
        {
            JOptionPane.showMessageDialog(this, "About Four Row Solitaire:\n\n Four Row Solitaire is a game that combines"
                    + " FreeCell and Four Row Solitaire.\nThe board contains four empty cells, with columns respectively "
                    + "right under them.\nThere are also four other cells on the right that are organized by suit and must "
                    + "be stacked in ascending order.\nOnce, all cards have been stacked into the Ace Piles located at the top "
                    + "right of the screen, the game is won.\n\nObjective:\n"
                    + "To stack all cards according to their suit in ascending order in the Ace Piles.\n\nValid Moves:\n-Card can be stacked "
                    + "in descending order but they must be of alternating color.\n-If a column is empty, It can and must"
                    + " be filled by a King.\n-An ordered stack can also be moved, but the highest card in the stack must "
                    + "one less than the card it's stacking up against and it should be of opposite color.\n\nMisc.:\n-"
                    + "There is a deck on the bottom right that must be used, but viewing it is limited.\n-A timer can be "
                    + "used.",
                    "Help!", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource() == about)
        {
            JOptionPane.showMessageDialog(this, "Four Row Solitaire was created and programmed by Matt Stephen.\n" +
                    "\nYou can modify this code in accordance with GPL v3.0.\n" +
                    "\nTo check if there is a newer version, go to fourrow.sourceforge.net.", 
                    "About Game", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getSource() == checkUpdate)
        {
            try
            {
                Desktop.getDesktop().browse(new URI("https://sourceforge.net/projects/fourrow"));
            }
            catch(Exception ex)
            {
                System.out.println(ex);
            }
        }
    }
}