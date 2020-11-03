package edu.msu.brandta7.project1_9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;

public class Piece implements Parcelable {

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

    /**
     * An ID to uniquely identify this piece
     */
    private int id;

    protected Piece(Parcel in) {
        loadBitmaps(App.getContext());
        isKing = in.readByte() != 0;
        xLoc = in.readInt();
        yLoc = in.readInt();
        team = in.readInt();
    }

    public Piece(Context context, int x, int y, int team){

        xLoc = x;
        yLoc = y;
        this.team = team;

        loadBitmaps(context);

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
     *
     * @param currentLength
     */
    public void adjustCoords(int currentLength, int oldLength) {

        int row = xLoc / oldLength;
        int col = yLoc / oldLength;

        xLoc = 0;
        yLoc = 0;

        xLoc += currentLength * row + currentLength / 2;
        yLoc += currentLength * col + currentLength / 2;

    }
}
