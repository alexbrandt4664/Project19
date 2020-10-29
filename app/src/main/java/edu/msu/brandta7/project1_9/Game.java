package edu.msu.brandta7.project1_9;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Game {

    private Board board;

    private GameView gameView;

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
        spaceLength = (int)(boardDim / 8);

        board = new Board(context, minDim, boardDim, spaceLength);

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

        // Check that the selected piece is the current player's and that the current player hasn't moved yet
        if (!current.getMoved() && (piece == null || piece.getTeam() == current.getTeam())) {

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
                    for (Node jump: jumps) {
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
        if (current == playerA) {
            current = playerB;
        }
        else {
            current = playerA;
        }

        // Reset the move status of the current player
        current.setMove(false);
    }
}
