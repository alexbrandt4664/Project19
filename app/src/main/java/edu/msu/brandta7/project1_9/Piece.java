package edu.msu.brandta7.project1_9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;

public class Piece implements Parcelable {

    /**
     * Bool for whether or not this piece is a king
     */
    private boolean isKing;

    /**
     * The x coordinate of the piece
     */
    private int xLoc;

    /**
     * The y coordinate of the piece
     */
    private int yLoc;

    /**
     * Bitmap for the regular piece
     */
    private Bitmap regPiece;

    /**
     * Bitmap for the king piece
     */
    private Bitmap kingPiece;

    /**
     * 0 and 1 represent different color pieces
     * 0: White
     * 1: Green
     */
    private int team;

    protected Piece(Parcel in) {
        loadBitmaps(App.getContext());
        isKing = in.readByte() != 0;
        xLoc = in.readInt();
        yLoc = in.readInt();
        team = in.readInt();
    }

    /**
     * Cosntructor
     * @param context The context
     * @param x The x coordinate
     * @param y The y coordinate
     * @param team This piece's team
     */
    public Piece(Context context, int x, int y, int team){

        xLoc = x;
        yLoc = y;
        this.team = team;

        loadBitmaps(context);

        isKing = false;
    }

    /**
     * Draw function for a piece
     * @param canvas The canvas object
     * @param spaceLength The length of the space the piece is in
     */
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
        if (isKing){
            canvas.drawBitmap(kingPiece, 0, 0, null);
        }
        else {
            canvas.drawBitmap(regPiece, 0, 0, null);
        }

        canvas.restore();
    }

    public boolean isKing() {
        return isKing;
    }

    public void setKing(boolean king) {
        isKing = king;
    }

    public void setxLoc(int xLoc) {
        this.xLoc = xLoc;
    }

    public void setyLoc(int yLoc) {
        this.yLoc = yLoc;
    }

    public int getTeam() {
        return team;
    }

    /**
     * Load the bitmaps into their respective references
     * @param context The context
     */
    private void loadBitmaps(Context context) {
        if(this.team == 0){
            regPiece = BitmapFactory.decodeResource(context.getResources(), R.drawable.white);
            kingPiece = BitmapFactory.decodeResource(context.getResources(), R.drawable.king_white);
        }
        else if(this.team == 1){
            regPiece = BitmapFactory.decodeResource(context.getResources(), R.drawable.green);
            kingPiece = BitmapFactory.decodeResource(context.getResources(), R.drawable.king_green);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte)(isKing ? 1 : 0));
        parcel.writeInt(xLoc);
        parcel.writeInt(yLoc);
        parcel.writeInt(team);
    }

    public static final Creator<Piece> CREATOR = new Creator<Piece>() {
        @Override
        public Piece createFromParcel(Parcel parcel) {
            return new Piece(parcel);
        }

        @Override
        public Piece[] newArray(int i) {
            return new Piece[i];
        }
    };

    /**
     * Adjust the coordinates of the piece based on the new orientation
     * @param currentLength The length of a square in the current board
     * @param oldLength The length of a square in the old orientation
     */
    public void adjustCoords(int currentLength, int oldLength) {

        int row = xLoc / oldLength;
        int col = yLoc / oldLength;

        xLoc = currentLength * row + currentLength / 2;
        yLoc = currentLength * col + currentLength / 2;

    }
}
