package chessengine;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class UserInterface extends JPanel implements MouseListener, MouseMotionListener {
    static int mouseX; static int mouseY;
    static int newMouseX; static int newMouseY;
    static int border=10;//padding for frame
    static int squareSize=64;
    static int mouseDrag[][][]=new int[8][8][2];
    static String chessboard[][]=new String[8][8];
    static boolean antiRepeat;
    @Override
    public void paintComponent(Graphics g) {
        if (!antiRepeat && ChessEngine.choice==0) {antiRepeat=true; userAsBlack();}
        super.paintComponent(g);
        this.setBackground(new Color(200, 100, 0));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        //System.out.println("test");
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                squareSize=(int) ((Math.min(getHeight(), getWidth())-2*border)/8);
            }
        });
        for (int i=0;i<64;i+=2) {//draw 
            g.fillRect((i%8+(i/8)%2)*squareSize+border, (i/8)*squareSize+border, squareSize, squareSize);
            g.setColor(new Color(150, 50, 30));
            g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize+border, ((i+1)/8)*squareSize+border, squareSize, squareSize);
        }
        Image chessPieceImage;
        chessPieceImage=new ImageIcon("ChessPieces.png").getImage();
        for (int i=0;i<64;i++) {//draw piece
            int j=-1,k=-1;
            switch (ChessEngine.chessboard[i/8][i%8]) {
                case "P": j=5; k=1-ChessEngine.choice;
                    break;
                case "p": j=5; k=ChessEngine.choice;
                    break;
                case "R": j=2;k=1-ChessEngine.choice;
                    break;
                case "r": j=2;k=ChessEngine.choice;
                    break;
                case "K": j=4;k=1-ChessEngine.choice;
                    break;
                case "k": j=4;k=ChessEngine.choice;
                    break;
                case "B": j=3;k=1-ChessEngine.choice;
                    break;
                case "b": j=3;k=ChessEngine.choice;
                    break;
                case "Q": j=1;k=1-ChessEngine.choice;
                    break;
                case "q": j=1;k=ChessEngine.choice;
                    break;
                case "A": j=0;k=1-ChessEngine.choice;
                    break;
                case "a": j=0;k=ChessEngine.choice;
                    break;
            }
            if (j!=-1 && k!=-1) {
                g.drawImage(chessPieceImage, (i%8)*squareSize+border+mouseDrag[i/8][i%8][0], (i/8)*squareSize+border+mouseDrag[i/8][i%8][1], (i%8+1)*squareSize+border+mouseDrag[i/8][i%8][0], (i/8+1)*squareSize+border+mouseDrag[i/8][i%8][1], j*64, k*64, (j+1)*64, (k+1)*64, this);
            }
            //g.drawString(ChessEngine.chessboard[i/8][i%8], (i%8)*squareSize+border+13, (i/8)*squareSize+border+21);
        }
        g.setColor(Color.BLUE);
        for (int i=0;i<8;i++) {
            for (int j=0;j<8;j++) {
                if (!chessboard[i][j].equals(ChessEngine.chessboard[i][j])) {
                    g.drawRoundRect(j*squareSize+border+3, i*squareSize+border+3, squareSize-6, squareSize-6, 10, 10);
                    g.drawRoundRect(j*squareSize+border+4, i*squareSize+border+4, squareSize-8, squareSize-8, 10, 10);
                }
            }
        }
        
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseDrag[(mouseY-border)/squareSize][(mouseX-border)/squareSize][0]=((e.getX()-border)/squareSize-(mouseX-border)/squareSize)*squareSize;
        mouseDrag[(mouseY-border)/squareSize][(mouseX-border)/squareSize][1]=((e.getY()-border)/squareSize-(mouseY-border)/squareSize)*squareSize;
        repaint();
    }
    @Override
    public void mousePressed(MouseEvent e) {
        mouseX=e.getX();
        mouseY=e.getY();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        newMouseX=e.getX();
        newMouseY=e.getY();
        mouseDrag[(mouseY-border)/squareSize][(mouseX-border)/squareSize][0]=0;
        mouseDrag[(mouseY-border)/squareSize][(mouseX-border)/squareSize][1]=0;
        repaint();
        if (e.getButton()==MouseEvent.BUTTON1) {//if left mouse click
            String dragMove;
            if ((newMouseY-border)/squareSize==0 && (mouseY-border)/squareSize==1 &&
                    "P".equals(ChessEngine.chessboard[(mouseY-border)/squareSize][(mouseX-border)/squareSize])) {
                //pawn promotion
                dragMove=""+(mouseX-border)/squareSize+(newMouseX-border)/squareSize+
                        ChessEngine.chessboard[(newMouseY-border)/squareSize][(newMouseX-border)/squareSize]+"QP";//assumes queen promotion
            } else if (Math.abs((mouseX-border)/squareSize-(newMouseX-border)/squareSize)==2 &&
                    "A".equals(ChessEngine.chessboard[(mouseY-border)/squareSize][(mouseX-border)/squareSize])) {
                //castling
                if ((mouseX-border)/squareSize>(newMouseX-border)/squareSize) {//left
                    dragMove=""+(mouseX-border)/squareSize+"0"+(newMouseX-border)/squareSize+((newMouseX-border)/squareSize+1)+"C";
                } else {//right
                    dragMove=""+(mouseX-border)/squareSize+"7"+(newMouseX-border)/squareSize+((newMouseX-border)/squareSize-1)+"C";
                }
            } else {
            dragMove=""+(mouseY-border)/squareSize+(mouseX-border)/squareSize+(newMouseY-border)/squareSize+
                    (newMouseX-border)/squareSize+
                    ChessEngine.chessboard[(newMouseY-border)/squareSize][(newMouseX-border)/squareSize];
            }
            String userMovePosibilities=ChessEngine.posibleMoves();
            if (userMovePosibilities.replace(dragMove, "").length()<userMovePosibilities.length()) {
                ChessEngine.makeMove(dragMove);
                ChessEngine.history+=dragMove;
                movePieceEvent();
            }
        }
    }
    public void userAsBlack() {
        ChessEngine.flipBoard();
        for (int i=0;i<8;i++) {
            System.arraycopy(ChessEngine.chessboard[i], 0, chessboard[i], 0, 8);
        }
        ChessEngine.flipBoard();
        System.out.println(ChessEngine.sortMoves2(ChessEngine.posibleMoves()));
        ChessEngine.makeMove(ChessEngine.printMove());
        ChessEngine.flipBoard();
        
        if (ChessEngine.posibleMoves().length()==0) {System.out.println("Defeat");}
    }
    public void movePieceEvent() {
        //check for castle remove castling
        if (Math.abs((mouseX-border)/squareSize-(newMouseX-border)/squareSize)==2 &&
                "A".equals(ChessEngine.chessboard[(newMouseY-border)/squareSize][(newMouseX-border)/squareSize])) {
            //(castling):
            if ((mouseX-border)/squareSize==3) {//black
                ChessEngine.castleBlackLong=false;
                ChessEngine.castleBlackShort=false;
            } else {//white
                ChessEngine.castleWhiteLong=false;
                ChessEngine.castleWhiteShort=false;
            }
        }
        for (int i=0;i<8;i++) {
            System.arraycopy(ChessEngine.chessboard[i], 0, chessboard[i], 0, 8);
        }
        ChessEngine.flipBoard();
        ChessEngine.rating(1, 0);
        System.out.println(ChessEngine.sortMoves2(ChessEngine.posibleMoves()));
        if (ChessEngine.posibleMoves().length()==0) {
            System.out.println("You win!");
            ChessEngine.flipBoard();
        } else {
            ChessEngine.makeMove(ChessEngine.printMove());
            ChessEngine.flipBoard();
            if (ChessEngine.posibleMoves().length()==0) {
                if (ChessEngine.kingSafe()) {
                    System.out.println("Stalemate");
                } else {
                    System.out.println("Defeat!");
                }
            }
        }
        repaint();
    }
}