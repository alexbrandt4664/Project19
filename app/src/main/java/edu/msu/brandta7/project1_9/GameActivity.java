package edu.msu.brandta7.project1_9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    /**
     * The name of Player A
     */
    private String aName;

    /**
     * The name of Player B
     */
    private String bName;

    /**
     * The name of the current player
     */
    private String currentPlayer;

    /**
     * The game view
     */
    GameView gameView;

    Game game;

    /**
     * The color of the current piece
     */
    private String currentColor;

    /**
     * Used to keep track of the current orientation
     * False = normal
     * True = landscape
     */
    private boolean state = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = findViewById(R.id.gameView);

        if (savedInstanceState != null) {
            game = savedInstanceState.getParcelable("GAME");
            state = savedInstanceState.getByte("ORIENTATION") == 1;

            aName = game.getPlayerA().getName();
            bName = game.getPlayerB().getName();
            currentPlayer = game.getCurrent().getName();

            // Have to set the new dimensions of the new game
            int tempMin = gameView.getGame().getMinDim();
            int tempBoardDim = gameView.getGame().getBoardDim();
            int tempLength = gameView.getGame().getSpaceLength();

            int oldLength = game.getSpaceLength();

            gameView.setGame(game);

            gameView.getGame().setBoardDim(tempBoardDim);
            gameView.getGame().setMinDim(tempMin);
            gameView.getGame().setSpaceLength(tempLength);

            gameView.getGame().getBoard().setMinDim(tempMin);
            gameView.getGame().getBoard().setSpaceLength(tempLength);

            // Adjust the coordinates of the pieces
            gameView.getGame().getPlayerA().adjustCoords(tempLength, oldLength);
            gameView.getGame().getPlayerB().adjustCoords(tempLength, oldLength);

            gameView.getGame().getBoard().adjustCoords(oldLength);

            changeColor();
        }
        else {
            // Get the two names passed as input
            aName = getIntent().getExtras().getString(getString(R.string.aKey));
            bName = getIntent().getExtras().getString(getString(R.string.bKey));

            currentPlayer = aName;
            changeColor();

            game = gameView.getGame();

            game.createPlayers(aName, bName);
            game.createBoard(getApplicationContext());

            Toast.makeText(getApplicationContext(), currentPlayer + getString(R.string.currentPlayer) + " " +
                    getString(R.string.colorMessage) + " " + currentColor, Toast.LENGTH_LONG).show();
        }
    }

    public void donePressed(View view) {
        currentPlayer = currentPlayer.equals(aName) ? bName : aName;
        changeColor();
        Toast.makeText(getApplicationContext(), currentPlayer + getString(R.string.currentPlayer) + " " +
                getString(R.string.colorMessage) + " " + currentColor, Toast.LENGTH_LONG).show();
        gameView.getGame().changeCurrent();
        boolean winner = gameView.getGame().checkWinner();

        // Check if the other player (current) lost
        if (winner) {
            createEnd();
        }
    }

    public void resignPressed(View view) {
        createEnd();
    }

    /**
     * Create an activity to go to the end of game activity
     */
    private void createEnd() {
        Intent intent = new Intent(this, EndActivity.class);

        // Pass the winner (the player who didn't press resign) to the end of the game
        intent.putExtra(getString(R.string.winnerKey), currentPlayer.equals(aName) ? bName : aName);
        intent.putExtra(getString(R.string.loserKey), currentPlayer);

        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("GAME", gameView.getGame());
        outState.putByte("ORIENTATION", (byte) ((state) ? 0 : 1));
    }

    /**
     * Changes the color string to the value corresponding to the current player
     */
    private void changeColor() {
        currentColor = currentPlayer.equals(aName) ? getString(R.string.white) : getString(R.string.green);
    }
}