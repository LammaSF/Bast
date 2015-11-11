/**
 * Class for Creating Enemies of type Space Craft
 */

import javax.swing.*;
import java.awt.*;

public class Spear implements IEnemy {

    private Image enemyImg;
    private int x_pos;
    private int y_pos;
    private boolean alive;

    private int currentFireDelay = 0;

    public Spear(int x, int y){
        this.x_pos = x;
        this.y_pos = y;
        this.alive = true;
        ImageIcon img = new ImageIcon("images/enemy.png");
        enemyImg      = img.getImage();
    }

    public void moveRight(){
        x_pos += 1;
    }

    public void moveLeft(){
        x_pos -= 1;
    }

    public void moveForward(int moveSpeed){
        y_pos += moveSpeed;
    }

    public void setAlive(boolean is){
        this.alive = is;
    }

    public boolean isAlive() {
        return alive;
    }

    public Image getImage(){
        return enemyImg;
    }

    public void setImage(String name){
        ImageIcon img = new ImageIcon(name);
        enemyImg      = img.getImage();
    }

    public int getX(){
        return this.x_pos;
    }

    public int getY(){
        return this.y_pos;
    }


    /**
     * Method that creates an "invisible" rectangle around the enemy, so
     * we can check for collisions
     */
    public Rectangle getBounds(){
        return new Rectangle(x_pos, y_pos, enemyImg.getWidth(null), enemyImg.getHeight(null));
    }
}