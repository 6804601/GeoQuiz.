package com.ctech.vandal.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia,  true),
            new Question(R.string.question_america,  true),
            new Question(R.string.question_asia,  false),
            new Question(R.string.question_europe,  true),
            new Question(R.string.question_africa,  false),
            new Question(R.string.question_canada,  false),
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG,  "onCreate(Bundle) has been called!");

        setContentView(R.layout.activity_main);

        mQuestionTextView = findViewById(R.id.question_text_view);

        mPreviousButton = findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex -1);
                if (mCurrentIndex < 0){
                    mCurrentIndex = mQuestionBank.length-1;
                }
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);



        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
            updateQuestion();

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            checkAnswer( true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            checkAnswer(false);
            }
        });

    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart has been called!");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume has been called!");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause has been called!");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop has been called!");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy has been called!");
    }

    private void updateQuestion(){
        int questionResourceId = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(questionResourceId);
    }
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResourceId = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResourceId = R.string.correct_toast;
        }
        else{
            messageResourceId = R.string.incorrect_toast;
        }
        Toast toast = Toast.makeText(this,
                messageResourceId,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,224);
        toast.show();
    }
    public void randomQuestion(View v){
        mCurrentIndex = (int) (Math.random() * 6);
        updateQuestion();
    }
}
