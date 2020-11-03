package edu.msu.brandta7.project1_9;

import android.os.Parcel;
import android.os.Parcelable;

public class Node implements Parcelable {

    /**
     * Coordinates for the center of the space
     */
    private int pixX;
    private int pixY;

    /**
     * Coordinates for place on the board
     * (0,0) starting at top left
     */
    private int x;
    private int y;

    /**
     * If a node was visited while checking for moves
     */
    private boolean visited;

    /**
     * The Piece in this space
     * If space is empty Piece = null
     */
    private Piece piece;

    public Node(int xLoc, int yLoc, int pixX, int pixY, boolean visited, Piece piece){
        this.x = xLoc;
        this.y = yLoc;
        this.pixX = pixX;
        this.pixY = pixY;
        this.piece = piece;
    }

    protected Node(Parcel in) {
        pixX = in.readInt();
        pixY = in.readInt();
        x = in.readInt();
        y = in.readInt();
        piece = in.readParcelable(Piece.class.getClassLoader());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPixX() {
        return pixX;
    }

    public int getPixY() {
        return pixY;
    }

    public boolean isVisited() {
        return visited;
    }

    public static final Creator<Node> CREATOR = new Creator<Node>() {
        @Override
        public Node createFromParcel(Parcel parcel) {
            return new Node(parcel);
        }

        @Override
        public Node[] newArray(int i) {
            return new Node[0];
        }
    };

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(pixX);
        parcel.writeInt(pixY);
        parcel.writeInt(x);
        parcel.writeInt(y);
        parcel.writeParcelable(piece, i);
    }

    /**
     * Adjust location of red tiles
     * @param currentLength The current length of the squares
     * @param oldLength The length of the squares in the previous orientation
     */
    public void adjustCoords(int currentLength, int oldLength) {

        int row = pixX / oldLength;
        int col = pixY / oldLength;

        pixX = 0;
        pixY = 0;

        pixX += currentLength * row + currentLength / 2;
        pixY += currentLength * col + currentLength / 2;

    }
}
