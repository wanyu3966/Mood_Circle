package moodcircle_interface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import eecs395_495.mhealth_moodcircle.R;

/**
 * This is the activity for the mood words aspect of the mood assessment.
 */

public class MoodWords extends AppCompatActivity {
    //positive buttons
    Button Happy;
    Button Grateful;
    Button Content;
    Button Enthusiastic;
    Button Inspired;
    Button Proud;
    EditText PositiveEmotion;
    //negative buttons
    Button Guilty;
    Button Anxious;
    Button Bored;
    Button Fearful;
    Button Angry;
    Button Sad;
    EditText NegativeEmotion;

    //submit button
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_words);

        //all emotion buttons
        Happy = (Button) findViewById(R.id.words_button_happy);
        Grateful = (Button) findViewById(R.id.words_button_grateful);
        Content = (Button) findViewById(R.id.words_button_content);
        Enthusiastic = (Button) findViewById(R.id.words_button_enthusiastic);
        Inspired = (Button) findViewById(R.id.words_button_inspired);
        Proud = (Button) findViewById(R.id.words_button_proud);
        Guilty= (Button) findViewById(R.id.words_button_guilty);
        Anxious= (Button) findViewById(R.id.words_button_anxious);
        Bored= (Button) findViewById(R.id.words_button_bored);
        Fearful= (Button) findViewById(R.id.words_button_fearful);
        Angry= (Button) findViewById(R.id.words_button_angry);
        Sad= (Button) findViewById(R.id.words_button_sad);

        //EditText
        PositiveEmotion= (EditText) findViewById(R.id.words_other_positive);
        NegativeEmotion= (EditText) findViewById(R.id.words_other_negative);

        //submit button
        submit= (Button) findViewById(R.id.words_button_submit);
    }

    /**
     * Called when the user clicks the SUBMIT button on the WORDS screen
     */
    public void saveWordsResponse(View view) {
        // save selected words (indicated via words selected during toggleWordsResponse)
        // save "other" words (e.g., other positive and other negative) if a user has input a unique word
        // initiate the next part of the assessment
        String pEmotion;
        String nEmotion;
        pEmotion=PositiveEmotion.getText().toString();
        nEmotion=NegativeEmotion.getText().toString();
        SharedPreferences sharedPreferences=getSharedPreferences("survey",MODE_ENABLE_WRITE_AHEAD_LOGGING);
        SharedPreferences.Editor motionEditor=sharedPreferences.edit();
        motionEditor.putString("pEmotion",pEmotion);
        motionEditor.putString("nEmotion",nEmotion);
        motionEditor.commit();
        Intent moodRatings=new Intent(MoodWords.this,MoodRatings.class);
        MoodWords.this.startActivity(moodRatings);
    }

    /**
     * Called when the user clicks the SUBMIT button on the WORDS screen
     */
    public void toggleWordsResponse(View view) {
        // if the word is unselected, select it.
        // if the word is selected, unselect it.

    }
}
