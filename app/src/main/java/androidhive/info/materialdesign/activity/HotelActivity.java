package androidhive.info.materialdesign.activity;

/**
 * Created by VNK on 6/25/2015.
 */
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.ListViewPagerAdapter;
import androidhive.info.materialdesign.model.HotelModel;


public class HotelActivity extends Activity {

    // Declare Variable
    ListViewPagerAdapter listViewPagerAdapter;
    int ListItemPostion;
    int heightHotelList=5;
    private ArrayList<String> hotelList ;
    int[] hotel_destination = new int[10];
    ListView lv1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from viewpager_main.xml
        setContentView(R.layout.view_pager_list_view);
        hotel_destination [0] = 86;
        hotel_destination [1] = 99;
        hotel_destination [2] = 65;
        hotel_destination [3] = 86;
        hotel_destination [4] = 99;

        lv1 = (ListView) findViewById(
                R.id.campaignListView);
        // lv1.setAdapter(new ArrayAdapter<String>(getActivity(),
        // android.R.layout.simple_list_item_1,aList));
        setData();


    }
private void setData(){
    hotelList=new ArrayList<>();
    for(int index=0;index<heightHotelList;index++){
        hotelList.add("http://stage.itraveller.com/backend/api/v1/hotel/destintionId/" + hotel_destination[index]);
    }
    listViewPagerAdapter = new ListViewPagerAdapter(HotelActivity.this , hotelList);

    // listViewPagerAdapter.add(null);
    lv1.setAdapter(listViewPagerAdapter);
}

    interface pagerCheckBoxChangedListner{
        public void OnCheckedChangeListenerCustomPager(int childPosition, boolean isChecked);
        public  void OnImageClickListenerCustomPager(int childpostion);
    }


    private class PagerCheckedChangeListnerCustom implements pagerCheckBoxChangedListner {
        int groupPosition;

        public PagerCheckedChangeListnerCustom(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        @Override
        public void OnCheckedChangeListenerCustomPager(int childPosition, boolean isChecked) {

        }

        @Override
        public void OnImageClickListenerCustomPager(int childpostion) {
            ArrayList<HotelModel> modelRow=ListViewPagerAdapter.mHotelModels.get(""+groupPosition);
            Log.i("PagerView Clicked",groupPosition+"Clicked"+childpostion+ " Check "+  modelRow.get(childpostion).getHotel_Name());
        }
    }
}
