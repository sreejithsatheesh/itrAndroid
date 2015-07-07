package androidhive.info.materialdesign.adapter;

/**
 * Created by VNK on 6/25/2015.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidhive.info.materialdesign.R;
import androidhive.info.materialdesign.model.ActivitiesModel;
import androidhive.info.materialdesign.volley.AppController;


public class ListViewPagerActivitiesAdapter extends ArrayAdapter<String> {
    ViewPager[] vp;
  // ViewPagerAdapter[] mViewPagerAdapter;

    private Context context;
    private ArrayList<String> navigationItems;
    public static HashMap<String,ArrayList<ActivitiesModel>> mActivitiesModel;
  //  private int selectedIndex;
    private Map<Integer, Integer> mPagerPositions ;

    public ListViewPagerActivitiesAdapter(Context context, ArrayList<String> navigationItems) {
        super(context, R.layout.view_pager_list_view, navigationItems);
        this.context = context;
        this.navigationItems = navigationItems;
        mActivitiesModel=new HashMap<>();
        for(int index=0;index<navigationItems.size();index++){
            mActivitiesModel.put(""+index,new ArrayList<ActivitiesModel>());
        }
        mPagerPositions= new HashMap<Integer, Integer>();
     //mViewPagerAdapter=new ViewPagerAdapter[navigationItems.size()];
       vp=new ViewPager[navigationItems.size()];
    }

    @Override
    public int getCount() {
        return navigationItems.size();
    }

//    public void setSelectedIndex(int position) {
//        selectedIndex = position;
//       // notifyDataSetChanged();
//    }

    @Override
    public String getItem(int position) {

        return navigationItems.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView( final int position, View convertView, ViewGroup parent) {
       // ViewPager vp;
       ViewPagerActivitiesAdapter mViewPagerAdapter;

        if (convertView == null) {
          // setSelectedIndex(position);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.hotel_pageviewer, null);

           // mPagerPositions.put(position,0);

        }else{

        }
        vp[position] = (ViewPager) convertView.findViewById(R.id.list_pager);
        mViewPagerAdapter = new ViewPagerActivitiesAdapter(mActivitiesModel.get(""+position),new PagerCheckedChangeListnerCustom(position));
        vp[position].setAdapter(mViewPagerAdapter);
        //vp[position].setTag(position);
        vp[position].setOnClickListener(new ViewPagerClickListner(position));
        vp[position].setOnPageChangeListener(new ViewPageChangeListner(position));
        if(mPagerPositions.get(position)!=null){
          //  Log.e("Pager position ", "parent " + position + "child position " + mPagerPositions.get(position));
             vp[position].setCurrentItem(mPagerPositions.get(position));
        }
     /*   vp[position].setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int childposition) {
                      Log.d("PAGER ", "PAGER SCROLL PARENT POSITION " + childposition + "parent position " + position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

     if(mActivitiesModel.get(""+position).size()<1){
         airportJSONForText(navigationItems.get(position), position);





//         vp[position].setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//             @Override
//             public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//             }
//
//             @Override
//             public void onPageSelected(int positionChild) {
//                // mPagerPositions.put(position, positionChild);
//             }
//
//             @Override
//             public void onPageScrollStateChanged(int state) {
//
//             }
//         });

     }else{
        // mViewPagerAdapter[position] = new ViewPagerAdapter(mActivitiesModel.get(""+position));
        // vp[position].setAdapter(mViewPagerAdapter[position]);
        // for(int index=0;index<navigationItems.size();index++) {
          //   if(mViewPagerAdapter[index]!=null) {
                //mViewPagerAdapter[position].notifyDataSetChanged();
                // vp[position].setAdapter(mViewPagerAdapter[position]);
            // }
//             if (mPagerPositions.get(position) != null)
//                 vp[position].setCurrentItem(mPagerPositions.get(position));
         //}
     }

        //Log.i("PageSelection" , "PageSelection" + position);

       // vp[position].setTag(position);


       // if(mViewPagerAdapter[position]==null)

//        Integer pagerPosition = mPagerPositions.get(position);
//        if (pagerPosition != null) {
//            vp.setCurrentItem(pagerPosition);
//        }
//        Log.i("PagerPosition",""+pagerPosition);
        /*//Integer pagerPosition = selectedIndex;

        if (pagerPosition != null) {
            vp.setCurrentItem(pagerPosition);
        }*/
        //convertView.setTag(position);
        return convertView;
    }

//    private class MyPageChangeListener extends
//            ViewPager.SimpleOnPageChangeListener {
//
//        private int currentPage;
//
//        @Override
//        public void onPageSelected(int position) {
//            //currentPage = position;
//            if (vp.isShown()) {
//               // Log.i("PageSelection", "CurrentPage" + position + "Index" + selectedIndex);
//                mPagerPositions.put(selectedIndex, position);
//            }
//        }

