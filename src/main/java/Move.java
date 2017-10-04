import BoardInfo.Board;
import Pieces.*;
import Player.*;

import java.util.ArrayList;
import java.util.Random;

public class Move {
    /**
     * @param board is a chess board that players are playing on
     * @param p is a piece that you want to move
     * @param toX is a x coordinate of destination
     * @param toY is a y coordinate of destination
     * @return TRUE if moveTo was successful. False, otherwise.
     */
    public boolean moveTo (Board board, Piece p, int toX, int toY) {
        try {
            boolean moved = false;

            if (p.isValidMove(board, toX, toY)) {
                if (board.isOccupied(toX, toY)) {
                    moveToCapture(board, p, board.getChessBoard()[toX][toY]);
                    if (p instanceof Witch) {
                        summonPawn(board, board.getMyPlayer(p.getId()), p);
                    }
                    moved = true;
                }
                else {
                    board.capture(toX, toY, p);
                    moved = true;
                }
            }

            Player me = board.getMyPlayer(p.getId());
            Player opponent = board.getOpponent(p.getId());

            setCheck(board, p);
            setStaleMate(board, me.getKing());
            setStaleMate(board, opponent.getKing());

            return moved;

        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        }

        return false;
    }

    /**
     * @param chessboard is a chess board that players are playing on
     * @param capturing is a capturing piece
     * @param captured is a captured piece
     * It is a helper function for moveTo
     */
    public void moveToCapture (Board chessboard, Piece capturing, Piece captured) {
        if (captured == null) {
            return;
        }

        int destX = captured.getCurrentX();
        int destY = captured.getCurrentY();

        if (capturing.isValidMove(chessboard, destX, destY)) {
            captured.setLife(false);
            captured.setCurrentX(-1);
            captured.setCurrentY(-1);
            chessboard.capture(destX, destY, capturing);
        }

    }

    /**
     * @param chessBoard is a chess board that players are playing on
     * @param pieces is a piece that tries to check the king
     * @param king a king piece that you want to check for
     * @return TRUE if the piece made checkmate. False, otherwise.
     */
    public static boolean isCheck(Board chessBoard, ArrayList<Piece> pieces, King king){
        if (pieces.size() == 0 || king == null){
            return false;
        }
        for (int numPiece = 0; numPiece < pieces.size(); numPiece ++){
            if(pieces.get(numPiece).isValidMove(chessBoard, king.getCurrentX(), king.getCurrentY())){
                return true;
            }
        }
        return false;
    }

    /**
     * @param chessBoard is a chess board that players are playing on
     * @param p is a piece inputted into moveTo function
     * This is a helper function for moveTo function. It checks and sets for any check condition for both players
     */
    public static void setCheck(Board chessBoard, Piece p) {
        Player opponent = chessBoard.getOpponent(p.getId());
        Player me = chessBoard.getMyPlayer(p.getId());
        King enemyKing = opponent.getKing();
        ArrayList<Piece> myPieces = chessBoard.getMyPlayer(p.getId()).getPieces();
        if(isCheck(chessBoard, myPieces, enemyKing)){
            chessBoard.getOpponent(p.getId()).setKingCheck(true);
        }
        else {
            chessBoard.getOpponent(p.getId()).setKingCheck(false);
        }

        if(isCheck(chessBoard, opponent.getPieces(), me.getKing())){
            chessBoard.getMyPlayer(p.getId()).setKingCheck(true);
        }
        else {
            chessBoard.getMyPlayer(p.getId()).setKingCheck(false);
        }

     }

    /**
     * @param chessBoard is a chess board that players are playing on
     * @param king is the piece that I want to check for stalemate
     * It checks and sets stalemate condition
     */
    public static void setStaleMate(Board chessBoard, King king) {
        Player me = chessBoard.getMyPlayer(king.getId());
        Player opponent = chessBoard.getOpponent((king.getId()));
        boolean[][] temp = king.validMoveAroundKing(chessBoard, opponent.getPieces());

        if (!king.validLegalMove(chessBoard, temp)) {
            chessBoard.getMyPlayer(king.getId()).setStaleMate(true);
        }
    }

    /**
     * @param chessBoard is a chess board that players are playing on
     * @param player is a player that is summoning the pawn
     * @param p is the piece player's witch
     * When a witch captures other pieces, it summons a pawn. This method is for summoning pawn.
     */
    public void summonPawn(Board chessBoard, Player player, Piece p){
        Random rand = new Random();
        int x;
        int y;
        do{
            x = rand.nextInt(((p.getCurrentX()+1)-(p.getCurrentX()-1)) + 1)  + p.getCurrentX()-1;
            System.out.println(x);
            y = rand.nextInt(((p.getCurrentY()+1)-(p.getCurrentY()-1)) + 1)  + p.getCurrentY()-1;
            System.out.println(y);
        }
        while (!(x >= 0 && x < 8 && y >=0 && y < 8) || chessBoard.isOccupied(x,y) == true);

        Pawn summoned = new Pawn (chessBoard, x,y,player.getId());
        player.setPiece(summoned);
        chessBoard.getChessBoard()[x][y] = summoned;
    }
}
