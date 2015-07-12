package androidhive.info.materialdesign.activity;

/**
 * Created by VNK on 6/25/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.HotelRoomAdapter;
import androidhive.info.materialdesign.adapter.ListViewPagerAdapter;
import androidhive.info.materialdesign.dragsort.DragAndSort;
import androidhive.info.materialdesign.model.ActivitiesModel;
import androidhive.info.materialdesign.model.HotelModel;
import androidhive.info.materialdesign.model.HotelRoomModel;
import androidhive.info.materialdesign.volley.AppController;


public class HotelActivity extends ActionBarActivity {

    // Declare Variable
    ListViewPagerAdapter listViewPagerAdapter;
    int ListItemPostion;
    int heightHotelList = 5;
    Toolbar mToolbar;
    private ArrayList<String> hotelList;
    String[] hotel_destination;
    ListView lv1;
    LinearLayout second;
    ///HOTEL ROOM ACTIVITY
    int[] value = new int[10];
    private ListView listView;
    private List<HotelRoomModel> roomList ;
    private HotelRoomAdapter adapter;
    int check_bit=0;
    String[] HotelRoomData;
    int cposition, gposition;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from viewpager_main.xml
        setContentView(R.layout.view_pager_list_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Bundle bundle = getIntent().getExtras();
        String IDS_value = bundle.getString("DestinationsIDs");
        hotel_destination = IDS_value.trim().split(",");
        HotelRoomData = new String[hotel_destination.length];
        second = (LinearLayout) findViewById(R.id.room_type_full);
        lv1 = (ListView) findViewById(R.id.campaignListView);
        // lv1.setAdapter(new ArrayAdapter<String>(getActivity(),
        // android.R.layout.simple_list_item_1,aList));
        SharedPreferences sharedpreferences = getSharedPreferences("Itinerary", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        setData();
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.i("TestDATA", "Testing" + position);
            }
        });

        Button activites = (Button) findViewById(R.id.to_activities);
        activites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<String> set = new HashSet<String>();
                for(int i =0; i< HotelRoomData.length;i++)
                {
                    Log.i("Hotel Room "+i,""+ HotelRoomData[i]);
                    set.add("" + HotelRoomData[i]);
                }
                editor.putStringSet("HotelRooms", set);
                editor.commit();

                Intent intent = new Intent(HotelActivity.this, ActivitiesActivity.class);
                startActivity(intent);
            }
        });

        //String url_checkroom = "http://stage.itraveller.com/backend/api/v1/roomtariff?region=7&room=52&checkInDate=2015-07-26";
        //url = "http://stage.itraveller.com/backend/api/v1/internationalflight?travelFrom=BOM&arrivalPort=MRU&departDate=2015-07-26&returnDate=2015-08-01&adults=2&children=0&infants=0&departurePort=MRU&travelTo=BOM";
        //hotelRoomsCheck(url_checkroom);



    }

    public interface RadiobuttonListener {
        public void RadioChangeListenerCustom(String position);
    }

    public interface pagerCheckBoxChangedListner1 {
        public void OnCheckedChangeListenerCustomPager(int childPosition, boolean isChecked);

        public void OnImageClickListenerCustomPager(int childpostion, int grouppostion);
    }

    private void setData() {
        hotelList = new ArrayList<>();
        for (int index = 0; index < hotel_destination.length; index++) {
            hotelList.add("http://stage.itraveller.com/backend/api/v1/hotel/destintionId/" + hotel_destination[index]);
            Log.i("HotelURL", "" + "http://stage.itraveller.com/backend/api/v1/hotel/destintionId/" + hotel_destination[index]);
        }
        listViewPagerAdapter = new ListViewPagerAdapter(HotelActivity.this, hotelList, new pagerCheckBoxChangedListner1() {
            @Override
            public void OnCheckedChangeListenerCustomPager(int childPosition, boolean isChecked) {
            }

            @Override
            public void OnImageClickListenerCustomPager(int childpostion, final int groupPosition) {
                cposition = childpostion;
                gposition = groupPosition;
                second.setVisibility(View.VISIBLE);
                lv1.setVisibility(View.GONE);
                roomList = new ArrayList<HotelRoomModel>();
                listView = (ListView) findViewById(R.id.room_type);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                final ArrayList<HotelModel> modelRow = ListViewPagerAdapter.mHotelModels.get("" + groupPosition);
                adapter = new HotelRoomAdapter(HotelActivity.this, roomList, new RadiobuttonListener() {
                    @Override
                    public void RadioChangeListenerCustom(String Value) {
                        Log.i("TestPostion",""+Value);
                        HotelRoomData[gposition] = Value;
                        final ArrayList<HotelModel> modelRow = ListViewPagerAdapter.mHotelModels.get("" + gposition);
                        for(int index =0 ; index<modelRow.size();index++) {
                            if(index==cposition) {
                                modelRow.get(cposition).setChecked(true);
                            }
                            else {
                                modelRow.get(index).setChecked(false);
                            }
                        }
                        ListViewPagerAdapter.mViewPagerAdapter.notifyDataSetChanged();
                    }

                });
                listView.setAdapter(adapter);
                //ArrayList<HotelModel> modelRow = ListViewPagerAdapter.mHotelModels.get("" + groupPosition);
                //modelRow.get(childpostion).
                Log.i("PagerView Clicked", groupPosition + "Clicked" + childpostion + " Check " + modelRow.get(childpostion).getHotel_Name());
                String url = "http://stage.itraveller.com/backend/api/v1/hotelRoom?regionId=7&hotelIds=["+ modelRow.get(childpostion).getHotel_Id() +"]&checkInDate=2015-07-26";
                hotelRooms(url);
                check_bit=1;
            }
        });


        // listViewPagerAdapter.add(null);
        lv1.setAdapter(listViewPagerAdapter);
    }


    public void hotelRooms(String url) {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Boolean", "" + response.getBoolean("success"));
                    Log.d("Error", "" + response.getJSONObject("error"));
                    Log.d("Payload", "" + response.getJSONObject("payload"));

                    // JSONObject jsonobj = response.getJSONObject("payload").getJSONObject()
                    // Parsing json
                    for (int i = 0; i < response.getJSONObject("payload").length(); i++) {
                        Iterator<?> destinationKeys = response.getJSONObject("payload").keys();

                        while (destinationKeys.hasNext()) {
                            String destinationKey = (String) destinationKeys.next();
                            JSONArray RoomObj = response.getJSONObject("payload").getJSONArray(destinationKey);
                            // destinationValue = destobj.getString("name");
                            Log.d("Room_Type", "" + RoomObj.length());
                            for (int inc = 0; inc < RoomObj.length(); inc++) {
                                Log.d("Room_Type", "Test" + RoomObj.getJSONObject(inc).getString("Hotel_Room_Id"));
                                value[inc] = RoomObj.getJSONObject(inc).getInt("Hotel_Room_Id");

                                String url_checkroom = "http://stage.itraveller.com/backend/api/v1/roomtariff?region=7&room=" + value[inc] + "&checkInDate=2015-07-26";
                                //url = "http://stage.itraveller.com/backend/api/v1/internationalflight?travelFrom=BOM&arrivalPort=MRU&departDate=2015-07-26&returnDate=2015-08-01&adults=2&children=0&infants=0&departurePort=MRU&travelTo=BOM";
                                hotelRoomsCheck(url_checkroom);
                            }
                        }

                        //JSONObject jsonarr1 =
                        //Log.d("Room_Type", "" + jsonarr.);
                    }

                } catch (JSONException e) {
                    Log.d("Error Catched", "" + e.getMessage());
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


    public void hotelRoomsCheck(String url) {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Boolean", "" + response.getBoolean("success"));
                    Log.d("Error", "" + response.getJSONObject("error"));
                    Log.d("Payload_RoomRate", "" + response.getJSONArray("payload"));

                    for (int i = 0; i < response.getJSONArray("payload").length(); i++) {

                        JSONObject jsonarr = response.getJSONArray("payload").getJSONObject(i);
                        HotelRoomModel hrm = new HotelRoomModel();
                        hrm.setHotel_Room_Id(jsonarr.getInt("Hotel_Room_Id"));
                        hrm.setHotel_Id(jsonarr.getInt("Hotel_Id"));
                        hrm.setRoom_Status(jsonarr.getInt("Room_Status"));
                        hrm.setRack_Rate(jsonarr.getInt("Rack_Rate"));
                        hrm.setDefault_Number(jsonarr.getInt("Default_Number"));
                        hrm.setMaximum_Number(jsonarr.getInt("Maximum_Number"));
                        hrm.setHotel_Room_Tariff_Id(jsonarr.getInt("Hotel_Room_Tariff_Id"));
                        hrm.setTAC(jsonarr.getInt("TAC"));
                        hrm.setCost(jsonarr.getInt("Cost"));
                        hrm.setMark_Up(jsonarr.getInt("Mark_Up"));
                        hrm.setDisplay_Tariff(jsonarr.getInt("Display_Tariff"));
                        hrm.setCompany_Id(jsonarr.getInt("Company_Id"));
                        hrm.setRoom_Type(jsonarr.getString("Room_Type"));
                        hrm.setRoom_Description(jsonarr.getString("Room_Description"));
                        hrm.setFrom(jsonarr.getString("From"));
                        hrm.setTo(jsonarr.getString("To"));
                        roomList.add(hrm);
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.d("Error Catched", "" + e.getMessage());
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

    public void onBackPressed() {

        if(check_bit==0)
        {
            finish();
        }
        else
        {
           lv1.setVisibility(View.VISIBLE);
            second.setVisibility(View.GONE);
            check_bit=0;
        }
    }

}
