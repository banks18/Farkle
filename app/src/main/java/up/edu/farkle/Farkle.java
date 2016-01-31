/*
    Farkle

    This is our implentation of the dice game Farkle for CS 301: Object-oriented Design

    Instead of doing the normal implementations for Homework 4c, Nuxoll approved these changes
    as being appropriate for a full score, pending correct implementation.

    - Implement the roll dice button so that when you click that button and
      random set of dice appear below.
    - Implement bank points button so that it adds to the user's score when
      clicked.  For now we will probably set a constant for each time it is
      clicked to demonstrate that it works.
    - For each of the six dice image buttons, we will have some sort of action
      (change color, highlight, or shadow) happen when it is clicked.
    - We will also undo the previous action if the die is clicked again.

    @authors Levi Banks, Alexa Baldwin, Briahna Santillana, Sara Perkins
    @version 2/1/2016
 */

package up.edu.farkle;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Farkle extends Activity implements View.OnClickListener {

    protected Button rollDiceButton;
    protected Button bankPointsButton;

    protected ImageButton dieOne;
    protected ImageButton dieTwo;
    protected ImageButton dieThree;
    protected ImageButton dieFour;
    protected ImageButton dieFive;
    protected ImageButton dieSix;

    protected ArrayList<ImageButton> diceButtons = new ArrayList<ImageButton>();
    protected ArrayList<Integer> values = new ArrayList<Integer>();
    protected ArrayList<Integer> diceWhiteResID = new ArrayList<Integer>();
    protected ArrayList<Integer> diceRedResID = new ArrayList<Integer>();
    protected boolean[] clickOn = {false, false, false, false, false, false};

    protected int player1Score = 0;
    protected int player2Score = 0;
    protected int bankPointsScore = 750;
    protected boolean player1Turn = true;
    protected TextView p1CurrentScore;
    protected TextView p2CurrentScore;

    @Override
    /*
        onCreate
        Initializes all instance variables and creates onClickListeners
        where appropriate. Also gets rid of title menu normally at the
        top of the app screen.

        @param savedInstanceState - Used to set OnClickListeners
     */
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_farkle);

        rollDiceButton = (Button) findViewById(R.id.rollDiceButton);
        rollDiceButton.setOnClickListener(this);

        bankPointsButton = (Button) findViewById(R.id.bankPointsButton);
        bankPointsButton.setOnClickListener(this);

        dieOne = (ImageButton) findViewById(R.id.dieOne);
        dieOne.setOnClickListener(this);
        dieOne.setVisibility(View.INVISIBLE);

        dieTwo = (ImageButton) findViewById(R.id.dieTwo);
        dieTwo.setOnClickListener(this);
        dieTwo.setVisibility(View.INVISIBLE);

        dieThree = (ImageButton) findViewById(R.id.dieThree);
        dieThree.setOnClickListener(this);
        dieThree.setVisibility(View.INVISIBLE);

        dieFour = (ImageButton) findViewById(R.id.dieFour);
        dieFour.setOnClickListener(this);
        dieFour.setVisibility(View.INVISIBLE);

        dieFive = (ImageButton) findViewById(R.id.dieFive);
        dieFive.setOnClickListener(this);
        dieFive.setVisibility(View.INVISIBLE);

        dieSix = (ImageButton) findViewById(R.id.dieSix);
        dieSix.setOnClickListener(this);
        dieSix.setVisibility(View.INVISIBLE);

        diceButtons.add(dieOne);
        diceButtons.add(dieTwo);
        diceButtons.add(dieThree);
        diceButtons.add(dieFour);
        diceButtons.add(dieFive);
        diceButtons.add(dieSix);

        diceWhiteResID.add(R.drawable.one_dice_white);
        diceWhiteResID.add(R.drawable.two_dice_white);
        diceWhiteResID.add(R.drawable.three_dice_white);
        diceWhiteResID.add(R.drawable.four_dice_white);
        diceWhiteResID.add(R.drawable.five_dice_white);
        diceWhiteResID.add(R.drawable.six_dice_white);
        
        diceRedResID.add(R.drawable.one_die);
        diceRedResID.add(R.drawable.two_die);
        diceRedResID.add(R.drawable.three_die);
        diceRedResID.add(R.drawable.four_die);
        diceRedResID.add(R.drawable.five_die);
        diceRedResID.add(R.drawable.six_die);

        p1CurrentScore = (TextView) findViewById(R.id.p1CurrentScore);
        p1CurrentScore.setText(0 + "");

        p2CurrentScore = (TextView) findViewById(R.id.p2CurrentScore);
        p2CurrentScore.setText(0 + "");

        for (int i = 0; i < 6; i++) {

            values.add(i, 0);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_farkle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    /*
        onClick
        This method is used to handle Events when buttons are clicked.

        @param view - This is the specific view that is being listened
                      for.
     */
    public void onClick(View view) {

        if(view.getId() == R.id.rollDiceButton) {
            setVisible();
            rollDice();
        } else if(view.getId() == R.id.bankPointsButton) {
            bankPoints();
        } else if(view.getId() == R.id.dieOne) {
            chooseDie(dieOne);
        } else if(view.getId() == R.id.dieTwo) {
            chooseDie(dieTwo);
        } else if(view.getId() == R.id.dieThree) {
            chooseDie(dieThree);
        } else if(view.getId() == R.id.dieFour) {
            chooseDie(dieFour);
        } else  if(view.getId() == R.id.dieFive) {
            chooseDie(dieFive);
        } else if(view.getId() == R.id.dieSix) {
            chooseDie(dieSix);
        }
    }

    /*
        setVisible
        This method makes the buttons visible the first time rollDice is clicked
     */
    public void setVisible() {
        dieOne.setVisibility(View.VISIBLE);
        dieTwo.setVisibility(View.VISIBLE);
        dieThree.setVisibility(View.VISIBLE);
        dieFour.setVisibility(View.VISIBLE);
        dieFive.setVisibility(View.VISIBLE);
        dieSix.setVisibility(View.VISIBLE);
    }

    /*
        rollDice
        This method randomly sets values for each of the dice.
     */
    public void rollDice() {
        for(int i = 0; i < 6; i++) {
            int rand = (int)(Math.random()*6);
            if(rand == 0) {
                diceButtons.get(i).setImageResource(R.drawable.one_die);
                values.add(i, 0);
            } else if(rand == 1) {
                diceButtons.get(i).setImageResource(R.drawable.two_die);
                values.add(i, 1);
            } else if(rand == 2) {
                diceButtons.get(i).setImageResource(R.drawable.three_die);
                values.add(i, 2);
            } else if(rand == 3) {
                diceButtons.get(i).setImageResource(R.drawable.four_die);
                values.add(i, 3);
            } else if(rand == 4) {
                diceButtons.get(i).setImageResource(R.drawable.five_die);
                values.add(i, 4);
            } else {
                diceButtons.get(i).setImageResource(R.drawable.six_die);
                values.add(i, 5);
            }
        }
    }

    /*
        bankPoints
        This method adds the bank points score to the score of
        the current player.
     */
    public void bankPoints() {
        if (player1Turn) {
            player1Score += bankPointsScore;
            p1CurrentScore.setText(""+player1Score);
        } else {
            player2Score += bankPointsScore;
            p2CurrentScore.setText(""+player2Score);
        }
    }

    /*
        chooseDie
        This method selects/deselects a given die,
        changing the color to white/red respectively.

        @param die - the selected die
     */
    public void chooseDie (ImageButton die) {
        int i = diceButtons.indexOf(die);
        if(!clickOn[i]) {
            die.setImageResource(diceWhiteResID.get(values.get(i)));
            clickOn [i] = !clickOn[i];
        } else {
            die.setImageResource(diceRedResID.get(values.get(i)));
            clickOn [i] = !clickOn[i];
        }
    }
}
