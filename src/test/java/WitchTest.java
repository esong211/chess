import BoardInfo.Board;
import Pieces.*;
import Player.Player;
import junit.framework.TestCase;
import org.junit.Test;

public class WitchTest extends TestCase{
    private static Board chessBoard;

    private static Move movement = new Move();

    private static Player player1;
    private static Player player2;

    private static King enemyKing;
    private static King myKing;

    private static Witch witch;
    private static Pawn pawn;

    @Override
    protected void setUp() {
        chessBoard = new Board(8,8);

        player1 = new Player(1);
        player2 = new Player(2);

        myKing = new King(chessBoard,7,7, 1);
        enemyKing = new King(chessBoard, 6, 6,2);
        witch = new Witch(chessBoard, 3,3,1);

        player1.setPiece(myKing);
        player1.setPiece(witch);
        player2.setPiece(enemyKing);

        chessBoard.setPlayer1(player1);
        chessBoard.setPlayer2(player2);

    }

    @Test
    public void testWitchValidMove() {
        //diagonal move
        assertEquals(true, movement.moveTo(chessBoard, witch, 2,2));

        //straight move two steps
        assertEquals(true, movement.moveTo(chessBoard, witch, 2,4));

        //straight move one step
        assertEquals(true, movement.moveTo(chessBoard, witch, 1,4));

        //diagonal move again
        assertEquals(true, movement.moveTo(chessBoard, witch, 0,3));
    }

    @Test
    public void testWitchInvalidMove() {
        //basic invalid move
        assertEquals(false, movement.moveTo(chessBoard, witch, 4,7));
        assertEquals(false, movement.moveTo(chessBoard, witch, 5,4));

        //can't jump over
        pawn = new Pawn(chessBoard, 3,4, 2);
        player2.setPiece(pawn);
        assertEquals(false, movement.moveTo(chessBoard, witch, 3,5));
    }

    @Test
    public void testCapture() {
        //It captures the opponent piece
        pawn = new Pawn(chessBoard, 3,4, 2);
        player2.setPiece(pawn);
        assertEquals(2, player1.getPieces().size());
        assertEquals(true, movement.moveTo(chessBoard, witch, 3,4));
        assertEquals(3, player1.getPieces().size());
        assertEquals(false, pawn.getLife());
    }

    @Test
    public void testConstructor() {
        Witch p= new Witch(4,4,-1);
        assertEquals(-1, p.getId());
        assertEquals(4, p.getCurrentX());
        assertEquals(4, p.getCurrentY());
    }
}
