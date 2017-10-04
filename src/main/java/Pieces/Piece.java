/**
 * Piece class that is a super class of chess pieces
 *
 * @author  SeokHyun Song
 * @version 1.0
 * @since   2017-09-17
 */

package Pieces;

import BoardInfo.*;

public class Piece {

    //currentX represents current location of x-axis in chess board.
    private int currentX;

    //currentY represents current location in y-axis in chess board.
    private int currentY;

    //indicates which player owns a piece
    private int id;

    //If it is true, the peice is alive. Otherwise, it is dead.
    private boolean life;

    public Piece(int x, int y, int id) {
        this.currentX = x;
        this.currentY = y;
        this.id = id;
        this.life = true;
    }

    public Piece(Board chessBoard, int x, int y, int id) {
        this.currentX = x;
        this.currentY = y;
        this.id = id;
        this.life = true;
        chessBoard.changePieceTo(x, y, this);
    }

    /**
     * @return Returns true if this piece is alive. Otherwise, returns false.
     */
    public boolean isLive () {
        return this.life;
    }

    /**
     * @param chessBoard is a current chess board.
     * @param destX is a destination on x-axis.
     * @param destY is a destination on y-axis.
     * @return if a movement to destination does not go out of chess board, it returns true. Otherwise, returns false.
     */
    public boolean isValidMove (Board chessBoard, int destX, int destY) {
        int width = chessBoard.getBoardWidth();
        int height = chessBoard.getBoardHeight();
        if ( (destX >= width) || (destY >= height) || (destX < 0) || (destY < 0) ){
            return false;
        }

        if ((chessBoard.isOccupied(destX,destY) && chessBoard.getChessBoard()[destX][destY].getId() == this.getId())) {
            return false;
        }
        return true;
    }

    //getter for currentX
    public int getCurrentX() {
        return currentX;
    }

    //getter for currentY
    public int getCurrentY() {
        return currentY;
    }

    //getter for life
    public boolean getLife() { return life; }

    //getter for id
    public int getId() {
        return this.id;
    }

    //setter for id
    public void setId (int id) {
        this.id = id;
    }
    //setter for currentX
    public void setCurrentX(int x) {
        currentX = x;
    }

    //setter for currentY
    public void setCurrentY(int y) {
        currentY = y;
    }

    //set x and y coordinates at once
    public void setXY (int x, int y) {
        currentX = x;
        currentY = y;
    }

    //setter for theLife
    public void setLife (boolean theLife) {
        life = theLife;
    }

    //Get direction for the piece. This is kinds of helper function for isLeaping functions in Witch, Queen, Rook and Bishop classes.
    public int getDirection (int cur, int dest) {
        if (dest - cur > 0) {
            cur++;
        } else if (dest == cur ) {
            return cur;
        }
        else{
            cur--;
        }
        return cur;
    }
}
