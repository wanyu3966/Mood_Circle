package moodcircle_interface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import eecs395_495.mhealth_moodcircle.MainActivity;
import eecs395_495.mhealth_moodcircle.R;

/** This needs to generate interface (UI) content based off of items inhereted from MoodWords.
 * Meaning, MoodWords should save the selected words and pass them along to this screen.
 * This screen should take each of those words and turn them into a rating (SeekBar), similar to
 * the ones on MoodBinary.
 */

public class MoodRatings extends AppCompatActivity {
    Button finish;
    TextView ratings_positive;
    TextView ratings_negative;
    SeekBar positive_Seekbar;
    SeekBar negative_Seekbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_ratings);
        finish= (Button) findViewById(R.id.ratings_button_finish);
        ratings_negative= (TextView) findViewById(R.id.ratings_negative);
        ratings_positive= (TextView) findViewById(R.id.ratings_positive);
        SharedPreferences sharedPreferences=getSharedPreferences("survey",MODE_ENABLE_WRITE_AHEAD_LOGGING);
        ratings_positive.setText(sharedPreferences.getString("pEmotion",null));
        ratings_negative.setText(sharedPreferences.getString("nEmotion",null));
    }

    /** Called when the user clicks the FINISH button on the RATINGS screen */
    public void finishAssessment(View view) {
        // save each position of every SeekBar
        // push all responses (from this screen and previous screens) to the server
        // push all passive data on phone to server
        // save time assessment was completed
        // change status text of associated assessment to "Complete" (@string/main_label_status_complete)

        Intent returnMain=new Intent(MoodRatings.this, MainActivity.class);
        MoodRatings.this.startActivity(returnMain);

    }

}
