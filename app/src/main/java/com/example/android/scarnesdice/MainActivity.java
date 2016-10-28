package com.example.android.scarnesdice;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.*;
import java.lang.*;


public class MainActivity extends AppCompatActivity {

    static int playerOverallScore = 0;
    static int playerTurnScore = 0;
    static int computerOverallScore = 0;
    static int computerTurnScore = 0;

    ImageView imageview;
    TextView turnTextView;
    TextView turnTotalTextView;
    TextView OverallTotalTextView;
    Button rollButton;
    Button holdButton;
    Handler handler = new Handler();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageview = (ImageView) findViewById(R.id.defaultImage);
        turnTextView = (TextView) findViewById(R.id.turnScore);
        turnTotalTextView = (TextView) findViewById(R.id.uscore);
        OverallTotalTextView = (TextView) findViewById(R.id.cscore);
        rollButton = (Button) findViewById(R.id.rollButton);
        holdButton = (Button) findViewById(R.id.holdButton);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void reset() {
        playerTurnScore = 0;
        playerOverallScore = 0;
        computerTurnScore = 0;
        computerOverallScore = 0;
        imageview.setImageResource(R.drawable.dice1);
        turnTextView.setText("Your turn ");
        displayOverallScore();
        displayTurnTotal(0);
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
        chImage(0);


    }

    public void displayOverallScore() {
        OverallTotalTextView.setText("YOUR SCORE: " + playerOverallScore + "\nCOMPUTER SCORE: " + computerOverallScore);
    }


    public int getRandom() {
        Random random = new Random();
        return random.nextInt(6) + 1;
    }

    public void chImage(int diceRoll) {
        switch (diceRoll) {
            case 1:
                imageview.setImageResource(R.drawable.dice1);
                break;
            case 2:
                imageview.setImageResource(R.drawable.dice2);
                break;
            case 3:
                imageview.setImageResource(R.drawable.dice3);
                break;
            case 4:
                imageview.setImageResource(R.drawable.dice4);
                break;
            case 5:
                imageview.setImageResource(R.drawable.dice5);
                break;
            case 6:
                imageview.setImageResource(R.drawable.dice6);
                break;
            default:
                imageview.setImageResource(R.drawable.dice1);
                break;

        }
    }


    public void changePlayerTurn() {
        if (computerOverallScore >= 100) {
            Toast.makeText(this, "Computer won the game :( , Try next time", Toast.LENGTH_SHORT).show();
            reset();
        } else {
            computerTurnScore = 0;
            turnTextView.setText(" Your Turn ");
            displayTurnTotal(0);
            displayOverallScore();
            chImage(0);
            rollButton.setEnabled(true);
            holdButton.setEnabled(true);
            playerTurnScore = 0;
        }
    }

    public void controlPlayerTurn(int diceRoll) {

        if (diceRoll == 1) {
            Toast.makeText(this, " You got 1 : (", Toast.LENGTH_SHORT).show();
            changeComputerTurn();
        } else {
            playerTurnScore = playerTurnScore + diceRoll;
            displayTurnTotal(playerTurnScore);

        }
    }

    private void displayTurnTotal(int turnTotal) {

        turnTotalTextView.setText(" ");
    }

    public final void computerTurn() {
        if (computerTurnScore <= 20) {
            int diceRoll = getRandom();
            chImage(diceRoll);
            controlComputerTurn(diceRoll);

        } else {
            computerOverallScore = computerOverallScore + computerTurnScore;
            changePlayerTurn();

        }
    }

    public void changeComputerTurn() {

        if (playerOverallScore > 100) {
            Toast.makeText(this, "CONGRATULATIOS!!!You Won:)))", Toast.LENGTH_LONG).show();
            OverallTotalTextView.setText("******* YOU WON *******");

            //reset(View);
            imageview.setImageResource(R.drawable.dice1);
            turnTextView.setText("WANT TO PLAY AGAIN!!!");
            turnTotalTextView.setText("HAVE A GOOD LUCK!");


        } else {
            playerTurnScore = 0;
            turnTextView.setText(" Computer's turn ");
            displayTurnTotal(0);
            displayOverallScore();
            rollButton.setEnabled(false);
            holdButton.setEnabled(false);
            chImage(0);
            // Handler handler = new Handler();


            handler.postDelayed(new Runnable() {
                @Override

                public void run() {
                    computerTurn();
                }
            }, 2000);
        }
    }


    public void controlComputerTurn(int diceRoll) {
        if (diceRoll == 1) {
            changePlayerTurn();
            Toast.makeText(this, " You got 1 :( ", Toast.LENGTH_SHORT).show();
        } else {
            computerTurnScore += diceRoll;
            displayTurnTotal(computerTurnScore);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    computerTurn();
                }
            }, 2000);
        }

    }


    public void roll(View view) {
        int diceRoll = getRandom();
        chImage(diceRoll);
        controlPlayerTurn(diceRoll);


    }

    public void hold(View view) {
        displayTurnTotal(playerTurnScore);
        playerOverallScore += playerTurnScore;
        displayOverallScore();
        changeComputerTurn();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}




