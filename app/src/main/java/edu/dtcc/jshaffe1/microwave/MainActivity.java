package edu.dtcc.jshaffe1.microwave;
// Jeff Shaffer 1/26/2016

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    // Text box references
    private TextView txtMinutes;
    private TextView txtSeconds;
    private TextView txtColon;
    private ImageView ivTurkey;     // To hide and display the turkey

    // Declare variables that can be reached from inside a class
    private int iClicks, iSeconds, iMinutes, iInitialSeconds, myColor;
    private boolean bIsRunning;
    String sSeconds, sMinutes;
    CountDownTimer microwaveTimer;  // Used as the timer for the microwave

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create text view objects
        txtMinutes = (TextView) findViewById(R.id.txtTimerMinutes);
        txtSeconds = (TextView) findViewById(R.id.txtTimerSeconds);
        txtColon = (TextView) findViewById(R.id.txtColon);

        // Create an object pointing to the color green that I will use
        myColor = ContextCompat.getColor(this, R.color.myGreen);

        // Create an object pointing to the turkey image
        ivTurkey = (ImageView) findViewById(R.id.ivTurkey);

        // Make turkey invisible to begin with
        ivTurkey.setVisibility(View.INVISIBLE);

        // Create button objects pointing to each button
        final Button btn0 = (Button) findViewById(R.id.btn0);
        final Button btn1 = (Button) findViewById(R.id.btn1);
        final Button btn2 = (Button) findViewById(R.id.btn2);
        final Button btn3 = (Button) findViewById(R.id.btn3);
        final Button btn4 = (Button) findViewById(R.id.btn4);
        final Button btn5 = (Button) findViewById(R.id.btn5);
        final Button btn6 = (Button) findViewById(R.id.btn6);
        final Button btn7 = (Button) findViewById(R.id.btn7);
        final Button btn8 = (Button) findViewById(R.id.btn8);
        final Button btn9 = (Button) findViewById(R.id.btn9);
        final Button btnStopReset = (Button) findViewById(R.id.btnStopReset);
        final Button btnStart = (Button) findViewById(R.id.btnStart);

        // Set the variable used to determine the running status of the timer
        bIsRunning = false;

        // Declare an initialize an OnClickListener
         View.OnClickListener displayValue = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Use the id of the object to determine which button was pressed
                switch(v.getId()){
                    case R.id.btn0:             // The zero button was pressed
                        setTimer(btn0, "0");    // Pass the pressed button and it's "value" to setTimer()
                        break;                  // Break the switch statement
                    case R.id.btn1:
                        setTimer(btn1, "1");
                        break;
                    case R.id.btn2:
                        setTimer(btn2, "2");
                        break;
                    case R.id.btn3:
                        setTimer(btn3, "3");
                        break;
                    case R.id.btn4:
                        setTimer(btn4, "4");
                        break;
                    case R.id.btn5:
                        setTimer(btn5, "5");
                        break;
                    case R.id.btn6:
                        setTimer(btn6, "6");
                        break;
                    case R.id.btn7:
                        setTimer(btn7, "7");
                        break;
                    case R.id.btn8:
                        setTimer(btn8, "8");
                        break;
                    case R.id.btn9:
                        setTimer(btn9, "9");
                        break;
                    case R.id.btnStopReset: // Stop/Reset button has been pressed
                        if(bIsRunning)      // Check if the timer is running
                            stopTimer();    // If so, stop the timer
                        else
                            resetTimer();   // Timer is not running so reset the text for the timer
                        break;
                    case R.id.btnStart:     // Start button was pressed
                        if(!bIsRunning)     // Check if the timer is already running
                            initializeTimer();  // If it is not running, initialize the timer
                        break;
                }
            }
        };

        // Set listeners
        btn0.setOnClickListener(displayValue);
        btn1.setOnClickListener(displayValue);
        btn2.setOnClickListener(displayValue);
        btn3.setOnClickListener(displayValue);
        btn4.setOnClickListener(displayValue);
        btn5.setOnClickListener(displayValue);
        btn6.setOnClickListener(displayValue);
        btn7.setOnClickListener(displayValue);
        btn8.setOnClickListener(displayValue);
        btn9.setOnClickListener(displayValue);
        btnStopReset.setOnClickListener(displayValue);
        btnStart.setOnClickListener(displayValue);

        // Set the number of button presses the user has accumulated
        iClicks = 0;
    }



    public void setTimer(Button btnPressed, String sBtnValue){
        // When a number is clicked, this is called to handle the click depending on several things
        // It will format the input numbers filling the text views from right to left
        String sSeconds = (String) txtSeconds.getText();
        String sMinutes = (String) txtMinutes.getText();
        String[] saFullText;
        saFullText = new String[4];

        //                          0, 1, 2, 3
        // fill array to look like [m, m, s, s]
        saFullText[0] = sMinutes.substring(0, 1);
        saFullText[1] = sMinutes.substring(1);
        saFullText[2] = sSeconds.substring(0, 1);
        saFullText[3] = sSeconds.substring(1);

        // Increment click counter to determine if it is minutes or seconds
        iClicks++;
        if(iClicks == 5)                    // Reset number of clicks (no more room for new numbers)
            iClicks = 1;
        if(iClicks == 1)                    // User is still inputting seconds
            saFullText[3] = sBtnValue;      // Put the value into the array
        else if(iClicks == 2) {
            saFullText[2] = saFullText[3];  // Fill index 2 with index 3's value
            saFullText[3] = sBtnValue;      // Assign new value to index 3
        } else if(iClicks == 3) {           // User is now inputting minutes
            saFullText[1] = saFullText[2];
            saFullText[2] = saFullText[3];
            saFullText[3] = sBtnValue;
        } else if(iClicks == 4){
            saFullText[0] = saFullText[1];
            saFullText[1] = saFullText[2];
            saFullText[2] = saFullText[3];
            saFullText[3] = sBtnValue;
        }

        sSeconds = saFullText[2] + saFullText[3];   // Extract seconds from the array
        sMinutes = saFullText[0] + saFullText[1];   // Extract minutes from the array
        // Read from the array and set the text boxes to their values

        txtSeconds.setText(sSeconds);   // Set the textView seconds field
        txtMinutes.setText(sMinutes);   // Set the textView minute field
    }


    public void resetTimer(){
        // Reset number of clicks, set mins and secs to original value, make turkey invisible
        iClicks = 0;
        txtMinutes.setText(R.string.txtTimerMin);
        txtSeconds.setText(R.string.txtTimerSec);
        ivTurkey.setVisibility(View.INVISIBLE);
    }

    public void stopTimer(){
        // Stops the timer and sets the isRunning variable
        microwaveTimer.cancel();
        bIsRunning = false;
    }

    public void initializeTimer(){
        // This will look at the numbers in the minutes and seconds field
        // If user enters a seconds value higher than 59, then it will be converted_
        // to minutes and seconds and the textViews will be adjusted to the new values

        // Initialize variables for, store the mins and secs as strings
        sSeconds = (String) txtSeconds.getText();
        sMinutes = (String) txtMinutes.getText();

        // Also store the mins and secs as integers
        iSeconds = Integer.parseInt(sSeconds);
        iMinutes = Integer.parseInt(sMinutes);

        // Convert seconds to minutes and seconds if it is over 59 seconds
        if(iSeconds > 59) {
            // Convert to minutes then try to add to minutes text
            if (iMinutes + iSeconds / 60 > 99) {
                // Minutes field is maxed out so reset the text to 59
                txtSeconds.setText(getString(R.string.fiftyNine, 59));
            }
            else {  // The minutes have not been maxed out
                iMinutes += iSeconds / 60;      // Add number of minutes
                iSeconds %= 60;                 // Find remainder to get seconds
                if (iSeconds < 10)              // Check for formatting, if < 10, a zero needs to be added
                    sSeconds = "0" + String.format("%d", iSeconds);
                else
                    sSeconds = String.format("%d", iSeconds);
                txtSeconds.setText(sSeconds);    // Set the seconds text

                if (iMinutes < 10)  // Check for the same formatting issue
                    sMinutes = "0" + String.format("%d", iMinutes);
                else
                    sMinutes = String.format("%d", iMinutes);
                txtMinutes.setText(sMinutes);   // Set the minutes text
            }
        }
        startTimer();   // Begin the timer
    }

    public void startTimer(){
        // Convert Minutes and seconds into just milliseconds
        // Add a little more than 1 extra second because the seconds won't be decremented_
        // Until after one second has passed
        int iMilliseconds = (Integer.parseInt(sSeconds) * 1000 + 1200)
                + (Integer.parseInt(sMinutes) * 60_000);
        iSeconds = Integer.parseInt(sSeconds);  // Re-set the seconds and minutes integer values
        iMinutes = Integer.parseInt(sMinutes);
        iInitialSeconds = iSeconds; // InitialSeconds will be used to tell if one second has passed
        bIsRunning =true;

        // Assign a new instance of a countdowntimer to the microwaveTimer using calculated milliseconds
        microwaveTimer = new CountDownTimer(iMilliseconds, 1000){
            public void onTick(long millisUntilFinished) {
                if (iSeconds != iInitialSeconds) {      // if one second has already passed
                    if (iSeconds > 0) {
                        if (iSeconds < 11) {
                            sSeconds = "0" + Integer.toString(--iSeconds);  // Format seconds field
                        } else {
                            sSeconds = Integer.toString(--iSeconds);
                        }
                        txtSeconds.setText(sSeconds);   // Set the new seconds to the textView
                    } else {
                        if (iMinutes == 1) {        // Minutes will need formatting
                            sMinutes = "0" + Integer.toString(--iMinutes);  // Decrement minutes before assignment
                        } else {
                            sMinutes = Integer.toString(--iMinutes);
                        }
                        txtMinutes.setText(sMinutes);   // Set the new minutes to the textView
                        iSeconds = 59;                  // Seconds now become 59
                        txtSeconds.setText(getString(R.string.fiftyNine, 59));
                    }
                } else {
                    iInitialSeconds++;
                }
            }

            public void onFinish(){
                // When countdown has finished show the turkey
                ivTurkey.setVisibility(View.VISIBLE);

                // Create a new timer used to flash the text on screen different colors
                new CountDownTimer(3000, 200){
                    public void onTick(long millisUntilFinished){
                        // Check what color the timer is currently
                        if(txtMinutes.getTextColors() == ColorStateList.valueOf(myColor)){
                            // Set the text color to black to make it look like it is flashing
                            txtMinutes.setTextColor(Color.BLACK);
                            txtSeconds.setTextColor(Color.BLACK);
                            txtColon.setTextColor(Color.BLACK);
                        } else {
                            // Set the text color back to the original
                            txtMinutes.setTextColor(myColor);
                            txtSeconds.setTextColor(myColor);
                            txtColon.setTextColor(myColor);
                        }
                    }

                    public void onFinish(){
                        // Ensure text is back to default color
                        txtMinutes.setTextColor(myColor);
                        txtSeconds.setTextColor(myColor);
                        txtColon.setTextColor(myColor);
                        this.cancel();
                    }
                }.start();
                // Cancel the timer and set the isRunning variable
                bIsRunning = false;
                microwaveTimer.cancel();
            }
        }.start();

    }
}

