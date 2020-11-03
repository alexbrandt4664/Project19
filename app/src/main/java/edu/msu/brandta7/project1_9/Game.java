package edu.msu.brandta7.project1_9;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Game implements Parcelable {

    private Board board;

    /**
     * Dimension of the view
     */
    private int minDim;

    /**
     * Dimension of the board
     */
    private int boardDim;

    /**
     * Length between spaces
     */
    private int spaceLength;

    /**
     * Available moves based on the piece selected
     */
    private ArrayList<Node> moves = new ArrayList<Node>();

    /**
     * The selected node
     */
    private Node selected = null;

    /**
     * The current player
     */
    private Player current;

    private Player playerA;

    private Player playerB;

    // The previously selected node
    private Node previousNode;

    public Game(Context context){

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int orientation = context.getResources().getConfiguration().orientation;

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        // When horizontal, flip dimensions
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){

            int actionBar = 0;
            int statusBar = 0;

            // Get the height of the status bar
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBar = context.getResources().getDimensionPixelSize(resourceId);
            }

            // Get the height of the action bar
            TypedValue device = new TypedValue();
            if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, device, true)) {
                actionBar = TypedValue.complexToDimensionPixelSize(device.data,context.getResources().getDisplayMetrics());
            }

            // Set the width equal to the previous height minus the status and action bars
            width = height - (statusBar + actionBar);
        }

        minDim = width;

        boardDim = minDim - 30;
        spaceLength = boardDim / 8;

    }

    protected Game(Parcel in) {
        board = in.readParcelable(Board.class.getClassLoader());
        minDim = in.readInt();
        boardDim = in.readInt();
        spaceLength = in.readInt();
        selected = in.readParcelable(Node.class.getClassLoader());
        current = in.readParcelable(Player.class.getClassLoader());
        playerA = in.readParcelable(Player.class.getClassLoader());
        playerB = in.readParcelable(Player.class.getClassLoader());
        previousNode = in.readParcelable(Node.class.getClassLoader());
        moves = in.readArrayList(moves.getClass().getClassLoader());
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    /**
     * Create the board object
     * @param context The context to create it in
     */
    public void createBoard(Context context) {
        board = new Board(context, minDim, spaceLength, this);
    }

    public void draw(Canvas canvas){

        board.draw(canvas, moves);

        ArrayList<Piece> pieces= board.getPieces();
        for(Piece piece : pieces){
            piece.draw(canvas, spaceLength);
        }
    }

    public boolean onTouchEvent(View view, MotionEvent event){

        int relX = (int)event.getX();
        int relY = (int)event.getY();
        Node node = board.getNodeByPix(relX, relY);
        Piece piece = node.getPiece();

        // Check if you selected the previously selected node and deselect it
        if (previousNode != null &&
                (node.getPixX() == previousNode.getPixX() && node.getPixY() == previousNode.getPixY() && selected != null)) {
            // Deselect the current node
            selected = null;
            moves = new ArrayList();
        }

        // Check that the selected piece is the current player's and that the current player hasn't moved yet
        else if (!current.getMoved() && (piece == null || piece.getTeam() == current.getTeam())) {

            previousNode = node;

            // If a piece hasn't been selected get it's moves and mark selected
            if (selected == null){
                moves = board.getMoves(node);
                if (moves.size() > 0){
                    selected = node;
                }
            }
            else {

                // If a piece has been selected and touch on move
                if (moves.contains(node)){

                    // Remove pieces that have been jumped
                    ArrayList<Node> jumps = board.getJumps(selected, node);
                    Player other = current == playerA ? playerB : playerA;

                    for (Node jump: jumps) {
                        other.removePiece(jump.getPiece());
                        jump.setPiece(null);
                    }

                    // Move the piece
                    movePiece(selected.getX(), selected.getY(), node.getX(), node.getY());
                    selected = null;
                    moves.clear();
                }

                // If selected but move not touched
                if (selected != null){
                    moves = board.getMoves(node);
                    selected = node;
                }
            }
        }

        view.invalidate();

        return false;
    }

    /**
     * Move a piece to a new location
     * @param curX Current x
     * @param curY Current y
     * @param newX New x
     * @param newY New y
     */
    public void movePiece(int curX, int curY, int newX, int newY){

        Node curNode = board.getNodeByLoc(curX, curY);
        Node newNode = board.getNodeByLoc(newX, newY);

        Piece piece = curNode.getPiece();
        curNode.setPiece(null);

        if (piece != null) {
            piece.setxLoc(newNode.getPixX());
            piece.setyLoc(newNode.getPixY());
        }

        // Set the king piece
        if (piece != null && piece.getTeam() == 0 && newY == 7){
            piece.setKing(true);
        }
        else if (piece != null && piece.getTeam() == 1 && newY == 0){
            piece.setKing(true);
        }
        newNode.setPiece(piece);

        current.setMove(true);
    }

    /**
     * Create the two players in the game
     * @param a The name of player A
     * @param b The name of player B
     */
    public void createPlayers(String a, String b) {
        playerA = new Player(a, 0);
        playerB = new Player(b, 1);

        // Set the current player to A
        current = playerA;
    }

    /**
     * Change the current player of the game
     */
    public void changeCurrent() {
        current = (current == playerA) ? playerB : playerA;

        // Reset the move status of the current player
        current.setMove(false);
    }

    /**
     * Checks for if the current player lost
     * @return True if the current player lost, false otherwise
     */
    public boolean checkWinner() {

        // Check whether or not the current player doesn't have pieces
        return !current.hasPieces();
    }

    public Player getPlayerA() {
        return playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public Player getCurrent() { return current; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(board, i);
        parcel.writeInt(minDim);
        parcel.writeInt(boardDim);
        parcel.writeInt(spaceLength);
        parcel.writeParcelable(selected, i);
        parcel.writeParcelable(current, i);
        parcel.writeParcelable(playerA, i);
        parcel.writeParcelable(playerB, i);
        parcel.writeParcelable(previousNode, i);
        parcel.writeList(moves);
    }

    public void setBoardDim(int boardDim) { this.boardDim = boardDim; }

    public int getBoardDim() { return boardDim; }

    public void setMinDim(int min) { minDim = min; }

    public int getMinDim() { return minDim; }

    public void setSpaceLength(int spaceLength) { this.spaceLength = spaceLength; }

    public int getSpaceLength() { return spaceLength; }

    public Board getBoard() { return board; }
}
