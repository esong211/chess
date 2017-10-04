import BoardInfo.Board;
import Pieces.*;
import Player.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import java.util.Stack;

/**
 * This is class that manages GUI and the whole chess game
 */
public class GameManager extends JFrame implements ActionListener, MouseListener {

    //subclass of GameManger. Each cell represents JPanel
    public class Cell extends JPanel {
        public int x;
        public int y;

        public Cell (BorderLayout b) {
            super(b);
        }
        public Cell () {
            this.x = -1;
            this.y = -1;
        }
    };

    //indicates whether it is first game or not
    private boolean firstGame = true;

    //represents whether a cell is selected or not
    private boolean selection = false;

    //true means game is started. Otherwise, false
    public boolean isPlaying = false;

    //the piece that is attacking the King
    private Piece attackingPiece = null;

    //stack for undo and redo functions
    public Stack<Piece> undo;
    public Stack<Piece> redo;

    //previously selected cells and colors
    private Color prevColor = Color.WHITE;
    private Cell prevPanel = null;

    //indicates turn of the game
    private static int turn = 1;

    //list of valid move panels with corresponding colors
    public ArrayList<Cell> validMovePanels = new ArrayList<Cell>();
    public ArrayList<Color> validMoveColors = new ArrayList<Color>();

    //Each JPanel represents each location on chess board.
    private Cell[][] piecePanel = new Cell[8][8];

    private static Move movement = new Move();
    public static Board chessboard = new Board(8, 8);

    Player player1 = new Player(1);
    Player player2 = new Player(2);

    JPanel boardPanel;

    public void mouseEntered(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}

    public void mouseClicked(MouseEvent e){
        //if game is not started yet, don't do anything.
        if (isPlaying) {
            Object source = e.getSource();
            if (source instanceof Cell) {
                Cell cell = (Cell) source;

                int x = cell.x;
                int y = cell.y;

                if (!selection) {
                    //Return if it is not valid selection
                    if (!chessboard.isOccupied(x,y) || chessboard.getChessBoard()[x][y].getId() != turn) {
                        selection = false;
                        return;
                    }
                    //color the cell and show possible moves
                    prevPanel = cell;
                    prevColor = cell.getBackground();
                    cell.setBackground(Color.DARK_GRAY);
                    showValidMove(x,y);

                }
                else {
                    //uncolor the selected piece cell
                    prevPanel.setBackground(prevColor);
                    boolean moved = false;
                    for(int i = 0; i < validMovePanels.size(); i ++) {
                        if (validMovePanels.get(i) == cell) {
                            //move piece
                            movePanel(prevPanel, x, y);
                            redo.clear();
                            if (chessboard.getOpponent(turn).getKingCheck()) {
                                showMessage(chessboard.getOpponent(turn).getName() + ", your king is in CHECK ");
                                attackingPiece = chessboard.getChessBoard()[x][y];
                            }

                            if (chessboard.checkMate(chessboard.getOpponent(turn), chessboard.getChessBoard()[x][y], chessboard.getMyPlayer(turn).getPieces())) {
                                endGame(turn);
                            }
                            moved = true;
                        }
                        validMovePanels.get(i).setBackground(validMoveColors.get(i));
                    }
                    validMovePanels.clear();
                    validMoveColors.clear();

                    if (!moved) {
                        showMessage("it is an illegal move");
                    }
                    else {
                        turn = (turn % 2) + 1;
                    }
                }
                selection = !selection;
            }
        }
    }

