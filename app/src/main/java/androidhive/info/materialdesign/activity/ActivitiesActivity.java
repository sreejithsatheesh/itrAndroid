package androidhive.info.materialdesign.activity;

/**
 * Created by VNK on 6/25/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.adapter.ListViewPagerActivitiesAdapter;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from viewpager_main.xml
        setContentView(R.layout.view_pager_list_view);
        from_destination [0] = 86;
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
        region_Id [4] = 86;


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
           // ArrayList<HotelModel> modelRow=ListViewPagerActivitiesAdapter.mHotelModels.get(""+groupPosition);
           // Log.i("PagerView Clicked",groupPosition+"Clicked"+childpostion+ " Check "+  modelRow.get(childpostion).getHotel_Name());
        }
    }
}
