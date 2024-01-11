package com.example.project9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText timeEditText;
    private Button startButton;
    private TextView countdownTextView;
    private CountDownTimer countDownTimer;
    private long totalTimeInMillis = 0;
    private boolean timerRunning;
    int sonut, sonut_player, sonut_computer = 0;
    int diem = 1000;
    boolean batay1, batay2 = true;
    String chuoikq1, chuoikq2;
    TextView tv_left, tv_right;
    TextView tv_may1, tv_may2;
    int diemMay1 = 0;
    int diemMay2 = 0;
    ArrayList<Integer> cacladarut = new ArrayList<Integer>();
    ImageView mangimg[] = new ImageView[6];
    int manghinhbai[][] = {
            {R.drawable.back,
                    R.drawable.ch1, R.drawable.ch2, R.drawable.ch3, R.drawable.ch4, R.drawable.ch5,
                    R.drawable.ch6, R.drawable.ch7, R.drawable.ch8, R.drawable.ch9, R.drawable.ch10, R.drawable.chj,
                    R.drawable.chq, R.drawable.chk},

            {R.drawable.back,
                    R.drawable.r1, R.drawable.r2, R.drawable.r3, R.drawable.r4, R.drawable.r5,
                    R.drawable.r6, R.drawable.r7, R.drawable.r8, R.drawable.r9, R.drawable.r10, R.drawable.rj,
                    R.drawable.rq, R.drawable.rk},

            {R.drawable.back,
                    R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4, R.drawable.c5,
                    R.drawable.c6, R.drawable.c7, R.drawable.c8, R.drawable.c9, R.drawable.c10, R.drawable.cj,
                    R.drawable.cq, R.drawable.ck},

            {R.drawable.back,
                    R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5,
                    R.drawable.b6, R.drawable.b7, R.drawable.b8, R.drawable.b9, R.drawable.b10, R.drawable.bj,
                    R.drawable.bq, R.drawable.bk}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeEditText = findViewById(R.id.timeEditText);
        startButton = findViewById(R.id.startButton);
        countdownTextView = findViewById(R.id.countdownTextView);

        ImageView iv1 = (ImageView) findViewById(R.id.imageView1);
        ImageView iv2 = (ImageView) findViewById(R.id.imageView2);
        ImageView iv3 = (ImageView) findViewById(R.id.imageView3);

        ImageView iv4 = (ImageView) findViewById(R.id.imageView4);
        ImageView iv5 = (ImageView) findViewById(R.id.imageView5);
        ImageView iv6 = (ImageView) findViewById(R.id.imageView6);

        mangimg[0] = iv1;
        mangimg[1] = iv2;
        mangimg[2] = iv3;
        mangimg[3] = iv4;
        mangimg[4] = iv5;
        mangimg[5] = iv6;

        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_may1= (TextView) findViewById(R.id.tv_may1);
        tv_may2 = (TextView) findViewById(R.id.tv_may2);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = timeEditText.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(MainActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(MainActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                    return;
                }
                closeKeyboard();
                startCountdownTimer(millisInput);
                timeEditText.setText("");
            }
        });
    }

    private void startCountdownTimer(long millisInput) {
        totalTimeInMillis = millisInput;

        updateWatchInterface();

        // Create and start a countdown timer
        countDownTimer = new CountDownTimer(totalTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update countdown text and simulate card game
                updateCountdownText(millisUntilFinished);
                ChiaBai();
            }

            @Override
            public void onFinish() {
                // Display the winner when the timer finishes
                displayWinner();
                timerRunning = false;
                updateWatchInterface();
            }
        }.start();

        // Set the timer as running
        timerRunning = true;
        updateWatchInterface();
    }
    private void updateCountdownText(long millisUntilFinished) {
        int hours = (int) (millisUntilFinished / 1000) / 3600;
        int minutes = (int) ((millisUntilFinished / 1000) % 3600) / 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        countdownTextView.setText(timeLeftFormatted);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void updateWatchInterface() {
        if (timerRunning) {
            timeEditText.setVisibility(View.INVISIBLE);
            startButton.setVisibility(View.INVISIBLE);
        } else {
            timeEditText.setVisibility(View.VISIBLE);
            startButton.setVisibility(View.VISIBLE);
        }
    }

    public void ChiaBai() {
        cacladarut.clear();
        Random rd = new Random();
        int x, y;

        sonut = 0;
        batay1 = batay2 = true;

        for (int i = 0; i < mangimg.length; i++) {
            while (true) {
                x = rd.nextInt(4);
                y = rd.nextInt(13) + 1;
                if (!kiemTraTrung(y, cacladarut)) {
                    cacladarut.add(y);
                    break;
                }
            }

            // Set card images to ImageViews
            mangimg[i].setImageResource(manghinhbai[x][y]);

            if (y < 11) {
                batay1 = false;
                batay2 = false;
            }

            sonut += (y < 10) ? y : 0;

            if (i == 2) {
                sonut_player = sonut % 10;
                if (batay1) {
                    chuoikq1 = "Máy 1 : Ba tây";
                    sonut_player = 10;
                } else {
                    chuoikq1 = "Máy 1 : " + sonut_player + " nút";
                }
                batay2 = true;
                sonut = 0;
            }

            if (i == 5) {
                // Update computer's score and display text
                sonut_computer = sonut % 10;
                if (batay2) {
                    chuoikq2 = "Máy 2 : Ba tây";
                    sonut_computer = 10;
                } else {
                    chuoikq2 = "Máy 2 : " + sonut_computer + " nút";
                }
            }
        }

        if (sonut_player > sonut_computer) {
            chuoikq1 += "- Thắng";
            chuoikq2 += "- Thua";
            diemMay1++;
            tv_left.setText(String.valueOf(diemMay1));
        } else if (sonut_player < sonut_computer) {
            chuoikq2 += "- Thắng";
            chuoikq1 += "- Thua";
            diemMay2++;
            tv_right.setText(String.valueOf(diemMay2));
        } else if (sonut_player == sonut_computer) {
            chuoikq1 += "- Hòa";
            chuoikq2 += "- Hòa";
        }

        tv_may1.setText(chuoikq1);
        tv_may2.setText(chuoikq2);

    }
    private boolean kiemTraTrung(int k, ArrayList<Integer> a) {
        return a.contains(k);
    }

    // Method to display the winner
    private void displayWinner(){
        String winner;
        if (diemMay1 > diemMay2) {
            winner = "Máy 1 Thắng!";
        } else if (diemMay1 < diemMay2) {
            winner = "Máy 2 Thắng!";
        } else {
            winner = "Cả 2 Hòa!";
        }
        countdownTextView.setText(winner);
    }
}
