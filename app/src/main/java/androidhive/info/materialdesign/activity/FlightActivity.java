package androidhive.info.materialdesign.activity;

/**
 * Created by VNK on 6/11/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.volley.AppController;


public class FlightActivity extends Activity {
/* When using Appcombat support library
   you need to extend Main Activity to ActionBarActivity.*/

        private Toolbar toolbar; // Declaring the Toolbar Object

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.plan_your_trip_test);

            String url ="http://stage.itraveller.com/backend/api/v1/internationalflight?travelFrom=BOM&arrivalPort=MRU&departDate=2015-07-26&returnDate=2015-08-01&adults=2&children=0&infants=0&departurePort=MRU&travelTo=BOM";

            JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,
                    url, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //Log.d("Boolean", "" + response.getBoolean("success"));
                        //Log.d("Error", ""+response.getJSONObject("error"));
                        //Log.d("Payload", ""+response.getJSONObject("payload"));
                        JSONObject jsonobj = response.getJSONObject("payload").getJSONObject("AvailResponse").getJSONObject("OriginDestinationOptions");
                        JSONArray jsonarr = jsonobj.getJSONArray("OriginDestinationOption");
                        for (int i = 0; i < jsonarr.length(); i++) {
                            JSONObject flight_fare = jsonarr.getJSONObject(i).getJSONObject("FareDetails");
                            flight_fare.getString("ActualBaseFare");
                            flight_fare.getString("Tax");
                            flight_fare.getString("STax");
                            flight_fare.getString("TCharge");
                            flight_fare.getString("SCharge");
                            flight_fare.getString("TDiscount");
                            flight_fare.getString("TMarkup");
                            flight_fare.getString("TPartnerCommission");
                            flight_fare.getString("TSdiscount");
                            flight_fare.getString("ocTax");

                            JSONObject flight_onward = jsonarr.getJSONObject(i).getJSONObject("onward").getJSONObject("FlightSegments");
                            JSONArray onward_arr = flight_onward.getJSONArray("FlightSegment");
                            for(int j = 0 ;j < onward_arr.length(); j++) {
                                onward_arr.getJSONObject(j).getString("AirEquipType");
                                onward_arr.getJSONObject(j).getString("ArrivalAirportCode");
                                onward_arr.getJSONObject(j).getString("ArrivalAirportName");
                                onward_arr.getJSONObject(j).getString("ArrivalDateTime");
                                onward_arr.getJSONObject(j).getString("DepartureAirportCode");
                                onward_arr.getJSONObject(j).getString("DepartureAirportName");
                                onward_arr.getJSONObject(j).getString("DepartureDateTime");
                                onward_arr.getJSONObject(j).getString("FlightNumber");
                                onward_arr.getJSONObject(j).getString("MarketingAirlineCode");
                                onward_arr.getJSONObject(j).getString("OperatingAirlineCode");
                                onward_arr.getJSONObject(j).getString("OperatingAirlineName");
                                onward_arr.getJSONObject(j).getString("OperatingAirlineFlightNumber");
                                onward_arr.getJSONObject(j).getString("NumStops");
                                onward_arr.getJSONObject(j).getString("LinkSellAgrmnt");
                                onward_arr.getJSONObject(j).getString("Conx");
                                onward_arr.getJSONObject(j).getString("AirpChg");
                                onward_arr.getJSONObject(j).getString("InsideAvailOption");
                                onward_arr.getJSONObject(j).getString("GenTrafRestriction");
                                onward_arr.getJSONObject(j).getString("DaysOperates");
                                onward_arr.getJSONObject(j).getString("JrnyTm");
                                onward_arr.getJSONObject(j).getString("EndDt");
                                onward_arr.getJSONObject(j).getString("StartTerminal");
                                onward_arr.getJSONObject(j).getString("EndTerminal");
                            }

                            JSONObject flight_return = jsonarr.getJSONObject(i).getJSONObject("Return");
                            String flight_id = jsonarr.getJSONObject(i).getString("id");
                            String flight_key = jsonarr.getJSONObject(i).getString("key");

                        }
                    } catch (JSONException e) {
                        Log.d("Error Catched","" +e.getMessage());
                    }


                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    System.err.println(error);
                    // TODO Auto-generated method stub
                    // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
                    // For AuthFailure, you can re login with user credentials.
                    // For ClientError, 400 & 401, Errors happening on client side when sending api request.
                    // In this case you can check how client is forming the api and debug accordingly.
                    // For ServerError 5xx, you can do retry or handle accordingly.
                    if( error instanceof NetworkError) {
                    } else if( error instanceof ServerError) {
                    } else if( error instanceof AuthFailureError) {
                    } else if( error instanceof ParseError) {
                    } else if( error instanceof NoConnectionError) {
                    } else if( error instanceof TimeoutError) {
                    }
                }
            }) {
            };
            strReq.setRetryPolicy(new DefaultRetryPolicy(10000,
                    5,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq);


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

