package edu.msu.brandta7.project1_9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Piece {

    private boolean isKing;

    private int xLoc;

    private int yLoc;

    private Bitmap regPiece;

    private Bitmap kingPiece;

    /**
     * 0 and 1 represent different color pieces
     * 0: White
     * 1: Green
     */
    private int team;

    public Piece(Context context, int x, int y, int team){

        xLoc = x;
        yLoc = y;
        this.team = team;

        if(this.team == 0){
            regPiece = BitmapFactory.decodeResource(context.getResources(), R.drawable.white);
            kingPiece = BitmapFactory.decodeResource(context.getResources(), R.drawable.king_white);
        }
        else if(this.team == 1){
            regPiece = BitmapFactory.decodeResource(context.getResources(), R.drawable.green);
            kingPiece = BitmapFactory.decodeResource(context.getResources(), R.drawable.king_green);
        }

        isKing = false;
    }

    public void draw(Canvas canvas, int spaceLength){
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(xLoc + 15, yLoc + 15);

        // Scale it to the right size
        float scaleFactor = (float)(spaceLength * .95) / (float)(regPiece.getWidth());
        canvas.scale(scaleFactor, scaleFactor);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(-regPiece.getWidth() / 2f, -regPiece.getHeight() / 2f);

        // Draw the bitmap
        canvas.drawBitmap(regPiece, 0, 0, null);
        canvas.restore();
    }
}
