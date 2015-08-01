package androidhive.info.itraveller.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

import androidhive.info.itraveller.R;
import androidhive.info.itraveller.adapter.FlightDomesticOnwardAdapter;
import androidhive.info.itraveller.adapter.FlightDomesticReturnAdapter;
import androidhive.info.itraveller.constant.CustomLoading;
import androidhive.info.itraveller.model.FlightModel;
import androidhive.info.itraveller.model.ReturnDomesticFlightModel;

/**
 * Created by VNK on 7/28/2015.
 */
public class FlightReturnDomestic extends Fragment {


    public static FlightDomesticReturnAdapter adapter;
    ListView listview;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.flights_domestic_list, container, false);

            listview = (ListView) rootView.findViewById(R.id.flights_listview);
            adapter = new FlightDomesticReturnAdapter(getActivity(), FlightDomesticActivity.return_domestic_model);
            listview.setAdapter(adapter);
            return rootView;
        }

}
