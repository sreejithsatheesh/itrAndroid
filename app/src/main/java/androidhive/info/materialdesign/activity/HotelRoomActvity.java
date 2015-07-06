package androidhive.info.materialdesign.activity;

/**
 * Created by VNK on 6/11/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.model.LandingModel;
import androidhive.info.materialdesign.volley.AppController;


public class HotelRoomActvity extends Activity {
/* When using Appcombat support library
   you need to extend Main Activity to ActionBarActivity.*/

    private Toolbar toolbar; // Declaring the Toolbar Object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_single);
        String url = "http://stage.itraveller.com/backend/api/v1/hotelRoom/hotelId/[2]";
        //url = "http://stage.itraveller.com/backend/api/v1/internationalflight?travelFrom=BOM&arrivalPort=MRU&departDate=2015-07-26&returnDate=2015-08-01&adults=2&children=0&infants=0&departurePort=MRU&travelTo=BOM";
        hotelRooms(url);
        //ViewGroup roompick = (ViewGroup) findViewById(R.id.hour_radio_group);  // This is the id of the RadioGroup we defined
        LinearLayout ll = (LinearLayout) findViewById(R.id.main);
        final RadioButton[] button = new RadioButton[5];

        for (int i = 0; i < 5; i++) {
            //Declaration for
            button[i]  = new RadioButton(this);
            final Button plus = new Button(this);
            final Button minus = new Button(this);

            plus.setText("+");
            minus.setText("-");
            button[i].setText(Integer.toString(i));

            plus.setId(i);
            minus.setId(i);
            button[i].setId(i);

            button[i].setChecked(false); // Only select button with same index as currently selected number of hours
            //button.setBackgroundResource(R.drawable.item_selector); // This is a custom button drawable, defined in XML
            //roompick.addView(button);
            ll.addView(plus);
            ll.addView(minus);
            ll.addView(button[i]);

            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // ((RadioGroup) view.getParent()).check(view.getId());
                    for (int index = 0; index < 5; index++) {
                        Log.i("Test", "" + view.getId());
                        if (view.getId() == button[index].getId())
                            button[index].setChecked(true);
                        else
                            button[index].setChecked(false);
                    }
                }
            });
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("Test", "" + plus.getId());
                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("Test", "" + minus.getId());
                }
            });

        }

    }

    public void hotelRooms(String url)
    {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Boolean", "" + response.getBoolean("success"));
                    Log.d("Error", ""+response.getJSONObject("error"));
                    Log.d("Payload", ""+response.getJSONArray("payload"));

                    // JSONObject jsonobj = response.getJSONObject("payload").getJSONObject()
                    // Parsing json
                    for (int i = 0; i < response.getJSONArray("payload").length(); i++) {

                        JSONObject jsonarr = response.getJSONArray("payload").getJSONObject(i);
                        LandingModel landing_model = new LandingModel();
                    }

                } catch (JSONException e) {
                    Log.d("Error Catched","" +e.getMessage());
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                    VolleyLog.d("Volley Error", "Error: " + error.networkResponse);

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }


}

