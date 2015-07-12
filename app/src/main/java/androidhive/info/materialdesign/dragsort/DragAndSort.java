package androidhive.info.materialdesign.dragsort;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

import org.json.JSONException;
import org.json.JSONObject;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.AirportAdapter;
import androidhive.info.materialdesign.adapter.PortAndLocAdapter;
import androidhive.info.materialdesign.adapter.RearrangePlaceAdapter;
import androidhive.info.materialdesign.constant.Utility;
import androidhive.info.materialdesign.model.AirportModel;
import androidhive.info.materialdesign.model.PortAndLocModel;
import androidhive.info.materialdesign.model.RearrangePlaceModel;
import androidhive.info.materialdesign.volley.AppController;

public class DragAndSort extends ActionBarActivity
{
    DragSortListView listView;
    String[] names;
   // ArrayAdapter<String> adapter;
    Toolbar mToolbar;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    ////// New Page
    private List<AirportModel> airportList = new ArrayList<AirportModel>();
    private AirportAdapter adapter_airport;
    //Adapter for port and locations
    private List<PortAndLocModel> portandLocList = new ArrayList<PortAndLocModel>();
    private PortAndLocAdapter adapter_portandLoc;
    //Adapter for places and rearrange
    private List<RearrangePlaceModel> rearrangeList = new ArrayList<RearrangePlaceModel>();
    private RearrangePlaceAdapter adapter_rearrange;

    public static ListView listview;
    private SearchView search_airport;
    private TextView from_home , from_travel , to_home ,to_travel;
    private String url;
    private int region_id;
    private int check_bit = 0;
    private ScrollView scroll;


    private LinearLayout destination_page;
    private LinearLayout airportlist_page;
   /* public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        Log.i("Dp to px",""+px);
        return px;
    }
*/

   private DragSortListView.DropListener onDrop = new DragSortListView.DropListener()
    {
        @Override
        public void drop(int from, int to)
        {
            if (from != to)
            {
                Log.i("TestData1","Test");
                RearrangePlaceModel item = adapter_rearrange.getList().get(from);
                adapter_rearrange.remove(item);
                adapter_rearrange.insert(item, to);
            }
        }
    };

