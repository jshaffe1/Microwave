package edu.dtcc.jshaffe1.microwave;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    // Text box references
    private TextView txtMinutes;
    private TextView txtSeconds;
    private int iClicks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create text view objects
        txtMinutes = (TextView) findViewById(R.id.txtTimerMinutes);
        txtSeconds = (TextView) findViewById(R.id.txtTimerSeconds);


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


         View.OnClickListener displayValue = new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switch(v.getId()){
                    case R.id.btn0:
                        setTimer(btn0, "0");
                        break;
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
                    case R.id.btnStopReset:
                        if(timerIsRunning())
                            stopTimer();
                        else
                            resetTimer();
                        break;
                    case R.id.btnStart:
                        if(!timerIsRunning())
                            startTimer();
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

        iClicks = 0;
    }



    public void setTimer(Button btnPressed, String sBtnValue){
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
        if(iClicks == 5)
            iClicks = 1;

        if(iClicks == 1)
            saFullText[3] = sBtnValue;
        else if(iClicks == 2) {
            saFullText[2] = saFullText[3];
            saFullText[3] = sBtnValue;

        } else if(iClicks == 3) {
            saFullText[1] = saFullText[2];
            saFullText[2] = saFullText[3];
            saFullText[3] = sBtnValue;
        } else if(iClicks == 4){
            saFullText[0] = saFullText[1];
            saFullText[1] = saFullText[2];
            saFullText[2] = saFullText[3];
            saFullText[3] = sBtnValue;
        }

        sSeconds = saFullText[2] + saFullText[3];
        sMinutes = saFullText[0] + saFullText[1];
        // Read from the array and set the text boxes to their values

        txtSeconds.setText(sSeconds);
        txtMinutes.setText(sMinutes);



        // First thoughts
      /*  // Seconds field is still valid
        if(iClicks == 1)
            txtSeconds.setText("0" + btnPressed.getText());
        else if(iClicks == 2)
            txtSeconds.setText(sSeconds + btnPressed.getText());
        else if(iClicks == 3) {                                       // Push numbers left
            txtMinutes.setText("0" + sMinutes.charAt(0));
            txtSeconds.setText()
        }
        else if(iClicks == 4)
            txtMinutes.setText(sMinutes + btnPressed.getText());*/

    }

    public boolean timerIsRunning(){
        return false;
    }

    public void resetTimer(){
        iClicks = 0;
        txtMinutes.setText(R.string.txtTimerMin);
        txtSeconds.setText(R.string.txtTimerSec);
    }

    public void stopTimer(){

    }

    public void startTimer(){
        String sSeconds = (String) txtSeconds.getText();
        String sMinutes = (String) txtMinutes.getText();
        int iMinutes, iSeconds;
        iSeconds = Integer.parseInt(sSeconds);
        iMinutes = Integer.parseInt(sMinutes);

        if(iSeconds > 59){
            // Convert to minutes then try to add to minutes text
            if (iMinutes + iSeconds / 60 > 99)      // Minutes is maxed out
                txtSeconds.setText("59");
            else {
                iMinutes += iSeconds / 60;      // Add number of minutes
                iSeconds %= 60;                 // Find remainder to get seconds
                if (iSeconds < 10)
                    txtSeconds.setText("0" + String.format("%d", iMinutes));
                else
                    txtSeconds.setText(String.format("%d", iSeconds));
                if (iMinutes < 10)
                    txtMinutes.setText("0" + String.format("%d", iMinutes));
                else
                    txtMinutes.setText(String.format("%d", iMinutes));
            }
        }

    }
}

