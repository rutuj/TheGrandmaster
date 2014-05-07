
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
        /* JFrame f=new JFrame("The Grandmaster");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UserInterface ui = new UserInterface();
        f.add(ui);
        f.setSize(500,500);
        f.setVisible(true); */
    }
        
        public static String posibleMoves() {
            String list="";
            for (int i=0; i<64; i++) {
                switch (chessboard[i/8][i%8]) {
                    case "P": list+=possibleP(i);
                        break;
                    case "R": list+=possibleR(i);
                        break;
                    case "N": list+=possibleN(i);
                        break;
                    case "B": list+=possibleB(i);
                        break;
                    case "Q": list+=possibleQ(i);
                        break;
                    case "K": list+=possibleK(i);
                        break;
            }
        }
        return list;//x1,y1,x2,yx,captured piece
    }
        public static String possibleP(int i) {
            String list = "";
            return list;
        }
        public static String possibleR(int i) {
            String list = "";
            return list;
        }
        public static String possibleN(int i) {
            String list = "";
            return list;
        }
        public static String possibleB(int i) {
            String list = "";
            return list;
        }
        public static String possibleQ(int i) {
            String list = "";
            return list;
        }
        public static String possibleK(int i) {
            String list = "";
            return list;
        }
    }
    

