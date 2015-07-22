package androidhive.info.materialdesign.activity;

/**
 * Created by VNK on 6/11/2015.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.volley.AppController;


public class SummaryActivity extends Activity {
/* When using Appcombat support library
   you need to extend Main Activity to ActionBarActivity.*/

        private Toolbar toolbar; // Declaring the Toolbar Object

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.payment_billing);
            SharedPreferences prefs = getSharedPreferences("Itinerary", MODE_PRIVATE);
            Set<String> HotelData = prefs.getStringSet("HotelRooms", null);
            Set<String> ActivitiesData = prefs.getStringSet("ActivitiesData", null);
            String transportation_rate = prefs.getString("TransportationCost", null);

            String[] HotelDataArray = HotelData.toArray(new String[HotelData.size()]);
            String[] ActivitiesDataArray = ActivitiesData.toArray(new String[ActivitiesData.size()]);

            String DayCount = prefs.getString("DestinationCount", null);
            String[] destination_day_count = DayCount.trim().split(",");
            int rate_of_rooms =0;
            for (int index = 0; index < HotelDataArray.length; index++) {   //Log.i("Hoteldataaaaaa",""+ HotelDataArray[index]);
                String[] hotel_room_Data = HotelDataArray[index].trim().split(",");
                //no fo rooms and price
                int no_room_price = Integer.parseInt("" + hotel_room_Data[3]) * Integer.parseInt("" + hotel_room_Data[2]);
                int room_rate = Integer.parseInt("" + destination_day_count[index]) * no_room_price;
                if(index == 0)
                {
                    rate_of_rooms = room_rate;
                }
                else{
                    rate_of_rooms = rate_of_rooms + room_rate;
                }
                Log.i("RoomRates","" +rate_of_rooms);
            }

            int activities_rate =0;
            int count_bit= 0;
            for (int index = 0; index < ActivitiesDataArray.length; index++) {   //Log.i("Hoteldataaaaaa",""+ HotelDataArray[index]);


                if(!ActivitiesDataArray[index].toString().equalsIgnoreCase("null")) {

                    String[] activities_Data = ActivitiesDataArray[index].trim().split(",");
                    Log.i("ActivityData","" +activities_Data);
                    if (count_bit == 0) {
                        activities_rate = Integer.parseInt("" + activities_Data[1]);
                        count_bit ++;
                    } else {
                        activities_rate = activities_rate + Integer.parseInt("" + activities_Data[1]);
                    }
                }
            }
            Log.i("ActvitiesRates","" +activities_rate);
            Log.i("TransportationRates","" +transportation_rate);

            int total_price = rate_of_rooms + activities_rate + Integer.parseInt("" +transportation_rate);
            double discount_val = 0.2;
            Double total_discount = Double.parseDouble("" + total_price) * discount_val ;
            TextView package_v = (TextView) findViewById(R.id.price_txt);
            TextView total = (TextView) findViewById(R.id.total_p_txt);
            TextView total_dis = (TextView) findViewById(R.id.booking_price_txt);
            package_v.setText("Rs " + total_price);
            total.setText("Rs " + total_price);
            total_dis.setText("Rs " + total_discount);
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

            //noinspection SimplifiableIfStatement
           /* if (id == R.id.action_settings) {
                return true;
            }*/

            return super.onOptionsItemSelected(item);
        }
    }

