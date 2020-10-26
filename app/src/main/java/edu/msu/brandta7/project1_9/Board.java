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

    private Paint red;

    private List<List<Node>> board = new ArrayList<List<Node>>(8);

    private int minDim;

    private int boardDim;

    private int spaceLength;

    public Board(Context context, int minDim, int boardDim, int spaceLength){

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(0xFF000000);

        green = new Paint(Paint.ANTI_ALIAS_FLAG);
        green.setColor(0xFFF0FFF0);

        grey = new Paint(Paint.ANTI_ALIAS_FLAG);
        grey.setColor(0xFFC0C0C0);

        red = new Paint(Paint.ANTI_ALIAS_FLAG);
        red.setColor(0xFFFF6961);
        red.setAlpha(90);

        this.minDim = minDim;
        this.boardDim = boardDim;
        this.spaceLength = spaceLength;

        // Initialize board array
        for(int i = 0; i < 8; i++)  {
            board.add(new ArrayList<Node>());
        }

        int pieceOffset = (int)(spaceLength / 2);
        int x_count = 0;
        int y_count = 0;
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

                node = new Node(x_count, y_count, x, y, false, piece);
                board.get(x_count).add(node);
                y_count += 1;
            }
            x_count += 1;
            y_count = 0;
        }
    }

    public void draw(Canvas canvas, ArrayList<Node> moves){

        // Draw the outline
        canvas.drawRect(0, 0,
                minDim, minDim, outlinePaint);

        // Draw a checkered pattern board
        boolean isGrey = false;
        Paint color = null;
        int count = 1;
        for (int x = 15; x < minDim - 30; x += spaceLength) {
            if (count % 2 == 0) {
                isGrey = true;
            } else {
                isGrey = false;
            }

            for (int y = 15; y < minDim - 30; y += spaceLength) {
                if (isGrey) {
                    color = grey;
                }
                else{
                    color = green;
                }
                canvas.drawRect(x, y,
                        x + spaceLength, y + spaceLength, color);
                isGrey = !isGrey;
            }
            count += 1;
        }
        for (Node move: moves) {
            int x = move.getPixX() - (spaceLength / 2) + 15;
            int y = move.getPixY() - (spaceLength / 2) + 15;
            canvas.drawRect(x, y,
                    x + spaceLength, y + spaceLength, red);
        }
    }


    /**
     * Get moves associated with the piece
     * @param node node to find the moves for
     * @return ArrayList<Node> containing all moves
     */
    public ArrayList<Node> getMoves(Node node){

        ArrayList<Node> moves = new ArrayList<Node>();

        if (node != null){
            Piece piece = node.getPiece();

            if(piece != null){
                if (piece.isKing()){
                    kingMoves(node, node.getX(), node.getY(), moves);
                }
                else if (piece.getTeam() == 0){
                    findMoves(node, node.getX(), node.getY(), moves, false);
                }
                else if (piece.getTeam() == 1){
                    findMoves(node, node.getX(), node.getY(), moves, true);
                }
            }
        }

        return moves;
    }

    /**
     * Helper function for getMoves
     * @param start starting location
     * @param x starting x location
     * @param y starting y location
     * @param moves ArrayList<Node> containing all moves
     */
    private void kingMoves(Node start, int x, int y, ArrayList<Node> moves){
        findMoves(start, x, y, moves, false);
        findMoves(start, x, y, moves, true);
        //findMoves(start, x, y, moves, true, true);
    }

    /**
     * Helper function for getMoves
     * @param start starting location
     * @param x starting x location
     * @param y starting y location
     * @param moves ArrayList<Node> containing all moves
     */
    private void findMoves(Node start, int x, int y, ArrayList<Node> moves, boolean isUp){

        Node curNode = null;

        if (x >= 0 && x <= board.size() - 1 && y >= 0 && y <= board.get(0).size() - 1 && !getNodeByLoc(x, y).isVisited()){
            curNode = getNodeByLoc(x, y);
        }
        else {
            return;
        }
        if (curNode != start && curNode.getPiece() == null){
            moves.add(curNode);
        }
        else if (curNode != start && curNode.getPiece() != null){
            return;
        }

        Node leftNode = null;
        Node rightNode = null;

        if (isUp && x - 1 >= 0 && y - 1 >= 0){
            leftNode = getNodeByLoc(x - 1, y - 1);
        }
        else if (!isUp && x - 1 >= 0 && y + 1 <= board.get(x).size() - 1){
            leftNode = getNodeByLoc(x - 1, y + 1);
        }

        if (isUp && x + 1 <= board.size() - 1 && y - 1 >= 0){
            rightNode = getNodeByLoc(x + 1, y - 1);
        }
        else if (!isUp && x + 1 <= board.size() - 1 && y + 1 <= board.get(x).size() - 1){
            rightNode = getNodeByLoc(x + 1, y + 1);
        }

        if (leftNode != null && leftNode.getPiece() == null && start == curNode){
            moves.add(leftNode);
        }

        else if (leftNode != null && leftNode.getPiece() != null && leftNode.getPiece().getTeam() != start.getPiece().getTeam()){
            if (isUp){
                findMoves(start, leftNode.getX() - 1, leftNode.getY() - 1, moves, isUp);
            }
            else {
                findMoves(start, leftNode.getX() - 1, leftNode.getY() + 1, moves, isUp);
            }
        }

        if (rightNode != null && rightNode.getPiece() == null && start == curNode){
            moves.add(rightNode);
        }

        else if (rightNode != null && rightNode.getPiece() != null && rightNode.getPiece().getTeam() != start.getPiece().getTeam()){
            if (isUp){
                findMoves(start, rightNode.getX() + 1, rightNode.getY() - 1, moves, isUp);
            }
            else {
                findMoves(start, rightNode.getX() + 1, rightNode.getY() + 1, moves, isUp);
            }
        }
    }


    /**
     * Get the jumps associated with a move
     * @param node Starting location
     * @param target Target location
     * @return ArrayList<Node> containing all nodes
     */
    public ArrayList<Node> getJumps(Node node, Node target){
        ArrayList<Node> jumps = new ArrayList<Node>();

        if (node != null){
            Piece piece = node.getPiece();

            if(piece != null){
                if (piece.isKing()){
                    kingJumps(node, target, node.getX(), node.getY(), jumps);
                }
                else if (piece.getTeam() == 0){
                    findJumps(node, target, node.getX(), node.getY(), jumps, false);
                }
                else if (piece.getTeam() == 1){
                    findJumps(node, target, node.getX(), node.getY(), jumps, true);
                }
            }
        }

        return jumps;
    }

    /**
     * Helper function for getJumps
     * @param start Starting location
     * @param target Target location
     * @param x Starting x location
     * @param y Starting y location
     * @param jumps ArrayList<Node> containing all jump nodes
     */
    private void kingJumps(Node start, Node target, int x, int y, ArrayList<Node> jumps){
        findJumps(start, target, x, y, jumps, true);
        findJumps(start, target, x, y, jumps, false);
    }

    /**
     * Helper function for getJumps
     * @param start Starting location
     * @param target Target location
     * @param x Starting x location
     * @param y Starting y location
     * @param jumps ArrayList<Node> containing all jump nodes
     * @return The target node
     */
    private Node findJumps(Node start, Node target, int x, int y, ArrayList<Node> jumps, boolean isUp){

        Node curNode = null;

        if (x >= 0 && x <= board.size() - 1 && y >= 0 && y <= board.get(0).size() - 1 && !getNodeByLoc(x, y).isVisited()){
            curNode = getNodeByLoc(x, y);
        }
        else {
            return null;
        }

        if (curNode != start && curNode.getPiece() != null){
            return null;
        }

        if (curNode == target){
            return curNode;
        }

        Node leftNode = null;
        Node rightNode = null;

        if (isUp && x - 1 >= 0 && y - 1 >= 0){
            leftNode = getNodeByLoc(x - 1, y - 1);
        }
        else if (!isUp && x - 1 >= 0 && y + 1 <= board.get(x).size() - 1){
            leftNode = getNodeByLoc(x - 1, y + 1);
        }

        if (isUp && x + 1 <= board.size() - 1 && y - 1 >= 0){
            rightNode = getNodeByLoc(x + 1, y - 1);
        }
        else if (!isUp && x + 1 <= board.size() - 1 && y + 1 <= board.get(x).size() - 1){
            rightNode = getNodeByLoc(x + 1, y + 1);
        }

        Node node = null;
        if (leftNode != null && leftNode.getPiece() != null && leftNode.getPiece().getTeam() != start.getPiece().getTeam()){

            if (isUp){
                node = findJumps(start, target, leftNode.getX() - 1, leftNode.getY() - 1, jumps, isUp);
            }
            else {
                node = findJumps(start, target, leftNode.getX() - 1, leftNode.getY() + 1, jumps, isUp);
            }
            if (node == target){
                jumps.add(leftNode);
                return target;
            }
        }

        if (rightNode != null && rightNode.getPiece() != null && rightNode.getPiece().getTeam() != start.getPiece().getTeam()){

            if (isUp){
                node = findJumps(start, target, rightNode.getX() + 1, rightNode.getY() - 1, jumps, isUp);
            }
            else {
                node = findJumps(start, target, rightNode.getX() + 1, rightNode.getY() + 1, jumps, isUp);
            }
            if (node == target){
                jumps.add(rightNode);
                return target;
            }

        }

        return curNode;
    }

    /**
     * Get all the pieces on the board
     * @return ArrayList<Piece> list of pieces
     */
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

    /**
     * Get a node based on pixels
     * @param x x location of pixels
     * @param y y location of pixels
     * @return Node at the correct location
     */
    public Node getNodeByPix(int x, int y){

        for (List<Node> col : board) {
            for (Node node : col) {
                int top = node.getPixY() - (spaceLength / 2);
                int bottom = node.getPixY() + (spaceLength / 2);
                int left = node.getPixX() - (spaceLength / 2);
                int right = node.getPixX() + (spaceLength / 2);
                if (x >= left && x <= right && y >= top && y <= bottom){
                    return node;
                }
            }
        }
        return null;
    }

    /**
     * Get a node based on the board location
     * @param x x location on board
     * @param y y location on board
     * @return Node at the correct location
     */
    public Node getNodeByLoc(int x, int y){
        return board.get(x).get(y);
    }
}
