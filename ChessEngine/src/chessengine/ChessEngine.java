
package chessengine;
import javax.swing.*;
public class ChessEngine {
    
    static String chessboard[][] = {
        {"r","n","b","q","k","b","n","r"},
        {"p","p","p","p","p","p","p","p"},
        {" "," "," "," "," ","K"," "," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {"P","P","P","P","P","P","P","P"},
        {"R","N","B","Q"," ","B","N","R"}};
    
    

    public static void main(String[] args) {
        
        System.out.println(possibleMoves());
        /* JFrame f=new JFrame("The Grandmaster");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UserInterface ui = new UserInterface();
        f.add(ui);
        f.setSize(500,500);
        f.setVisible(true);
        1  9 17 25 33
        2 10 18 26 ....
        3 11 19 27
        4 12 20 28
        5 13 21 29
        6 14 22 30 
        7 15 23 31
        8 16 24 32
        
        */
    }
        
    /**
     *
     * @return
     */
    public static String possibleMoves() {
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
                    case "K": 
                        list+=possibleK(i);
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
            String list = "", oldPiece;
            int x=i/8, y = i%8;
            for(int j = 0;j <= 8;j++)
            {
                
                if(j!=4) {
                    try {
                    if (Character.isLowerCase(chessboard[x-1 + j/3][y-1 + j%3].charAt(0)) || " ".equals(chessboard[x-1 + j/3][y-1 + j%3])) {
                        
                        oldPiece = chessboard[x-1 + j/3][y-1 + j%3];
                        chessboard[x-1 + j/3][y-1 + j%3] = "K";
                        if(true) {
                            list = list + x + y + (x-1 + j/3) + (y-1 + j%3) + oldPiece;                          
                        }
                        chessboard[x-1 + j/3][y-1 + j%3] = oldPiece;
                        chessboard[x][y] = "K";
                        
                    } 
                    }
                    catch(Exception e) {
                    }
                    }
                        
            }
            return list;
        }

    /* private static boolean kingSafe() {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    
        }*/
        
    }
    

