package androidhive.info.itraveller.adapter;

/**
 * Created by VNK on 7/28/2015.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import androidhive.info.itraveller.activity.FlightOnwardDomestic;
import androidhive.info.itraveller.activity.FlightReturnDomestic;

public class FlightViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public FlightViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            FlightOnwardDomestic flight_onward = new FlightOnwardDomestic();
            return flight_onward;
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            FlightReturnDomestic flight_return = new FlightReturnDomestic();
            return flight_return;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip
    public int getCount() {
        return NumbOfTabs;
    }
}