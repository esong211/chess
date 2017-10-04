package Player;

import Pieces.*;

import java.util.ArrayList;

public class Player {

    //chess pieces that the player has.
    private ArrayList<Piece> pieces = new ArrayList<Piece>();
    private boolean turn = false;
    private int score = 0;
    //player ID
    private int id;

    //name of the player
    private String name = null;

    //set into true if a king of the player is in Checkmate
    private boolean kingCheck = false;
    private boolean staleMate = false;

    public Player (int playerId) {
        id = playerId;
    }

    //getters
    public int getId() {
        return id;
    }
    public ArrayList<Piece> getPieces() {
        return pieces;
    }
    public int getScore() {
        return score;
    }
    public String getName() {
        return name;
    }

    //setters
    public void setName(String s) {
        name = s;
    }
    public void incrScore() {
        score ++;
    }
    public void setPiece(Piece piece) {
        pieces.add(piece);
    }

    //returns king of a player
    public King getKing(){
        for (int i = 0; i < pieces.size(); i ++) {
            if (pieces.get(i) instanceof King) {
                return (King)pieces.get(i);
            }
        }
        return null;
    }

    //setter for kingCheck
    public void setKingCheck(boolean check) {
        kingCheck = check;
    }

    //setter for staleMate
    public void setStaleMate(boolean check) { staleMate = check; }

    //getter for kingCheck
    public boolean getKingCheck() {
        return kingCheck;
    }

    //getter for staleMate
    public boolean getStaleMate() {
        return staleMate;
    }

}
