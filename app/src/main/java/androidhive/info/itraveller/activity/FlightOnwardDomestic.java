package androidhive.info.itraveller.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import androidhive.info.itraveller.R;
import androidhive.info.itraveller.adapter.FlightDomesticOnwardAdapter;
import androidhive.info.itraveller.model.OnwardDomesticFlightModel;

/**
 * Created by VNK on 7/28/2015.
 */
public class FlightOnwardDomestic extends Fragment {


    public static FlightDomesticOnwardAdapter adapter = null;
    List<OnwardDomesticFlightModel> onward_domestic_model;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.flights_domestic_list, container, false);
            //CustomLoading.LoadingScreen(getActivity(), false);
            ListView listview = (ListView) rootView.findViewById(R.id.flights_listview);
            adapter = new FlightDomesticOnwardAdapter(getActivity(), onward_domestic_model);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            return rootView;
        }

    public void setOnwardModel(List<OnwardDomesticFlightModel> onward_domestic_model) {
        this.onward_domestic_model = onward_domestic_model;
    }
}
