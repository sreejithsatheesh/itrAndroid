package androidhive.info.itraveller.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidhive.info.itraveller.R;
import androidhive.info.itraveller.model.FlightModel;
import androidhive.info.itraveller.model.OnwardDomesticFlightModel;
import androidhive.info.itraveller.model.TransportationModel;
import androidhive.info.itraveller.volley.AppController;

public class FlightDomesticOnwardAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<OnwardDomesticFlightModel> Flightitems;
    private  int _screen_height;
    int index=0;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private RadioButton mSelectedRB;
    private int mSelectedPosition = -1;
    final SharedPreferences.Editor editor;


    public FlightDomesticOnwardAdapter(Activity activity, List<OnwardDomesticFlightModel> flightitems) {
        this.activity = activity;
        this.Flightitems = flightitems;
        SharedPreferences sharedpreferences = activity.getSharedPreferences("Itinerary", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
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

    public List<OnwardDomesticFlightModel> getList(){
        return Flightitems;
    }
 
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            holder = new ViewHolder();
            index=0;
            convertView = inflater.inflate(R.layout.flight_domestic_row, null);
            holder.radioButton = (RadioButton) convertView.findViewById(R.id.radiobtn);
            holder.fl_name = (TextView) convertView.findViewById(R.id.name);
            holder.fl_dep = (TextView) convertView.findViewById(R.id.dep);
            holder.fl_arr = (TextView) convertView.findViewById(R.id.arr);
            holder.fl_price = (TextView) convertView.findViewById(R.id.price);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }

 
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        final OnwardDomesticFlightModel m = Flightitems.get(position);

            holder.fl_name.setText(m.getFlightNumber());
            holder.fl_arr.setText(m.getDepartureAirportCode());
            holder.fl_dep.setText(m.getArrivalAirportCode());
            holder.fl_price.setText(m.getActualBaseFare());
        Log.v("DepatureTime", ""+m.getDepartureDateTime());
        String originalString = "2010-07-14 09:00:02";
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(originalString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String newString = new SimpleDateFormat("H:mm").format(date); // 9:00
        Log.v("DepatureTime", ""+newString);


        holder.radioButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (position != mSelectedPosition && mSelectedRB != null) {
                    mSelectedRB.setChecked(false);
                }

                int flightcharge = Integer.parseInt(m.getActualBaseFare()) + Integer.parseInt(m.getTax()) + Integer.parseInt(m.getSTax()) +
                        Integer.parseInt(m.getTCharge()) + Integer.parseInt(m.getSCharge()) + Integer.parseInt(m.getTDiscount()) +
                        Integer.parseInt(m.getTMarkup()) + Integer.parseInt(m.getTPartnerCommission()) + Integer.parseInt(m.getTSdiscount());


                editor.putString("OnwardFlightPrice",""+ flightcharge);
                editor.commit();

                mSelectedPosition = position;
                mSelectedRB = (RadioButton) v;
            }
        });

        /*if(mSelectedPosition != position){
            holder.radioButton.setChecked(false);
        }else{
            holder.radioButton.setChecked(true);
            if(mSelectedRB != null && holder.radioButton != mSelectedRB){
                mSelectedRB = holder.radioButton;
            }
        }*/
        if(mSelectedPosition != position){
            holder.radioButton.setChecked(false);
        }else{
            holder.radioButton.setChecked(true);
            if(mSelectedRB != null && holder.radioButton != mSelectedRB){
                mSelectedRB = holder.radioButton;
            }

        }
        return convertView;
    }

    private class ViewHolder {
        RadioButton radioButton;
        TextView fl_name;
        TextView fl_dep;
        TextView fl_arr;
        TextView fl_price;
    }

}