import BoardInfo.Board;
import Pieces.King;
import Pieces.Pawn;
import Player.Player;
import junit.framework.TestCase;
import org.junit.Test;

public class PawnTest extends TestCase {
    private static Board chessBoard;
    private static Pawn piece;
    private static Pawn pawn1;
    private static Move movement = new Move();
    private static Player player1;
    private static Player player2;
    private static King enemyKing;
    private static King myKing;
    @Override
    protected void setUp() {
        chessBoard = new Board(8,8);
        piece = new Pawn(chessBoard,2,3, 2);

        player1 = new Player(1);
        player2 = new Player(2);
        chessBoard.setPlayer1(player1);
        chessBoard.setPlayer2(player2);

        myKing = new King(chessBoard,7,7, 1);
        enemyKing = new King(chessBoard, 6, 6,2);

        player1.setPiece(enemyKing);
        player2.setPiece(myKing);
    }

    @Test
    public void testPawnMovement() {
        //valid movement x+2 directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 4, 3));

        //valid movement x+1 directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 5, 3));

        //update chess board
        assertEquals(true, chessBoard.isOccupied(5,3));
        assertEquals(true, chessBoard.getChessBoard()[5][3] instanceof Pawn);
        assertEquals(false, chessBoard.isOccupied(4, 3));

        pawn1 = new Pawn(chessBoard, 6, 2, 1);
        //valid movement x+. y+ directions since pawn at position [6][2] is opponent's pawn
        assertEquals(true, movement.moveTo(chessBoard, piece, 6, 2));

        //the piece should have correct current x and y values
        assertEquals(6, piece.getCurrentX());
        assertEquals(2, piece.getCurrentY());
    }

    @Test
    public void testPawnInvalidMove() {
        // basic invalid movement
        assertEquals(false, movement.moveTo(chessBoard, piece, 2, 4));

        // not the first move, but try to move to x+2 position
        assertEquals(true, movement.moveTo(chessBoard, piece, 3, 3));
        assertEquals(false, movement.moveTo(chessBoard, piece, 3, 5));

        // try to move to x+, y+ direction where it is an empty spot.
        assertEquals(false, movement.moveTo(chessBoard, piece, 4, 6));

        // try to move to x+, y- direction where it is an empty spot.
        assertEquals(false, movement.moveTo(chessBoard, piece, 5, 5));

        pawn1 = new Pawn(chessBoard, 4,4, 2);
        // try to move to x+, y+ direction where it is occupied by ally.
        assertEquals(false, movement.moveTo(chessBoard, piece, 4, 4));

    }

    @Test
    public void testPawnLeapOver() {
        pawn1 = new Pawn(chessBoard, 2,4, 1);
        assertEquals(false, movement.moveTo(chessBoard, piece, 2, 5));
    }

    @Test
    public void testConstructor() {
        Pawn p = new Pawn(4,4,-1);
        assertEquals(-1, p.getId());
        assertEquals(4, p.getCurrentX());
        assertEquals(4, p.getCurrentY());
    }

    @Test
    public void testSetStartPos() {
        Pawn p = new Pawn(4,4,-1);
        p.setStartPos(5,5);
        assertEquals(5, p.getStartPosX());
        assertEquals(5, p.getStartPosY());

    }
}
