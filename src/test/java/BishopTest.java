import BoardInfo.Board;
import Pieces.Bishop;
import Pieces.King;
import Pieces.Pawn;
import Player.Player;
import junit.framework.TestCase;
import org.junit.Test;

public class BishopTest extends TestCase {
    private static Board chessBoard;
    private static Bishop bishop;
    private static Pawn pawn1;
    private static Move movement = new Move();
    private static Player player1;
    private static Player player2;
    private static King piece;
    private static King enemyKing;

    @Override
    protected void setUp() {
        chessBoard = new Board(8,8);
        player1 = new Player(1);
        player2 = new Player(2);
        chessBoard.setPlayer1(player1);
        chessBoard.setPlayer2(player2);

        bishop = new Bishop(chessBoard,2,2, 1);

        piece = new King(chessBoard,0,0, 1);
        enemyKing = new King(chessBoard, 1, 1,2);

        player1.setPiece(piece);
        player2.setPiece(enemyKing);
    }

    @Test
    public void testConstructor() {
        bishop = new Bishop(3,3,-1);
        assertEquals(-1, bishop.getId());
        assertEquals(3, bishop.getCurrentX());
        assertEquals(3, bishop.getCurrentY());
    }
    @Test
    public void testBishopValidMovement() {
        //valid movement x+. y+ directions
        assertEquals(true, movement.moveTo(chessBoard, bishop, 5, 5));

        //update chess board
        assertEquals(true, chessBoard.isOccupied(5,5));
        assertEquals(true, chessBoard.getChessBoard()[5][5] instanceof Bishop);

        //valid movement x-. y- directions
        assertEquals(true, movement.moveTo(chessBoard, bishop, 3, 3));

        //valid movement x+. y- directions
        assertEquals(true, movement.moveTo(chessBoard, bishop, 4, 2));

        //valid movement x-. y+ directions
        assertEquals(true, movement.moveTo(chessBoard, bishop, 2, 4));

        //the piece should have correct current x and y values
        assertEquals(2, bishop.getCurrentX());
        assertEquals(4, bishop.getCurrentY());

        pawn1 = new Pawn(chessBoard, 4,7, 2);
        player2.setPiece(pawn1);
        assertEquals(false, movement.moveTo(chessBoard, bishop, 4, 7));
    }

    @Test
    public void testBishopInvalidMove() {
        // check if moveTo function returns correct value. Rook can leap over other pieces
        assertEquals(false, movement.moveTo(chessBoard, bishop, 0, 2));

        // the piece can't leap over other piece
        pawn1 = new Pawn(chessBoard, 3, 3, 1);
        assertEquals(false, movement.moveTo(chessBoard, bishop, 4, 4));
        // the piece can't move to occupied position
        assertEquals(false, movement.moveTo(chessBoard, bishop, 3, 3));

        // basic invalid movement
        assertEquals(false, movement.moveTo(chessBoard, bishop, 5, 1));
    }
}
