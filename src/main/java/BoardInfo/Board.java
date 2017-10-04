/**
 * Board class that construct chess board for chess game.
 *
 * @author  SeokHyun Song
 * @version 1.0
 * @since   2017-09-17
 */

package BoardInfo;

import Pieces.*;
import Player.Player;

import java.util.ArrayList;

public class Board {

    /**
     * 2d array [width][height] that represents chess board.
     * Value inside of each element represents a player ID.
     * If the value is 0, then the location is empty spot.
     */
    private Piece[][] chessBoard;

    //size of board width
    private int boardWidth = 0;

    //size of board height
    private int boardHeight = 0;

    //Getter for chessBoard
    public Piece[][] getChessBoard (){
        return this.chessBoard;
    }

    //Players
    Player player1;
    Player player2;

    //set up standard chess piece
    public void setUpStandardPiecesForBlack() {
        for (int i = 0; i < player1.getPieces().size(); i++ ) {
            player1.getPieces().remove(i);
        }
        player1.getPieces().add(new King(this,7,4,player1.getId()));

        for (int i = 0; i < 8; i ++) {
            player1.getPieces().add(new Pawn(this,6,i,player1.getId()));
        }
        player1.getPieces().add(new Rook(this,7,0,player1.getId()));
        player1.getPieces().add(new Rook(this,7,7,player1.getId()));

        player1.getPieces().add(new Queen(this,7,3,player1.getId()));

        player1.getPieces().add(new Knight(this,7,1,player1.getId()));
        player1.getPieces().add(new Knight(this,7,6,player1.getId()));

        player1.getPieces().add(new Bishop(this,7,2,player1.getId()));
        player1.getPieces().add(new Bishop(this,7,5,player1.getId()));
    }

    //Standard piece set up for the BLACK player
    public void setUpStandardPiecesForWhite(){
        for (int i = 0; i < player2.getPieces().size(); i++ ) {
            player2.getPieces().remove(i);
        }
        player2.getPieces().add(new King(this,0,3, player2.getId()));

        for (int i = 0; i < 8; i ++) {
            player2.getPieces().add(new Pawn(this,1,i, player2.getId()));
        }
        player2.getPieces().add(new Rook(this,0,7,player2.getId()));
        player2.getPieces().add(new Rook(this,0,0,player2.getId()));

        player2.getPieces().add(new Queen(this,0,4,player2.getId()));

        player2.getPieces().add(new Knight(this,0,1,player2.getId()));
        player2.getPieces().add(new Knight(this,0,6,player2.getId()));

        player2.getPieces().add(new Bishop(this,0,2,player2.getId()));
        player2.getPieces().add(new Bishop(this,0,5,player2.getId()));
    }

    //Custom piece set up for the Black player
    public void setUpCustomPieceForWhite() {
        for (int i = 0; i < player2.getPieces().size(); i++ ) {
            player2.getPieces().remove(i);
        }
        player2.getPieces().add(new King(this,0,3, player2.getId()));

        for (int i = 0; i < 8; i ++) {
            player2.getPieces().add(new Pawn(this,1,i, player2.getId()));
        }
        player2.getPieces().add(new Rook(this,0,7,player2.getId()));
        player2.getPieces().add(new Rook(this,0,0,player2.getId()));

        player2.getPieces().add(new Queen(this,0,4,player2.getId()));

        player2.getPieces().add(new Witch(this,0,1,player2.getId()));
        player2.getPieces().add(new Guardian(this,0,6,player2.getId()));

        player2.getPieces().add(new Bishop(this,0,2,player2.getId()));
        player2.getPieces().add(new Bishop(this,0,5,player2.getId()));
    }

