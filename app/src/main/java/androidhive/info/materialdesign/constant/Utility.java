package androidhive.info.materialdesign.constant;

/**
 * Created by VNK on 6/29/2015.
 */
    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.view.View;
    import android.view.ViewGroup;
    import android.view.View.MeasureSpec;
    import android.widget.ListAdapter;
    import android.widget.ListView;

    import java.text.DateFormat;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.Date;

public class Utility {
        public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }

        public void Custom_alert(String msg) {
           /* AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    getActivity());

            // Setting Dialog Title
            alertDialog2.setTitle("Exenta");

            // Setting Dialog Message
            alertDialog2.setMessage(msg);

            // Setting Icon to Dialog
//		alertDialog2.setIcon(R.drawable.ic_launcher);

            // Setting Positive "Yes" Btn
            alertDialog2.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog

                            Intent intent = new Intent(getActivity(), Leave_Request.class);
                            startActivity(intent);

                        }
                    });
            // Setting Negative "NO" Btn
	*//*	alertDialog2.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Write your code here to execute after dialog
						Toast.makeText(getActivity(), "Cancelled",
								Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}
				});*//*

            // Showing Alert Dialog
            alertDialog2.show();*/
        }

    public static String addDays(String baseDate, int daysToAdd, String dateformatonward , String returnformat) {

        String finalDate=null;
        DateFormat df = new SimpleDateFormat(dateformatonward);
        try {
           Date d = df.parse(baseDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
            //Format "yyyy-MM-dd"
            DateFormat date_format = new SimpleDateFormat(returnformat);
            finalDate = date_format.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalDate;
    }


    }

