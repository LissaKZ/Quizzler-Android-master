package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestionTextView;
    int mIndex;
    int mQuestion;
    ProgressBar mProgressBar;
    int mScore;
    TextView mScoreTextView;

    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
   };

    final int PROGRESS_BAR_INCRIMENT=(int)Math.ceil(100/mQuestionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null){
            mScore=savedInstanceState.getInt("ScoreKey");
            mIndex=savedInstanceState.getInt("IndexKey");
        }else {
            mScore=0;
            mIndex=0;
        }

        mFalseButton=findViewById(R.id.false_button);
        mTrueButton=findViewById(R.id.true_button);
        mQuestionTextView=findViewById(R.id.question_text_view);
        mProgressBar=findViewById(R.id.progress_bar);
        mScoreTextView=findViewById(R.id.score);

        mQuestion=mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                updateQuestion();
                Log.d("Quizzler","Button pressed");
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                updateQuestion();
                Log.d("Quizzler","Button pressed");
            }
        });

    }

    private void updateQuestion(){
        mIndex=(mIndex+1)%mQuestionBank.length;
        if(mIndex==0){
            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Game over!");
            alert.setCancelable(false);
            alert.setMessage("Your score is "+mScore+"/"+mQuestionBank.length);
            alert.setPositiveButton("Close app", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.show();
        }
        mQuestion=mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mScoreTextView.setText("Score "+mScore+"/"+mQuestionBank.length);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCRIMENT);
    }

    private void checkAnswer(boolean Answer){
        boolean correctAnswer=mQuestionBank[mIndex].isAnswer();
        if (Answer==correctAnswer){
            Toast.makeText(getApplicationContext(),R.string.correct_toast,Toast.LENGTH_SHORT).show();
            mScore=mScore+1;
        }else {
            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey",mScore);
        outState.putInt("IndexKey",mIndex);
    }

}
