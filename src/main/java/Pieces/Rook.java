/**
 * @author  SeokHyun Song
 * @version 1.0
 * @since   2017-09-17
 */

package Pieces;

import BoardInfo.*;

public class Rook extends Piece {
    public Rook(int x, int y, int id) {
        super(x, y, id);
    }

    public Rook(Board chessBoard, int x, int y, int id) {
        super(chessBoard, x, y, id);
    }

    /**
     * @param chessBoard is a current chess board.
     * @param destX      is a destination on x-axis.
     * @param destY      is a destination on y-axis.
     * @return this function returns TRUE if a Rook does leap over other pieces. Otherwise, returns FALSE.
     */
    private boolean isLeaping (Board chessBoard, int destX, int destY) {
        int x = getCurrentX();
        int y = getCurrentY();
        boolean leap = true;
        do {
            if (destX - x != 0) {
                x = getDirection(x, destX);
            }
            if (destY- y != 0) {
                y = getDirection(y, destY);
            }
            if (!(destX == x && destY == y)){
                if ((!super.isValidMove(chessBoard, x, y)) || chessBoard.isOccupied(x,y)) {
                    return true;
                }
            }
        } while ( destX != x || destY != y );

        return false;
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

        if ((destY == getCurrentY()) && (destX - getCurrentX() !=0) && !isLeaping(chessBoard, destX, destY)) {
            return true;
        }

        else if ((destX == getCurrentX()) && (destY - getCurrentY() !=0) && !isLeaping(chessBoard, destX, destY)) {
            return true;
        }

        return false;
    }


}
