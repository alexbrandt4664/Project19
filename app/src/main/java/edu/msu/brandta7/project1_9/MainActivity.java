package edu.msu.brandta7.project1_9;

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
}