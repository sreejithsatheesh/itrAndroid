package androidhive.info.materialdesign.activity;

/**
 * Created by VNK on 6/11/2015.
 */

    import android.app.Activity;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.DragEvent;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.MotionEvent;
    import android.view.View;
    import android.widget.LinearLayout;
    import android.widget.Toast;
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
    import com.android.volley.VolleyLog;
    import com.android.volley.toolbox.JsonObjectRequest;
    import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.Iterator;

    import androidhive.info.materialdesign.R;
    import androidhive.info.materialdesign.volley.AppController;


public class TestActionBar extends Activity {
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
                        Log.d("Boolean", "" + response.getBoolean("success"));
                        Log.d("Error", ""+response.getJSONObject("error"));
                        Log.d("Payload", ""+response.getJSONObject("payload"));


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

