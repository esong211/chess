import BoardInfo.Board;
import Pieces.*;
import junit.framework.TestCase;
import org.junit.Test;
import Player.*;

public class KingTest extends TestCase {
    private static Board chessBoard;
    private static Pawn pawn1;
    private static Rook rook;
    private static Move movement = new Move();

    private static Player player1;
    private static Player player2;
    private static King enemyKing;
    private static King piece;

    @Override
    protected void setUp() {
        chessBoard = new Board(8,8);
        player1 = new Player(1);
        player2 = new Player(2);
        chessBoard.setPlayer1(player1);
        chessBoard.setPlayer2(player2);

        piece = new King(chessBoard,6,0, 1);
        enemyKing = new King(chessBoard, 1, 1,2);

        player1.setPiece(piece);
        player2.setPiece(enemyKing);
    }
    @Test
    public void testConstructor() {
        enemyKing = new King(4,4,-1);
        assertEquals(-1, enemyKing.getId());
        assertEquals(4, enemyKing.getCurrentX());
        assertEquals(4, enemyKing.getCurrentY());
    }
    @Test
    public void testKingValidMovement() {
        //valid movement x+. y+ directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 7, 1));

        //update chess board
        assertEquals(true, chessBoard.isOccupied(7,1));
        assertEquals(true, chessBoard.getChessBoard()[7][1] instanceof King);
        assertEquals(false, chessBoard.isOccupied(6, 0));

        //valid movement x-. y- directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 6, 0));

        //valid movement x+ directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 7, 0));

        //valid movement y+ directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 7, 1));

        //the piece should have correct current x and y values
        assertEquals(7, piece.getCurrentX());
        assertEquals(1, piece.getCurrentY());
    }

    @Test
    public void testKingInvalidMove() {
        // basic invalid movement
        assertEquals(false, movement.moveTo(chessBoard, piece, 4, 0));

        pawn1 = new Pawn(chessBoard, 5, 0, 1);
        assertEquals(false, movement.moveTo(chessBoard, piece, 5, 0));

        // basic invalid movement
        assertEquals(false, movement.moveTo(chessBoard, piece, 4, 2));

        rook = new Rook(chessBoard, 7, 4, 2);
        chessBoard.getPlayer2().setPiece(rook);
        assertEquals(false, movement.moveTo(chessBoard, piece, 7, 0));
    }
}
