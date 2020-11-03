package edu.msu.brandta7.project1_9;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Player implements Parcelable {

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

    /**
     * A list of this player's pieces
     */
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

    protected Player(Parcel in) {
        name = in.readString();
        team = in.readInt();
        moved = in.readByte() != 0;
        pieces = in.readArrayList(Piece.class.getClassLoader());
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel parcel) {
            return new Player(parcel);
        }

        @Override
        public Player[] newArray(int i) {
            return new Player[i];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(team);
        dest.writeByte((byte)(moved ? 1 : 0));
        dest.writeList(pieces);
    }

    public String getName() { return name; }

    /**
     * Adjusts the coordinates of this player's pieces
     * @param spaceLength The current length of a square
     * @param oldLength The length of the squares in the previous orientation
     */
    public void adjustCoords(int spaceLength, int oldLength) {
        for (Piece piece : pieces) {
            piece.adjustCoords(spaceLength, oldLength);
        }
    }
}
