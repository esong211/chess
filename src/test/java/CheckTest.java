import BoardInfo.Board;
import Pieces.*;
import junit.framework.TestCase;
import Player.*;
import org.junit.Test;

public class CheckTest extends TestCase {
    private static Board chessBoard;
    private static Move movement = new Move();
    private static Pawn pawn;
    private static Knight knight;
    private static Queen queen;
    private static Rook rook;
    private static Bishop bishop;
    Player player1;
    Player player2;
    private static King enemyKing;
    private static King piece;

    @Override
    protected void setUp() {
        chessBoard = new Board(8,8);
        rook = new Rook(chessBoard,3,3,1);
        piece = new King(chessBoard, 7,7,1);
        enemyKing = new King(chessBoard, 1, 4,2);
        player1 = new Player(1);
        player2 = new Player(2);

        player2.setPiece(enemyKing);
        player1.setPiece(piece);
        player1.setPiece(rook);

        chessBoard.setPlayer1(player1);
        chessBoard.setPlayer2(player2);
    }

    @Test
    public void testKinginCheck() {
        assertEquals(false, player2.getKingCheck());
        assertEquals(true, movement.moveTo(chessBoard,rook,0,3));

        //check
        assertEquals(true, movement.moveTo(chessBoard,rook,0,4));
        assertEquals(true, player2.getKingCheck());
    }
}
