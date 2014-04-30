
package chessengine;
import javax.swing.*;
public class ChessEngine {
    
    static String chessboard[][] = {
        {"r","n","b","q","k","b","n","r"},
        {"p","p","p","p","p","p","p","p"},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {"P","P","P","P","P","P","P","P"},
        {"R","N","B","Q","K","B","N","R"}};
    
    

    public static void main(String[] args) {
        JFrame f=new JFrame("The Grandmaster");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UserInterface ui = new UserInterface();
        f.add(ui);
        f.setSize(500,500);
        f.setVisible(true);
    }
    
}

