package edu.msu.brandta7.project1_9;

public class Node {

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

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
