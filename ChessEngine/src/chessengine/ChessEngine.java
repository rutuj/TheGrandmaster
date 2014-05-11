
package chessengine;
import javax.swing.*;
public class ChessEngine {
    
    static String chessboard[][] = {
        {"r","n","b","q","k","b","n","r"},
        {"p","p","p","p"," ","p","p","p"},
        {" "," "," "," "," ","Q"," "," "},
        {" "," "," "," ","p"," ","K"," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {"P","P","P","P","P","P","P","P"},
        {"R","N","B"," "," ","B","N","R"}};
    
    static int kingPosW;
    static int defaultDepth = 4;
    

    public static void main(String[] args) {
        while(!"K".equals(chessboard[kingPosW/8][kingPosW%8])) {kingPosW++;}
       // while(!"K".equals(chessboard[kingPosB/8][kingPosB%8])) {kingPosB++;}
        
        System.out.println(possibleMoves());
        /* JFrame f=new JFrame("The Grandmaster");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UserInterface ui = new UserInterface();
        f.add(ui);
        f.setSize(500,500);
        f.setVisible(true);
        1  2  3  4  5  6  7  8
        9  10 11 12 13 14 15 16
        17 18 19 20 21 22 23 24
        .....
        
        */
    }
        
        public static String alphaBeta(int depth, int beta, int alpha, String move, int player) {
        //return in the form of 1234b##########
        String list=possibleMoves();
        if (depth==0 || list.length()==0) {return move+(rating()*(player*2-1));}
        //sort later
        player=1-player;//either 1 or 0
        for (int i=0;i<list.length();i+=5) {
            makeMove(list.substring(i,i+5));
            flipBoard();
            String returnString=alphaBeta(depth-1, beta, alpha, list.substring(i,i+5), player);
            int value=Integer.valueOf(returnString.substring(5));
            flipBoard();
            undoMove(list.substring(i,i+5));
            if (player==0) {
                if (value<=beta) {beta=value; if (depth==defaultDepth) {move=returnString.substring(0,5);}}
            } else {
                if (value>alpha) {alpha=value; if (defaultDepth==depth) {move=returnString.substring(0,5);}}
            }
            if (alpha>=beta) {
                if (player==0) {return move+beta;} else {return move+alpha;}
            }
        }
        if (player==0) {return move+beta;} else {return move+alpha;}
    }
    public static void flipBoard() {
        
    }
    public static void makeMove(String move) {
        int x1 = Character.getNumericValue(move.charAt(0)) , x2 = Character.getNumericValue(move.charAt(1));
        int y1 = Character.getNumericValue(move.charAt(2)) , y2 = Character.getNumericValue(move.charAt(3));
        if(move.charAt(4)!='P') {
            chessboard[x2][y2] = Character.toString(move.charAt(4));
            chessboard[x1][y1] = " ";
        }
        else {
            chessboard[1][y1]=" ";
            chessboard[0][y2]=String.valueOf(move.charAt(3));
        }
    }
    
    public static void undoMove(String move) {
        int x1 = Character.getNumericValue(move.charAt(0)) , x2 = Character.getNumericValue(move.charAt(1));
        int y1 = Character.getNumericValue(move.charAt(2)) , y2 = Character.getNumericValue(move.charAt(3));
        if(move.charAt(4)!='P') {
            chessboard[x1][y1] = chessboard[x2][y2];
            chessboard[x2][y2] = Character.toString(move.charAt(4));
        }
        else {
           chessboard[1][y1]="P";
           chessboard[0][y2]=String.valueOf(move.charAt(2));
        }
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
        return list;
    }
        public static String possibleP(int i) {
        String list="", oldPiece;
        int x=i/8, y=i%8;
        for (int j=-1; j<=1; j+=2) {
            try {//kill black piece
                if (Character.isLowerCase(chessboard[x-1][y+j].charAt(0)) && i>=16) {
                    oldPiece=chessboard[x-1][y+j];
                    chessboard[x][y]=" ";
                    chessboard[x-1][y+j]="P";
                    if (kingSafe()) {
                        list=list+x+y+(x-1)+(y+j)+oldPiece;
                    }
                    chessboard[x][y]="P";
                    chessboard[x-1][y+j]=oldPiece;
                }
            } catch (Exception e) {}
            try {//kill + promotion
                if (Character.isLowerCase(chessboard[x-1][y+j].charAt(0)) && i<16) {
                    String[] temp={"Q","R","B","K"};
                    for (int k=0; k<4; k++) {
                        oldPiece=chessboard[x-1][y+j];
                        chessboard[x][y]=" ";
                        chessboard[x-1][y+j]=temp[k];
                        if (kingSafe()) {
                            //column1,column2,captured-piece,new-piece,P
                            list=list+y+(y+j)+oldPiece+temp[k]+"P";
                        }
                        chessboard[x][y]="P";
                        chessboard[x-1][y+j]=oldPiece;
                    }
                }
            } catch (Exception e) {}
        }
        try {//move 1
            if (" ".equals(chessboard[x-1][y]) && i>=16) {
                oldPiece=chessboard[x-1][y];
                chessboard[x][y]=" ";
                chessboard[x-1][y]="P";
                if (kingSafe()) {
                    list=list+x+y+(x-1)+y+oldPiece;
                }
                chessboard[x][y]="P";
                chessboard[x-1][y]=oldPiece;
            }
        } catch (Exception e) {}
        try {//only promotion
            if (" ".equals(chessboard[x-1][y]) && i<16) {
                String[] temp={"Q","R","B","K"};
                for (int k=0; k<4; k++) {
                    oldPiece=chessboard[y-1][x];
                    chessboard[x][y]=" ";
                    chessboard[x-1][y]=temp[k];
                    if (kingSafe()) {
                        //column1,column2,captured-piece,new-piece,P
                        list=list+y+y+oldPiece+temp[k]+"P";
                    }
                    chessboard[x][y]="P";
                    chessboard[x-1][y]=oldPiece;
                }
            }
        } catch (Exception e) {}
        try {//move 2
            if (" ".equals(chessboard[x-1][y]) && " ".equals(chessboard[x-2][y]) && i>=48) {
                oldPiece=chessboard[x-2][y];
                chessboard[y][x]=" ";
                chessboard[x-2][y]="P";
                if (kingSafe()) {
                    list=list+x+y+(x-2)+y+oldPiece;
                }
                chessboard[x][y]="P";
                chessboard[x-2][y]=oldPiece;
            }
        } catch (Exception e) {}
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
                        if(kingSafe()) {
                            list = list + x + y + x + (y + ctr*j) + oldPiece;                          
                        }
                        chessboard[x][y + ctr*j] = oldPiece;
                        chessboard[x][y] = piece;
                        ctr++;
                    
                }
                if(Character.isLowerCase(chessboard[x][y + ctr*j].charAt(0))) {
                        oldPiece = chessboard[x][y + ctr*j];
                        chessboard[x][y + ctr*j] = piece;
                        if(kingSafe()) {
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
                        if(kingSafe()) {
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
                        if(kingSafe()) {
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
                    if(kingSafe()) {
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
                    if(kingSafe()) {
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
        } //end of possibleN function
        public static String possibleB(int i,String piece) {
            String list = "", oldPiece;
            int ctr = 1, x = i/8, y = i%8;
            for(int j = -1;j <= 1; j+=2) {
                try {
                while(" ".equals(chessboard[x + ctr*j][y + ctr*j])) {
                        oldPiece = chessboard[x + ctr*j][y + ctr*j];
                        chessboard[x + ctr*j][y + ctr*j] = piece;
                        if(kingSafe()) {
                            list = list + x + y + (x + ctr*j) + (y + ctr*j) + oldPiece;                          
                        }
                        chessboard[x + ctr*j][y + ctr*j] = oldPiece;
                        chessboard[x][y] = piece;
                        ctr++;
                    
                }
                if(Character.isLowerCase(chessboard[x + ctr*j][y + ctr*j].charAt(0))) {
                        oldPiece = chessboard[x + ctr*j][y + ctr*j];
                        chessboard[x + ctr*j][y + ctr*j] = piece;
                        if(kingSafe()) {
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
                        if(kingSafe()) {
                            list = list + x + y + (x + ctr*j) + (y - ctr*j) + oldPiece;                          
                        }
                        chessboard[x + ctr*j][y - ctr*j] = oldPiece;
                        chessboard[x][y] = piece;
                        ctr++;
                    
                }
                if(Character.isLowerCase(chessboard[x + ctr*j][y - ctr*j].charAt(0))) {
                        oldPiece = chessboard[x + ctr*j][y - ctr*j];
                        chessboard[x + ctr*j][y - ctr*j] = piece;
                        if(kingSafe()) {
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
       /* public static String possibleQ(int i) {
            String list = "";            
            return list;
        }*/
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
                        if(kingSafe()) {
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

        public static boolean kingSafe() {
            //check for bishop and diagonal queen movement
            int ctr=1;
            for (int i=-1; i<=1; i+=2) {
                for (int j=-1; j<=1; j+=2) {
                    try {
                        while(" ".equals(chessboard[kingPosW/8+ctr*i][kingPosW%8+ctr*j])) {ctr++;}
                        if ("b".equals(chessboard[kingPosW/8+ctr*i][kingPosW%8+ctr*j]) ||
                                "q".equals(chessboard[kingPosW/8+ctr*i][kingPosW%8+ctr*j])) {
                            return false;
                        }
                    } catch (Exception e) {}
                    ctr=1;
                }
            }
            //check for rook and perpendicular queen movement
            for (int i=-1; i<=1; i+=2) {
                try {
                    while(" ".equals(chessboard[kingPosW/8][kingPosW%8+ctr*i])) {ctr++;}
                    if ("r".equals(chessboard[kingPosW/8][kingPosW%8+ctr*i]) ||
                            "q".equals(chessboard[kingPosW/8][kingPosW%8+ctr*i])) {
                        return false;
                    }
                } catch (Exception e) {}
                ctr=1;
                try {
                    while(" ".equals(chessboard[kingPosW/8+ctr*i][kingPosW%8])) {ctr++;}
                    if ("r".equals(chessboard[kingPosW/8+ctr*i][kingPosW%8]) ||
                            "q".equals(chessboard[kingPosW/8+ctr*i][kingPosW%8])) {
                        return false;
                    }
                } catch (Exception e) {}
                ctr=1;
            }
            //check for knight
            for (int i=-1; i<=1; i+=2) {
                for (int j=-1; j<=1; j+=2) {
                    try {
                        if ("k".equals(chessboard[kingPosW/8+i][kingPosW%8+j*2])) {
                            return false;
                        }
                    } catch (Exception e) {}
                    try {
                        if ("k".equals(chessboard[kingPosW/8+i*2][kingPosW%8+j])) {
                            return false;
                        }
                    } catch (Exception e) {}
                }
            }
            //check for pawn
            if (kingPosW>=16) { //check so that king doesn't lie in last 2 lines
                try {
                    if ("p".equals(chessboard[kingPosW/80-1][kingPosW%8-1])) {
                        return false;
                    }
                } catch (Exception e) {}
                try {
                    if ("p".equals(chessboard[kingPosW/80-1][kingPosW%8+1])) {
                        return false;
                    }
                } catch (Exception e) {}
                //king
                for (int i=-1; i<=1; i++) {
                    for (int j=-1; j<=1; j++) {
                        if (i!=0 || j!=0) {
                            try {
                                if ("a".equals(chessboard[kingPosW/8+i][kingPosW%8+j])) {
                                    return false;
                                }
                            } catch (Exception e) {}
                        }
                    }
                }
            }
            return true;
        }
     /*private static boolean kingSafe() {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return 1;
        }*/

    private static int rating() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
    }
    

