package androidhive.info.materialdesign.activity;

/**
 * Created by VNK on 6/11/2015.
 */

    import android.app.Activity;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.DragEvent;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.MotionEvent;
    import android.view.View;
    import android.widget.LinearLayout;
    import android.widget.Toast;
    import android.widget.Toolbar;

    import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

    import androidhive.info.materialdesign.R;


public class TestActionBar extends Activity {
/* When using Appcombat support library
   you need to extend Main Activity to ActionBarActivity.*/

        private Toolbar toolbar; // Declaring the Toolbar Object

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.filter);

           // toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object
           // setSupportActionBar(toolbar);
           // Setting toolbar as the ActionBar with setSupportActionBar() call
            // Setup the new range seek bar
// Setup the new range seek bar
            RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<Integer>(this);
            // Set the range
            rangeSeekBar.setRangeValues(12000, 110000);
            rangeSeekBar.setSelectedMinValue(12000);
            rangeSeekBar.setSelectedMaxValue(110000);
            rangeSeekBar.setNotifyWhileDragging(true);
            // Add to layout
            LinearLayout layout = (LinearLayout) findViewById(R.id.seekbar_placeholder);
            layout.addView(rangeSeekBar);

            rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
                @Override
                public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                    Log.i("SeekBar", "Min :" + minValue + " Max :" + maxValue);
                }
            });

        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            //getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
           /* if (id == R.id.action_settings) {
                return true;
            }*/

            return super.onOptionsItemSelected(item);
        }
    }

