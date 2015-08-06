package androidhive.info.itraveller.constant;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidhive.info.itraveller.R;
import androidhive.info.itraveller.activity.SummaryActivity;

public class CustomLoading {
	static Dialog dialog;
	Context context;
	public static void LoadingScreen(Context context, Boolean check) {
		SharedPreferences prefsData = context.getSharedPreferences("Itinerary", context.MODE_PRIVATE);
		String F_bit = ""+prefsData.getString("FlightBit",null);
		int flightBit = Integer.parseInt(""+F_bit);
		context = context;
		// create a Dialog component
		dialog = new Dialog(context);
		// tell the Dialog to use the dialog.xml as it's layout description
		dialog.setTitle("Flight Details");
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_dialog_box);
		dialog.findViewById(R.id.skip);
		Button skip_btn = (Button) dialog.findViewById(R.id.skip);
		final Context finalContext = context;
		skip_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
				Intent intent = new Intent(finalContext.getApplicationContext(), SummaryActivity.class);
				finalContext.startActivity(intent);
				//finish();
			}
		});
		dialog.setCanceledOnTouchOutside(check);
		dialog.show();
	}
	public static void LoadingHide() {
		// create a Dialog componen
		dialog.dismiss();
	}
}
