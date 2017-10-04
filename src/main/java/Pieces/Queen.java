package Pieces;

import BoardInfo.*;

public class Queen extends Piece{

    public Queen(int x, int y, int id) {
        super(x, y, id);
    }
    public Queen(Board chessBoard, int x, int y, int id) {
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

        do {
            //diagonal case
            if (Math.abs(getCurrentX()-destX) == Math.abs(getCurrentY()-destY)) {
                x = getDirection(x, destX);
                y = getDirection(y, destY);

            }
            //straight case
            else {
                if (destX - x != 0) {
                    x = getDirection(x, destX);
                }

                else {
                    y = getDirection(y, destY);
                }
            }
            if (!(destX == x && destY == y)) {
                if ((!super.isValidMove(chessBoard, x, y)) || chessBoard.isOccupied(x, y)) {
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
        boolean leap = isLeaping(chessBoard, destX, destY);
        if (( Math.abs(getCurrentX()-destX) == Math.abs(getCurrentY()-destY)) && !leap) {
            return true;
        }

        if ((destY == getCurrentY()) && (destX - getCurrentX() !=0) && !leap){
            return true;
        }

        if ((destX == getCurrentX()) && (destY - getCurrentY() !=0) && !leap){
            return true;
        }

        return false;
    }
}
