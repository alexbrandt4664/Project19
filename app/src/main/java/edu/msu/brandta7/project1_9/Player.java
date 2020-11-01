package edu.msu.brandta7.project1_9;

import java.util.ArrayList;

public class Player {

    /**
     * Name of the player
     */
    private String name;

    /**
     * Team number
     * 0: Player A
     * 1: Player B
     */
    private int team;

    /**
     * Whether or not the player has moved for their current turn
     */
    private boolean moved;

    private ArrayList<Piece> pieces;

    /**
     * Constructor
     * @param name The name of the player
     * @param teamNum The player's team number
     */
    public Player(String name, int teamNum) {
        this.name = name;
        this.team = teamNum;
        moved = false;
        pieces = new ArrayList<>();
    }

    public int getTeam() { return  team; }

    public void setMove(boolean status) {
        moved = status;
    }

    public boolean getMoved() { return moved; }

    /**
     * Adds a piece to this player's list of pieces
     * @param piece The piece to add
     */
    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    /**
     * Removes the piece from this player's list of pieces
     * @param piece The piece to remove
     */
    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    /**
     * Check whether or not this player is out of pieces
     * @return True if this player has run out of pieces, false otherwise
     */
    public boolean hasPieces() {
        return pieces.size() != 0;
    }
}
