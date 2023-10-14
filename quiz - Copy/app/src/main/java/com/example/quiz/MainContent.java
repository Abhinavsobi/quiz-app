package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainContent extends AppCompatActivity implements View.OnClickListener{

    TextView totalquestionsTextview;
    TextView questionTextview;
    Button A, B, C, D;
    Button Button2;
    int score = 0;
    int totalquestions = quizanswers.question.length;
    int currentQindex = 0;
    String selectedAns = "";
    private static final long Countdown=20000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;
    private TextView textViewCountDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);
        totalquestionsTextview = findViewById(R.id.total_question);
        questionTextview = findViewById(R.id.questions);
        textViewCountDown= findViewById(R.id.timing);
        A = findViewById(R.id.A);
        B = findViewById(R.id.B);
        C = findViewById(R.id.C);
        D = findViewById(R.id.D);
        Button2= findViewById(R.id.button2);
        A.setOnClickListener(this);
        B.setOnClickListener(this);
        C.setOnClickListener(this);
        D.setOnClickListener(this);
        Button2.setOnClickListener(this);
        totalquestionsTextview.setText("Total question : " + totalquestions);

        loadNewQuestion();
    }


    @Override
    public void onClick(View view) {
        Button clickedButton=(Button) view;
        A.setBackgroundColor(Color.WHITE);
        B.setBackgroundColor(Color.WHITE);
        C.setBackgroundColor(Color.WHITE);
        D.setBackgroundColor(Color.WHITE);
        if(clickedButton.getId()==R.id.button2){
            if(selectedAns.equals(quizanswers.correct[currentQindex])){
                score++;
            }


            countDownTimer.cancel();
            currentQindex++;
            loadNewQuestion();

        }
        else
        {
            selectedAns=clickedButton.getText().toString();
            clickedButton.setBackgroundColor(0xFF00FF00);
        }
    }
    void loadNewQuestion()
    {
        if(currentQindex==totalquestions){
            finishQuiz();
            return;
        }

        questionTextview.setText(quizanswers.question[currentQindex]);
        A.setText(quizanswers.choices[currentQindex][0]);
        B.setText(quizanswers.choices[currentQindex][1]);
        C.setText(quizanswers.choices[currentQindex][2]);
        D.setText(quizanswers.choices[currentQindex][3]);
        timeLeftInMillis=Countdown;
        startCountDown();
    }
    private void startCountDown(){
        countDownTimer=new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis=l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                 timeLeftInMillis=0;
                 updateCountDownText();
                 countDownTimer.cancel();
                 currentQindex++;
                 loadNewQuestion();



            }
        }.start();
    }
    private void updateCountDownText(){
        int minutes=(int)(timeLeftInMillis/1000)/60;
        int sec=(int)(timeLeftInMillis/1000)%60 ;
        String time=String.format(Locale.getDefault(),"%02d:%02d",minutes,sec);
        textViewCountDown.setText(time);
        if(timeLeftInMillis<10000){
            textViewCountDown.setTextColor(Color.RED);
        }
        else{
            textViewCountDown.setTextColor(Color.BLACK);
        }
    }
    void finishQuiz()
    {
        String passStatus="";
        if(score>=totalquestions*0.60)
        {
            passStatus="Passed";
        }
        else {
            passStatus="Failed";
        }
        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("score is "+score+" out of "+totalquestions)
                .setPositiveButton("restart",((dialogInterface, i) -> restartQuiz()))
                .setNegativeButton("Quit App",(((dialogInterface, i) -> Quit())))
                .setCancelable(false)
                .show();
    }
    void restartQuiz()
    {
        Intent next = new Intent(MainContent.this,MainActivity.class);
        startActivity(next);
    }
    void Quit(){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }

}