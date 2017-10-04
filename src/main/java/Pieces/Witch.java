package Pieces;

import BoardInfo.Board;

/**
 * This is an imagery piece class called WITCH. The witch can make a movement to any location that sums up to 2 or less.
 * The witch cannot jump over other pieces while moving.
 * For example, movement from (0,0) to (1,1) is valid movement for the piece because sum of change of x and change of y
 * is 2. Movement from (0,0) to (2,1) is not a valid movement since sum of change of x and change of y is 3.
 */
public class Witch extends Piece{
    public Witch(int x, int y, int id) {
        super(x, y, id);
    }

    public Witch(Board chessBoard, int x, int y, int id) {
        super(chessBoard, x, y, id);
    }

    /**
     * @param chessBoard is a current chess board.
     * @param destX      is a destination on x-axis.
     * @param destY      is a destination on y-axis.
     * @return this function returns TRUE if a witch movement to destination is valid. Otherwise, returns FALSE.
     */
    @Override
    public boolean isValidMove (Board chessBoard, int destX, int destY) {
        //check if sum of change of x and change of y are smaller than or equal to 2.
        if (!super.isValidMove(chessBoard, destX, destY)) {
            return false;
        }

        int changeX = Math.abs(destX - this.getCurrentX());
        int changeY = Math.abs(destY - this.getCurrentY());

        if (changeX + changeY > 2) {
            return false;
        }

        //jump over other pieces case
        if (isLeaping(chessBoard, destX, destY)){
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
            if(x != destX){
                x = getDirection(x, destX);
            }

            if(y != destY){
                y = getDirection(y, destY);
            }

            if( x == destX && y == destY){
                return false;
            }

            if (!super.isValidMove(chessBoard, x, y) || chessBoard.isOccupied(x,y)) {
                return true;
            }
        } while ( destX != x || destY != y );

        return false;
    }
}