//        public final int getCurrentPage() {
//            return currentPage;
//        }
//    }

    public void airportJSONForText (String url, final int position )
    {

        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.GET,
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("Test", "Testing" + response);
                    Log.d("Boolean", "" + response.getBoolean("success"));
                    Log.d("Error", ""+response.getJSONObject("error"));
                    Log.d("Payload", ""+response.getJSONArray("payload"));

                    // JSONObject jsonobj = response.getJSONObject("payload").get;
                    // Parsing json
                    ArrayList activitiesList=new ArrayList();
                    for (int i = 0; i < response.getJSONArray("payload").length(); i++) {
                        JSONObject jsonarr = response.getJSONArray("payload").getJSONObject(i);

                        ActivitiesModel activities_model = new ActivitiesModel();

                        activities_model.setId(jsonarr.getInt("Id"));
                        activities_model.setTitle(jsonarr.getString("Title"));
                        activities_model.setCost(jsonarr.getInt("Cost"));
                        activities_model.setHotel_Id(jsonarr.getString("Hotel_Id"));
                        activities_model.setMarkup(jsonarr.getInt("Markup"));
                        activities_model.setDisplay(jsonarr.getInt("Display"));
                        activities_model.setStatus(jsonarr.getInt("Status"));
                        activities_model.setRegion_Id(jsonarr.getString("Region_Id"));
                        activities_model.setDestination_Id(jsonarr.getInt("Destination_Id"));
                        activities_model.setCompany_Id(jsonarr.getString("Company_Id"));
                        activities_model.setDay(jsonarr.getString("Day"));
                        activities_model.setDuration(jsonarr.getString("Duration"));
                        activities_model.setImage(jsonarr.getString("Image"));
                        activities_model.setFlag(jsonarr.getInt("Flag"));
                        activities_model.setDescription(jsonarr.getString("Description"));
                        activities_model.setNot_Available_Month(jsonarr.getString("Not_Available_Month"));
                        activities_model.setNot_Available_Days(jsonarr.getString("Not_Available_Days"));
                        activities_model.setDestination_Id_From(jsonarr.getString("Destination_Id_From"));
                        activities_model.setBookable(jsonarr.getString("Bookable"));

                        activitiesList.add(activities_model);

                    }
                   mActivitiesModel.put(position+"",activitiesList);

                } catch (JSONException e) {
                    e.printStackTrace();
                    VolleyLog.d("Volley Error", "Error: " + e.getMessage());
                }

                 notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Volley Error", "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private class ViewPagerClickListner implements ViewPager.OnClickListener{

        int postionClicked;
        public ViewPagerClickListner(int postionClick)
        {
            this.postionClicked = postionClick;
        }

        @Override
        public void onClick(View view) {

          Log.e("Pager position ", "parent " + postionClicked + "child position " + mPagerPositions.get(postionClicked) + "Viewpager.Currentpostion" +vp[postionClicked].getCurrentItem());
            vp[postionClicked].getCurrentItem();

        }
    }
    private class ViewPageChangeListner implements ViewPager.OnPageChangeListener{
        private int selectPosition;
        public ViewPageChangeListner(int selectPosition){
            this.selectPosition=selectPosition;

        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.d("PAGER ", "PAGER SCROLL child POSITION " + position + "parent position " + selectPosition);
            mPagerPositions.put(selectPosition,position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

     interface pagerCheckBoxChangedListner{
        public void OnCheckedChangeListenerCustomPager(int childPosition, boolean isChecked);
         public  void OnImageClickListenerCustomPager(int childpostion);
    }


    private class PagerCheckedChangeListnerCustom implements pagerCheckBoxChangedListner{
        int groupPosition;

        public PagerCheckedChangeListnerCustom(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        @Override
        public void OnCheckedChangeListenerCustomPager(int childPosition,boolean isChecked) {
            ArrayList<ActivitiesModel> modelRow=mActivitiesModel.get(""+groupPosition);
            for(int index =0 ; index<modelRow.size();index++) {
                if(childPosition==index) {
                    modelRow.get(index).setChecked(isChecked);
                    mActivitiesModel.put("" + groupPosition, modelRow);
                }
                else
                    modelRow.get(index).setChecked(false);

            }

        }

        @Override
        public void OnImageClickListenerCustomPager(int childpostion) {
            ArrayList<ActivitiesModel> modelRow=mActivitiesModel.get(""+groupPosition);

            //Log.i("PagerView Clicked",groupPosition+"Clicked"+childpostion+ " Check "+  modelRow.get(childpostion).getHotel_Name());
        }
    }
}