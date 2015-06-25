package androidhive.info.materialdesign.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.RegionPlaceAdapter;
import androidhive.info.materialdesign.dragsort.DragAndSort;
import androidhive.info.materialdesign.model.RegionPlaceModel;
import androidhive.info.materialdesign.volley.AppController;

/**
 * Created by VNK on 6/9/2015.
 */


public class RegionPlace extends ActionBarActivity {

    private String url = "http://stage.itraveller.com/backend/api/v1/itinerary/regionId/";
    private List<RegionPlaceModel> regionList = new ArrayList<RegionPlaceModel>();
    private RegionPlaceAdapter adapter;
    private ListView listView;
    private Toolbar mToolbar;
    public static final String MY_PREFS = "ScreenHeight";
    private  int _screen_height;
    int toggle =0;
    private Button filter_btn;
    private LinearLayout filter_details;
    private Bundle bundle;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.region_place_listview);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


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
        filter_btn = (Button) findViewById(R.id.filterbutton);
        final TextView minval = (TextView) findViewById(R.id.minvalue);
        final TextView maxval = (TextView) findViewById(R.id.maxvalue);
        filter_details = (LinearLayout) findViewById(R.id.filterdetails);
        final Button day_night = (Button) findViewById(R.id.day_night);
        final Button sub_btn = (Button) findViewById(R.id.subbtn);
        sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggle > 1) {
                    toggle--;
                    day_night.setText("Day " + toggle);
                }
                else
                    day_night.setText("Day/Night");
            }
        });
        final Button add_btn = (Button) findViewById(R.id.addbtn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle++;
                day_night.setText("Day "+ toggle);
            }
        });
        final Button filter_btn = (Button) findViewById(R.id.filterbutton);

        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filter_btn.getText().toString().equalsIgnoreCase("Filter")) {
                    filter_details.setVisibility(View.VISIBLE);
                    filter_btn.setText("Apply Filter");

                    //adapter.filter(Integer.parseInt(minval.getText().toString()),Integer.parseInt(maxval.getText().toString()));
                }
                else
                {
                    filter_details.setVisibility(View.GONE);
                    filter_btn.setText("Filter");
                    adapter.getFilter().filter(minval.getText().toString() + "," + maxval.getText().toString() + "," + toggle);
                }
            }
        });
        SharedPreferences prefs = getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
        _screen_height = prefs.getInt("Screen_Height", 0)-(prefs.getInt("Status_Height", 0) + prefs.getInt("ActionBar_Height", 0));
        Log.i("iTraveller", "Screen Height: " + _screen_height);
        int width = prefs.getInt("Screen_Width", 0); //0 is the default value.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,(_screen_height - 60));
        filter_details.setLayoutParams(lp);
        filter_details.setVisibility(View.GONE);
        RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<Integer>(this);
        // Set the range
        rangeSeekBar.setRangeValues(12000, 110000);
        rangeSeekBar.setSelectedMinValue(12000);
        rangeSeekBar.setSelectedMaxValue(110000);
        rangeSeekBar.setNotifyWhileDragging(true);
        // Add to layout
        LinearLayout layout = (LinearLayout) findViewById(R.id.seekbar_placeholder);
        layout.addView(rangeSeekBar);

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, final Integer minValue, final Integer maxValue) {
                Log.i("SeekBar", "Min :" + minValue + " Max :" + maxValue);
                runOnUiThread(new Runnable() {
                    public void run() {
                        // Update UI elements
                        minval.setText(""+minValue);
                        maxval.setText(""+maxValue);
                    }
                });

            }
        });

        //Bundle to read value from another activity
        bundle = getIntent().getExtras();
        //Print
        System.out.println("RegionID: " + bundle.getInt("RegionID"));
        url= url + bundle.getInt("RegionID");
        getSupportActionBar().setTitle(bundle.getString("RegionName"));

      // mToolbar = (Toolbar) findViewById(R.id.toolbar);
    //   setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new RegionPlaceAdapter(this, regionList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


            }
        });

        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    //Log.d("Boolean", "" + response.getJSONObject("Itinerary"));
                    JSONObject resobj = response.getJSONObject("Itinerary");
                    Iterator<?> keys = resobj.keys();
                    while(keys.hasNext() ) {
                        String key = (String)keys.next();
                        Log.i("KeyValue",""+key);
                       if ( resobj.get(key) instanceof JSONObject ) {
                           RegionPlaceModel region_adp = new RegionPlaceModel();

                           JSONObject jsonobj = new JSONObject(resobj.get(key).toString());
                           // Log.d("res1",""+xx.getJSONObject("Master"));
                           Log.d("KeyDestinationDiscount", "" + jsonobj.getInt("Discount"));

                           region_adp.setTitle(jsonobj.getJSONObject("Master").getString("Title"));
                           region_adp.setLink(jsonobj.getJSONObject("Master").getString("Link"));
                           //region_adp.setImage(jsonobj.getJSONObject("Master").getString("Image"));
                           region_adp.setImage("http://stage.itraveller.com/backend/userdata/packages/" + jsonobj.getJSONObject("Master").getInt("Itinerary_Id") + ".jpg");
                           Log.d("Images:", "http://stage.itraveller.com/backend/userdata/packages/" + jsonobj.getJSONObject("Master").getInt("Itinerary_Id") + ".jpg");
                           region_adp.setArrival_Port_Id(jsonobj.getJSONObject("Master").getInt("Arrival_Port_Id"));
                           region_adp.setDeparture_Port_Id(jsonobj.getJSONObject("Master").getInt("Departure_Port_Id"));
                           region_adp.setItinerary_Id(jsonobj.getJSONObject("Master").getInt("Itinerary_Id"));
                           region_adp.setDuration_Day(jsonobj.getJSONObject("Master").getInt("Duration_Day"));
                           region_adp.setPrice(jsonobj.getJSONObject("Master").getInt("Price"));
                           region_adp.setDiscount(jsonobj.getInt("Discount"));


                           Iterator<?> destinationKeys = jsonobj.getJSONObject("Destination").keys();
                           int i = 0;
                           String destinationKeyValue = null;
                           String destinationValue = null;
                           while(destinationKeys.hasNext() ) {
                               String destinationKey = (String) destinationKeys.next();
                               if(i == 0)
                               {
                                   destinationKeyValue = destinationKey;
                                   JSONObject destobj = jsonobj.getJSONObject("Destination").getJSONObject(destinationKey);
                                   destinationValue = destobj.getString("name");

                                   i++;
                               }
                               else{
                                   destinationKeyValue = destinationKeyValue + "," + destinationKey;
                                   JSONObject destobj = jsonobj.getJSONObject("Destination").getJSONObject(destinationKey);
                                   destinationValue = destinationValue + "," + destobj.getString("name").toString();
                               }
                               //Log.i("KeyDestination", "" + jsonobj.getJSONObject("Destination").getString(destinationKey));
                               region_adp.setDestination_Key(destinationKeyValue);
                               region_adp.setDestination(destinationValue);
                           }
                           Log.i("Key_DestinationValue", "" + destinationKeyValue);
                           Log.i("Key_Destination", "" + destinationValue);
                           //region_adp.setDestination("Testing");
                           Log.d("Discount", "" +region_adp.getDiscount());
                           regionList.add(region_adp);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               // pDialog.hide();
               //  region_adapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                //searchText.startAnimation(animFadein);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void onBackPressed() {
        if(filter_btn.getText().toString().equalsIgnoreCase("Apply Filter"))
        {
            filter_details.setVisibility(View.GONE);
            filter_btn.setText("Filter");
        }
        else
        {
            finish();
        }
    }
}
