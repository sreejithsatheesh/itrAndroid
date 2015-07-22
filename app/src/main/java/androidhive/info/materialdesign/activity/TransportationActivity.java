package androidhive.info.materialdesign.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.TransportationAdapter;
import androidhive.info.materialdesign.model.TransportationModel;
import androidhive.info.materialdesign.volley.AppController;

/**
 * Created by VNK on 6/9/2015.
 */


public class TransportationActivity extends ActionBarActivity {

    private String url = "http://stage.itraveller.com/backend/api/v1/transportation?region=";
    private List<TransportationModel> transportationList = new ArrayList<TransportationModel>();
    private TransportationAdapter adapter;
    private ListView transportation_list;
    private Toolbar mToolbar;
    public static final String MY_PREFS = "ScreenHeight";
    private  int _screen_height;
    int toggle =0;
    private Button filter_btn;
    private LinearLayout filter_details;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    public static int lowest_trans = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transportation_list);

        sharedpreferences = getSharedPreferences("Itinerary", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        SharedPreferences prefsData = getSharedPreferences("Itinerary", MODE_PRIVATE);
        String Region_id = prefsData.getString("RegionID", null);
        url = url + Region_id;
        Log.i("Transportation_URL", "" + url);
        Log.i("ArrivalAirport",""+prefsData.getString("ArrivalAirport", null));
        Log.i("DepartureAirport",""+prefsData.getString("DepartureAirport", null));
        Log.i("ArrivalPort",""+prefsData.getString("ArrivalPort", null));
        Log.i("DeparturePort",""+prefsData.getString("DeparturePort", null));

        String NewURL = "http://stage.itraveller.com/backend/api/v1/destination/destinationId/";
        ShortName(NewURL + prefsData.getString("ArrivalPort", null),"Arrival");
        ShortName(NewURL + prefsData.getString("DeparturePort", null),"Departure");
        //mToolbar = (Toolbar) findViewById(R.id.toolbar);
        /*setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
*/
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        _screen_height = prefs.getInt("Screen_Height", 0)-(prefs.getInt("Status_Height", 0) + prefs.getInt("ActionBar_Height", 0));
        Log.i("iTraveller", "Screen Height: " + _screen_height);
        int width = prefs.getInt("Screen_Width", 0); //0 is the default value.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,(_screen_height - 60));


        Button next_btn = (Button) findViewById(R.id.to_payment);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefsData = getSharedPreferences("Itinerary", MODE_PRIVATE);
                if(prefsData.getString("TravelFrom", null).equalsIgnoreCase("1")||prefsData.getString("TravelTo", null).equalsIgnoreCase("1")) {

                }
                else
                {
                    Intent intent = new Intent(TransportationActivity.this, FlightActivity.class);
                    startActivity(intent);
                }
            }
        });



        //Bundle to read value from another activity
        /*Bundle bundle = getIntent().getExtras();
        //Print
        System.out.println("RegionID: " + bundle.getInt("Region_Id"));
        url= url + bundle.getInt("RegionID");
        getSupportActionBar().setTitle(bundle.getString("Title"));*/

      // mToolbar = (Toolbar) findViewById(R.id.toolbar);
    //   setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        transportation_list = (ListView) findViewById(R.id.transportation_list);
        adapter = new TransportationAdapter(this, transportationList);
        transportation_list.setAdapter(adapter);

        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Boolean", ""+response.getBoolean("success"));
                    Log.d("Error", ""+response.getJSONObject("error"));
                    Log.d("Payload", ""+response.getJSONArray("payload"));

                    // JSONObject jsonobj = response.getJSONObject("payload").get;
                    // Parsing json
                    for (int i = 0; i < response.getJSONArray("payload").length(); i++) {

                        JSONObject jsonarr = response.getJSONArray("payload").getJSONObject(i);
                        String Tra_url = "http://stage.itraveller.com/backend/api/v1/b2ctransportation?transportationId=";
                        TransportationCost(Tra_url + jsonarr.getInt("Id"),jsonarr.getString("Title"),jsonarr.getInt("Max_Person"),jsonarr.getString("Image"),i);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Error", "Error: " + error.getMessage());
                //pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    public void TransportationCost(String TransURL, final String title, final int max_person, final String img, final int index)
    {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,
                TransURL, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Boolean", ""+response.getBoolean("success"));
                    Log.d("Error", ""+response.getJSONObject("error"));
                    Log.d("Payload", ""+response.getJSONObject("payload"));

                    // JSONObject jsonobj = response.getJSONObject("payload").get;
                    // Parsing json
                        JSONObject jsonarr = response.getJSONObject("payload");
                        TransportationModel transportation_model = new TransportationModel();
                        if(index == 0) {
                        lowest_trans = Integer.parseInt(""+jsonarr.getInt("Cost"));
                        }
                    else {
                            if((lowest_trans >= Integer.parseInt(""+jsonarr.getInt("Cost"))) && (Integer.parseInt(""+jsonarr.getInt("Cost")) !=0) ){
                                lowest_trans = Integer.parseInt(""+jsonarr.getInt("Cost"));
                            }
                        }
                        transportation_model.setId(jsonarr.getInt("Id"));
                        transportation_model.setTransportation_Id(jsonarr.getInt("Transportation_Id"));
                        transportation_model.setTitle("" + title);
                        transportation_model.setCost(jsonarr.getInt("Cost"));
                        transportation_model.setCost1(jsonarr.getInt("Cost1"));
                        transportation_model.setKM_Limit(jsonarr.getInt("KM_Limit"));
                        transportation_model.setPrice_Per_KM(jsonarr.getInt("Price_Per_KM"));
                        transportation_model.setMax_Person(max_person);
                        transportation_model.setImage(img);

                        transportationList.add(transportation_model);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Error", "Error: " + error.getMessage());
                //pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }*/

        if(id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*public void onBackPressed() {
        if(filter_btn.getText().toString().equalsIgnoreCase("Apply Filter"))
        {
            filter_details.setVisibility(View.GONE);
            filter_btn.setText("Filter");
        }
        else
        {
            finish();
        }
    }*/

    public void ShortName(String ShortnameURL, final String arr_dep)
    {
        Log.i("ShortNameURL " + arr_dep,""+ShortnameURL);
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,
                ShortnameURL, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Boolean", ""+response.getBoolean("success"));
                    Log.d("Error", ""+response.getJSONObject("error"));
                    Log.d("Payload", ""+response.getJSONObject("payload"));

                    // JSONObject jsonobj = response.getJSONObject("payload").get;
                    // Parsing json
                    for (int i = 0; i < response.getJSONObject("payload").length(); i++) {
                        if(arr_dep.equalsIgnoreCase("Departure")) {
                            if (response.getJSONObject("payload").getString("Code").equalsIgnoreCase("1")) {
                                editor.putString("TravelFrom", "1");
                            } else {
                                editor.putString("TravelFrom", response.getJSONObject("payload").getString("Code"));
                            }
                        }else if(arr_dep.equalsIgnoreCase("Arrival")){
                            if (response.getJSONObject("payload").getString("Code").equalsIgnoreCase("1")) {
                                editor.putString("TravelTo", "1");
                            } else {
                                editor.putString("TravelTo", response.getJSONObject("payload").getString("Code"));
                            }
                        }
                        editor.commit();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Error", "Error: " + error.getMessage());
                //pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
}