
import Pieces.Pawn;
import Pieces.Piece;
import junit.framework.TestCase;
import org.junit.Test;
import Player.*;

public class GameManagerTest extends TestCase {

    @Test
    public void testShowValidMove() {
        GameManager game = new GameManager();
        game.showValidMove(6,1);
        assertEquals(4, game.validMovePanels.get(0).x);
        assertEquals(1, game.validMovePanels.get(0).y);

        assertEquals(5, game.validMovePanels.get(1).x);
        assertEquals(1, game.validMovePanels.get(1).y);
    }

    @Test
    public void testEndGame() {
        GameManager game = new GameManager();
        game.endGame(1);
        Player turnPlayer = game.player1;
        assertEquals(1, turnPlayer.getScore());
        assertEquals(false, game.isPlaying);
    }

    @Test
    public void testUndo() {
        GameManager game = new GameManager();
        Pawn p = new Pawn(4,4,-1);
        game.addUndo(p, 4,4);
        assertEquals(4, game.undo.peek().getCurrentX());
        assertEquals(4, game.undo.peek().getCurrentY());
    }

    @Test
    public void testRedo() {
        GameManager game = new GameManager();
        Pawn p = new Pawn(4,4,-1);
        game.addRedo(p, 4,4);
        assertEquals(4, game.redo.peek().getCurrentX());
        assertEquals(4, game.redo.peek().getCurrentY());
    }

    @Test
    public void testdeepCopyObj() {
        GameManager game = new GameManager();
        Pawn pawn = new Pawn(4,4,-1);
        Piece p = game.deepCopyObj(pawn);
        assertEquals(true, p instanceof Pawn);
        assertEquals(4, p.getCurrentX());
        assertEquals(4, p.getCurrentY());
        assertEquals(-1, p.getId());
    }

    @Test
    public void testTurnName() {
        GameManager game = new GameManager();
        assertEquals("player1",game.turnName());

    }
}
