package Pieces;

import BoardInfo.Board;
import Player.*;

/**
 * This is imagery piece that I created.
 * Its movement is very simple. Guardian only can move to any location around its allied king.
 * However, if its destination is occupied other pieces, it can't move to that location.
 * In addition, this piece can not capture other pieces including opponent pieces.
 */
public class Guardian extends Piece{
    public Guardian (int x, int y, int id) {
        super(x,y,id);
    }
    public Guardian (Board chessBoard, int x, int y, int id) {
        super(chessBoard, x, y, id);
    }

    /**
     * @param chessBoard is a current chess board.
     * @param destX      is a destination on x-axis.
     * @param destY      is a destination on y-axis.
     * @return TRUE if it is a valid move. Otherwise returns FALSE
     */
    @Override
    public boolean isValidMove(Board chessBoard,int destX, int destY) {
        Player player = chessBoard.getMyPlayer(this.getId());
        King myKing = player.getKing();

        if (myKing == null){
            return false;
        }

        int distX = Math.abs(destX - myKing.getCurrentX());
        int distY = Math.abs(destY - myKing.getCurrentY());

        //Can't move to its king's location
        if (myKing.getCurrentX() == destX && myKing.getCurrentY() == destY){
            return false;
        }

        if (chessBoard.isOccupied(destX, destY)){
            return false;
        }

        if (distX > 1 || distY > 1){
            return false;
        }

        return true;
    }

}
