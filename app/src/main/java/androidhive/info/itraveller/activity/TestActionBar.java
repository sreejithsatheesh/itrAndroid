package androidhive.info.itraveller.activity;

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

    import org.json.JSONException;
    import org.json.JSONObject;
    import org.w3c.dom.CharacterData;
    import org.w3c.dom.Document;
    import org.w3c.dom.Element;
    import org.w3c.dom.Node;
    import org.w3c.dom.NodeList;
    import org.xml.sax.InputSource;

    import java.io.StringReader;

    import javax.xml.parsers.DocumentBuilder;
    import javax.xml.parsers.DocumentBuilderFactory;

    import androidhive.info.itraveller.R;
    import androidhive.info.itraveller.volley.AppController;


public class TestActionBar extends Activity {
/* When using Appcombat support library
   you need to extend Main Activity to ActionBarActivity.*/

        private Toolbar toolbar; // Declaring the Toolbar Object

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.plan_your_trip_test);

            String url ="http://stage.itraveller.com/backend/api/v1/domesticflight?travelFrom=BOM&arrivalPort=BLR&departDate=2015-07-29&returnDate=2015-08-01&adults=2&children=0&infants=0&departurePort=BLR&travelTo=BOM";

            JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,
                    url, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.d("Boolean", "" + response.getBoolean("success"));
                        Log.d("Error", ""+response.getJSONObject("error"));
                        Log.d("Payload", ""+response.getJSONObject("payload"));
                        Log.d("Payload", ""+response.getJSONObject("payload").getString("onward"));

                        try {
                            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                            InputSource is = new InputSource();
                            is.setCharacterStream(new StringReader(""+response.getJSONObject("payload").getString("onward")));

                            Document doc = db.parse(is);
                            NodeList nodes = doc.getElementsByTagName("OriginDestinationOption");

                            for (int i = 0; i < nodes.getLength(); i++) {
                                Element element = (Element) nodes.item(i);

                                NodeList name = element.getElementsByTagName("ActualBaseFare");
                                Element line = (Element) name.item(0);
                                System.out.println("Request: " + getCharacterDataFromElement(line));
                                NodeList name1 = element.getElementsByTagName("AirEquipType");
                                Element line1 = (Element) name1.item(0);
                                System.out.println("Request: " + getCharacterDataFromElement(line1));

                                /*NodeList title = element.getElementsByTagName("Destination");
                                line = (Element) title.item(0);
                                System.out.println("Response__Depart: " + getCharacterDataFromElement(line));*/
                            }
                        }
                        catch (Exception e){

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

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
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

