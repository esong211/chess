/**
 * @author  SeokHyun Song
 * @version 1.0
 * @since   2017-09-17
 */

package Pieces;

import BoardInfo.*;

public class Knight extends Piece{
    public Knight(int x, int y, int id) {
        super(x, y, id);
    }
    public Knight(Board chessBoard, int x, int y, int id) {
        super(chessBoard, x, y, id);
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

        int x = Math.abs(getCurrentX() - destX);
        int y = Math.abs(getCurrentY()-destY);


        if (x==1 && y==2){
            return true;
        }
        else if (x==2 && y==1) {
            return true;
        }


        return false;
    }
}
