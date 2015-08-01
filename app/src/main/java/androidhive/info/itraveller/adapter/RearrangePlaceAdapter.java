package androidhive.info.itraveller.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import androidhive.info.itraveller.R;
import androidhive.info.itraveller.model.RearrangePlaceModel;
import androidhive.info.itraveller.volley.AppController;

public class RearrangePlaceAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<RearrangePlaceModel> RearrangeItems;
    private  int _screen_height;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public RearrangePlaceAdapter(Activity activity, List<RearrangePlaceModel> rearrangeItems) {
        this.activity = activity;
        this.RearrangeItems = rearrangeItems;
    }

    /*public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0) {
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }*/
    @Override
    public int getCount() {
        return RearrangeItems.size();
    }
 
    @Override
    public Object getItem(int location) {
        return RearrangeItems.get(location);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_item_handle_left, null);
 
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView title = (TextView) convertView.findViewById(R.id.destination_name);
        final TextView days = (TextView) convertView.findViewById(R.id.days);
        Button btn_plus =(Button) convertView.findViewById(R.id.plus_btn);
        Button btn_minus =(Button) convertView.findViewById(R.id.minus_btn);

        // getting data for the row
        final RearrangePlaceModel m = RearrangeItems.get(position);
        //setListViewHeightBasedOnChildren(DragAndSort.listview);
        // title
        title.setText(m.getPlace());
        /*if(m.getNights().toString().equalsIgnoreCase("0"))
            days.setText("1");
        else*/
        days.setText(m.getNights());

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = Integer.parseInt(days.getText().toString()) + 1;
                days.setText(""+x);
                m.setNights(""+x);
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int x = Integer.parseInt(days.getText().toString()) - 1;

                if(x>0)
                {
                days.setText(""+x);
                m.setNights(""+x);}
            }
        });

        return convertView;
    }

    public void remove(RearrangePlaceModel item) {
        RearrangeItems.remove(item);
        notifyDataSetChanged();
    }

    public void insert(RearrangePlaceModel item, int position) {
        RearrangeItems.add(position, item);
        notifyDataSetChanged();
    }

    public List<RearrangePlaceModel> getList(){
        return RearrangeItems;
    }

}