import BoardInfo.Board;
import Pieces.King;
import Player.Player;
import junit.framework.TestCase;
import org.junit.Test;
import Pieces.Pawn;
import Pieces.Rook;

public class RookTest extends TestCase {
    private static Board chessBoard;
    private static Rook rook;
    private static Pawn pawn1;
    private static Pawn pawn2;
    private static Pawn pawn3;
    private static Pawn pawn4;
    private static Move movement = new Move();

    private static Player player1;
    private static Player player2;
    private static King enemyKing;
    private static King myKing;

    @Override
    protected void setUp() {
        chessBoard = new Board(8,8);
        rook = new Rook(chessBoard,2,2, 1);
        pawn1 = new Pawn(chessBoard, 1, 2, 1);
        pawn2 = new Pawn(chessBoard, 4, 2, 2);
        pawn3 = new Pawn(chessBoard, 2, 6, 1);
        pawn3 = new Pawn(chessBoard, 2, 1, 1);

        player1 = new Player(1);
        player2 = new Player(2);
        chessBoard.setPlayer1(player1);
        chessBoard.setPlayer2(player2);

        myKing = new King(chessBoard,7,7, 1);
        enemyKing = new King(chessBoard, 6, 6,2);

        player1.setPiece(myKing);
        player2.setPiece(enemyKing);
    }

    @Test
    public void testRookValidMovement() {
        boolean check = movement.moveTo(chessBoard, rook, 2, 5);
        //update chess board
        assertEquals(true, chessBoard.isOccupied(2,5));

        //checks if moveTo returns correct value
        assertEquals(true, check);

         //checks if chessBoard has correct object
        assertEquals(true, (chessBoard.getChessBoard()[2][5]) instanceof Rook );

        //the piece should have correct current x and y values
        assertEquals(2, rook.getCurrentX());
        assertEquals(5, rook.getCurrentY());
    }

    @Test
    public void testRookInvalidMove() {
        // check if moveTo function returns correct value. Rook can leap over other pieces
        assertEquals(false, movement.moveTo(chessBoard, rook, 0, 2));

        //basic logic of rook movement
        assertEquals(false, movement.moveTo(chessBoard, rook, 3,5));

        // check if moveTo function returns correct value. Rook can leap over other pieces
        assertEquals(false, movement.moveTo(chessBoard, rook, 7,2));

        // check if chessBoard stays the same
        assertEquals(true, chessBoard.getChessBoard()[2][2] instanceof Rook);

        // Rook can't leap over in y + direction
        assertEquals(false, movement.moveTo(chessBoard, rook, 2,7));

        // Rook can't leap over in y - direction
        assertEquals(false, movement.moveTo(chessBoard, rook, 2,0));
    }

    @Test
    public void testConstructor() {
        Rook p = new Rook(4,4,-1);
        assertEquals(-1, p.getId());
        assertEquals(4, p.getCurrentX());
        assertEquals(4, p.getCurrentY());
    }
}
