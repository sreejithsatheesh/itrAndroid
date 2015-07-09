package androidhive.info.materialdesign.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.model.Transportation;
import androidhive.info.materialdesign.volley.AppController;

public class TransportationAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Transportation> TransportationItems;
    public static final String MY_PREFS = "ScreenHeight";
    private  int _screen_height;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public TransportationAdapter(Activity activity, List<Transportation> TransportationItems) {
        this.activity = activity;
        this.TransportationItems = TransportationItems;
    }
 
    @Override
    public int getCount() {
        return TransportationItems.size();
    }
 
    @Override
    public Object getItem(int location) {
        return TransportationItems.get(location);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.transportation_row, null);
 
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();


        SharedPreferences prefs = activity.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
            _screen_height =
                    prefs.getInt("Screen_Height", 0)-
                            (prefs.getInt("Status_Height", 0) + prefs.getInt("ActionBar_Height", 0));
            Log.i("iTraveller", "Screen Height: "+_screen_height);
            int width = prefs.getInt("Screen_Width", 0); //0 is the default value.


        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        FrameLayout frame_lay = (FrameLayout) convertView.findViewById(R.id.imgMain);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView textView_persons = (TextView) convertView.findViewById(R.id.textView_persons);
        TextView textView_Km = (TextView) convertView.findViewById(R.id.textView_Km);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,_screen_height/2);
        frame_lay.setLayoutParams(lp);

        // getting data for the row
        Transportation m = TransportationItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl("http://cdn.wonderfulengineering.com/wp-content/uploads/2014/08/Mercedes-Benz-S-Class2.jpg" , imageLoader);
        //Log.i("ImageURL", "http://stage.itraveller.com/backend/images/destinations/" + m.getRegion_Name() + ".jpg");
        // title
        title.setText(m.getTitle());
        textView_persons.setText("Ideal for upto " + m.getMax_Person() + "persons");
        textView_Km.setText("" + m.getKM_Limit());
 
        return convertView;
    }
 
}