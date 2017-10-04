import BoardInfo.Board;
import Pieces.Guardian;
import Pieces.King;
import Pieces.Pawn;
import Pieces.Witch;
import Player.Player;
import junit.framework.TestCase;
import org.junit.Test;

public class GuardianTest extends TestCase {
    private static Board chessBoard;

    private static Move movement = new Move();

    private static Player player1;
    private static Player player2;

    private static King enemyKing;
    private static King myKing;

    private static Guardian guardian;
    private static Pawn pawn;

    @Override
    protected void setUp() {
        chessBoard = new Board(8,8);

        player1 = new Player(1);
        player2 = new Player(2);

        myKing = new King(chessBoard,5,5, 1);
        enemyKing = new King(chessBoard, 3, 3,2);
        guardian = new Guardian(chessBoard, 4,4,1);
        pawn = new Pawn(chessBoard, 5,6,2);

        player1.setPiece(myKing);
        player1.setPiece(guardian);
        player2.setPiece(enemyKing);
        player2.setPiece(pawn);

        chessBoard.setPlayer1(player1);
        chessBoard.setPlayer2(player2);
    }

    @Test
    public void testConstructor() {
        guardian = new Guardian(4,4,-1);
        assertEquals(-1, guardian.getId());
        assertEquals(4, guardian.getCurrentX());
        assertEquals(4, guardian.getCurrentY());
    }
    @Test
    public void testGuardianValidMove() {
        //basic movement test
        assertEquals(true, movement.moveTo(chessBoard,guardian,6,6));
        assertEquals(true, movement.moveTo(chessBoard,guardian,6,5));
    }

    @Test
    public void testGuardianInValidMove() {
        //basic invalid movement test
        assertEquals(false, movement.moveTo(chessBoard,guardian,3,2));
        assertEquals(false, movement.moveTo(chessBoard,guardian,1,2));
    }

    @Test
    public void testCannotCapture() {
        //test whether the piece can capture other piece
        assertEquals(false, movement.moveTo(chessBoard,guardian,5,6));
    }
}
