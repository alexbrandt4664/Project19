package edu.msu.brandta7.project1_9;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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

        board.draw(canvas);

        ArrayList<Piece> pieces= board.getPieces();
        for(Piece piece : pieces){
            piece.draw(canvas, spaceLength);
        }

    }
}
