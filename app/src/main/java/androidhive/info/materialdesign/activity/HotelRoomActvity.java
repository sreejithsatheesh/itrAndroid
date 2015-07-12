package androidhive.info.materialdesign.activity;

/**
 * Created by VNK on 6/11/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.HotelRoomAdapter;
import androidhive.info.materialdesign.model.HotelRoomModel;
import androidhive.info.materialdesign.volley.AppController;


public class HotelRoomActvity extends Activity {
/* When using Appcombat support library
   you need to extend Main Activity to ActionBarActivity.*/

    private Toolbar toolbar; // Declaring the Toolbar Object
    int[] value = new int[10];
    private ListView listView;
    private List<HotelRoomModel> roomList = new ArrayList<HotelRoomModel>();
    private HotelRoomAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotelrooms_listview);
        String url = "http://stage.itraveller.com/backend/api/v1/hotelRoom/hotelId/[19]";
        //String url_checkroom = "http://stage.itraveller.com/backend/api/v1/roomtariff?region=7&room=52&checkInDate=2015-07-26";
        //url = "http://stage.itraveller.com/backend/api/v1/internationalflight?travelFrom=BOM&arrivalPort=MRU&departDate=2015-07-26&returnDate=2015-08-01&adults=2&children=0&infants=0&departurePort=MRU&travelTo=BOM";
        hotelRooms(url);
        //hotelRoomsCheck(url_checkroom);
        listView = (ListView) findViewById(R.id.room_type);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter = new HotelRoomAdapter(this, roomList, new HotelActivity.RadiobuttonListener() {
            @Override
            public void RadioChangeListenerCustom(String position) {

            }
        });
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            }
        });

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
                    Log.d("Payload", ""+response.getJSONObject("payload"));

                    // JSONObject jsonobj = response.getJSONObject("payload").getJSONObject()
                    // Parsing json
                    for (int i = 0; i < response.getJSONObject("payload").length(); i++) {
                        Iterator<?> destinationKeys = response.getJSONObject("payload").keys();

                        while(destinationKeys.hasNext())
                        {
                            String destinationKey = (String) destinationKeys.next();
                            JSONArray RoomObj = response.getJSONObject("payload").getJSONArray(destinationKey);
                           // destinationValue = destobj.getString("name");
                            Log.d("Room_Type",""+RoomObj.length());
                            for(int inc = 0; inc < RoomObj.length();inc++) {
                                Log.d("Room_Type", "Test" + RoomObj.getJSONObject(inc).getString("Hotel_Room_Id"));
                                value[inc] = RoomObj.getJSONObject(inc).getInt("Hotel_Room_Id");

                                String url_checkroom = "http://stage.itraveller.com/backend/api/v1/roomtariff?region=7&room="+ value[inc] +"&checkInDate=2015-07-26";
                                //url = "http://stage.itraveller.com/backend/api/v1/internationalflight?travelFrom=BOM&arrivalPort=MRU&departDate=2015-07-26&returnDate=2015-08-01&adults=2&children=0&infants=0&departurePort=MRU&travelTo=BOM";
                                hotelRoomsCheck(url_checkroom);
                            }
                        }

                        //JSONObject jsonarr1 =
                        //Log.d("Room_Type", "" + jsonarr.);
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


    public void hotelRoomsCheck(String url)
    {
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Boolean", "" + response.getBoolean("success"));
                    Log.d("Error", ""+response.getJSONObject("error"));
                    Log.d("Payload_RoomRate", ""+response.getJSONArray("payload"));

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

