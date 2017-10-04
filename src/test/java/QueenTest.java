import BoardInfo.Board;
import Pieces.King;
import Pieces.Pawn;
import Pieces.Queen;
import Player.Player;
import junit.framework.TestCase;
import org.junit.Test;

public class QueenTest extends TestCase {
    private static Board chessBoard;
    private static Queen piece;
    private static Pawn pawn1;
    private static Pawn pawn2;
    private static Move movement = new Move();
    private static Player player1;
    private static Player player2;
    private static King enemyKing;
    private static King myKing;


    @Override
    protected void setUp() {
        chessBoard = new Board(8,8);
        piece = new Queen(chessBoard,2,2, 1);

        player1 = new Player(1);
        player2 = new Player(2);
        chessBoard.setPlayer1(player1);
        chessBoard.setPlayer2(player2);

        myKing = new King(chessBoard,7,7, 1);
        enemyKing = new King(chessBoard, 0, 0,2);

        player1.setPiece(myKing);
        player2.setPiece(enemyKing);
    }

    @Test
    public void testQueenValidMovement() {
        //valid diagonal movement x+. y+ directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 4, 4));

        //valid straight movement x+ directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 6, 4));
        //valid straight movement y+ directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 6, 6));
        //update chess board
        assertEquals(true, chessBoard.isOccupied(6,6));
        assertEquals(true, chessBoard.getChessBoard()[6][6] instanceof Queen);
        assertEquals(false, chessBoard.isOccupied(6,4));

        //valid diagonal movement x-. y- directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 4, 4));

        //valid straight movement x- directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 2, 4));

        //valid straight movement y- directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 2, 2));

        //valid diagonal movement x+. y- directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 3, 1));

        //valid diagonal movement x-. y+ directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 1, 3));

        //the piece should have correct current x and y values
        assertEquals(1, piece.getCurrentX());
        assertEquals(3, piece.getCurrentY());
    }

    @Test
    public void testQueenInvalidMove() {
        // basic invalid movement
        assertEquals(false, movement.moveTo(chessBoard, piece, 1, 0));

        // the piece can't leap over other piece
        pawn1 = new Pawn(chessBoard, 3, 3, 1);
        assertEquals(false, movement.moveTo(chessBoard, piece, 4, 4));

        // the piece can't leap over other piece
        pawn2 = new Pawn(chessBoard, 4, 2, 1);
        assertEquals(false, movement.moveTo(chessBoard, piece, 5, 2));

    }

    @Test
    public void testConstructor() {
        Queen p = new Queen(4,4,-1);
        assertEquals(-1, p.getId());
        assertEquals(4, p.getCurrentX());
        assertEquals(4, p.getCurrentY());
    }
}
