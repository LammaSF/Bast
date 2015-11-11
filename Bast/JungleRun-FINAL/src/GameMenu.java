/**
 * Class that holds the values and calculations for drawing the game
 * menu. Also has method for drawing the whole menu.
 */

import javax.swing.*;
import java.awt.*;

public class GameMenu {

    // get the screen dimensions
    private int screenWidth  = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    private Image gameTitle;

    private int gameTitleX;
    private int gameTitleY;

    private boolean isSettingsOpened    = false;
    private boolean isBackButtonClicked = false;
    private boolean isMainMenuActive;

    private int buttonWidth  = 330;
    private int buttonHeight = 75;

    private int buttonX = (screenWidth / 2) - 150;

    private int startButtonY    = screenHeight - (screenHeight - ((45 * screenHeight) / 120));
    private int settingsButtonY = startButtonY + 175;
    private int exitButtonY     = settingsButtonY + 170;
    private int backButtonY     = screenHeight - 150;

    private Rectangle startButton    = new Rectangle(buttonX, startButtonY, buttonWidth, buttonHeight);
    private Rectangle settingsButton = new Rectangle(buttonX, settingsButtonY, buttonWidth, buttonHeight);
    private Rectangle exitButton     = new Rectangle(buttonX, exitButtonY, buttonWidth, buttonHeight);
    private Rectangle backButton     = new Rectangle(buttonX, backButtonY, buttonWidth, buttonHeight);

    public Rectangle startButton(){
        return startButton;
    }

    public Rectangle settingsButton(){
        return settingsButton;
    }

    public Rectangle exitButton(){
        return exitButton;
    }

    public Rectangle backButton(){
        return backButton;
    }

    public void setSettingsOpened(boolean is){
        isSettingsOpened = is;
    }

    public void setBackClicked(boolean is){
        isBackButtonClicked = is;
    }

    public boolean isMainMenuActive(){
        return isMainMenuActive;
    }

    public GameMenu(){
        ImageIcon gameImg = new ImageIcon("images/gameTitle.png");
        gameTitle = gameImg.getImage();

        gameTitleX = (screenWidth / 2) - (gameTitle.getWidth(null) / 2);
        gameTitleY = screenHeight - (screenHeight - ((10 * screenHeight) / 100));
    }

    /**
     * Method for drawing the game menu
     */
    public void drawGameMenu(Graphics g, Image background, int padding,
                             boolean isStartHovered, boolean isExitHovered,
                             boolean isSettingsHovered, boolean isBackHovered){
        if (!isSettingsOpened){

            isMainMenuActive = true;

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, screenWidth, screenHeight);
            g.drawImage(background, 0, 0, null);

            g.drawImage(gameTitle, gameTitleX, gameTitleY,
                    gameTitle.getWidth(null), gameTitle.getHeight(null), null);

            /* draw the frame around the buttons */
            g.setColor(new Color(48, 241, 0));
            g.drawRect(startButton.x - 2, startButton.y - 1, startButton.width + 2, startButton.height + 2);
            g.drawRect(settingsButton.x - 2, settingsButton.y - 1, settingsButton.width + 2, settingsButton.height + 2);
            g.drawRect(exitButton.x - 2, exitButton.y - 1, exitButton.width + 2, exitButton.height + 2);

            /* draw the buttons */
            if (!isStartHovered){
                g.setColor(new Color(1, 14, 22));
            } else {
                g.setColor(new Color(158, 0, 3, 255));
            }
            g.fillRect(startButton.x, startButton.y, startButton.width, startButton.height);

            if (!isSettingsHovered){
                g.setColor(new Color(1, 14, 22));
            } else {
                g.setColor(new Color(158, 0, 3, 223));
            }
            g.fillRect(settingsButton.x, settingsButton.y, settingsButton.width, settingsButton.height);
            if (!isExitHovered){
                g.setColor(new Color(1, 14, 22));
            } else {
                g.setColor(new Color(158, 0, 3, 223));
            }
            g.fillRect(exitButton.x, exitButton.y, exitButton.width, exitButton.height);

            /* draw names of buttons */
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.setColor(new Color(34, 155, 0));

            g.drawString("START", buttonX + 115, startButtonY + padding);
            g.drawString("SETTINGS", buttonX + 90, settingsButtonY + padding);
            g.drawString("EXIT", buttonX + 130, exitButtonY + padding);

        } else {  // draw SETTINGS MENU
            if (!isBackButtonClicked){

                isMainMenuActive = false;

                //back Background
                g.drawImage(background, 0, 0, null);
                g.setColor(new Color(118, 255, 53));

                int width = screenWidth - 1270;

                //left side
                // moves the whole frame
                g.fillRect(650, 450, width, screenHeight-500);

                if (!isBackHovered){
                    g.setColor(new Color(34, 155, 0));
                } else {
                    g.setColor(new Color(112, 4, 0, 223));
                }
                g.fillRect(backButton.x, backButton.y, backButton.width, backButton.height);
                // draw Controls
                g.setColor(new Color(0, 0, 0));
                g.setFont(new Font("Arial", Font.BOLD, 40));
                // letters position
                g.drawString("Controls", 900, 500);

                // player one
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Move Forward:    ^", 875, 550);
                g.drawString("Move Back:         v", 875, 580);
                g.drawString("Move Right:         >", 875, 610);
                g.drawString("Move Left:           <", 875, 640);


                g.drawString("Pause Game / Back to Main menu: ESC", 875, 700);

                // back to main menu button
                g.drawRect(backButton.x - 2, backButton.y - 1, backButton.width + 2, backButton.height + 2);
                g.setFont(new Font("Arial", Font.BOLD, 26));
                g.drawString("BACK", buttonX + 125, backButtonY + padding);

            }

            else { //draw back Main menu

                isMainMenuActive = true;

                g.setColor(Color.BLACK);
                g.fillRect(0, 0, screenWidth, screenHeight);

                g.drawImage(background, 0, 0, null);

                g.drawImage(gameTitle, gameTitleX, gameTitleY,
                        gameTitle.getWidth(null), gameTitle.getHeight(null), null);

                /* draw the frame around the buttons */
                g.setColor(new Color(1, 178, 241));
                g.drawRect(startButton.x - 2, startButton.y - 1, startButton.width + 2, startButton.height + 2);
                g.drawRect(settingsButton.x - 2, settingsButton.y - 1, settingsButton.width + 2, settingsButton.height + 2);
                g.drawRect(exitButton.x - 2, exitButton.y - 1, exitButton.width + 2, exitButton.height + 2);

                /* draw the buttons */
                g.setColor(new Color(1, 14, 22));
                g.fillRect(startButton.x, startButton.y, startButton.width, startButton.height);
                g.fillRect(settingsButton.x, settingsButton.y, settingsButton.width, settingsButton.height);
                g.fillRect(exitButton.x, exitButton.y, exitButton.width, exitButton.height);

                /* draw names of buttons */
                g.setFont(new Font("Arial", Font.BOLD, 26));
                g.setColor(new Color(1, 178, 241));

                g.drawString("START", buttonX + 80, startButtonY + padding);
                g.drawString("SETTINGS", buttonX + 60, settingsButtonY + padding);
                g.drawString("EXIT", buttonX + 95, exitButtonY + padding);

                isBackButtonClicked = false;
                isSettingsOpened    = false;
            }
        }
    }
}