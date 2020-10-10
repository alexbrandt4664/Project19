package edu.msu.brandta7.project1_9;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Function to move the players to the game activity
     * @param view
     */
    public void createGame(View view) {

        // Read the contents of the user input
        String aName = ((EditText)findViewById(R.id.aName)).getText().toString();
        String bName = ((EditText)findViewById(R.id.bName)).getText().toString();

        // Check if either of the inputs were empty
        if (aName.equals("") || bName.equals("")) {
            Toast.makeText(getApplicationContext(), getString(R.string.emptyInput), Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(getString(R.string.aKey), aName);
            intent.putExtra(getString(R.string.bKey), bName);
            startActivity(intent);
        }
    }

    /**
     * Function to display a how-to dialog to the players
     * @param view
     */
    public void showTutorial(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.moveRules) + "\n" +
                getString(R.string.diagonalRules) + "\n" +
                getString(R.string.killRules) + "\n" +
                getString(R.string.doneRules) + "\n" +
                getString(R.string.resignRules) + "\n");
        builder.setTitle(getString(R.string.howTo) + " " + getString(R.string.title));
        builder.setPositiveButton(android.R.string.ok, null);
        builder.create().show();
    }
}