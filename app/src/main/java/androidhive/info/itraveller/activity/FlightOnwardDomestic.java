package androidhive.info.itraveller.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidhive.info.itraveller.R;
import androidhive.info.itraveller.adapter.FlightDomesticOnwardAdapter;

/**
 * Created by VNK on 7/28/2015.
 */
public class FlightOnwardDomestic extends Fragment {


    public static FlightDomesticOnwardAdapter adapter;
    ListView listview;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.flights_domestic_list, container, false);
            //CustomLoading.LoadingScreen(getActivity(), false);
            listview = (ListView) rootView.findViewById(R.id.flights_listview);
            adapter = new FlightDomesticOnwardAdapter(getActivity(), FlightDomesticActivity.onward_domestic_model);
            listview.setAdapter(adapter);
            return rootView;
        }


}
