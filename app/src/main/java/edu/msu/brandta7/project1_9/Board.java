package edu.msu.brandta7.project1_9;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Paint outlinePaint;

    private Paint green;

    private Paint grey;

    private List<List<Node>> board = new ArrayList<List<Node>>(8);

    private int minDim;

    private int boardDim;

    private String test = "";

    private int spaceLength;

    public Board(Context context, int minDim, int boardDim, int spaceLength){

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(0xFF000000);

        green = new Paint(Paint.ANTI_ALIAS_FLAG);
        green.setColor(0xFFF0FFF0);

        grey = new Paint(Paint.ANTI_ALIAS_FLAG);
        grey.setColor(0xFFC0C0C0);

        this.minDim = minDim;
        this.boardDim = boardDim;
        this.spaceLength = spaceLength;

        // Initialize board array
        for(int i = 0; i < 8; i++)  {
            board.add(new ArrayList<Node>());
        }

        // Fill in board with nodes
        int pieceOffset = (int)(spaceLength / 2);
        int x_count = 0;
        int[] locs1 = {1, 5, 7};
        int[] locs2 = {0, 2, 6};
        boolean is1 = true;
        for(int x = pieceOffset; x < this.minDim - pieceOffset - 15; x += spaceLength){

            // Check the column
            if((x_count + 2) % 2 == 0){is1 = true;}
            else{is1 = false;}

            for(int y = pieceOffset; y < this.minDim - pieceOffset - 15; y += spaceLength){
                Piece piece = null;
                Node node = null;

                // If column is loc1 add a piece at those locations
                if (is1){
                    for (int i = 0; i < locs1.length; i++){
                        if(locs1[i] == board.get(x_count).size()){
                            if(y < (int)(minDim / 2)){
                                piece = new Piece(context, x, y, 0);
                            }
                            else{
                                piece = new Piece(context, x, y, 1);
                            }
                        }
                    }
                }
                // If column is loc2 add a piece at those locations
                else {
                    for (int i = 0; i < locs2.length; i++){
                        if(locs2[i] == board.get(x_count).size()){
                            if(y < (int)(minDim / 2)){
                                piece = new Piece(context, x, y, 0);
                            }
                            else{
                                piece = new Piece(context, x, y, 1);
                            }
                        }
                    }
                }

                node = new Node(x, y, piece);
                board.get(x_count).add(node);
            }
            x_count += 1;
        }
        int u = 0;
    }

    public void draw(Canvas canvas){

        // Draw the outline
        canvas.drawRect(0, 0,
                minDim, minDim, outlinePaint);

        // Draw a checkered pattern board
        boolean isGrey = false;
        Paint color;
        int count = 1;
        for (int x = 15; x < minDim - 30; x += spaceLength){
            if (count % 2 == 0){isGrey = true;}
            else {isGrey =false;}

            for (int y = 15; y < minDim - 30; y += spaceLength){
                if(isGrey){
                    color = grey;
                    isGrey = false;
                }
                else{
                    isGrey = true;
                    color = green;
                }
                canvas.drawRect(x, y,
                        x + spaceLength, y + spaceLength, color);
            }
            count += 1;
        }

    }

    public ArrayList<Piece> getPieces(){

        // Create a list of all pieces on the board
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board.get(i).get(j).getPiece() != null){
                    pieces.add(board.get(i).get(j).getPiece());
                }
            }
        }
        return pieces;
    }
}
