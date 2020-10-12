package edu.msu.brandta7.project1_9;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;

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
     * Reference for height and width
     */
    private Bitmap mainBoard;

    public Game(Context context, GameView gameView){

        this.gameView = gameView;

        mainBoard = BitmapFactory.decodeResource(context.getResources(), R.drawable.board);

        /**
         * Hard coded need to be changed
         */

        int orientation = context.getResources().getConfiguration().orientation;
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            width = height;
        }
        else {
            height = width;
        }

        int minDim = width;

        boardDim = minDim - 30;
        spaceLength = (int)(boardDim / 8);

        board = new Board(context, minDim, boardDim, spaceLength);

    }

    public void draw(Canvas canvas){

        board.draw(canvas);

        ArrayList<Piece> pieces= board.getPieces();
        for(Piece piece : pieces){
            piece.draw(canvas, spaceLength);
        }
    }
}
