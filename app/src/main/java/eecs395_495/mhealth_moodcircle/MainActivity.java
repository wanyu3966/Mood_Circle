package eecs395_495.mhealth_moodcircle;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView location;
    TextView recognize;
    TextView hello;
    TextView call_log;
    ListView location_View;

    ContentResolver cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        location = (TextView) findViewById(R.id.location);
        hello = (TextView) findViewById(R.id.hello);
        recognize = (TextView) findViewById(R.id.recognize);
        call_log = (TextView) findViewById(R.id.call_log);
        location_View = (ListView) findViewById(R.id.list);

        cr = this.getContentResolver();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_location) {
            // Handle the location actions
            GPSTracker gps = new GPSTracker(this);
//            TextView textView = (TextView) findViewById(locate);
            location.setVisibility(View.INVISIBLE);
            hello.setVisibility(View.INVISIBLE);
            call_log.setVisibility(View.INVISIBLE);
            recognize.setVisibility(View.INVISIBLE);

            if (gps.canGetLocation()) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
//                float size= (float) 15.0;
//                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                String[] location_arr = {"\n\n\nYour location is:\n Latitude:" + String.valueOf(latitude), "longitude:" + String.valueOf(longitude)};
                location.setText("Your location is - Latitude:" + latitude + "\nLongitude: " + longitude);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, location_arr);
                location_View.setAdapter(arrayAdapter);
                location_View.setVisibility(View.VISIBLE );
//                location.setTextSize(size);
            } else {
                return false;
            }
        } else if (id == R.id.nav_Calllog) {
            //CALL_LOG

            Call_LogHelper call_logHelper=new Call_LogHelper();
            call_logHelper.getCallDetails(this);

            String[] callDetails = new String[5];
            callDetails[0] = "\n\n\n";
            callDetails[1] = call_logHelper.getCall_Log();
            ArrayAdapter<String> callAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, callDetails);
            location_View.setAdapter(callAdapter);
            location_View.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_smsMessage) {

            //SMS Message
            List<Sms> msg = new ArrayList<>();
//            msg = this.getAllSms();
            SMS_Tracker sms_tracker=new SMS_Tracker();
            sms_tracker.getAllSms(this);
            msg=sms_tracker.getSmsList();
            StringBuilder sb=new StringBuilder();

            Iterator<Sms> msgIterator=msg.iterator();
            while (msgIterator.hasNext()){
                sb.append(msgIterator.next().getWholeMsg());
                sb.append("\n-------");
            }
            String[] smsMsg=new String[5];
            smsMsg[0]="\n\n\n";
            smsMsg[1]=sb.toString();
            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, smsMsg);
            location_View.setAdapter(arrayAdapter);
            location_View.setVisibility(View.VISIBLE);


        } else if (id == R.id.nav_questionnaire) {
            location_View.setVisibility(View.INVISIBLE);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}



