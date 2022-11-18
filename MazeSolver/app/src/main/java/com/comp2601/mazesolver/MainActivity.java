package com.comp2601.mazesolver;


import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Random;
import java.util.Timer;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    //Variables to Enable Gesture Swipes
    private static final String TAG = "swipe position";
    private float x1, x2, y1, y2;
    private static int MIN_DISTANCE = 150;
    private GestureDetector gestureDetector;

    // Scale of the game (number of rows and columns)
    // THIS IS WHERE U CAN CHANGE THE NUMBER OF CELLS/ ROWS AND COLUMNS
    private  static final int NUM_ROWS = 4;
    private  static final int NUM_COLS = 4;

    private  Button startGame;


    // Create button double array
    Button buttons[][] = new Button[NUM_ROWS][NUM_COLS];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set Orientation To Portrait
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Set Gesture detector for Swipes
        this.gestureDetector = new GestureDetector(MainActivity.this, this);


        // Adding buttons with UI Threads
        TableLayout gameLayout = findViewById(R.id.gameTable);

        Button tempButtonArr [] = new Button[NUM_COLS];

        //For every row, make the desired amount of columns each with buttons in the right size
        for (int i = 0; i<NUM_ROWS; i++){
            TableRow tableRow = new TableRow(MainActivity.this);
            gameLayout.addView(tableRow);
            for (int j = 0; j<NUM_COLS; j++){
                Button button = new Button(MainActivity.this);
                button.setBackgroundColor(getResources().getColor(R.color.empty));
                button.setText(getResources().getString(R.string.empty));
                tableRow.addView(button);
                tempButtonArr[j] = button;
            }
            buttons[i] = tempButtonArr;
            tempButtonArr = new Button[NUM_COLS];
        }


        startGame = (Button) findViewById(R.id.startGame);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewNum();
                createNewNum();
            }
        });
    }



    public void createNewNum(){

        boolean checker = false;
        int randomX = 0;
        int randomY = 0;

        while (checker != true){
            Random rand = new Random();

            randomX = rand.nextInt((NUM_COLS - 1) + 1) + 0;
            randomY = rand.nextInt((NUM_ROWS - 1) + 1) + 0;

            if (buttons[randomX][randomY].getText().toString() == getResources().getString(R.string.empty)){
                checker = true;
            }
        }

        buttons[randomX][randomY].setBackgroundColor(getResources().getColor(R.color.two));
        buttons[randomX][randomY].setText(getResources().getString(R.string.two));
    }

    public void checkDown(){
        for (int i = 0; i<NUM_ROWS; i++){
            for (int j = 0; j<NUM_COLS; j++){

                // If the button is in the last row do nothing
                if (i == 3){
                    break;
                }


                Button currentButton = buttons[i][j];
                Button belowCurrentButton = buttons[i+1][j];

                // if the button has a value
                if (currentButton.getText().toString() != getResources().getString(R.string.empty)){

                    // if the below button is empty
                    if (belowCurrentButton.getText().toString() == getResources().getString(R.string.empty)){
                        //set the below button to be the current button and set the current button to empty
                        belowCurrentButton.setText(buttons[i][j].getText());
                        currentButton.setText(getResources().getString(R.string.empty));
                        currentButton.setBackgroundColor(getResources().getColor(R.color.empty));
                    }

                    // if the button has a match under
                    if (belowCurrentButton.getText().toString() == currentButton.getText().toString()){
                        int newNum = Integer.valueOf(currentButton.getText().toString());
                        newNum = newNum * 2;
                        System.out.println(newNum);
                        belowCurrentButton.setText(String.valueOf(newNum));
                        currentButton.setText(getResources().getString(R.string.empty));
                        currentButton.setBackgroundColor(getResources().getColor(R.color.empty));
                    }
                }
            }
        }
    }

    public void checkUp(){
        for (int i = 3; i>0; i--){
            for (int j = 0; j<NUM_COLS; j++){

                // if the button is in the first row do nothing
                if (i == 0){
                    break;
                }


                Button currentButton = buttons[i][j];
                Button aboveCurrentButton = buttons[i-1][j];

                // if the button has a value
                if (currentButton.getText().toString() != getResources().getString(R.string.empty)){

                    // if the above button is empty
                    if (aboveCurrentButton.getText().toString() == getResources().getString(R.string.empty)){
                        //set the above button to be the current button and set the current button to empty
                        aboveCurrentButton.setText(buttons[i][j].getText());
                        currentButton.setText(getResources().getString(R.string.empty));
                        currentButton.setBackgroundColor(getResources().getColor(R.color.empty));
                    }

                    // if the button has a match above
                    if (aboveCurrentButton.getText().toString() == currentButton.getText().toString()){
                        int newNum = Integer.valueOf(currentButton.getText().toString());
                        newNum = newNum * 2;
                        System.out.println(newNum);
                        aboveCurrentButton.setText(String.valueOf(newNum));
                        currentButton.setText(getResources().getString(R.string.empty));
                        currentButton.setBackgroundColor(getResources().getColor(R.color.empty));
                    }
                }
            }
        }
    }

    public void checkRight(){
        for (int i = 0; i<NUM_ROWS; i++){
            for (int j = 0; j < NUM_COLS; j++){

                // If the button is in the last collum do nothing
                if (i == 3){
                    break;
                }


                Button currentButton = buttons[j][i];
                Button rightCurrentButton = buttons[j][i+1];

                // if the button has a value
                if (currentButton.getText().toString() != getResources().getString(R.string.empty)){

                    // if the below button is empty
                    if (rightCurrentButton.getText().toString() == getResources().getString(R.string.empty)){
                        //set the below button to be the current button and set the current button to empty
                        rightCurrentButton.setText(buttons[j][i].getText());
                        currentButton.setText(getResources().getString(R.string.empty));
                        currentButton.setBackgroundColor(getResources().getColor(R.color.empty));
                    }

                    // if the button has a match under
                    if (rightCurrentButton.getText().toString() == currentButton.getText().toString()){
                        int newNum = Integer.valueOf(currentButton.getText().toString());
                        newNum = newNum * 2;
                        System.out.println(newNum);
                        rightCurrentButton.setText(String.valueOf(newNum));
                        currentButton.setText(getResources().getString(R.string.empty));
                        currentButton.setBackgroundColor(getResources().getColor(R.color.empty));
                    }
                }
            }
        }
    }

    public void checkLeft(){
        for (int i = 0; i<NUM_ROWS; i++){
            for (int j = 3; j > 0; j--){

                // If the button is in the last collum do nothing
                if (i == 0){
                    break;
                }


                Button currentButton = buttons[j][i];
                Button rightCurrentButton = buttons[j][i-1];

                // if the button has a value
                if (currentButton.getText().toString() != getResources().getString(R.string.empty)){

                    // if the below button is empty
                    if (rightCurrentButton.getText().toString() == getResources().getString(R.string.empty)){
                        //set the below button to be the current button and set the current button to empty
                        rightCurrentButton.setText(buttons[j][i].getText());
                        currentButton.setText(getResources().getString(R.string.empty));
                        currentButton.setBackgroundColor(getResources().getColor(R.color.empty));
                    }

                    // if the button has a match under
                    if (rightCurrentButton.getText().toString() == currentButton.getText().toString()){
                        int newNum = Integer.valueOf(currentButton.getText().toString());
                        newNum = newNum * 2;
                        System.out.println(newNum);
                        rightCurrentButton.setText(String.valueOf(newNum));
                        currentButton.setText(getResources().getString(R.string.empty));
                        currentButton.setBackgroundColor(getResources().getColor(R.color.empty));
                    }
                }
            }
        }
    }

    public void checkGame(){
        int gameOver = 0;
        for (int i = 0; i< NUM_ROWS; i++){
            for (int j = 0; j< NUM_COLS; j++){
                if (buttons[i][j].getText().toString() == getResources().getString(R.string.empty)){
                    gameOver += 0;
                }
                if (buttons[i][j].getText().toString() == getResources().getString(R.string.two_thousand)){
                    gameOver += 1;
                }
                CharSequence comp = buttons[i][j].getText();

                System.out.println(comp);

                if (!(comp.equals(""))){
                    int comp3 = Integer.valueOf(comp.toString().trim());

                    if (comp3 == 2){
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.two));
                    }else if (comp3 == 4 ){
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.four));
                    }else if (comp3 == 8 ){
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.eight));
                    }else if (comp3 == 16 ){
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.sixteen));
                    }else if (comp3 == 32 ){
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.thirty_two));
                    }else if (comp3 == 64 ){
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.sixty_four));
                    }else if (comp3 == 128 ){
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.hundred_twenty_eight));
                    }else if (comp3 == 256 ){
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.two_hundred));
                    }else if (comp3 == 512 ){
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.five_hundred));
                    }else if (comp3 == 1024 ){
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.one_thousand));
                    }else if (comp3 == 2048 ){
                        buttons[i][j].setBackgroundColor(getResources().getColor(R.color.two_thousand));
                    }


                }
            }
        }

        if (gameOver >= 1){
            System.out.println("You won");
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        gestureDetector.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                x1 = event.getX();
                y1 = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                float valueX = x2-x1;
                float valueY = y2-y1;

                if(Math.abs(valueX) > MIN_DISTANCE){
                    if (x2>x1){
                        checkRight();
                        createNewNum();
                        checkGame();
                    }else{
                        checkLeft();
                        createNewNum();
                        checkGame();
                    }
                }else if (Math.abs(valueY) > MIN_DISTANCE){
                    if (y2>y1){
                        checkDown();
                        createNewNum();
                        checkGame();
                    }else{
                        checkUp();
                        createNewNum();
                        checkGame();
                    }
                }


        }




        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }


}
