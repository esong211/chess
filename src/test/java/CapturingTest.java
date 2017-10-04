import BoardInfo.Board;
import Pieces.*;
import Player.Player;
import junit.framework.TestCase;
import org.junit.Test;

public class CapturingTest extends TestCase {
    private static Board chessBoard;
    private static Move movement = new Move();
    private static Pawn pawn;
    private static King king;
    private static Knight knight;
    private static Queen queen;
    private static Player player1;
    private static Player player2;
    private static King enemyKing;
    private static King piece;

    @Override
    protected void setUp() {
        chessBoard = new Board(8,8);
        player1 = new Player( 1);
        player2 = new Player( 2);
        chessBoard.setPlayer1(player1);
        chessBoard.setPlayer2(player2);

        piece = new King(chessBoard,7,7, 1);
        enemyKing = new King(chessBoard, 6, 6,2);

        player1.setPiece(piece);
        player2.setPiece(enemyKing);
    }

    @Test
    public void testPawnCapturingOther() {
        pawn = new Pawn(chessBoard, 2,2,2);
        knight = new Knight(chessBoard, 3,3,1);
        queen = new Queen(chessBoard,1,3,1);

        boolean check = movement.moveTo(chessBoard, pawn, 1,3);
        //pawn can't capture queen since they are in same team
        assertEquals(false,check);

        //pawn captures knight
        assertEquals(true, movement.moveTo(chessBoard, pawn, 3,3));

        //check if the chessBoard is updated
        assertEquals(3,pawn.getCurrentX());
        assertEquals(3,pawn.getCurrentY());
        assertEquals(true, chessBoard.getChessBoard()[3][3] instanceof Pawn);

        //check if knight is marked as dead
        assertEquals(false, knight.getLife());
    }

    @Test
    public void testKingCapturingOther() {
        pawn = new Pawn(chessBoard, 2,2,1);
        knight = new Knight(chessBoard, 3,3,2);
        king = new King(chessBoard, 3,2,1);

        //king can't capture queen since they are in same team
        assertEquals(false,movement.moveTo(chessBoard, king, 2,2));

        //king captures knight
        assertEquals(true, movement.moveTo(chessBoard, king, 3,3));

        //check if the chessBoard is updated
        assertEquals(3,king.getCurrentX());
        assertEquals(3,king.getCurrentY());
        assertEquals(true, chessBoard.getChessBoard()[3][3] instanceof King);

        //check if knight is marked as dead
        assertEquals(false, knight.getLife());
        assertEquals(-1, knight.getCurrentX());
        assertEquals(-1, knight.getCurrentY());
    }
}