    //set up standard chess piece
    public void setUpCustomPiecesForBlack() {
        for (int i = 0; i < player1.getPieces().size(); i++ ) {
            player1.getPieces().remove(i);
        }
        player1.getPieces().add(new King(this,7,4,player1.getId()));

        for (int i = 0; i < 8; i ++) {
            player1.getPieces().add(new Pawn(this,6,i,player1.getId()));
        }
        player1.getPieces().add(new Rook(this,7,0,player1.getId()));
        player1.getPieces().add(new Rook(this,7,7,player1.getId()));

        player1.getPieces().add(new Queen(this,7,3,player1.getId()));

        player1.getPieces().add(new Witch(this,7,1,player1.getId()));
        player1.getPieces().add(new Guardian(this,7,6,player1.getId()));

        player1.getPieces().add(new Bishop(this,7,2,player1.getId()));
        player1.getPieces().add(new Bishop(this,7,5,player1.getId()));
    }
    /**
     * @param width size of chess board width
     * @param height size of chess board height
     * @return 2d array with the input width and height
     * Constructor for Board class
     */
    public Board (int width, int height){
        this.chessBoard = new Piece[width][height];
        this.boardWidth = width;
        this.boardHeight = height;
    }

    /**
     * @return size of width of chess board
     */
    public int getBoardWidth (){
        return boardWidth;
    }

    /**
     * @return size of height of chess board
     */
    public int getBoardHeight (){
        return boardHeight;
    }

    //getter for player1
    public Player getPlayer1 () {
        return player1;
    }

    //getter for player2
    public Player getPlayer2 () {
        return player2;
    }

        //sync a player with a chess board player.
    public void setPlayer1 (Player player) {
        player1 = player;
    }
    public void setPlayer2 (Player player) {
        player2 = player;
    }

    /**
     * @param x location on x-axis.
     * @param y location on y-axis.
     * @return True if the current [x][y] location is occupied. Otherwise, returns false.
     */
    public boolean isOccupied (int x, int y){
        try {
            if( this.chessBoard[x][y] != null){
                return true;
            }
        } catch (IndexOutOfBoundsException e){
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        }

        return false;
    }

    /**
     * @param x location on x-axis.
     * @param y location on y-axis.
     * @param piece that you want to put in
     */
    public void changePieceTo (int x, int y, Piece piece){
        try{
            this.chessBoard[x][y] = piece;
        } catch (IndexOutOfBoundsException e){
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        }
    }

    /**
     * @param x location on x-axis.
     * @param y location on y-axis.
     *
     * Remove a piece on given location.
     */
    public void emptySpot (int x, int y){
        changePieceTo(x, y, null);
    }

    /**
     * @param x destination X
     * @param y destination Y
     * @param piece that you want to move to capture other piece.
     * It empties the current location of the piece and capture the piece at destination
     */
    public void capture (int x, int y, Piece piece) {
        emptySpot(piece.getCurrentX(), piece.getCurrentY());
        changePieceTo(x, y, piece);
        piece.setXY(x,y);
    }

    /**
     * @param id is a player id
     * @return Opponent player
     * It turns opponent player of the player who has the input id.
     */
    public Player getOpponent (int id) {
        if (player1.getId() == id) {
            return player2;
        }
        else if (player2.getId() == id) {
            return player1;
        }

        return null;
    }

    /**
     * @param id is a player id
     * @return a player corresponding to the input id.
     */
    public Player getMyPlayer (int id) {
        if (player1.getId() == id) {
            return player1;
        }
        else if (player2.getId() == id) {
            return player2;
        }

        return null;
    }

    /**
     * @param opponent is an opponent player
     * @param myCheckPiece is the piece that attacking the opponent's king
     * @param opPieces is an array list of opponent's pieces
     * @return TRUE if there is no legal move that opponent can make and opponent's king is under attack. Otherwise, FALSE.
     */
    public boolean checkMate (Player opponent, Piece myCheckPiece, ArrayList<Piece> opPieces) {
        if (!opponent.getStaleMate() && !opponent.getKingCheck()) {
            return false;
        }

        if (opponent.getKingCheck()) {
            for (int i = 0; i < opPieces.size(); i ++) {
                if (opPieces.get(i).isValidMove(this, myCheckPiece.getCurrentX(), myCheckPiece.getCurrentY())) {
                    return false;
                }
            }
            Player attacking = this.getOpponent(opponent.getId());
            boolean[][] aroundKing = opponent.getKing().validMoveAroundKing(this, attacking.getPieces());
            if (opponent.getKing().validLegalMove(this, aroundKing)) {
                return false;
            }
        }
        return true;
    }
}
