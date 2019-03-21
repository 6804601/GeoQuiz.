    package com.ctech.vandal.geoquiz;

    import android.app.Activity;
    import android.content.Intent;
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
        private static final String KEY_INDEX = "index";
        private static final int REQUEST_CODE_CHEAT = 0;

        private Button mTrueButton;
        private Button mFalseButton;
        private ImageButton mNextButton;
        private ImageButton mPreviousButton;
        private TextView mQuestionTextView;
        private Button mCheatButton;

        private Question[] mQuestionBank = new Question[]{
                new Question(R.string.question_australia, true),
                new Question(R.string.question_america, true),
                new Question(R.string.question_asia, false),
                new Question(R.string.question_europe, true),
                new Question(R.string.question_africa, false),
                new Question(R.string.question_canada, false),
        };
        int cheatsAvailable = 3, messageResourceId = 0;
        private int mCurrentIndex = 0;

        private boolean mIsCheater;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Log.d(TAG, "onCreate(Bundle) has been called!");

            setContentView(R.layout.activity_main);

            if (savedInstanceState != null) {
                mIsCheater = savedInstanceState.getBoolean(KEY_INDEX, false);
                mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            }

            mQuestionTextView = findViewById(R.id.question_text_view);

            mPreviousButton = findViewById(R.id.previous_button);
            mPreviousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCurrentIndex = (mCurrentIndex - 1);
                    if (mCurrentIndex < 0) {
                        mCurrentIndex = mQuestionBank.length - 1;
                    }
                    updateQuestion();
                }
            });

            mTrueButton = (Button) findViewById(R.id.true_button);
            mFalseButton = (Button) findViewById(R.id.false_button);

            mNextButton = findViewById(R.id.next_button);
            mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIsCheater = false;
                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                    updateQuestion();
                }
            });
            updateQuestion();
            mCheatButton = findViewById(R.id.cheat_button);
            mCheatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cheatsAvailable >= 0) {
                        showLeft(true);
                        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                        Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                        startActivityForResult(intent, REQUEST_CODE_CHEAT);
                        cheatsAvailable--;
                    } else {
                        showLeft(false);
                    }
                }
            });
            mTrueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(true);
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
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            if (requestCode == REQUEST_CODE_CHEAT) {
                if (data == null) {
                    return;
                }
                mIsCheater = CheatActivity.wasAnswerShown(data);
            }
        }

        @Override
        public void onStart() {
            super.onStart();
            Log.d(TAG, "onStart has been called!");
        }

        @Override
        public void onResume() {
            super.onResume();
            Log.d(TAG, "onResume has been called!");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.d(TAG, "onPause has been called!");
        }

        @Override
        public void onSaveInstanceState(Bundle savedInstanceState) {
            super.onSaveInstanceState(savedInstanceState);
            Log.i(TAG, "onSaveInstanceState");
            savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
            savedInstanceState.putBoolean(KEY_INDEX, mIsCheater);
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.d(TAG, "onStop has been called!");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d(TAG, "onDestroy has been called!");
        }

        private void updateQuestion() {
            //Log.d(TAG, "Updating Question Text", new Exception());
            int questionResourceId = mQuestionBank[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(questionResourceId);
        }

        private void checkAnswer(boolean userPressedTrue) {
            boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
            if (mIsCheater) {
                messageResourceId = R.string.judgment_toast;
            } else {
                if (userPressedTrue == answerIsTrue) {
                    messageResourceId = R.string.correct_toast;
                } else {
                    messageResourceId = R.string.incorrect_toast;
                }
            }
            Toast toast = Toast.makeText(this,
                    messageResourceId,
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 224);
            toast.show();
        }

        public void randomQuestion(View v) {
            mCurrentIndex = (int) (Math.random() * 6);
            updateQuestion();
        }

        public void showLeft(boolean ifLeft) {
            if (ifLeft) {
                String messageResourceId = Integer.toString(cheatsAvailable);
                Toast toast = Toast.makeText(this,
                        messageResourceId,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 224);
                toast.show();
            } else {
                String messageResourceId = "You have no cheats left, stop!";
                Toast toast = Toast.makeText(this,
                        messageResourceId,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 224);
                toast.show();
            }
        }
    }