    private DragSortListView.RemoveListener onRemove = new DragSortListView.RemoveListener()
    {
        @Override
        public void remove(int which)
        {
            adapter_rearrange.remove(adapter_rearrange.getList().get(which));
         //   LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, rearrangeList.size()* (dpToPx(70)));
          //  listView.setLayoutParams(lp);
            Utility.setListViewHeightBasedOnChildren(listView);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.destination);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //Main Layout
        destination_page = (LinearLayout) findViewById(R.id.destination_page);
        airportlist_page = (LinearLayout) findViewById(R.id.airport_listview);

        LinearLayout from_airport_sp = (LinearLayout) findViewById(R.id.from_home_airport);
        LinearLayout from_port_sp = (LinearLayout) findViewById(R.id.from_travel_port);
        LinearLayout to_port_sp = (LinearLayout) findViewById(R.id.to_travel_port);
        LinearLayout to_airport_sp = (LinearLayout) findViewById(R.id.to_home_airport);
        ImageButton add_new = (ImageButton) findViewById(R.id.imageButton);


        NetworkImageView img = (NetworkImageView) findViewById(R.id.thumbnail);
        TextView title_sp = (TextView) findViewById(R.id.title_rp);
        TextView duration_sp = (TextView) findViewById(R.id.duration_rp);
        scroll = (ScrollView) findViewById(R.id.scrollView);
        from_home = (TextView) findViewById(R.id.from_home_destination);
        from_travel = (TextView) findViewById(R.id.from_travel_destination);
        to_home = (TextView) findViewById(R.id.to_home_destination);
        to_travel = (TextView) findViewById(R.id.to_travel_destination);
        url = "http://stage.itraveller.com/backend/api/v1/destination?regionId=" + region_id + "&port=1";

        //Default value set
        from_home.setText("MUMBAI");
        to_home.setText("MUMBAI");
        Bundle bundle = getIntent().getExtras();
        img.setImageUrl(bundle.getString("Image"), imageLoader);
        duration_sp.setText(bundle.getInt("Duration") + " Nights / " + (bundle.getInt("Duration") - 1) + " Days");
        title_sp.setText(bundle.getString("Title"));
        region_id = bundle.getInt("RegionID");
        String TestValue = bundle.getString("Destinations");

        airportJSONForText("http://stage.itraveller.com/backend/api/v1/destination?regionId=" + region_id + "&port=1", bundle.getInt("ArrivalPort"),bundle.getInt("DeparturePort"));

        listView = (DragSortListView) findViewById(R.id.listview);
        names = TestValue.trim().split(",");

        adapter_rearrange = new RearrangePlaceAdapter(this,rearrangeList);
        listView.setAdapter(adapter_rearrange);
        //listView.setAdapter(adapter);
        for(int i = 0; i<names.length ; i++)
        {
            RearrangePlaceModel m = new RearrangePlaceModel();
            m.setPlace(names[i]);
            m.setNights("0");
            rearrangeList.add(m);
        }
        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destination_page.setVisibility(View.GONE);
                airportlist_page.setVisibility(View.VISIBLE);
                url = "http://stage.itraveller.com/backend/api/v1/destination?regionId=" + region_id + "&port=0";
                airportJSON(url, false);
                check_bit = 5;
            }
        });

        //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, rearrangeList.size()* (dpToPx(70)) );
        //listView.setLayoutParams(lp);
        Utility.setListViewHeightBasedOnChildren(listView);

        adapter_rearrange.notifyDataSetChanged();

        listView.setDropListener(onDrop);
        listView.setRemoveListener(onRemove);
        final DragSortController controller = new DragSortController(listView);
        controller.setDragHandleId(R.id.drag_handle);
        //controller.setClickRemoveId(R.id.);
        controller.setRemoveEnabled(true);
        controller.setSortEnabled(true);
        controller.setDragInitMode(1);

        //controller.setRemoveMode(removeMode);

        listView.setFloatViewManager(controller);
        //listView.setOnTouchListener(controller);
        //listView.setOnLongClickListener(controller);
        listView.setDragEnabled(true);
       /* listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                controller.onLongPress(motionEvent);
                return false;
            }
        });*/

        from_airport_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ValueData", "" + rearrangeList.get(0).getPlace());
                /*final Intent i = new Intent(DragAndSort.this, AirportList.class);
                i.putExtra("Url", "http://stage.itraveller.com/backend/api/v1/airport");
                i.putExtra("Place" , 1);
                startActivity(i);*/
                destination_page.setVisibility(View.GONE);
                airportlist_page.setVisibility(View.VISIBLE);
                url = "http://stage.itraveller.com/backend/api/v1/airport";
                airportJSON(url, true);
                check_bit = 1;
            }
        });
        from_port_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destination_page.setVisibility(View.GONE);
                airportlist_page.setVisibility(View.VISIBLE);
                url = "http://stage.itraveller.com/backend/api/v1/destination?regionId=" + region_id + "&port=1";
                airportJSON(url, false);
                check_bit = 2;
            }
        });
        to_port_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destination_page.setVisibility(View.GONE);
                airportlist_page.setVisibility(View.VISIBLE);
                url = "http://stage.itraveller.com/backend/api/v1/destination?regionId=" + region_id + "&port=1";
                airportJSON(url, false);
                check_bit = 3;
            }
        });
        to_airport_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destination_page.setVisibility(View.GONE);
                airportlist_page.setVisibility(View.VISIBLE);
                url = "http://stage.itraveller.com/backend/api/v1/airport";
                airportJSON(url,true);
                check_bit = 4;
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        // PLACE FIND ACTIVITY//////////////////////////////////////////////////////////////////////////////
        listview = (ListView) findViewById(R.id.list);
        search_airport = (SearchView) findViewById(R.id.searchView);
        adapter_airport = new AirportAdapter(this,airportList);
        adapter_portandLoc = new PortAndLocAdapter(this, portandLocList);

        search_airport.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if ((check_bit == 1) || (check_bit == 4))
                    adapter_airport.getFilter().filter(s);
                else
                    adapter_portandLoc.getFilter().filter(s);

                return false;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //view.getId();
                Log.i("Hellon","Test "+check_bit);
             if(check_bit == 1) {
                 from_home.setText(AirportAdapter.AirportItems.get(position).getValue());
                 to_home.setText(AirportAdapter.AirportItems.get(position).getValue());
             }
                else if(check_bit == 2)
             {
                 from_travel.setText(PortAndLocAdapter.PortandLocItems.get(position).getValue());
                 to_travel.setText(PortAndLocAdapter.PortandLocItems.get(position).getValue());
             }
                else if(check_bit == 3)
             {
                 to_travel.setText(PortAndLocAdapter.PortandLocItems.get(position).getValue());
             }
                else if(check_bit == 4)
             {
                 to_home.setText(AirportAdapter.AirportItems.get(position).getValue());
             }
                else if(check_bit == 5){

                 RearrangePlaceModel m = new RearrangePlaceModel();
                 m.setPlace(portandLocList.get(position).getValue());
                 m.setNights("0");
                 rearrangeList.add(m);
                 adapter_rearrange.notifyDataSetChanged();
                // LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, rearrangeList.size()*70 );
                // listView.setLayoutParams(lp);
                 Utility.setListViewHeightBasedOnChildren(listView);
             }
                check_bit=0;
                destination_page.setVisibility(View.VISIBLE);
                airportlist_page.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void airportJSON (String url ,final Boolean airport)
    {

        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("Test", "Testing" + response);
                    Log.d("Boolean", "" + response.getBoolean("success"));
                    Log.d("Error", "" + response.getJSONObject("error"));
                    Log.d("PayloadValue", ""+ airport);
                    airportList.clear();
                    portandLocList.clear();
                    // JSONObject jsonobj = response.getJSONObject("payload").get;
                    // Parsing json
                    for (int i = 0; i < response.getJSONArray("payload").length(); i++) {
                        JSONObject jsonarr = response.getJSONArray("payload").getJSONObject(i);
                        if(airport == true) {

                            AirportModel airport_model = new AirportModel();

                            Log.i("AirportValue",""+jsonarr.getString("value"));
                            airport_model.setKey(jsonarr.getString("key"));
                            //airport_model.setCode(jsonarr.getString("Code"));
                            airport_model.setValue(jsonarr.getString("value"));
                            /*airport_model.setLat(jsonarr.getString("Lat"));
                            airport_model.setLong(jsonarr.getString("Long"));
                            airport_model.setTimezone(jsonarr.getString("Timezone"));
                            airport_model.setCity(jsonarr.getString("City"));
                            airport_model.setCountry(jsonarr.getString("Country"));
                            airport_model.setCountry_Code(jsonarr.getString("Country_Code"));
                            airport_model.setStatus(jsonarr.getInt("Status"));*/
                            airportList.add(airport_model);
                            listview.setAdapter(adapter_airport);
                        }
                        else
                        {
                            PortAndLocModel portandloc_model = new PortAndLocModel();

                            portandloc_model.setValue(jsonarr.getString("value"));
                            portandloc_model.setKey(jsonarr.getInt("key"));
                            /*portandloc_model.setDestination_Id(jsonarr.getInt("Destination_Id"));
                            portandloc_model.setDestination_Name(jsonarr.getString("Destination_Name"));
                            portandloc_model.setDistrict(jsonarr.getString("District"));
                            portandloc_model.setState(jsonarr.getString("State"));
                            portandloc_model.setPivot_Latitude(jsonarr.getInt("Pivot_Latitude"));
                            portandloc_model.setPivot_Longitude(jsonarr.getInt("Pivot_Longitude"));
                            portandloc_model.setEnable_Flag(jsonarr.getInt("Enable_Flag"));
                            portandloc_model.setAlias(jsonarr.getString("Alias"));
                            portandloc_model.setStory(jsonarr.getString("Story"));
                            portandloc_model.setPage_Title(jsonarr.getString("Page_Title"));
                            portandloc_model.setPage_Description(jsonarr.getString("Page_Description"));
                            portandloc_model.setPage_Heading(jsonarr.getString("Page_Heading"));
                            portandloc_model.setMin_Stay(jsonarr.getInt("Min_Stay"));
                            portandloc_model.setPort(jsonarr.getInt("Port"));
                            portandloc_model.setCode(jsonarr.getString("Code"));
                            portandloc_model.setSight_Seeing(jsonarr.getInt("Sight_Seeing"));
                            portandloc_model.setSealevel(jsonarr.getString("Sealevel"));
                            portandloc_model.setType(jsonarr.getString("Type"));
                            portandloc_model.setSummer_Temp(jsonarr.getString("Summer_Temp"));
                            portandloc_model.setWinter_Temp(jsonarr.getString("Winter_Temp"));
                            portandloc_model.setRegion(jsonarr.getInt("Region"));
                            portandloc_model.setDate(jsonarr.getString("Date"));
                            portandloc_model.setAdmin_Id(jsonarr.getString("admin_Id"));*/

                            portandLocList.add(portandloc_model);

                            listview.setAdapter(adapter_portandLoc);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    VolleyLog.d("Volley Error", "Error: " + e.getMessage());
                }
                //pDialog.hide();
                //region_adapter.notifyDataSetChanged();
                adapter_airport.notifyDataSetChanged();
                adapter_portandLoc.notifyDataSetChanged();

                //searchText.startAnimation(animFadein);
                //searchText.setFocusableInTouchMode(true);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Error", "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(strReq);
    }

    /*public void onBackPressed() {
        *//*if(filter_btn.getText().toString().equalsIgnoreCase("Apply Filter"))
        {
            filter_details.setVisibility(View.GONE);
            filter_btn.setText("Filter");
        }
        else
        {
            finish();
        }*//*
    }*/



    public void airportJSONForText (String url ,final int arrival, final int departure)
    {

        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("Test", "Testing" + response);
                    Log.d("Boolean", "" + response.getBoolean("success"));
                    Log.d("Error", ""+response.getJSONObject("error"));
                    Log.d("Payload", ""+response.getJSONArray("payload"));
                    Log.d("KeyValueKey", "Arr"+arrival);
                    // JSONObject jsonobj = response.getJSONObject("payload").get;
                    // Parsing json
                    for (int i = 0; i < response.getJSONArray("payload").length(); i++) {
                        JSONObject jsonarr = response.getJSONArray("payload").getJSONObject(i);
                        Log.d("KeyValueKey", ""+jsonarr.getInt("key"));
                         if(jsonarr.getInt("key") == arrival)
                         {
                              from_travel.setText(jsonarr.getString("value"));
                             to_travel.setText(jsonarr.getString("value"));
                         }
                        else if(jsonarr.getInt("key") == departure)
                         {
                                to_travel.setText(jsonarr.getString("value"));
                         }
                     }

                } catch (JSONException e) {
                    e.printStackTrace();
                    VolleyLog.d("Volley Error", "Error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Error", "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(strReq);
    }

    public void onBackPressed() {
        if(check_bit==0)
        {
            finish();
        }
        else
        {
            destination_page.setVisibility(View.GONE);
            airportlist_page.setVisibility(View.VISIBLE);
        }
    }
}

