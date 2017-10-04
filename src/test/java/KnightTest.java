import BoardInfo.Board;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Player.Player;
import junit.framework.TestCase;
import org.junit.Test;

public class KnightTest extends TestCase {
    private static Board chessBoard;
    private static Knight piece;
    private static Pawn pawn1;
    private static Move movement = new Move();

    private static Player player1;
    private static Player player2;
    private static King enemyKing;
    private static King myKing;

    @Override
    protected void setUp() {
        chessBoard = new Board(8,8);
        piece = new Knight(chessBoard,3,3, 1);

        player1 = new Player(1);
        player2 = new Player(2);
        chessBoard.setPlayer1(player1);
        chessBoard.setPlayer2(player2);

        myKing = new King(chessBoard,6,6, 1);
        enemyKing = new King(chessBoard, 7, 7,2);

        player1.setPiece(piece);
        player1.setPiece(myKing);
        player2.setPiece(enemyKing);

    }
    @Test
    public void testConstructor() {
        Knight knight = new Knight(4,4,-1);
        assertEquals(-1, knight.getId());
        assertEquals(4, knight.getCurrentX());
        assertEquals(4, knight.getCurrentY());
    }

    @Test
    public void testKnightValidMovement() {
        //valid movement x+. y+ directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 4, 5));

        //update chess board
        assertEquals(true, chessBoard.isOccupied(4,5));
        assertEquals(true, chessBoard.getChessBoard()[4][5] instanceof Knight);

        //valid movement x-. y- directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 2, 4));

        //valid movement x+. y- directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 3, 2));

        //valid movement x-. y+ directions
        assertEquals(true, movement.moveTo(chessBoard, piece, 1, 1));

        //the piece should have correct current x and y values
        assertEquals(1, piece.getCurrentX());
        assertEquals(1, piece.getCurrentY());
    }
    @Test
    public void testKnightInvalidMove() {
        // check if moveTo function returns correct value.
        assertEquals(false, movement.moveTo(chessBoard, piece, 1, 1));

        // the piece can leap over other piece
        pawn1 = new Pawn(chessBoard, 4, 3, 1);
        assertEquals(true, movement.moveTo(chessBoard, piece, 4, 5));

        // basic invalid movement
        assertEquals(false, movement.moveTo(chessBoard, piece, 0, 0));
    }
}
