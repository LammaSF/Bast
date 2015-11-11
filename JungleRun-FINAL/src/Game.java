/**
 * This Game class is the main class of the game and it is responsible for handling animations, collisions, drawing,
 * taking mouse and keyboard input.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements ActionListener {

    private Player player;
    private Image background;
    private Image backgroundMenu;
    private Timer time;

    private int backgroundY;
    private int bgMotion;
    private int bgMotionSec;
    private double playerOneScore;

    /* GAME VARIABLES */
    private int escapeCounter = 0;

    /* GAME BOOLEAN data */
    private boolean isGameLost;
    private boolean isGameStarted = false;

    // get the screen dimensions
    private int screenWidth  = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    private DataConfig cfg = new DataConfig();

    /* add all the enemies and their weapons into ArrayList */
    public ArrayList<Spear> enemies = new ArrayList<Spear>();

    Random randGen = new Random();

    /* GAME CONSTANTS */
    private final int ENEMY_SPEED        = 2;
    private final int BUTTON_PADDING_TOP = 35;
    private final int ENEMIES_SPAWN_Y    = 6000;

    /* MENU BUTTONS AND VALUES*/
    GameMenu menu = new GameMenu();

    private boolean isStartButtonHovered;
    private boolean isExitButtonHovered;
    private boolean isSettingsButtonHovered;
    private boolean isBackButtonHovered;

    /**
     * Class for registering key presses
     */
    private class MyActionListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e){
            player.keyPressed(e);

            if (e.getKeyCode() == KeyEvent.VK_F1){
                saveConfig();
            }
            if (e.getKeyCode() == KeyEvent.VK_F12){
                loadConfig();
            }

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE && escapeCounter < 1){
                isGameStarted = false;
                ++escapeCounter;
            }
            else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && escapeCounter > 0){
                isGameStarted = true;
                --escapeCounter;
            }
            else if (e.getKeyCode() == KeyEvent.VK_Y) {

                GameFrame.frame.dispose();
                // if(!player.isAlive()) {
                JFrame frame = new JFrame();
                frame.add(new Game(), BorderLayout.CENTER);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("Jungle Run");
                frame.setExtendedState(Frame.MAXIMIZED_BOTH);
                frame.setUndecorated(true);
                frame.setAlwaysOnTop(true);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
                // }
            }

        }

        @Override
        public void keyReleased(KeyEvent e){
            player.keyReleased(e);

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE && escapeCounter > 0){
                isGameStarted = false;
            }
            else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && escapeCounter < 1){
                isGameStarted = true;
            }
        }
    }

    /**
     * Class for registering mouse events
     */
    private class MouseEvents extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e){
            int mouseX = e.getX();
            int mouseY = e.getY();

            if (mouseX > menu.startButton().x && mouseX < menu.startButton().x + menu.startButton().width &&
                    mouseY > menu.startButton().y && mouseY < menu.startButton().y + menu.startButton().height &&
                    !isGameStarted && menu.isMainMenuActive()){

                isStartButtonHovered = true;

            } else {
                isStartButtonHovered = false;
            }

            if (mouseX > menu.settingsButton().x && mouseX < menu.settingsButton().x + menu.settingsButton().width &&
                    mouseY > menu.settingsButton().y && mouseY < menu.settingsButton().y + menu.settingsButton().height &&
                    !isGameStarted && menu.isMainMenuActive()){

                isSettingsButtonHovered = true;

            } else {
                isSettingsButtonHovered = false;
            }

            if (mouseX > menu.exitButton().x && mouseX < menu.exitButton().x + menu.exitButton().width &&
                    mouseY > menu.exitButton().y && mouseY < menu.exitButton().y + menu.exitButton().height &&
                    !isGameStarted && menu.isMainMenuActive()){

                isExitButtonHovered = true;

            } else {
                isExitButtonHovered = false;
            }

            if (mouseX > menu.backButton().x && mouseX < menu.backButton().x + menu.backButton().width &&
                    mouseY > menu.backButton().y && mouseY < menu.backButton().y + menu.backButton().height &&
                    !isGameStarted){

                isBackButtonHovered = true;

            } else {
                isBackButtonHovered = false;
            }
        }

        @Override
        public void mousePressed(MouseEvent e){
            int mouseX = e.getX();
            int mouseY = e.getY();

            if (mouseX > menu.startButton().x && mouseX <  menu.startButton().x +  menu.startButton().width &&
                    mouseY >  menu.startButton().y && mouseY <  menu.startButton().y +  menu.startButton().height &&
                    !isGameStarted && menu.isMainMenuActive()){

                isGameStarted = true;
            }
            if (mouseX > menu.settingsButton().x && mouseX < menu.settingsButton().x + menu.settingsButton().width &&
                    mouseY > menu.settingsButton().y && mouseY < menu.settingsButton().y + menu.settingsButton().height &&
                    !isGameStarted && menu.isMainMenuActive()){

                menu.setSettingsOpened(true);
            }
            if (mouseX > menu.backButton().x && mouseX < menu.backButton().x + menu.backButton().width &&
                    mouseY > menu.backButton().y && mouseY < menu.backButton().y + menu.backButton().height &&
                    !isGameStarted){

                menu.setBackClicked(true);
            }
            if (mouseX > menu.exitButton().x && mouseX < menu.exitButton().x + menu.exitButton().width &&
                    mouseY > menu.exitButton().y && mouseY < menu.exitButton().y + menu.exitButton().height &&
                    !isGameStarted && menu.isMainMenuActive()){

                System.exit(0);
            }
        }
    }

    public Game(){

        player    = new Player();
 
        /* spawn enemies */
        for (int i = 0; i < 40 ; ++i){
            int x_position = 50 + randGen.nextInt(screenWidth - 100);
            int y_position = -randGen.nextInt(ENEMIES_SPAWN_Y);

            enemies.add(new Spear(x_position, y_position));
        }
 
        /* add key and mouse listeners */
        addKeyListener(new MyActionListener());
        addMouseListener(new MouseEvents());
        addMouseMotionListener(new MouseEvents());

        ImageIcon img = new ImageIcon("images/background.jpg");
        background    = img.getImage();

        // set the background for the game menu for different resolutions
        String menuBackground = "images/gameMenuBG.jpg";
        ImageIcon bgImg = new ImageIcon(menuBackground);
        backgroundMenu  = bgImg.getImage();
        bgMotion = background.getHeight(null);
        bgMotionSec = 0;
        backgroundY = 0;

        playerOneScore = 0;

        isGameLost = false;

        setFocusable(true);

        time = new Timer(2, this);
        time.start();
    }

    /**
     * Method for calculating background speed
     */
    public void backgroundMovement(){

        bgMotion    -= 1;
        bgMotionSec += 1;
        backgroundY += 1;
    }

    /**
     * This function handles collision detection
     * for all aspects of the game
     */
    public void detectCollisions(){

        Rectangle playerBounds = player.getBounds();

        ArrayList<Rectangle> enemiesBounds = new ArrayList<Rectangle>();
 
        /* HANDLE COLLISION DETECTION FOR ALL THE ENEMIES */
        for (int i = 0; i < enemies.size(); ++i){
            enemiesBounds.add(enemies.get(i).getBounds());
        }
 
 
        /* check if player collides with enemies */
        for (int i = 0; i < enemies.size(); ++i){
            if (playerBounds.intersects(enemies.get(i).getBounds()) &&
                    enemies.get(i).isAlive()){

                player.setAlive(false);
            }
        }

        if (!player.isAlive()){
            isGameLost = true;
        }
    }

    /**
     * This method takes all the actions that are performed in the game
     * and repeats them every x ms, where x is the value in Timer(x, this);
     * In my case x = 2.
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if (isGameStarted){

            if (player.isAlive()){
                if (player.isKeyLeft() && player.getX() > 20){
                    player.moveLeft();
                }
                if (player.isKeyRight() && player.getX() < (screenWidth - 116)){
                    player.moveRight();
                }
                if (player.isKeyUp() && player.getY() > 20){
                    player.moveForward();
                }
                if (player.isKeyDown() && player.getY() < (screenHeight - 118)){
                    player.moveBack();
                }
                if (player.isKeyLeft()){
                    player.setImage("images/DuckLeft.gif");
                }
                else if (player.isKeyRight()){
                    player.setImage("images/DuckRight.gif");
                }
                else  if (player.isKeyDown()){
                    player.setImage("images/DuckDown.gif");
                }
                else {
                    player.setImage("images/DuckUp.gif");
                }
            }

            backgroundMovement();

            if (player.isAlive()){
                detectCollisions();
            }

            if (!player.isAlive()){
                player.moveDeadPlayer();
            }

            moveEnemies();
            repaint();
        }
    }
    /**
     * Method for controlling enemies' movement
     */
    public void moveEnemies(){
        Random rand = new Random();

        for (int i = 0; i < enemies.size(); ++i){
            Spear enemy = enemies.get(i);

            if (enemies.get(i).isAlive()){
                enemies.get(i).moveForward(ENEMY_SPEED);
                if (i % 2 == 0 && enemies.get(i).getY() > 0){
                    if (enemies.get(i).getX() > 0 && enemies.get(i).getX() < 400){
                        enemies.get(i).moveRight();
                    }
                    if (enemies.get(i).getX() > 500 && enemies.get(i).getX() < screenWidth - 60){
                        enemies.get(i).moveLeft();
                    }
                }
            }

            if (!enemy.isAlive() || enemy.getY() > screenHeight){
                int x_position = 50 + rand.nextInt(screenWidth - 100);
                int y_position = -rand.nextInt(ENEMIES_SPAWN_Y);
                enemy = new Spear(x_position, y_position);

                enemies.set(i, enemy);
            }

        }
    }

    private static Random rand = new Random();

    /**
     * Main method for drawing the whole game:
     * Game Menu
     * Background
     * Enemies
     * Player...
     */
    @Override
    public void paint(Graphics g){

        super.paint(g);
        Graphics2D graphics2D = (Graphics2D)g;

        if (!isGameStarted){

            drawGameMenu(graphics2D);

        } else {
            drawBackground(graphics2D);
            drawEnemies(graphics2D);
            drawPlayers(graphics2D);
            drawStats(graphics2D);

            if (isGameLost) {
                graphics2D.setFont(new Font("SanSerif", Font.BOLD, 60));
                graphics2D.setColor(Color.orange);
                graphics2D.drawString("YOU LOST", (screenWidth - 200) / 2, screenHeight / 2);

                graphics2D.drawString("Do you want to play again? Press Y or Esc", (screenWidth - 1000) / 2, screenHeight / 2 + 100);

            }
        }
    }

    /**
     * Method for drawing the Game Menu
     */
    public void drawGameMenu(Graphics g){
        menu.drawGameMenu(g, backgroundMenu, BUTTON_PADDING_TOP,
                isStartButtonHovered, isExitButtonHovered,
                isSettingsButtonHovered, isBackButtonHovered);

        repaint();
    }

    /**
     * Method for calculating and drawing the scrolling background
     */
    public void drawBackground(Graphics g){
        if ((backgroundY - 0) % (background.getHeight(null) * 2) == 0){
            bgMotionSec = 0;
        } else if ((backgroundY - background.getHeight(null)) % (background.getHeight(null) * 2) == 0){
            bgMotion = (background.getHeight(null) * 2);
        }
        g.drawImage(background, 0, background.getHeight(null) - bgMotion, null);
        if (backgroundY > 0){
            g.drawImage(background, 0, -(background.getHeight(null) - bgMotionSec), null);
        }
    }
    /**
     * Method for drawing enemies in the game
     */
    public void drawEnemies(Graphics g){
        int count=0;
        for (int i = 0; i < enemies.size(); ++i){
            if (enemies.get(i).isAlive()){
                g.drawImage(enemies.get(i).getImage(), enemies.get(i).getX(), enemies.get(i).getY(), null);
            }
            if (i==7 && player.isAlive()) {
                //if(i==count)
                playerOneScore += 0.1d;
            }
            count++;
        }
    }

    /**
     * Method for drawing players in the game
     */
    public void drawPlayers(Graphics g){
        if (player.isAlive()){
            g.drawImage(player.getImage(), player.getX(), player.getY(), null);
        } else {
            player.setImage("images/explosion.gif");
            g.drawImage(player.getImage(), player.getX(), player.getY(), null);
        }
    }

    /**
     * Method for drawing game statistics
     */
    public void drawStats(Graphics g){
        g.setFont(new Font("SanSerif", Font.BOLD, 20));
        g.setColor(Color.GREEN);
        g.drawString("PLAYER", screenWidth - 200, 30);
        g.drawString("POINTS: " +(int) playerOneScore, screenWidth - 200, 70);
    }

    /**
     * Method for saving the current game progress
     */
    public void saveConfig(){
        ObjectOutputStream out;
        /* save the data */
        cfg.player_one_x_pos  = player.getX();
        cfg.player_one_y_pos  = player.getY();
        cfg.player_one_score  = (int) playerOneScore;

        cfg.is_player_one_alive = player.isAlive();
        cfg.background_position   = backgroundY;
        cfg.background_motion     = bgMotion;
        cfg.background_motion_sec = bgMotionSec;

        cfg.is_game_lost = false;

        try{
            out = new ObjectOutputStream(new FileOutputStream("Game.cfg"));
            out.writeObject(cfg);
            out.flush();
            out.close();
        }
        catch(IOException e){}
    }

    /**
     * Method for loading the last saved game progress
     */
    public void loadConfig(){
        ObjectInputStream in;
        try{
            in = new ObjectInputStream(new FileInputStream("Game.cfg"));
            try{
                cfg = (DataConfig)in.readObject();
                /* load the data */
                player.setX(cfg.player_one_x_pos);
                player.setY(cfg.player_one_y_pos);
                playerOneScore = cfg.player_one_score;
                player.setAlive(cfg.is_player_one_alive);
                backgroundY = cfg.background_position;
                bgMotion    = cfg.background_motion;
                bgMotionSec = cfg.background_motion_sec;
                isGameLost = cfg.is_game_lost;
            }
            catch(ClassNotFoundException e){}
            catch(IOException e){}
            in.close();
        }
        catch(IOException e){}
    }
}