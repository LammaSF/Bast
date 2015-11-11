import java.awt.*;

public class Menu {
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
            g.setFont(new Font("Arial", Font.BOLD, 26));
            g.setColor(new Color(34, 155, 0));

            g.drawString("START", buttonX + 80, startButtonY + padding);
            g.drawString("SETTINGS", buttonX + 60, settingsButtonY + padding);
            g.drawString("EXIT", buttonX + 95, exitButtonY + padding);

        } else {  // draw SETTINGS MENU
            if (!isBackButtonClicked){

                isMainMenuActive = false;

                //back Background
                g.drawImage(background, 0, 0, null);
                g.setColor(new Color(253, 251, 255));

                int width = screenWidth - 1400;

                //left side
                g.fillRect(800, 0, width, screenHeight);

                if (!isBackHovered){
                    g.setColor(new Color(253, 251, 255));
                } else {
                    g.setColor(new Color(112, 4, 0, 223));
                }
                g.fillRect(backButton.x, backButton.y, backButton.width, backButton.height);
                // draw Controls
                g.setColor(new Color(0, 0, 0));
                g.setFont(new Font("Arial", Font.BOLD, 26));
                // letters position
                g.drawString("Controls", 900, 100);

                // player one
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Move Forward:    ^", 900, 150);
                g.drawString("Move Back:         v", 900, 180);
                g.drawString("Move Right:         >", 900, 210);
                g.drawString("Move Left:           <", 900, 240);

                // player two

                g.drawString("Pause Game / Back to Main menu: ESC", 900, 400);
                g.drawString("Save Game: F1", 900, 430);
                g.drawString("Load Last Saved Game: F12", 900, 460);

                // back to main menu button
                g.drawRect(backButton.x - 2, backButton.y - 1, backButton.width + 2, backButton.height + 2);
                g.setFont(new Font("Arial", Font.BOLD, 26));
                g.drawString("BACK", buttonX + 85, backButtonY + padding);

            }
