/**
 * @author  SeokHyun Song
 * @version 1.0
 * @since   2017-09-17
 */

package Pieces;

import BoardInfo.*;

public class Bishop extends Piece{

    public Bishop(int x, int y, int id) {
        super(x,y,id);
    }

    public Bishop(Board chessBoard, int x, int y, int id) {
        super(chessBoard, x, y, id);
     }

    /**
     * @param chessBoard is a current chess board.
     * @param destX      is a destination on x-axis.
     * @param destY      is a destination on y-axis.
     * @return this function returns TRUE if a bishop movement to destination is valid. Otherwise, returns FALSE.
     */
    @Override
    public boolean isValidMove (Board chessBoard, int destX, int destY) {
        if (!super.isValidMove(chessBoard, destX, destY)) {
           return false;
        }

        int x = getCurrentX();
        int y = getCurrentY();

        if ( (Math.abs(destX - x) != Math.abs(destY - y)) || isLeaping(chessBoard, destX, destY)) {
            return false;
        }

        return true;
    }

    /**
     * @param chessBoard is a current chess board.
     * @param destX      is a destination on x-axis.
     * @param destY      is a destination on y-axis.
     * @return this function returns TRUE if a bishop does leap over other pieces. Otherwise, returns FALSE.
     */
    private boolean isLeaping (Board chessBoard, int destX, int destY) {
        int x = getCurrentX();
        int y = getCurrentY();
        do {
            x = getDirection(x, destX);
            y = getDirection(y, destY);

            if (x==destX && y == destY){
                return false;
            }
            if (!super.isValidMove(chessBoard, x, y) || chessBoard.isOccupied(x,y)) {
                return true;
            }
        } while ( destX != x || destY != y );

        return false;
    }
}
