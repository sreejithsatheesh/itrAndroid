package androidhive.info.materialdesign.activity;

/**
 * Created by VNK on 6/25/2015.
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.ListViewPagerActivitiesAdapter;
import androidhive.info.materialdesign.model.ActivitiesModel;
import androidhive.info.materialdesign.model.HotelModel;


public class ActivitiesActivity extends Activity {

    // Declare Variable
    ListViewPagerActivitiesAdapter listViewPagerAdapter;
    int ListItemPostion;
    int heightactivitiesList=4;
    private ArrayList<String> activitiesList ;
    int[] from_destination = new int[10];
    int[] to_destination = new int[10];
    int[] region_Id = new int[10];
    int[] day = new int[10];
    int[] hotel_Id = new int [10];
    ListView lv1;
    String[] destination_id, deatination_day_count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from viewpager_main.xml
        setContentView(R.layout.view_pager_list_view);

        SharedPreferences prefs = getSharedPreferences("Itinerary", MODE_PRIVATE);
        String Region_id = prefs.getString("RegionID", null);
        //Log.i("Hoteldataaaaaa","RID"+ Region_id);

        String Destinations = prefs.getString("DestinationID", null);
        destination_id = Destinations.trim().split(",");
        //Log.i("Hoteldataaaaaa","DID"+ Destinations);

        String DayCount = prefs.getString("DestinationCount", null);
        deatination_day_count = Destinations.trim().split(",");
        //Log.i("Hoteldataaaaaa","DDC"+ DayCount);

        String Arrival_port = prefs.getString("ArrivalPort", null);
        //Log.i("Hoteldataaaaaa","AP"+ Arrival_port);
        String Departure_port = prefs.getString("DeparturePort", null);
        //Log.i("Hoteldataaaaaa","DP"+ Departure_port);

        Set<String> HotelData = prefs.getStringSet("HotelRooms", null);
        String[] HotelDataArray = HotelData.toArray(new String[HotelData.size()]);
        for(int index =0 ; index< HotelDataArray.length ; index ++)
        {
            //Log.i("Hoteldataaaaaa",""+ HotelDataArray[index]);
            String[] hotel_room_Data = HotelDataArray[index].trim().split(",");
        }

       /* from_destination [0] = 86;
        from_destination [1] = 99;
        from_destination [2] = 65;
        from_destination [3] = 86;
        from_destination [4] = 99;

        to_destination [0] = 86;
        to_destination [1] = 99;
        to_destination [2] = 86;
        to_destination [3] = 86;
        to_destination [4] = 86;

        region_Id [0] = 86;
        region_Id [1] = 99;
        region_Id [2] = 86;
        region_Id [3] = 86;
        region_Id [4] = 86;*/


        lv1 = (ListView) findViewById(
                R.id.campaignListView);
        // lv1.setAdapter(new ArrayAdapter<String>(getActivity(),
        // android.R.layout.simple_list_item_1,aList));
        setData();


    }
private void setData(){
    activitiesList=new ArrayList<>();
    for(int index=0;index<heightactivitiesList;index++){
        /*activitiesList.add("http://stage.itraveller.com/backend/api/v1/activities?fromDestination="
                + from_destination[index] + "&toDestination=" + to_destination[index]
                + "&regionIds=" + region_Id[index]);*/
        activitiesList.add("http://stage.itraveller.com/backend/api/v1/activities?fromDestination=85&toDestination=85&regionIds=7&day=1&hotelId=4370");
    }
    listViewPagerAdapter = new ListViewPagerActivitiesAdapter(ActivitiesActivity.this , activitiesList);

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
           ArrayList<ActivitiesModel> modelRow=ListViewPagerActivitiesAdapter.mActivitiesModel.get(""+groupPosition);
           Log.i("PagerView Clicked",groupPosition+"Clicked"+childpostion+ " Check "+  modelRow.get(childpostion).getHotel_Id());
        }
    }
}
