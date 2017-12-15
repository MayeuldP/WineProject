package com.example.mayeul.wineproject.Home.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mayeul.wineproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAYEUL on 15/12/2017.
 */

public class CardView extends BaseAdapter {

    //private List<String> data;
    private Context context;
    //private int number_elem;
    private List<ArrayList<String> > data;

    public CardView(List<ArrayList<String>> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // normally use a viewholder
            v = inflater.inflate(R.layout.card_view, parent, false);
        }
        Typeface mtypeFace = Typeface.createFromAsset(context.getAssets(),
                "fonts/GOTHIC.TTF");
        /*On masque la map, car elle est*/
        if (position > 0)
            v.findViewById(R.id.map).setVisibility(View.GONE);
        ((TextView) v.findViewById(R.id.textView)).setTypeface(mtypeFace);
        ((TextView) v.findViewById(R.id.textView2)).setTypeface(mtypeFace);

        ((TextView) v.findViewById(R.id.textView)).setText(data.get(position).get(0));
        ((TextView) v.findViewById(R.id.textView2)).setText(data.get(position).get(1));

        return v;
    }
}
