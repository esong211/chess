/**
 * @author  SeokHyun Song
 * @version 1.0
 * @since   2017-09-17
 */

package Pieces;

import BoardInfo.*;

import java.util.ArrayList;

public class King extends Piece{
    public King(int x, int y, int id) {
        super(x,y,id);
    }
    public King(Board chessBoard, int x, int y, int id) {
        super(chessBoard, x, y, id);
    }

    /**
     * @param chessBoard is a current chess board.
     * @param destX      is a destination on x-axis.
     * @param destY      is a destination on y-axis.
     * @return returns TRUE if movement to destination is valid. Otherwise, returns FALSE
     */
    public boolean isValidMove (Board chessBoard, int destX, int destY) {
        if (!super.isValidMove(chessBoard, destX, destY)) {
            return false;
        }

        //check for stalemate case
        ArrayList<Piece> opponentPieces = chessBoard.getOpponent(this.getId()).getPieces();

        boolean[][] atNearKing = validMoveAroundKing(chessBoard, opponentPieces);
        boolean possible = validLegalMove(chessBoard, atNearKing);

        if(!possible){
            return false;
        }

        //check for basic movement case
        boolean basic = basicMove(chessBoard, destX, destY);

        if ( basic && atNearKing[destX][destY] == false ) {
            return true;
        }
        return false;
    }

    /**
     * @param chessboard is a chess board that players are playing on
     * @param pieces is a array list of pieces of a player
     * @return boolean 2d array
     * For each piece on the array list, it checks whether the piece can move to position where around the king.
     */
    public boolean[][] validMoveAroundKing (Board chessboard, ArrayList<Piece> pieces) {
        boolean[][] atNearKing = new boolean[chessboard.getBoardWidth()][chessboard.getBoardHeight()];
        atNearKing[getCurrentX()][getCurrentY()] = true;
        for (int i = 0; i < pieces.size(); i++) {
            for (int x = this.getCurrentX()-1; x < this.getCurrentX() + 2; x++) {
                for (int y = this.getCurrentY()-1; y < this.getCurrentY() + 2; y++) {

                    if (!(pieces.get(i) instanceof King)){
                        if (super.isValidMove(chessboard, x, y)) {
                            if ((pieces.get(i).getCurrentX() == x && pieces.get(i).getCurrentY() == y) && canBeProtected(chessboard, pieces, x, y)){
                                atNearKing[x][y] = true;
                            }
                            else if (pieces.get(i).isValidMove(chessboard, x, y)) {
                                atNearKing[x][y] = true;
                            }
                        }
                    }
                    //case the piece is a king
                    else{
                        if(((King) pieces.get(i)).basicMove(chessboard, x, y)){
                            atNearKing[x][y] = true;
                        }
                    }

                }
            }
        }

        return atNearKing;
    }

    /**
     * @param chessBoard
     * @param aroundKing is a 2d array returned by validMoveAroundKing function
     * @return TRUE if there is legal movement that the king can move to. False, otherwise.
     */
    public boolean validLegalMove (Board chessBoard, boolean[][] aroundKing) {
        for (int i = getCurrentX() - 1; i < getCurrentX() + 2; i++) {
            for (int j = getCurrentY() - 1; j < getCurrentY() + 2; j++){
                if (i >= 0 && j >= 0 && i < (chessBoard.getBoardHeight()-1) && j < (chessBoard.getBoardWidth()-1)) {
                    if (!aroundKing[i][j]) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * @param destX is a x coordinate of the destination
     * @param destY is a y coordinate of the destination
     * @return TRUE if the King's movement is valid. FALSE otherwise.
     *
     * This function checks for King's basic movement;
     */
    public boolean basicMove (Board chessBoard, int destX, int destY) {
        if (!super.isValidMove(chessBoard, destX, destY)) {
            return false;
        }
        int absX = Math.abs(getCurrentX() - destX);
        int absY = Math.abs(getCurrentY() - destY);

        double distance = Math.sqrt(Math.pow(absX, 2) + Math.pow(absY, 2));

        if ( ((distance == 1.0) || (distance == Math.sqrt(2)))) {
            return true;
        }
        return false;
    }

    /**
     * @param chessBoard
     * @param pieces
     * @param destX
     * @param destY
     * @return tells whether the piece at destination can be protected from other allied pieces.
     *         If it can be covered by other pieces, the function returns TRUE. Otherwise, FALSE
     */
    public boolean canBeProtected (Board chessBoard, ArrayList<Piece> pieces, int destX, int destY){
        for (int i = 0 ;i <pieces.size(); i++) {
            if (pieces.get(i) instanceof Guardian) {
                continue;
            }

            if (!(pieces.get(i) instanceof King)) {
                int origId = pieces.get(i).getId();
                pieces.get(i).setId(-1);
                boolean check = pieces.get(i).isValidMove(chessBoard, destX, destY);
                pieces.get(i).setId(origId);

                if(check){
                    return true;
                }
            }
            else {
                if (((King)pieces.get(i)).basicMove(chessBoard, destX,  destY)) {
                    return true;
                }
            }
        }
        return false;
    }
}
