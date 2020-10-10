package edu.msu.brandta7.project1_9;

public class Node {

    /**
     * Coordinates for the center of the space
     */
    private int x;
    private int y;

    /**
     * The Piece in this space
     * If space is empty Piece = null
     */
    private Piece piece;

    public Node(int xLoc, int yLoc, Piece piece){
        x = xLoc;
        y = yLoc;
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
