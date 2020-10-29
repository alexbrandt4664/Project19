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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameView = findViewById(R.id.gameView);

        // Get the two names passed as input
        aName = getIntent().getExtras().getString(getString(R.string.aKey));
        bName = getIntent().getExtras().getString(getString(R.string.bKey));

        currentPlayer = aName;

        gameView.getGame().createPlayers(aName, bName);

        Toast.makeText(getApplicationContext(), currentPlayer + getString(R.string.currentPlayer), Toast.LENGTH_SHORT).show();
    }

    public void donePressed(View view) {
        currentPlayer = currentPlayer.equals(aName) ? bName : aName;
        Toast.makeText(getApplicationContext(), currentPlayer + getString(R.string.currentPlayer), Toast.LENGTH_SHORT).show();
        gameView.getGame().changeCurrent();
    }

    public void resignPressed(View view) {
        Intent intent = new Intent(this, EndActivity.class);

        //TODO Create a function in game to get the winner
        // Pass the winner (the player who didn't press resign) to the end of the game
        intent.putExtra(getString(R.string.winnerKey), currentPlayer.equals(aName) ? bName : aName);
        intent.putExtra(getString(R.string.loserKey), currentPlayer);

        startActivity(intent);
    }
}