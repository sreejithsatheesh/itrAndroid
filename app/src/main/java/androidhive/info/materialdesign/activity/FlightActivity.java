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

import java.util.ArrayList;
import java.util.Iterator;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.constant.Constants;
import androidhive.info.materialdesign.constant.CustomLoading;
import androidhive.info.materialdesign.constant.Utility;
import androidhive.info.materialdesign.model.FlightModel;
import androidhive.info.materialdesign.model.OnwardFlightModel;
import androidhive.info.materialdesign.model.ReturnFlightModel;
import androidhive.info.materialdesign.volley.AppController;


public class FlightActivity extends Activity {
/* When using Appcombat support library
   you need to extend Main Activity to ActionBarActivity.*/

    private Toolbar toolbar; // Declaring the Toolbar Object
    private ArrayList<FlightModel> flight_model = new ArrayList<FlightModel>();
    private ArrayList<OnwardFlightModel> onward_model = new ArrayList<OnwardFlightModel>();
    private ArrayList<ReturnFlightModel> return_model = new ArrayList<ReturnFlightModel>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.plan_your_trip_test);


            CustomLoading.LoadingScreen(FlightActivity.this, false);

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
                            FlightModel mflight = new FlightModel();
                            JSONObject flight_fare = jsonarr.getJSONObject(i).getJSONObject("FareDetails");
                            mflight.setActualBaseFare(flight_fare.getString("ActualBaseFare").toString());
                            mflight.setTax(flight_fare.getString("Tax").toString());
                            mflight.setSTax(flight_fare.getString("STax").toString());
                            mflight.setTCharge(flight_fare.getString("TCharge").toString());
                            mflight.setSCharge(flight_fare.getString("SCharge").toString());
                            mflight.setTDiscount(flight_fare.getString("TDiscount").toString());
                            mflight.setTMarkup(flight_fare.getString("TMarkup").toString());
                            mflight.setTPartnerCommission(flight_fare.getString("TPartnerCommission").toString());
                            mflight.setTSdiscount(flight_fare.getString("TSdiscount").toString());
                            mflight.setOcTax(flight_fare.getString("ocTax").toString());

                            JSONObject flight_onward = jsonarr.getJSONObject(i).getJSONObject("onward").getJSONObject("FlightSegments");
                            JSONArray onward_arr = flight_onward.getJSONArray("FlightSegment");
                            for(int j = 0 ;j < onward_arr.length(); j++) {
                                OnwardFlightModel monward = new OnwardFlightModel();
                                monward.setAirEquipType(onward_arr.getJSONObject(j).getString("AirEquipType").toString());
                                monward.setArrivalAirportCode(onward_arr.getJSONObject(j).getString("ArrivalAirportCode").toString());
                                monward.setArrivalAirportName(onward_arr.getJSONObject(j).getString("ArrivalAirportName").toString());
                                monward.setArrivalDateTime(onward_arr.getJSONObject(j).getString("ArrivalDateTime").toString());
                                monward.setDepartureAirportCode(onward_arr.getJSONObject(j).getString("DepartureAirportCode").toString());
                                monward.setDepartureAirportName(onward_arr.getJSONObject(j).getString("DepartureAirportName").toString());
                                monward.setDepartureDateTime(onward_arr.getJSONObject(j).getString("DepartureDateTime").toString());
                                monward.setFlightNumber(onward_arr.getJSONObject(j).getString("FlightNumber").toString());
                                monward.setMarketingAirlineCode(onward_arr.getJSONObject(j).getString("MarketingAirlineCode").toString());
                                monward.setOperatingAirlineCode(onward_arr.getJSONObject(j).getString("OperatingAirlineCode").toString());
                                monward.setOperatingAirlineName(onward_arr.getJSONObject(j).getString("OperatingAirlineName").toString());
                                monward.setOperatingAirlineFlightNumber(onward_arr.getJSONObject(j).getString("OperatingAirlineFlightNumber").toString());
                                monward.setNumStops(onward_arr.getJSONObject(j).getString("NumStops").toString());
                                monward.setLinkSellAgrmnt(onward_arr.getJSONObject(j).getString("LinkSellAgrmnt").toString());
                                monward.setConx(onward_arr.getJSONObject(j).getString("Conx").toString());
                                monward.setAirpChg(onward_arr.getJSONObject(j).getString("AirpChg").toString());
                                monward.setInsideAvailOption(onward_arr.getJSONObject(j).getString("InsideAvailOption").toString());
                                monward.setGenTrafRestriction(onward_arr.getJSONObject(j).getString("GenTrafRestriction").toString());
                                monward.setDaysOperates(onward_arr.getJSONObject(j).getString("DaysOperates").toString());
                                monward.setJrnyTm(onward_arr.getJSONObject(j).getString("JrnyTm").toString());
                                monward.setEndDt(onward_arr.getJSONObject(j).getString("EndDt").toString());
                                monward.setStartTerminal(onward_arr.getJSONObject(j).getString("StartTerminal").toString());
                                monward.setEndTerminal(onward_arr.getJSONObject(j).getString("EndTerminal").toString());
                                onward_model.add(monward);
                            }

                            JSONObject flight_return = jsonarr.getJSONObject(i).getJSONObject("Return").getJSONObject("FlightSegments");
                            JSONArray return_arr = flight_onward.getJSONArray("FlightSegment");
                            for(int j = 0 ;j < onward_arr.length(); j++) {
                                ReturnFlightModel mreturn = new ReturnFlightModel();
                                mreturn.setAirEquipType(onward_arr.getJSONObject(j).getString("AirEquipType").toString());
                                mreturn.setArrivalAirportCode(onward_arr.getJSONObject(j).getString("ArrivalAirportCode").toString());
                                mreturn.setArrivalAirportName(onward_arr.getJSONObject(j).getString("ArrivalAirportName").toString());
                                mreturn.setArrivalDateTime(onward_arr.getJSONObject(j).getString("ArrivalDateTime").toString());
                                mreturn.setDepartureAirportCode(onward_arr.getJSONObject(j).getString("DepartureAirportCode").toString());
                                mreturn.setDepartureAirportName(onward_arr.getJSONObject(j).getString("DepartureAirportName").toString());
                                mreturn.setDepartureDateTime(onward_arr.getJSONObject(j).getString("DepartureDateTime").toString());
                                mreturn.setFlightNumber(onward_arr.getJSONObject(j).getString("FlightNumber").toString());
                                mreturn.setMarketingAirlineCode(onward_arr.getJSONObject(j).getString("MarketingAirlineCode").toString());
                                mreturn.setOperatingAirlineCode(onward_arr.getJSONObject(j).getString("OperatingAirlineCode").toString());
                                mreturn.setOperatingAirlineName(onward_arr.getJSONObject(j).getString("OperatingAirlineName").toString());
                                mreturn.setOperatingAirlineFlightNumber(onward_arr.getJSONObject(j).getString("OperatingAirlineFlightNumber").toString());
                                mreturn.setNumStops(onward_arr.getJSONObject(j).getString("NumStops").toString());
                                mreturn.setLinkSellAgrmnt(onward_arr.getJSONObject(j).getString("LinkSellAgrmnt").toString());
                                mreturn.setConx(onward_arr.getJSONObject(j).getString("Conx").toString());
                                mreturn.setAirpChg(onward_arr.getJSONObject(j).getString("AirpChg").toString());
                                mreturn.setInsideAvailOption(onward_arr.getJSONObject(j).getString("InsideAvailOption").toString());
                                mreturn.setGenTrafRestriction(onward_arr.getJSONObject(j).getString("GenTrafRestriction").toString());
                                mreturn.setDaysOperates(onward_arr.getJSONObject(j).getString("DaysOperates").toString());
                                mreturn.setJrnyTm(onward_arr.getJSONObject(j).getString("JrnyTm").toString());
                                mreturn.setEndDt(onward_arr.getJSONObject(j).getString("EndDt").toString());
                                mreturn.setStartTerminal(onward_arr.getJSONObject(j).getString("StartTerminal").toString());
                                mreturn.setEndTerminal(onward_arr.getJSONObject(j).getString("EndTerminal").toString());
                                return_model.add(mreturn);
                            }

                            mflight.setOnward_model(onward_model);
                            mflight.setReturn_model(return_model);

                            String flight_id = jsonarr.getJSONObject(i).getString("id").toString();
                            String flight_key = jsonarr.getJSONObject(i).getString("key").toString();

                            mflight.setId(flight_id);
                            mflight.setKey(flight_key);
                            flight_model.add(mflight);

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