    /**
     * @param s is a message that you want to display on the screen
     */
    public void showMessage (String s) {
        JOptionPane.showMessageDialog(null, s, "Chess Game Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Constructor for the class.
     * Set up all GUI settings and standard chess rules
     */
    public GameManager() {
        player1.setName("player1");
        player2.setName("player2");

        chessboard.setPlayer1(player1);
        chessboard.setPlayer2(player2);
        undo = new Stack<Piece>();
        redo = new Stack<Piece>();

        chessboard.setUpStandardPiecesForWhite();
        chessboard.setUpStandardPiecesForBlack();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            //silently ignore
        }
        setTitle("Chess Game");
        setSize(500, 500);
        boardPanel = initializePanel();
        setUpMenu(this);
        setContentPane(boardPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawBoard();
        addPiecesOnBoard();
    }

    /**
     * @param curX is current x-axis location of the piece
     * @param curY is current y-axis location of the piece
     */
    public void showValidMove(int curX, int curY) {
        Player curPlayer = chessboard.getMyPlayer(turn);
        boolean[][] aroundKing = null;
        if (chessboard.getChessBoard()[curX][curY] instanceof King) {
            aroundKing = ((King) chessboard.getChessBoard()[curX][curY]).validMoveAroundKing(chessboard, chessboard.getOpponent(turn).getPieces());
        }
        for (int height = 0; height < 8; height ++) {
            for (int width = 0; width < 8; width++) {
                if (curPlayer.getKingCheck()) {
                    //if selected piece is a king
                    if (chessboard.getChessBoard()[curX][curY] instanceof King) {
                        if ((!aroundKing[width][height]) && chessboard.getChessBoard()[curX][curY].isValidMove(chessboard,width,height)) {
                            addValidInfo(width,height);
                        }
                    }
                    //for other pieces
                    else {
                        if (chessboard.getChessBoard()[curX][curY].isValidMove(chessboard, width, height)) {
                            if (!chessboard.isOccupied(width,height)) {
                                chessboard.getChessBoard()[width][height] = new Piece(width, height, -1);
                                if (!attackingPiece.isValidMove(chessboard, curPlayer.getKing().getCurrentX(), curPlayer.getKing().getCurrentY())) {
                                    addValidInfo(width,height);
                                }
                            }
                            else {
                                if (width == attackingPiece.getCurrentX() && height == attackingPiece.getCurrentY()) {
                                    addValidInfo(width,height);
                                }
                            }
                        }
                    }
                }
                else if (chessboard.getChessBoard()[curX][curY].isValidMove(chessboard, width, height)) {
                    addValidInfo(width,height);
                }
            }
        }
    }

    /**
     * @param width is current x-axis location of the piece
     * @param height is current y-axis location of the piece
     * helper function for showValidMove
     */
    public void addValidInfo (int width, int height) {
        validMovePanels.add(piecePanel[width][height]);
        validMoveColors.add(piecePanel[width][height].getBackground());
        piecePanel[width][height].setBackground(Color.DARK_GRAY);
    }

    /**
     * @param winnderID is id of the winner
     * end the game and show information dialogue
     */
    public void endGame(int winnderID) {
        Player winner = chessboard.getMyPlayer(winnderID);
        winner.incrScore();
        isPlaying = false;
        showMessage(winner.getName() + " won this game!");
        initGame();
        undo.clear();
        redo.clear();
    }

    /**
     * set up initial game
     */
    public void initGame() {
        for(int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j ++) {
                chessboard.getChessBoard()[i][j] = null;
            }
        }
        chessboard.setUpStandardPiecesForWhite();
        chessboard.setUpStandardPiecesForBlack();
        reDraw();
    }

    /**
     * @param prev is previously located cell
     * @param curX x-coordinate of destination
     * @param curY y-coordinate of destination
     * update the panel icons after moving the piece
     */
    public void movePanel(Cell prev, int curX, int curY) {
        storeUndo(prev, curX, curY);
        movement.moveTo(chessboard, chessboard.getChessBoard()[prev.x][prev.y], curX, curY);
        reDraw();
    }

    /**
     * @param prev is previously located cell
     * @param destX x-coordinate of destination
     * @param destY y-coordinate of destination
     * stored current and destination cell information to do undo.
     */
    private void storeUndo(Cell prev, int destX, int destY) {
        Piece destPiece = chessboard.getChessBoard()[destX][destY];
        if (destPiece == null) {
            destPiece = new Piece(destX, destY, -1);
        }
        else {
            destPiece = deepCopyObj(destPiece);
        }
        Piece curPiece= chessboard.getChessBoard()[prev.x][prev.y];
        curPiece = deepCopyObj(curPiece);

        undo.push(curPiece);
        undo.push(destPiece);
    }

    /**
     * @param p is the piece added into redo
     * @param destX x-coordinate of destination
     * @param destY y-coordinate of destination
     * add the piece to redo stack
     */
    public void addRedo(Piece p, int destX, int destY) {
        Piece redoPiece = deepCopyObj(p);
        if (redoPiece == null) {
            redoPiece = new Piece(destX, destY, -1);
        }

        if (redoPiece instanceof Pawn) {
            ((Pawn) redoPiece).setStartPos(p.getCurrentX(), p.getCurrentY());
        }
        redo.push(redoPiece);
    }

    /**
     * @param p is the piece added into redo
     * @param destX x-coordinate of destination
     * @param destY y-coordinate of destination
     * add the piece to undo stack
     */
    public void addUndo(Piece p, int destX, int destY){
        Piece undoPiece = deepCopyObj(p);
        if (undoPiece == null) {
            undoPiece = new Piece(destX, destY, -1);
        }
        if (undoPiece instanceof Pawn) {
            ((Pawn) undoPiece).setStartPos(p.getCurrentX(), p.getCurrentY());
        }
        undo.push(undoPiece);
    }

    /**
     * @param p is the piece to copy
     * @return deep copied object
     * deep copy function for all pieces.
     */
    public Piece deepCopyObj (Piece p) {
        if (p instanceof Pawn){
            return new Pawn (p.getCurrentX(), p.getCurrentY(), p.getId());
        }
        else if (p instanceof Bishop) {
            return new Bishop (p.getCurrentX(), p.getCurrentY(), p.getId());
        }
        else if (p instanceof Guardian) {
            return new Guardian (p.getCurrentX(), p.getCurrentY(), p.getId());
        }
        else if (p instanceof King) {
            return new King (p.getCurrentX(), p.getCurrentY(), p.getId());
        }
        else if (p instanceof Knight) {
            return new Knight (p.getCurrentX(), p.getCurrentY(), p.getId());
        }
        else if (p instanceof Queen) {
            return new Queen (p.getCurrentX(), p.getCurrentY(), p.getId());
        }
        else if (p instanceof Rook) {
            return new Rook (p.getCurrentX(), p.getCurrentY(), p.getId());
        }
        else if (p instanceof Witch) {
            return new Witch (p.getCurrentX(), p.getCurrentY(), p.getId());
        }
        return null;
    }

    //get name of player in turn
    public String turnName() {
        String turnPlayer = null;
        if (player1.getId() == turn) {
            turnPlayer = player1.getName();
        }
        else {
            turnPlayer = player2.getName();
        }
        return turnPlayer;
    }
    /**
     * Draws a board on the JPanel
     */
    private void drawBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                piecePanel[i][j] = new Cell(new BorderLayout());
                piecePanel[i][j].x = i;
                piecePanel[i][j].y = j;
                boardPanel.add(piecePanel[i][j]);
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        piecePanel[i][j].setBackground(Color.WHITE);
                    } else {
                        piecePanel[i][j].setBackground(Color.LIGHT_GRAY);
                    }
                } else {
                    if (j % 2 != 0) {
                        piecePanel[i][j].setBackground(Color.WHITE);
                    } else {
                        piecePanel[i][j].setBackground(Color.LIGHT_GRAY);
                    }
                }
            }
        }
    }

    /**
     * redraw all pieces on the Jframe
     */
    private void reDraw() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                piecePanel[i][j].remove(0);
                piecePanel[i][j].add(getIcon(chessboard.getChessBoard()[i][j]), BorderLayout.CENTER);
                piecePanel[i][j].validate();
            }
        }
    }

    /**
     * @param p is a piece on the board
     * @return corresponding png file of the input piece
     */
    private JLabel getIcon(Piece p) {
        if (p == null) {
            return new JLabel();
        }
        if (p.getId() == 1) {
            if (p instanceof Rook) {
                ImageIcon blackRook = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/blackRook.png");
                return new JLabel(blackRook);
            }
            else if (p instanceof Queen) {
                ImageIcon blackQueen = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/blackQueen.png");
                return new JLabel(blackQueen);
            }
            else if (p instanceof Pawn) {
                ImageIcon blackPawn = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/blackPawn.png");
                return new JLabel(blackPawn);
            }
            else if (p instanceof King) {
                ImageIcon blackKing = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/blackKing.png");
                return new JLabel(blackKing);
            }
            else if (p instanceof Knight) {
                ImageIcon blackKnight = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/blackKnight.png");
                return new JLabel(blackKnight);
            }
            else if (p instanceof Bishop) {
                ImageIcon blackBishop = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/blackBishop.png");
                return new JLabel(blackBishop);
            }
            else if (p instanceof Witch) {
                ImageIcon blackWitch = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/blackWitch.png");
                return new JLabel(blackWitch);
            }
            else if (p instanceof Guardian) {
                ImageIcon blackGuardian = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/blackGuardian.png");
                return new JLabel(blackGuardian);
            }
        }
        else {
            if (p instanceof Rook) {
                ImageIcon whiteRook = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/whiteRook.png");
                return new JLabel(whiteRook);
            }
            else if (p instanceof Queen) {
                ImageIcon whiteQueen = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/whiteQueen.png");
                return new JLabel(whiteQueen);
            }
            else if (p instanceof Pawn) {
                ImageIcon whitePawn = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/whitePawn.png");
                return new JLabel(whitePawn);
            }
            else if (p instanceof King) {
                ImageIcon whiteKing = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/whiteKing.png");
                return new JLabel(whiteKing);
            }
            else if (p instanceof Knight) {
                ImageIcon whiteKnight = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/whiteKnight.png");
                return new JLabel(whiteKnight);
            }
            else if (p instanceof Bishop) {
                ImageIcon whiteBishop = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/whiteBishop.png");
                return new JLabel(whiteBishop);
            }
            else if (p instanceof Witch) {
                ImageIcon whiteWitch = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/whiteWitch.png");
                return new JLabel(whiteWitch);
            }
            else if (p instanceof Guardian) {
                ImageIcon whiteGuardian = new ImageIcon(System.getProperty("user.dir") + "/src/main/resources/whiteGuardian.png");
                return new JLabel(whiteGuardian);
            }
        }

        return new JLabel();
    }

    /**
     * Insert chess piece icon on the board. Get an icon from getIcon method.
     */
    private void addPiecesOnBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                piecePanel[i][j].add(getIcon(chessboard.getChessBoard()[i][j]), BorderLayout.CENTER);
                piecePanel[i][j].validate();
                piecePanel[i][j].addMouseListener(this);
            }
        }
    }

    /**
     * @return the Panel divided into 8 x 8 grid
     */
    private JPanel initializePanel() {
        JPanel panel = new JPanel(new GridLayout(8, 8));
        panel.setSize(new Dimension(500,500));
        return panel;
    }

    /**
     * @param window JFrame that would be set up
     * Add menu bar and menu items on the JFrame
     */
    ActionListener scoreAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            showMessage(player1.getName() + " : " + player1.getScore() + "\n" + player2.getName() + " : " + player2.getScore());
        }
    };

    /**
     * action listener for start button
     */
    private ActionListener startAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (!isPlaying) {
                if (firstGame) {
                    String name1 = null;
                    while (name1 == null || name1.compareTo("") == 0) {
                        name1 = JOptionPane.showInputDialog("please enter player1's name. Empty string is not acceptable");
                        if (name1 == null) {
                            showMessage("cancelled");
                            return;
                        }
                    }
                    String name2 = null;
                    while (name2 == null || name2.compareTo("") == 0 || name2.compareTo(name1) == 0) {
                        name2 = JOptionPane.showInputDialog("please enter player2's name. Empty string is not acceptable");
                        if (name2 == null) {
                            showMessage("cancelled");
                            return;
                        }
                    }
                    player1.setName(name1);
                    player2.setName(name2);
                    firstGame = false;
                }
                isPlaying = true;
                showMessage("Start Game!");
            }
            else {
                showMessage("Playing the game");
            }
        }
    };

    /**
     * action listener for forfeit button
     */
    private ActionListener forefeitAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (!isPlaying) {
                showMessage("Game is not even started yet");
            }
            else {
                String turnPlayer = turnName();
                int check = JOptionPane.showConfirmDialog(null, turnPlayer + ", are you sure you want to end the game?", "Forfeit", JOptionPane.YES_NO_OPTION);
                if (check == 0) {
                    if (turn == 1) {
                        endGame(2);
                    } else {
                        endGame(1);
                    }
                }
            }
        }
    };

    /**
     * action listener for restart button
     */
    private ActionListener restartAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (!isPlaying) {
                showMessage("Game is not even started yet");
            }
            else {
                int oneCheck = JOptionPane.showConfirmDialog(null, player1.getName() + ", are you sure you want to restart the game?", "Restart", JOptionPane.YES_NO_OPTION);
                int twoCheck = -1;
                if (oneCheck == 0) {
                    twoCheck = JOptionPane.showConfirmDialog(null, player2.getName() + ", are you sure you want to restart the game?", "Restart", JOptionPane.YES_NO_OPTION);
                    if (twoCheck == 0) {
                        player1.incrScore();
                        player2.incrScore();
                        isPlaying = false;
                        showMessage("Game is ended due to restart");
                        initGame();
                    }
                }
            }
        }
    };

    /**
     * action listener for custom button
     */
    private ActionListener customAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (isPlaying) {
                showMessage("Game mode can not be changed while playing the game");
            }
            else {
                chessboard.setUpCustomPiecesForBlack();
                chessboard.setUpCustomPieceForWhite();
                reDraw();
            }
        }
    };

    /**
     * action listener for undo button
     */
    private ActionListener undoAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (undo.size() == 0) {
                showMessage("There is no more steps to undo");
                return;
            }
            Piece cur = undo.pop();
            Piece prev = undo.pop();
            addRedo(chessboard.getChessBoard()[cur.getCurrentX()][cur.getCurrentY()], cur.getCurrentX(), cur.getCurrentY());
            addRedo(chessboard.getChessBoard()[prev.getCurrentX()][prev.getCurrentY()], prev.getCurrentX(), prev.getCurrentY());

            if (cur.getId() != -1) {
                chessboard.getChessBoard()[cur.getCurrentX()][cur.getCurrentY()] = deepCopyObj(cur);
            }
            else {
                chessboard.getChessBoard()[cur.getCurrentX()][cur.getCurrentY()] = null;
            }
            chessboard.getChessBoard()[prev.getCurrentX()][prev.getCurrentY()] = deepCopyObj(prev);
            reDraw();
            turn = (turn % 2) + 1;


        }
    };

    /**
     * action listener for redo button
     */
    private ActionListener redoAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (redo.size() == 0) {
                showMessage("There is no more steps to redo");
                return;
            }
            Piece cur = redo.pop();
            Piece dest = redo.pop();
            addUndo(chessboard.getChessBoard()[dest.getCurrentX()][dest.getCurrentY()], dest.getCurrentX(), dest.getCurrentY());
            addUndo(chessboard.getChessBoard()[cur.getCurrentX()][cur.getCurrentY()], cur.getCurrentX(), cur.getCurrentY());
            if (cur.getId() != -1) {
                chessboard.getChessBoard()[cur.getCurrentX()][cur.getCurrentY()] = deepCopyObj(cur);
            }
            else {
                chessboard.getChessBoard()[cur.getCurrentX()][cur.getCurrentY()] = null;
            }

            if (dest.getId() != -1) {
                chessboard.getChessBoard()[dest.getCurrentX()][dest.getCurrentY()] = deepCopyObj(dest);
            }
            else {
                chessboard.getChessBoard()[dest.getCurrentX()][dest.getCurrentY()] = null;
            }

            reDraw();
            turn = (turn % 2) + 1;


        }
    };
    /**
     * action listener for standard button
     */
    private ActionListener standardAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (isPlaying) {
                showMessage("Game mode can not be changed while playing the game");
            }
            else {
                chessboard.setUpStandardPiecesForWhite();
                chessboard.setUpStandardPiecesForBlack();
                reDraw();
            }
        }
    };

    /**
     * @param window is a JFrame to set up
     * Helper method for constructor. Set up initial settings of JFrame
     */
    private void setUpMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();

        JMenu menu = new JMenu("Menu");
        JMenu undoRedo = new JMenu("Undo/Redo");

        JMenuItem start = new JMenuItem("Start");
        JMenuItem forfeit = new JMenuItem("Forfeit");
        JMenuItem restart = new JMenuItem("Restart");
        JMenuItem score = new JMenuItem("Score");
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem redo = new JMenuItem("Redo");

        score.addActionListener(scoreAction);
        start.addActionListener(startAction);
        forfeit.addActionListener(forefeitAction);
        restart.addActionListener(restartAction);
        undo.addActionListener(undoAction);
        redo.addActionListener(redoAction);
        menu.add(start);
        menu.add(forfeit);
        menu.add(restart);
        menu.add(score);
        undoRedo.add(undo);
        undoRedo.add(redo);

        menubar.add(menu);
        menubar.add(undoRedo);
        menu.addSeparator();
        JMenu modeMenu = new JMenu("Mode");
        JMenuItem standard = new JMenuItem("Standard");
        JMenuItem custom = new JMenuItem("Custom");

        custom.addActionListener(customAction);
        standard.addActionListener(standardAction);

        modeMenu.add(standard);
        modeMenu.add(custom);
        menu.add(modeMenu);

        window.setJMenuBar(menubar);
    }

    /**
     * @param e is an ActionEvent that I want to perform on
     * This is a test function for next week's assignment
     */
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(null,
                "I was clicked by " + e.getActionCommand(),
                "Chess Game Message", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame game = new GameManager();
                game.setVisible(true);
            }
        });

    }
}
