package androidhive.info.itraveller.activity;

/**
 * Created by VNK on 8/15/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;

import androidhive.info.itraveller.R;
import androidhive.info.itraveller.volley.AppController;

import static androidhive.info.itraveller.R.id.LinearLayout1;
import static androidhive.info.itraveller.R.id.btn_confirm_payment;


public class ItinerarySummaryActivity extends ActionBarActivity {
/* When using Appcombat support library
   you need to extend Main Activity to ActionBarActivity.*/

    private Toolbar mToolbar; // Declaring the Toolbar Object
    String onward_flight_rate="";
    String return_flight_rate="";
    int flight_rate = 0;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Price Summary");

        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        int TotalCountDays = 0;
        SharedPreferences prefs = getSharedPreferences("Itinerary", MODE_PRIVATE);
        Set<String> HotelData = prefs.getStringSet("HotelRooms", null);
        Set<String> Hotels = prefs.getStringSet("Hotels", null);
        String DayCount = prefs.getString("DestinationCount", null);
        String[] deatination_day_count = DayCount.trim().split(",");
       // Array
        for (int x = 0; x < deatination_day_count.length; x++) {
            TotalCountDays = TotalCountDays + Integer.parseInt(deatination_day_count[x]);
        }

        String DestinationName = prefs.getString("DestinationName", null);
        String[] destination_name = DestinationName.trim().split(",");
        ArrayList<String> Mat_Destination = new ArrayList<String>();

        String Arrival_port = prefs.getString("ArrivalPort", null);
        //Log.i("Hoteldataaaaaa","AP"+ Arrival_port);
        String Departure_port = prefs.getString("DeparturePort", null);

        int Mat_count = 0;
        for (int index = 0; index < (destination_name.length + 2); index++) {
            //Log.i("DestinationMat",deatination_day_count[index]);
            if (index == 0) {
                Mat_Destination.add(Arrival_port);
            } else if (index == (destination_name.length + 1)) {
                Mat_Destination.add(Departure_port);
            } else {
                for(int j = 0; j< (Integer.parseInt(deatination_day_count[Mat_count]) + 1); j++) {
                    Mat_Destination.add(destination_name[Mat_count]);
                }
                Mat_count++;
            }
        }
        for(int i = 0 ; i < Mat_Destination.size(); i++){
            Log.i("DestinationName_Mat", Mat_Destination.get(i));

        }

        Set<String> ActivitiesData = prefs.getStringSet("ActivitiesData", null);
        String transportation_rate = prefs.getString("TransportationCost", null);
        LinearLayout main_lay = (LinearLayout) findViewById(R.id.main_layout);

        String[] HotelsArray = Hotels.toArray(new String[Hotels.size()]);
        String[] ActivitiesDataArray = ActivitiesData.toArray(new String[ActivitiesData.size()]);

        String[] activities_val = new String[ActivitiesDataArray.length];
        for(int index=0;index <ActivitiesDataArray.length; index++){
            if(!ActivitiesDataArray[index].toString().equalsIgnoreCase("null")) {
                String activities_title = "No activities";
                int count_bit = 0;
                String[] activities_Data = ActivitiesDataArray[index].trim().split("-");
                for (int j = 0; j < activities_Data.length; j++) {
                    String[] activites_data_value = activities_Data[j].trim().split(",");
                    if(!activites_data_value.toString().equalsIgnoreCase("null")) {
                        if (count_bit == 0) {
                            activities_title = activites_data_value[2];
                            count_bit++;
                        } else {
                            activities_title = activities_title + ", " + activites_data_value[2];
                        }
                    }
                }
                activities_val[index] = ""+activities_title;
                Log.i("Actvities_Titles",activities_title);
            }
            else{
                activities_val[index] = "No Activities";
                Log.i("Actvities_Titles",activities_val[index]);
            }
        }

        int count =0;
        for (int i = 0; i < HotelsArray.length; i++) {

                for (int j = 0; j < Integer.parseInt(deatination_day_count[i]); j++) {
                    String[] hotels_Data = HotelsArray[count].trim().split(",");

                    View view = LayoutInflater.from(this).inflate(R.layout.summary_row, null);

                    NetworkImageView imageView = (NetworkImageView) view.findViewById(R.id.thumbnail);
                    imageView.setImageUrl("http://stage.itraveller.com/backend/images/hotels/" + hotels_Data[2] + ".jpg", imageLoader);
                    TextView hotel_name = (TextView) view.findViewById(R.id.hotel_name);
                    TextView hotel_des = (TextView) view.findViewById(R.id.hotel_des);
                    TextView activities_title_txt = (TextView) view.findViewById(R.id.activities);
                    // Assigning value to  imageview and textview here
                    hotel_name.setText(hotels_Data[0]);
                    hotel_des.setText(hotels_Data[1]);
                    activities_title_txt.setText(activities_val[i]);

                    main_lay.addView(view);

                }
                count++;
        }





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}

