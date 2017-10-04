import BoardInfo.Board;
import Pieces.Pawn;
import Pieces.Piece;
import junit.framework.TestCase;
import Player.*;
public class BoardTest extends TestCase {
    private Board chessBoard = new Board(8,8);
    Player player1 = new Player(1);
    Player player2 = new Player(2);

    public void testBoardSize() {
        chessBoard.setPlayer1(player1);
        chessBoard.setPlayer2(player2);
        assertEquals(8, chessBoard.getBoardHeight());
        assertEquals(8, chessBoard.getBoardWidth());
        chessBoard.setUpCustomPieceForWhite();
        chessBoard.setUpCustomPiecesForBlack();
    }

    public void testGetChessBoard() {
        Pawn p = new Pawn(chessBoard, 4,4, -1);
        Piece[][] pieces = chessBoard.getChessBoard();
        assertEquals(true, pieces[4][4] instanceof Pawn);

    }
}
