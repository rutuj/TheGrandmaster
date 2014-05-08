
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
        {"P","P","P"," ","P","P","P","P"},
        {"R","N","B","Q","K","B","N","R"}};
    
    

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
                    case "R": list+=possibleR(i,"R");
                        break;
                    case "N": list+=possibleN(i);
                        break;
                    case "B": list+=possibleB(i,"B");
                        break;
                    case "Q": list+=possibleR(i,"Q");
                              list+=possibleB(i,"Q");
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
        public static String possibleR(int i, String piece) {
            String list = "", oldPiece;
            int ctr = 1, x = i/8, y = i%8;
            for(int j = -1;j <= 1; j+=2) {
                try {
                while(" ".equals(chessboard[x][y + ctr*j])) {
                        oldPiece = chessboard[x][y + ctr*j];
                        chessboard[x][y + ctr*j] = piece;
                        if(true) {
                            list = list + x + y + x + (y + ctr*j) + oldPiece;                          
                        }
                        chessboard[x][y + ctr*j] = oldPiece;
                        chessboard[x][y] = piece;
                        ctr++;
                    
                }
                if(Character.isLowerCase(chessboard[x][y + ctr*j].charAt(0))) {
                        oldPiece = chessboard[x][y + ctr*j];
                        chessboard[x][y + ctr*j] = piece;
                        if(true) {
                            list = list + x + y + x + (y + ctr*j) + oldPiece;                          
                        }
                        chessboard[x][y + ctr*j] = oldPiece;
                        chessboard[x][y] = piece;                    
                    
                }
                
        }
                catch(Exception e) {}
                ctr = 1;
                try {
                    while(" ".equals(chessboard[x + j*ctr][y])) {
                        oldPiece = chessboard[x + j*ctr][y];
                        chessboard[x + j*ctr][y] = piece;
                        if(true) {
                            list = list + x + y + (x + j*ctr) + y + oldPiece;
                        }
                        chessboard[x + j*ctr][y] = oldPiece;
                        chessboard[x][y] = piece;
                        ctr++;
                        }
                    if(Character.isLowerCase(chessboard[x + j*ctr][y].charAt(0)))
                    {
                        oldPiece = chessboard[x + j*ctr][y];
                        chessboard[x + j*ctr][y] = piece;
                        if(true) {
                            list = list + x + y + (x + j*ctr) + y + oldPiece;
                        }
                        chessboard[x + j*ctr][y] = oldPiece;
                        chessboard[x][y] = piece;                        
                    }
                }
                catch(Exception e) {}
                ctr = 1;
            }
            return list;
        } //end of possibleR function
        
        
        public static String possibleN(int i) {
            String list = "", oldPiece;
            int x = i/8, y = i%8;
            for(int j = -2;j <= 2;j+=4) {
                for(int k = -1;k <= 1;k+=2) {
                    try {
                if(Character.isLowerCase(chessboard[x + k][y + j].charAt(0)) || " ".equals(chessboard[x + k][y + j])) {
                    oldPiece = chessboard[x + k][y + j];
                    chessboard[x + k][y + j] = "N";
                    if(true) {
                        list = list + x + y + (x + k) + (y + j) + oldPiece;
                    }
                    chessboard[x + k][y + j] = oldPiece;
                    chessboard[x][y] = "N";
                            }
                    }
                catch(Exception e) {}
                try {
                if(Character.isLowerCase(chessboard[x + j][y + k].charAt(0)) || " ".equals(chessboard[x + j][y + k])) {
                    oldPiece = chessboard[x + j][y + k];
                    chessboard[x + j][y + k] = "N";
                    if(true) {
                        list = list + x + y + (x + j) + (y + k) + oldPiece;
                    }
                    chessboard[x + j][y + k] = oldPiece;
                    chessboard[x][y] = "N";
                }
                    }
                catch(Exception e) {}
                
                    
                }
            }
                
            
            return list;
        }
        public static String possibleB(int i,String piece) {
            String list = "", oldPiece;
            int ctr = 1, x = i/8, y = i%8;
            for(int j = -1;j <= 1; j+=2) {
                try {
                while(" ".equals(chessboard[x + ctr*j][y + ctr*j])) {
                        oldPiece = chessboard[x + ctr*j][y + ctr*j];
                        chessboard[x + ctr*j][y + ctr*j] = piece;
                        if(true) {
                            list = list + x + y + (x + ctr*j) + (y + ctr*j) + oldPiece;                          
                        }
                        chessboard[x + ctr*j][y + ctr*j] = oldPiece;
                        chessboard[x][y] = piece;
                        ctr++;
                    
                }
                if(Character.isLowerCase(chessboard[x + ctr*j][y + ctr*j].charAt(0))) {
                        oldPiece = chessboard[x + ctr*j][y + ctr*j];
                        chessboard[x + ctr*j][y + ctr*j] = piece;
                        if(true) {
                            list = list + x + y + (x + ctr*j) + (y + ctr*j) + oldPiece;                          
                        }
                        chessboard[x + ctr*j][y + ctr*j] = oldPiece;
                        chessboard[x][y] = piece;                    
                    
                }
                
        }
                catch(Exception e) {}
                ctr = 1;
                try {
                while(" ".equals(chessboard[x + ctr*j][y - ctr*j])) {
                        oldPiece = chessboard[x + ctr*j][y - ctr*j];
                        chessboard[x + ctr*j][y - ctr*j] = piece;
                        if(true) {
                            list = list + x + y + (x + ctr*j) + (y - ctr*j) + oldPiece;                          
                        }
                        chessboard[x + ctr*j][y - ctr*j] = oldPiece;
                        chessboard[x][y] = piece;
                        ctr++;
                    
                }
                if(Character.isLowerCase(chessboard[x + ctr*j][y - ctr*j].charAt(0))) {
                        oldPiece = chessboard[x + ctr*j][y - ctr*j];
                        chessboard[x + ctr*j][y - ctr*j] = piece;
                        if(true) {
                            list = list + x + y + (x + ctr*j) + (y - ctr*j) + oldPiece;                          
                        }
                        chessboard[x + ctr*j][y - ctr*j] = oldPiece;
                        chessboard[x][y] = piece;                    
                    
                }
                
        }
                catch(Exception e) {}
                ctr = 1;
            }
            return list;
        } //end of possibleB function
            
           
        
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
        } //end of possibleK function

     /*private static boolean kingSafe() {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return 1;
        }*/
        
    }
    

