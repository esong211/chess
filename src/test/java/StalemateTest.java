import BoardInfo.Board;
import Pieces.*;
import Player.Player;
import junit.framework.TestCase;
import org.junit.Test;

public class StalemateTest extends TestCase {
    private static Board chessBoard;
    private static Move movement = new Move();
    private static King king;
    private static Rook rook1;
    private static Rook rook2;
    private static Bishop bishop1;
    private static Bishop bishop2;

    GameManager game = new GameManager();

    private static Player player1;
    private static Player player2;
    private static King enemyKing;

    @Override
    protected void setUp() {
        chessBoard = new Board(8,8);
        player1 = new Player(1);
        player2 = new Player(2);

        rook1 = new Rook(chessBoard,2,2,1);
        king = new King(chessBoard,4,4,2);
        enemyKing = new King(chessBoard, 6, 6,1);

        player1.setPiece(enemyKing);
        player1.setPiece(rook1);
        player2.setPiece(king);
        chessBoard.setPlayer1(player1);
        chessBoard.setPlayer2(player2);

    }

    @Test
    public void testStalemate() {
        assertEquals(true, movement.moveTo(chessBoard,king,3,3));
        rook2 = new Rook(chessBoard,5,4,1);
        player1.setPiece(rook2);

        assertEquals(true, movement.moveTo(chessBoard, rook2, 4, 4));
        bishop1 = new Bishop(chessBoard,0,0,1);
        bishop2 = new Bishop(chessBoard, 5,5,1);
        player1.setPiece(bishop1);
        player1.setPiece(bishop2);

        assertEquals(true, movement.moveTo(chessBoard, bishop1, 1, 1));
        boolean[][] validCells = king.validMoveAroundKing(chessBoard, player1.getPieces());
        assertEquals(false, king.validLegalMove(chessBoard, validCells));
        assertEquals(true, player2.getStaleMate());
    }
}
