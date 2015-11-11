//Creates the main frame

import javax.swing.*;
import java.awt.*;

public class GameFrame {
    public static JFrame frame = new JFrame();
    public GameFrame(){
        frame.add(new Game(), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Jungle Run");
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args){
        new GameFrame();
    }
}