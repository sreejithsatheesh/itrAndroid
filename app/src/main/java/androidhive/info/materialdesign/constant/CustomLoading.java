package androidhive.info.materialdesign.constant;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidhive.info.materialdesign.R;

public class CustomLoading {
	static Dialog dialog;
	public static void LoadingScreen(Context context, Boolean check) {

		// create a Dialog component
		dialog = new Dialog(context);
		// tell the Dialog to use the dialog.xml as it's layout description
		dialog.setTitle("Flight Details");
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_dialog_box);
		dialog.findViewById(R.id.skip);
		Button skip_btn = (Button) dialog.findViewById(R.id.skip);
		skip_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
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
