package androidhive.info.itraveller.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import androidhive.info.itraveller.R;
import androidhive.info.itraveller.activity.SummaryActivity;
import androidhive.info.itraveller.model.FlightModel;
import androidhive.info.itraveller.volley.AppController;

public class FlightAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<FlightModel> Flightitems;
    private  int _screen_height;
    int index=0;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public FlightAdapter(Activity activity, List<FlightModel> flightitems) {
        this.activity = activity;
        this.Flightitems = flightitems;
    }

    @Override
    public int getCount() {
        return Flightitems.size();
    }
 
    @Override
    public Object getItem(int location) {
        return Flightitems.get(location);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<FlightModel> getList(){
        return Flightitems;
    }
 
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            index=0;
            convertView = inflater.inflate(R.layout.flights_row, null);

        }

 
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TableLayout tl = (TableLayout) convertView.findViewById(R.id.onward_table);
        tl.removeAllViewsInLayout();
        TableLayout tl_return = (TableLayout) convertView.findViewById(R.id.return_table);
        tl_return.removeAllViewsInLayout();

        for (int i = 0; i <Flightitems.get(position).getOnward_model().size(); i++) {

            TableRow row= new TableRow(activity);
            row= new TableRow(activity);
            row.setId(index);
            index++;
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView flight = new TextView(convertView.getContext());
            TextView arrival = new TextView(convertView.getContext());
            TextView departure = new TextView(convertView.getContext());
            TextView price = new TextView(convertView.getContext());
            flight.setText(""+Flightitems.get(position).getOnward_model().get(i).getFlightNumber());
            arrival.setText(""+Flightitems.get(position).getOnward_model().get(i).getDepartureAirportName());
            departure.setText(""+Flightitems.get(position).getOnward_model().get(i).getArrivalAirportName());
            price.setText(""+Flightitems.get(position).getActualBaseFare());

            row.addView(flight);
            row.addView(arrival);
            row.addView(departure);
            row.addView(price);
            tl.addView(row);
        }

        for (int i = 0; i <Flightitems.get(position).getReturn_model().size(); i++) {

            TableRow r_row= new TableRow(activity);
            r_row= new TableRow(activity);
            r_row.setId(index);
            index++;
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            r_row.setLayoutParams(lp);
            TextView r_flight = new TextView(convertView.getContext());
            TextView r_arrival = new TextView(convertView.getContext());
            TextView r_departure = new TextView(convertView.getContext());
            TextView r_price = new TextView(convertView.getContext());
            r_flight.setText(""+Flightitems.get(position).getReturn_model().get(i).getFlightNumber());
            r_arrival.setText(""+Flightitems.get(position).getReturn_model().get(i).getDepartureAirportName());
            r_departure.setText(""+Flightitems.get(position).getReturn_model().get(i).getArrivalAirportName());
            r_price.setText(""+Flightitems.get(position).getActualBaseFare());

            r_row.addView(r_flight);
            r_row.addView(r_arrival);
            r_row.addView(r_departure);
            r_row.addView(r_price);
            tl_return.addView(r_row);
        }

        Button select_btn = (Button) convertView.findViewById(R.id.btn_select);
        select_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ButtonClicked", ""+position);
                SharedPreferences sharedpreferences = activity.getSharedPreferences("Itinerary", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedpreferences.edit();
                int ActualPrice = Integer.parseInt(Flightitems.get(position).getActualBaseFare()) * 2;
                editor.putString("FlightPrice", ""+ ActualPrice);
                editor.commit();
                Intent intent = new Intent(activity, SummaryActivity.class);
                activity.startActivity(intent);


            }
        });

        // getting data for the row
        final FlightModel m = Flightitems.get(position);
        //setListViewHeightBasedOnChildren(DragAndSort.listview);
        // title
      //  title.setText(m.getPlace());
      //  days.setText(m.getNights());

        return convertView;
    }

}