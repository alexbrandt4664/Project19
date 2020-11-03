package edu.msu.brandta7.project1_9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        // Set the text for the winner and loser
        String winner = getIntent().getExtras().getString(getString(R.string.winnerKey));
        String loser = getIntent().getExtras().getString(getString(R.string.loserKey));

        TextView winnerText = findViewById(R.id.txtWinner);
        String winnerMsg = winner + " " + winnerText.getText().toString();
        winnerText.setText(winnerMsg);

        TextView loserText = findViewById(R.id.txtLoser);
        String loserMsg = loser + " " + loserText.getText().toString();
        loserText.setText(loserMsg);
    }

    public void returnPressed(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}