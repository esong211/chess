/**
 * @author  SeokHyun Song
 * @version 1.0
 * @since   2017-09-17
 */

package Pieces;

import BoardInfo.*;

public class Pawn extends Piece{

    //startPosX and startPosY are starting position of a pawn
    private int startPosX;
    private int startPosY;

    public Pawn (int x, int y, int id) {
        super(x,y,id);
        startPosX = x;
        startPosY = y;
    }

    public Pawn (Board chessBoard, int x, int y, int id) {
        super(chessBoard, x, y, id);
        startPosX = x;
        startPosY = y;
    }

    // setter for startPosX and startPosY
    public void setStartPos (int x, int y) {
        this.startPosX = x;
        this.startPosY = y;
    }

    //getter for startPosX
    public int getStartPosX () {
        return startPosX;
    }

    //getter for startPosY
    public int getStartPosY () {
        return startPosY;
    }
    /**
     * @param sourceX x coordinate of destination
     * @param sourceY y coordinate of destination
     * @return TRUE if a pawn is at starting position. FALSE, otherwise
     */
    private boolean isAtStartPos (int sourceX, int sourceY) {
        if (sourceX == startPosX && sourceY == startPosY){
            return true;
        }
        return false;
    }


    /**
     * @param chessBoard is a current chess board.
     * @param destX      is a destination on x-axis.
     * @param destY      is a destination on y-axis.
     * @return returns TRUE if movement to destination is valid. Otherwise, returns FALSE
     */
    @Override
    public boolean isValidMove (Board chessBoard, int destX, int destY) {
        if (!super.isValidMove(chessBoard, destX, destY)) {
            return false;
        }


        //case it is white player
        if (this.getId() == 2) {
            if (destX-getCurrentX() == 1 && destY-getCurrentY() == 0) {
                return true;
            }
            if (chessBoard.isOccupied(destX,destY) && (Math.abs(destY - getCurrentY()) == 1) && destX-getCurrentX() == 1) {
                return true;
            }

            if ((destX - getCurrentX() == 2) && (Math.abs(destY - getCurrentY()) == 0) && isAtStartPos(getCurrentX(), getCurrentY())) {
                if (!chessBoard.isOccupied(getCurrentX()+1, getCurrentY())) {
                    return true;
                }
            }

        }
        //case it is black player
        else {
            if (getCurrentX()-destX == 1 && destY-getCurrentY() == 0) {
                return true;
            }
            if (chessBoard.isOccupied(destX,destY) && (Math.abs(destY - getCurrentY()) == 1) && getCurrentX()-destX == 1) {
                return true;
            }
            if ((getCurrentX()-destX == 2) && (Math.abs(destY - getCurrentY()) == 0) && isAtStartPos(getCurrentX(), getCurrentY())) {
                if (!chessBoard.isOccupied(getCurrentX()-1, getCurrentY())) {
                    return true;
                }
            }
        }

        return false;
    }

}
