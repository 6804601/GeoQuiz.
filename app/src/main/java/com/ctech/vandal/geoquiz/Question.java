package com.ctech.vandal.geoquiz;

public class Question {
    private int mTextRedId;
    private boolean mAnswerTrue;

    public Question(int textResId, boolean answerTrue){
        mTextRedId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getmTextRedId() {
        return mTextRedId;
    }

    public void setmTextRedId(int mTextRedId) {
        this.mTextRedId = mTextRedId;
    }

    public boolean ismAnswerTrue() {
        return mAnswerTrue;
    }

    public void setmAnswerTrue(boolean mAnswerTrue) {
        this.mAnswerTrue = mAnswerTrue;
    }
}
